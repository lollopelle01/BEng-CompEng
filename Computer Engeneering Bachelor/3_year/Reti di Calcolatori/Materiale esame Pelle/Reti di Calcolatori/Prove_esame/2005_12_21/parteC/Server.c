// pellegrino lorenzo 0000971455 compito1
#include <dirent.h>
#include <errno.h>
#include <fcntl.h>
#include <netdb.h>
#include <netinet/in.h>
#include <regex.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/select.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#define LINE_LENGTH 128
#define WORD_LENGTH 128
#define max(a, b)   ((a) > (b) ? (a) : (b))
#define N 5
#define K 3
#define LENGHT_NAME 30

/********************************************************/
void gestore(int signo) {
    int stato;
    printf("esecuzione gestore di SIGCHLD\n");
    wait(&stato);
}
/********************************************************/

typedef struct{
    char nomeStanza[LENGHT_NAME];
    char stato[2+1];
    char utenti[K][LENGHT_NAME];
} Stanza;

int main(int argc, char **argv) {
    struct sockaddr_in cliaddr, servaddr;
    struct hostent    *hostTcp, *hostUdp;
    int                port, listen_sd, conn_sd, udp_sd, nread, maxfdp1, len;
    const int          on = 1;
    fd_set             rset;
    char               read_char, err[128], word_buffer[WORD_LENGTH];
    
    // per servizi
    char nomeStanza[LENGHT_NAME], servizio, risp, buff[ LENGHT_NAME + 1 + (2+1) + 1 + K*(LENGHT_NAME+1)];
    int esito, i, j, trovati;
    Stanza stanze[N];

    /* CONTROLLO ARGOMENTI ---------------------------------- */
    if (argc != 2) {
        printf("Error: %s port \n", argv[0]);
        exit(1);
    } else {
        nread = 0;
        while (argv[1][nread] != '\0') {
            if ((argv[1][nread] < '0') || (argv[1][nread] > '9')) {
                printf("Secondo argomento non intero\n");
                exit(2);
            }
            nread++;
        }
        port = atoi(argv[1]);
        if (port < 1024 || port > 65535) {
            printf("Porta scorretta...");
            exit(2);
        }
    }

    /* INIZIALIZZAZIONE STRUTTURA DATI*/
    for(i=0; i<N; i++){
        strcpy(stanze[i].nomeStanza, "L");
        strcpy(stanze[i].stato, "L");
        for(j=0; j<K; j++){
            strcpy(stanze[i].utenti[j], "L");
        }
    }
    printf("Tabella inizializzata\n");
    // per debug
    printf("Tabella:\n");
    for(i=0; i<N; i++){
        printf("%s\t",stanze[i].nomeStanza);
        printf("%s\t",stanze[i].stato);
        for(j=0; j<K; j++){
            printf("%s\t", stanze[i].utenti[j]);
        }
        printf("\n");
    }

    /* INIZIALIZZAZIONE INDIRIZZO SERVER ----------------------------------------- */
    memset((char *)&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family      = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY;
    servaddr.sin_port        = htons(port);

    /* CREAZIONE E SETTAGGI SOCKET TCP --------------------------------------- */
    listen_sd = socket(AF_INET, SOCK_STREAM, 0);
    if (listen_sd < 0) {
        perror("creazione socket ");
        exit(3);
    }
    printf("Server: creata la socket d'ascolto per le richieste di ordinamento, fd=%d\n",
           listen_sd);

    if (setsockopt(listen_sd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0) {
        perror("set opzioni socket d'ascolto");
        exit(3);
    }
    printf("Server: set opzioni socket d'ascolto ok\n");

    if (bind(listen_sd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0) {
        perror("bind socket d'ascolto");
        exit(3);
    }
    printf("Server: bind socket d'ascolto ok\n");

    if (listen(listen_sd, 5) < 0) {
        perror("listen");
        exit(3);
    }
    printf("Server: listen ok\n");

    /* CREAZIONE E SETTAGGI SOCKET UDP --------------------------------------- */
    udp_sd = socket(AF_INET, SOCK_DGRAM, 0);
    if (udp_sd < 0) {
        perror("apertura socket UDP");
        exit(4);
    }
    printf("Creata la socket UDP, fd=%d\n", udp_sd);

    if (setsockopt(udp_sd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0) {
        perror("set opzioni socket UDP");
        exit(4);
    }
    printf("Set opzioni socket UDP ok\n");

    if (bind(udp_sd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0) {
        perror("bind socket UDP");
        exit(4);
    }
    printf("Bind socket UDP ok\n");

    signal(SIGCHLD, gestore);

    /* PULIZIA E SETTAGGIO MASCHERA DEI FILE DESCRIPTOR ------------------------- */
    FD_ZERO(&rset);
    maxfdp1 = max(listen_sd, udp_sd) + 1;

    /* CICLO DI RICEZIONE RICHIESTE --------------------------------------------- */
    for (;;) {
        FD_SET(listen_sd, &rset);
        FD_SET(udp_sd, &rset);

        if ((nread = select(maxfdp1, &rset, NULL, NULL, NULL)) < 0) {
            if (errno == EINTR) {
                continue;
            } else {
                perror("select");
                exit(5);
            }
        }
        /* GESTIONE RICHIESTE UDP  ----------------------------- */
        if (FD_ISSET(udp_sd, &rset)) {
            printf("Ricevuta richiesta di UDP: sospensione operatività\n");
            len = sizeof(struct sockaddr_in);


            if (recvfrom(udp_sd, nomeStanza, sizeof(nomeStanza), 0, (struct sockaddr *)&cliaddr, &len) < 0) {
                perror("recvfrom ");
                continue;
            }

            printf("Operazione richiesta sul stanza: %s ", nomeStanza);

            hostUdp = gethostbyaddr((char *)&cliaddr.sin_addr, sizeof(cliaddr.sin_addr), AF_INET);
            if (hostUdp == NULL) {
                printf("client host information not found\n");
            } else {
                printf("Operazione richiesta da: %s %i\n", hostUdp->h_name,
                       (unsigned)ntohs(cliaddr.sin_port));
            }

            esito = -1;
            for(i=0; i<N && esito==-1; i++){
                if(strcmp(nomeStanza, stanze[i].nomeStanza)==0){
                    stanze[i].stato[1] = stanze[i].stato[0];
                    stanze[i].stato[2] = '\0';
                    stanze[i].stato[0] = 'S'; 
                    esito = 0;
                }
            }
            
                            // per debug
                            printf("Tabella:\n");
                            for(i=0; i<N; i++){
                                printf("%s\t",stanze[i].nomeStanza);
                                printf("%s\t",stanze[i].stato);
                                for(j=0; j<K; j++){
                                    printf("%s\t", stanze[i].utenti[j]);
                                }
                                printf("\n");
                            }

            // Send result to the client
            if (sendto(udp_sd, &esito, sizeof(int), 0, (struct sockaddr *)&cliaddr, len) < 0) {
                perror("sendto ");
                continue;
            }
        }
        /* GESTIONE RICHIESTE TCP  ----------------------------- */
        if (FD_ISSET(listen_sd, &rset)) {
            printf("Ricevuta richiesta TCP: visualizzazione stato tabella\n");
            len = sizeof(cliaddr);
            if ((conn_sd = accept(listen_sd, (struct sockaddr *)&cliaddr, &len)) < 0) {
                if (errno == EINTR) {
                    perror("Forzo la continuazione della accept");
                    continue;
                } else
                    exit(6);
            }
            if (fork() == 0) {
                close(listen_sd);
                hostTcp = gethostbyaddr((char *)&cliaddr.sin_addr, sizeof(cliaddr.sin_addr), AF_INET);
                if (hostTcp == NULL) {
                    printf("client host information not found\n");
                    close(conn_sd);
                    exit(6);
                } else
                    printf("Server (figlio): host client e' %s \n", hostTcp->h_name);

                // Leggo la richiesta del client
                while ((nread = read(conn_sd, &servizio, 1)) > 0) {
                    printf("Server (figlio): richiesta visualizzazione = %s \n", servizio);
                    if(servizio == 'S'){ // inizio server
                        if (stanze != NULL) { 
                            risp = 'S';
                            write(conn_sd, &risp, 1);

                             // per debug
                            printf("Tabella:\n");
                            for(i=0; i<N; i++){
                                printf("%s\t",stanze[i].nomeStanza);
                                printf("%s\t",stanze[i].stato);
                                for(j=0; j<K; j++){
                                    printf("%s\t", stanze[i].utenti[j]);
                                }
                                printf("\n");
                            }

                            for(i=0; i<N; i++){
                                strcpy(buff, stanze[i].nomeStanza);
                                strcat(buff, "\t");
                                strcat(buff, stanze[i].stato);
                                strcat(buff, "\t");
                                for(j=0; j<K; j++){
                                    strcat(buff, stanze[i].utenti[j]);
                                    strcat(buff, "\t");
                                }
                                strcat(buff, "\n");
                                write(conn_sd, buff, sizeof(buff));
                            }
                        } 
                        else
                        { // err 
                            risp = 'N';
                            printf("Tabella ha avuto problemi \n");
                            write(conn_sd, &risp, sizeof(char));
                        }
                    }
                } // while read req

                // Libero risorse
                printf("Figlio TCP terminato, libero risorse e chiudo. \n");
                close(conn_sd);
                exit(0);
            }               // if fork
            close(conn_sd); // padre
        }                   // if TCP
    }                       // for
} // main