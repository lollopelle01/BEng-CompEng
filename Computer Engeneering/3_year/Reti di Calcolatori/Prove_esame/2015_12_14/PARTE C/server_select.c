/**
 * server_select.c
 *  Il server discrimina due servizi con la select:
 *    + elimina le occorrenze di una parola in un file (UDP)
 *    + restituisce i nomi dei file in un direttorio di secondo livello
 **/

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
#include <sys/stat.h>
#include <unistd.h>

#define LINE_LENGTH 256

#define N 4
#define max(a, b)   ((a) > (b) ? (a) : (b))

/********************************************************/
void gestore(int signo) {
    int stato;
    printf("esecuzione gestore di SIGCHLD\n");
    wait(&stato);
}
/********************************************************/
typedef struct {
    char id[LINE_LENGTH];
    char num_persone[5];
    char tipo_prenotazione[LINE_LENGTH];
    char tipo_veicolo[LINE_LENGTH];
    char targa[LINE_LENGTH];
    char immagine[LINE_LENGTH];
} Prenotazione;

typedef struct{
    char identificatore[LINE_LENGTH];
    char persone[5];
}ReqUdp;

int main(int argc, char **argv) {
    struct sockaddr_in cliaddr, servaddr;
    struct hostent    *hostTcp, *hostUdp;
    int                port, listen_sd, conn_sd, udp_sd, nread, maxfdp1, len;
    const int          on = 1;
    fd_set             rset;
    char               err[128];
    DIR           *dir1;
    struct dirent *dd1;

    ReqUdp req;
    Prenotazione       prenotazione[N];
    int esito;
    char tipo_pren[LINE_LENGTH], fileName[LINE_LENGTH], newDir[LINE_LENGTH]; 

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
    for(int i = 0; i < N; i++){
        strcpy(prenotazione[i].id, "L");
        strcpy(prenotazione[i].num_persone, "L");
        strcpy(prenotazione[i].tipo_prenotazione, "L");
        strcpy(prenotazione[i].tipo_veicolo, "L");
        strcpy(prenotazione[i].targa, "L");
        strcpy(prenotazione[i].immagine, "L");
    }
    strcpy(prenotazione[0].id, "AAFG89");
    strcpy(prenotazione[0].num_persone, "3");
    strcpy(prenotazione[0].tipo_prenotazione, "mezza piazzola");
    strcpy(prenotazione[0].tipo_veicolo, "niente");
    strcpy(prenotazione[0].targa, "L");
    strcpy(prenotazione[0].immagine, "piazz.png");

    strcpy(prenotazione[1].id, "RTFO54");
    strcpy(prenotazione[1].num_persone, "2");
    strcpy(prenotazione[1].tipo_prenotazione, "piazzola deluxe");
    strcpy(prenotazione[1].tipo_veicolo, "camper");
    strcpy(prenotazione[1].targa, "AA567AA");
    strcpy(prenotazione[1].immagine, "piazz_deluxe.png");

    strcpy(prenotazione[2].id, "RFTW76");
    strcpy(prenotazione[2].num_persone, "4");
    strcpy(prenotazione[2].tipo_prenotazione, "piazzola");
    strcpy(prenotazione[2].tipo_veicolo, "camper");
    strcpy(prenotazione[2].targa, "TH789YU");
    strcpy(prenotazione[2].immagine, "mezz_piazz.png");

    strcpy(prenotazione[3].id, "MNBJ46");
    strcpy(prenotazione[3].num_persone, "3");
    strcpy(prenotazione[3].tipo_prenotazione, "piazzola");
    strcpy(prenotazione[3].tipo_veicolo, "auto");
    strcpy(prenotazione[3].targa, "RT324SX");
    strcpy(prenotazione[3].immagine, "piazz2.png");

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
            printf("Ricevuta richiesta di UDP: aggiornamento del numero di persone\n");
            len = sizeof(struct sockaddr_in);

            if (recvfrom(udp_sd, &req, sizeof(req), 0, (struct sockaddr *)&cliaddr, &len) < 0) {
                perror("recvfrom ");
                continue;
            }

            printf("Operazione richiesta sulla prenotazione: %s, le persone diventano: %s\n", req.identificatore, req.persone);

            hostUdp = gethostbyaddr((char *)&cliaddr.sin_addr, sizeof(cliaddr.sin_addr), AF_INET);
            if (hostUdp == NULL) {
                printf("client host information not found\n");
            } else {
                printf("Operazione richiesta da: %s %i\n", hostUdp->h_name, (unsigned)ntohs(cliaddr.sin_port));
            }

            esito = -1;
            for(int i = 0; i < N; i++){
                if(strcmp(prenotazione[i].id, req.identificatore) == 0){
                    strcpy(prenotazione[i].num_persone, req.persone);
                    esito = 0;
                }
            }
            
            printf("Ora le persone per la prenotazione %s, sono %s\n", req.identificatore, req.persone);
            printf("SERVER: libero e riavvio.\n");

            // Send result to the client
            if (sendto(udp_sd, &esito, sizeof(int), 0, (struct sockaddr *)&cliaddr, len) < 0) {
                perror("sendto ");
                continue;
            }
        }
        /* GESTIONE RICHIESTE TCP  ----------------------------- */
        if (FD_ISSET(listen_sd, &rset)) {
            printf("Ricevuta richiesta TCP: scarico immagini relative al tipo di prenotazione\n");
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
                

                int num_file = 0, fd;
                char buff;
                struct stat stat;
                

                // Leggo la richiesta del client
                while ((nread = read(conn_sd, tipo_pren, sizeof(tipo_pren))) > 0) {
                    printf("Server (figlio): tipo di prenotazione richiesto: %s\n", tipo_pren);

                    for(int i = 0; i < N; i++){
                        if(strcmp(prenotazione[i].tipo_prenotazione, tipo_pren)==0){
                            num_file++;
                        }
                    }
                    write(conn_sd, &num_file, sizeof(num_file));
                    for(int i=0; i<N; i++){
                            if(strcmp(prenotazione[i].tipo_prenotazione, tipo_pren)==0){

                                fd = open(prenotazione[i].immagine, O_RDONLY);

                                fstat(fd, &stat);
                                long byteNum = stat.st_size;
                                
                                write(conn_sd, prenotazione[i].immagine, sizeof(prenotazione[i].immagine)); // invio nome file
                                write(conn_sd, &byteNum, sizeof(long)); // invio dimensione

                                printf("Scarico il file '%s' --> lungo %lu byes\n", prenotazione[i].immagine, byteNum);
                                for(long i=0; i<byteNum; i++){
                                    read(fd, &buff, 1);
                                    write(conn_sd, &buff, 1);
                                }
                                close(fd);
                            }
                    }

                }
                // Libero risorse
                printf("Figlio TCP terminato, libero risorse e chiudo. \n");
                close(conn_sd);
                exit(0);
            }               // if fork
            close(conn_sd); // padre
        }                   // if TCP
    }                       // for
} // main