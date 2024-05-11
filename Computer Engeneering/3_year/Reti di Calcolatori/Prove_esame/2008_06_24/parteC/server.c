// pellegrino lorenzo 0000971455

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

#define LINE_LENGTH 256
#define WORD_LENGTH 30
#define max(a, b)   ((a) > (b) ? (a) : (b))

/********************************************************/
void gestore(int signo) {
    int stato;
    printf("esecuzione gestore di SIGCHLD\n");
    wait(&stato);
}
/********************************************************/
typedef struct {
    char fileName[LINE_LENGTH];
    char parola[WORD_LENGTH];
} ReqUDP;

int main(int argc, char **argv) {
    struct sockaddr_in cliaddr, servaddr;
    struct hostent    *hostTcp, *hostUdp;
    int                port, listen_sd, conn_sd, udp_sd, nread, maxfdp1, len;
    const int          on = 1;
    fd_set             rset;
    ReqUDP             req;

    // per servizi
    int fd, ris=0, i=0, j=0, countParole=0;
    char parolaBuff[WORD_LENGTH], c;
    char lineaBuff[LINE_LENGTH], fileName[LINE_LENGTH], parola[WORD_LENGTH];


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
            printf("Ricevuta richiesta di UDP: numero occorrenze di linee con parola in file \n");
            len = sizeof(struct sockaddr_in);

            if (recvfrom(udp_sd, &req, sizeof(req), 0, (struct sockaddr *)&cliaddr, &len) < 0) {
                perror("recvfrom ");
                continue;
            }

            hostUdp = gethostbyaddr((char *)&cliaddr.sin_addr, sizeof(cliaddr.sin_addr), AF_INET);
            if (hostUdp == NULL) {
                printf("client host information not found\n");
            } else {
                printf("Operazione richiesta da: %s %i\n", hostUdp->h_name,
                       (unsigned)ntohs(cliaddr.sin_port));
            }

            /*Gestione della richiesta*/
            i=0; 
            fd = open(req.fileName, O_RDONLY);
            if(fd < 0){ris = -1;}
            else{
                ris=0;
                while(read(fd, &c, 1) > 0){
                    if(c==' ' || c=='\n'){ // fine della parola
                        parolaBuff[i]='\0';
                        i=0;

                        if(strcmp(req.parola, parolaBuff)==0){countParole++;}
                        if(c=='\n'){ // fine della riga
                            if(countParole > 0){ris++;}
                            countParole=0;
                        }

                    }
                    else{ // è parola
                        parolaBuff[i]=c;
                        i++;
                    }
                }

                // fine parola e riga con EOF
                parolaBuff[i]='\0';
                if(strcmp(req.parola, parolaBuff)==0){countParole++;}
                if(countParole > 0){ris++;}

            }
            close(fd);
            

            // Send result to the client
            if (sendto(udp_sd, &ris, sizeof(int), 0, (struct sockaddr *)&cliaddr, len) < 0) {
                perror("sendto ");
                continue;
            }
        }
        /* GESTIONE RICHIESTE TCP  ----------------------------- */
        if (FD_ISSET(listen_sd, &rset)) {
            printf("Ricevuta richiesta TCP: trasferimento linee con parola da file\n");
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
                hostTcp =
                    gethostbyaddr((char *)&cliaddr.sin_addr, sizeof(cliaddr.sin_addr), AF_INET);
                if (hostTcp == NULL) {
                    printf("client host information not found\n");
                    close(conn_sd);
                    exit(6);
                } else
                    printf("Server (figlio): host client e' %s \n", hostTcp->h_name);

                // Leggo la richiesta del client
                while ((nread = read(conn_sd, fileName, sizeof(fileName))) > 0) {
                    printf("Richiesta: file-->%s\n", fileName);
                    read(conn_sd, parola, sizeof(parola));
                    printf("Richiesta: parola-->%s\n", parola);

                    /*Gestione richiesta*/
                    i=0; j=0;
                    fd = open(fileName, O_RDONLY);
                    if(fd < 0){strcpy(lineaBuff, "errore_lettura_file");write(conn_sd, lineaBuff, sizeof(lineaBuff));}
                    else{
                        while(read(fd, &c, 1) > 0){
                            if(c==' ' || c=='\n'){ // fine della parola
                                parolaBuff[i]='\0';
                                i=0;

                                lineaBuff[j]=c;
                                j++;

                                if(strcmp(parola, parolaBuff)==0){countParole++;}
                                if(c=='\n'){ // fine della riga
                                    if(countParole > 0){ // mando la linea
                                        lineaBuff[j]='\0';
                                        write(conn_sd, lineaBuff, sizeof(lineaBuff));
                                    }
                                    countParole=0;
                                    j=0;
                                }

                            }
                            else{ // è parola ed è linea
                                parolaBuff[i]=c;
                                i++;
                                lineaBuff[j]=c;
                                j++;
                            }
                        }

                        // fine parola e riga con EOF
                        parolaBuff[i]='\0';
                        lineaBuff[j]='\0';
                        if(strcmp(parola, parolaBuff)==0){countParole++;}
                        if(countParole > 0){write(conn_sd, lineaBuff, sizeof(lineaBuff));}

                        // finisco lo stream
                        strcpy(lineaBuff, "fine_stream_file");
                        write(conn_sd, lineaBuff, sizeof(lineaBuff));
                    }
                    close(fd);

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