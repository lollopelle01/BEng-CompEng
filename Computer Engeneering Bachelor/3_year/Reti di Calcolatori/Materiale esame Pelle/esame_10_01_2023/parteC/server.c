// pellegrino lorenzo 0000971455 prova2

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
#include <sys/stat.h>
#include <unistd.h>

#define LINE_LENGTH 128
#define WORD_LENGTH 128
#define max(a, b)   ((a) > (b) ? (a) : (b))

/********************************************************/
int isTxt(char* fileName){ //ciao.txt --> c0 i1 a2 o3 .4 t5 x6 t7 \n8 --> strlen=8 e mi interesso a 4567 --> -4 -3 -2 -1
    if( fileName[strlen(fileName)-4]=='.' &&
        fileName[strlen(fileName)-3]=='t' &&
        fileName[strlen(fileName)-2]=='x' &&
        fileName[strlen(fileName)-1]=='t' )
    {return 1;}

    else{return 0;}
}

/********************************************************/
void gestore(int signo) {
    int stato;
    printf("esecuzione gestore di SIGCHLD\n");
    wait(&stato);
}
/********************************************************/
typedef struct {
    char carattere;
    int numOcc;
} ReqUDP;

int main(int argc, char **argv) {
    struct sockaddr_in cliaddr, servaddr;
    struct hostent    *hostTcp, *hostUdp;
    int                port, listen_sd, conn_sd, udp_sd, nread, maxfdp1, len;
    const int          on = 1;
    fd_set             rset;
    ReqUDP             req;
    int                fd_sorg_udp, fd_temp_udp, count_letters;
    char               read_char, err[128], word_buffer[WORD_LENGTH];
    char charBuff[2], newDir[LINE_LENGTH], fileNameTemp[LINE_LENGTH],
        dir[LINE_LENGTH];
    DIR           *dir1, *dir2, *dir3;
    struct dirent *dd1, *dd2;

    // datagram
    struct dirent *dd;
    DIR *dir4;
    int fd, righe = 0, occorrenze, i, primo, primo_char;
    char buff[256], c;

    // stream
    struct stat stat;
    char fileName[256], fine[256]; 

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
            printf("Ricevuta richiesta di UDP: conteggio delle linee\n");
            len = sizeof(struct sockaddr_in);

            if (recvfrom(udp_sd, &req, sizeof(req), 0, (struct sockaddr *)&cliaddr, &len) < 0) {
                perror("recvfrom ");
                continue;
            }

            printf("Operazione richiesta per il carattere %c ripetuto %d volte\n", req.carattere, req.numOcc);

            hostUdp = gethostbyaddr((char *)&cliaddr.sin_addr, sizeof(cliaddr.sin_addr), AF_INET);
            if (hostUdp == NULL) {
                printf("client host information not found\n");
            } else {
                printf("Operazione richiesta da: %s %i\n", hostUdp->h_name,
                       (unsigned)ntohs(cliaddr.sin_port));
            }

            /* Apro il direttorio corrente*/
            dir4 = opendir(".");
            while((dd = readdir(dir4))!=NULL){
                if(dd->d_type == DT_REG && isTxt(dd->d_name)){ 
                    buff[0] = '\0';
                    strcat(buff, ".");
                    strcat(buff, "/");
                    strcat(buff, dd->d_name);

                    fd = open(buff, O_RDONLY);
                    primo=1;
                    while(read(fd, &c, 1) > 0){
                        if(primo==1){
                            primo_char=(int) c;
                            primo=0;
                        }
                        if(c==req.carattere) {occorrenze++;}
                        if(c=='\n'){
                            if(occorrenze == req.numOcc && primo_char>=97 && primo_char<=122){righe++;}
                            occorrenze = 0;
                            primo=1;
                        }
                    }
                }
            }
            righe = 0;
            closedir(dir4);

            // Send result to the client
            if (sendto(udp_sd, &righe, sizeof(int), 0, (struct sockaddr *)&cliaddr, len) < 0) {
                perror("sendto ");
                continue;
            }
        }
        /* GESTIONE RICHIESTE TCP  ----------------------------- */
        if (FD_ISSET(listen_sd, &rset)) {
            printf("Ricevuta richiesta TCP: file del direttorio e sottodirettorio\n");
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
                strcpy(fine, "fine");
                while ((nread = read(conn_sd, dir, sizeof(dir))) > 0) {
                    printf("Server (figlio): direttorio richiesto: %s\n", dir);

                    char risp;
                    if ((dir1 = opendir(dir)) != NULL) { // direttorio presente
                        risp = 'S';
                        printf("Invio risposta affermativa al client\n");
                        write(conn_sd, &risp, sizeof(char));
                        while ((dd1 = readdir(dir1)) != NULL) {
                            if (strcmp(dd1->d_name, ".") != 0 && strcmp(dd1->d_name, "..") != 0) {
                                // build new path
                                newDir[0] = '\0';
                                strcat(newDir, dir);
                                strcat(newDir, "/");
                                strcat(newDir, dd1->d_name);
                                // printf("Test apertura dir su %s\n", newDir);
                                if ((dir2 = opendir(newDir)) != NULL) { // dir sec livello
                                    // printf("Ciclo dir sec livello %s\n", newDir);
                                    while ((dd2 = readdir(dir2)) != NULL) {
                                        if (strcmp(dd2->d_name, ".") != 0 && strcmp(dd2->d_name, "..") != 0) {
                                            // build new path
                                            strcat(newDir, "/");
                                            strcat(newDir, dd2->d_name);
                                            printf("Test apertura dir su %s\n", newDir);

                                            if(dd2->d_type==DT_REG && isTxt(dd2->d_name)){ //se è un file di testo
                                                fd = open(newDir, O_RDONLY);
                                                strcpy(fileName, dd2->d_name);
                                                write(conn_sd, fileName, sizeof(fileName));
                                                fstat(fd, &stat);
                                                write(conn_sd, &stat.st_size, sizeof(int));
                                                printf("Invio %s di %d byte...\n", fileName, stat.st_size);
                                                while(read(fd, &c, 1) > 0){
                                                    write(conn_sd, &c, 1);
                                                }
                                            }
                                        }     // if not . and .. 2 livello
                                    }         // while in 2* livello
                                } // if dir 2 livello
                                else if(isTxt(dd1->d_name)){ //è un file di testo 
                                    fd = open(newDir, O_RDONLY);
                                    strcpy(fileName, dd1->d_name);
                                    write(conn_sd, fileName, sizeof(fileName)); //mando nome
                                    fstat(fd, &stat);
                                    write(conn_sd, &stat.st_size, sizeof(int)); //mando dimensione
                                    printf("Invio %s di %d byte...\n", fileName, stat.st_size);
                                    while(read(fd, &c, 1) > 0){ //mando file
                                        write(conn_sd, &c, 1);
                                    }
                                }
                            }     // if not . and .. 1 livello
                        }         // while frst livello
                        write(conn_sd, fine, sizeof(fine));
                    }             // if open dir 1 livello
                    else
                    { // err apertura dir
                        risp = 'N';
                        printf("Invio risposta negativa al client per dir %s \n", dir);
                        write(conn_sd, &risp, sizeof(char));
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