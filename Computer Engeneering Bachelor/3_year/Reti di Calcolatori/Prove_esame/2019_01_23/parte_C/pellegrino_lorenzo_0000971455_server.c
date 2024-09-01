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
typedef char nome[13]; // 13 è la dimensione massima di una stringa

//Inizializzazione e ----------------------------------------------------------------
void stampa(int N, nome tabella[N][4]){
    printf("Tabella:\n");
    for(int i=0; i<N; i++){
        printf("%d) /----------------------------------------------------/\n", i+1);
        printf("Targa: %s\n", tabella[i][0]);
        printf("Patente: %s\n", tabella[i][1]);
        printf("Tipo: %s\n", tabella[i][2]);
        printf("Folder: %s\n", tabella[i][3]);
    }
}

/********************************************************/
void gestore(int signo) {
    int stato;
    printf("esecuzione gestore di SIGCHLD\n");
    wait(&stato);
}
/********************************************************/
typedef struct {
    nome targa[7+1];
    nome newPatente[5+1];
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
    char charBuff[2], newDir[LINE_LENGTH], fileNameTemp[LINE_LENGTH], fileName[LINE_LENGTH],
        dir[LINE_LENGTH];
    DIR           *dir1;
    struct dirent *dd1;

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

    int N = 5;
    nome tabella[N][4];

    /* INIZIALIZZAZIONE TABELLA ------------------------------------------------------*/
    for(int i=0; i<N; i++){
        strcpy(tabella[i][0], "L");
        strcpy(tabella[i][1], "0");
        strcpy(tabella[i][2], "L");
        strcpy(tabella[i][3], "L");
    }

    nome    targhe[] = {"AN745NL", "FE457GF", "NU547PL", "LR897AH", "MD506DW"},
            patenti[] = {"00001", "10001", "10101", "01010", "01110"},
            tipi[] = {"auto", "camper"};
    for(int i=0; i<N; i++){
        strcpy(tabella[i][0], targhe[i]);
        strcpy(tabella[i][1], patenti[i]);
        strcpy(tabella[i][2], tipi[i%2]);
        strcat(targhe[i], "_img");
        strcpy(tabella[i][3], targhe[i]);
    }
    printf("Inizializzazione tabella effettuata\n");
    stampa(N, tabella);

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

            printf("Ricevuta richiesta di UDP: eliminazione di una parola\n");
            len = sizeof(struct sockaddr_in);

            if (recvfrom(udp_sd, &req, sizeof(req), 0, (struct sockaddr *)&cliaddr, &len) < 0) {
                perror("recvfrom ");
                continue;
            }

            printf("Nuova patente di %s: %s\n", req.targa, req.newPatente);

            hostUdp = gethostbyaddr((char *)&cliaddr.sin_addr, sizeof(cliaddr.sin_addr), AF_INET);
            if (hostUdp == NULL) {
                printf("client host information not found\n");
            } else {
                printf("Operazione richiesta da: %s %i\n", hostUdp->h_name,
                       (unsigned)ntohs(cliaddr.sin_port));
            }

            int esito = -1;
            for(int i=0; i<N && esito==-1; i++){
                if(strcmp(req.targa, tabella[i][0])==0){
                    strcpy(tabella[i][1], req.newPatente);
                    esito = 0;
                }
            }
            //stampa(N, tabella);

            // Send result to the client
            if (sendto(udp_sd, &esito, sizeof(int), 0, (struct sockaddr *)&cliaddr, len) < 0) {
                perror("sendto ");
                continue;
            }
        }
        /* GESTIONE RICHIESTE TCP  ----------------------------- */
        if (FD_ISSET(listen_sd, &rset)) {
            printf("Ricevuta richiesta TCP: file del direttorio secondo livello\n");
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
                nome targa, folder;
                char fileName[256], buff;
                int trovato = -1, fd, num_file = 0;
                unsigned long dim_file;
                struct stat stat;
                while ((nread = read(conn_sd, targa, sizeof(targa))) > 0) {
                    printf("Server (figlio): targa richiesta: %s\n", targa);

                    for(int i=0; i<N && trovato==-1; i++){
                        if(strcmp(tabella[i][0], targa)==0){
                            strcpy(folder, tabella[i][3]);
                            trovato=0;
                        }
                    }

                    char risp;
                    if ((dir1 = opendir(folder)) != NULL) { // direttorio presente
                        risp = 'S';
                        printf("Invio risposta affermativa al client\n");
                        write(conn_sd, &risp, sizeof(char));
                        while ((dd1 = readdir(dir1)) != NULL) {
                            if (dd1->d_type==DT_REG) { //se sono file
                                num_file++;
                            }     // if not . and .. 1 livello
                        }         // while frst livello
                        closedir(dir1);
                    }
                    else
                    { // err apertura dir
                        risp = 'N';
                        printf("Invio risposta negativa al client per dir %s \n", folder);
                        write(conn_sd, &risp, sizeof(char));
                    }
                    write(conn_sd, &num_file, sizeof(int)); //mando numero di file
                    printf("Ho trovato %d immagini\n", num_file);

                    if ((dir1 = opendir(folder)) != NULL && num_file > 0) { // direttorio presente
                        while ((dd1 = readdir(dir1)) != NULL) {
                            if (dd1->d_type==DT_REG) { //se sono file
                                // build new path
                                newDir[0] = '\0';
                                strcat(newDir, folder);
                                strcat(newDir, "/");
                                strcat(newDir, dd1->d_name);

                                fd = open(newDir, O_RDONLY);
                                
                                fstat(fd, &stat);
                                long byteNum = stat.st_size;

                                strcpy(fileName, dd1->d_name);
                                write(conn_sd, fileName, sizeof(fileName)); // invio nome file
                                write(conn_sd, &byteNum, sizeof(long)); // invio dimensione
                                printf("Scarico il file %s --> %lu byes\n", fileName, byteNum);
                                for(long i=0; i<byteNum; i++){
                                    read(fd, &buff, 1);
                                    write(conn_sd, &buff, 1);
                                }
                                close(fd);
                            }     // if not . and .. 1 livello
                        }         // while frst livello
                    }             // if open dir 1 livello
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