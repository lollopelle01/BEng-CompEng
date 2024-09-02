
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
int isConsonante(char c){
    int ascii = (int) c, result = 0;
    if( (ascii<=90 && ascii>=65) || (ascii<=122 && ascii>=97) ){ //se è lettera
        if(c!='A' && c!='E' && c!='I' && c!='O' && c!='U'){ //se non è vocale maiuscola
            if(c!='a' && c!='e' && c!='i' && c!='o' && c!='u'){ //se non è vocale minuscola
                result = 1;
            }
        }
    }
    return result;
}

/********************************************************/
int isVocale(char c){
    int ascii = (int) c, result = 0;
    if( (ascii<=90 && ascii>=65) || (ascii<=122 && ascii>=97) ){ //se è lettera
        if(!isConsonante(c)){
            result=1;
        }
    }
    return result;
}

/********************************************************/
int isOk(char* fileName){
    int consonanti=0, vocali=0;
    for(int i=0; i<strlen(fileName); i++){
        if(isConsonante(fileName[i])){consonanti++;}
        if(isVocale(fileName[i])){vocali++;}
    }
    printf("\nFileName: %s\tCons-->%d\tVoc-->%d\n", fileName, consonanti, vocali);

    if(vocali!=0 && consonanti!=0){return 1;}
    else {return 0;}
}

/********************************************************/
void gestore(int signo) {
    int stato;
    printf("esecuzione gestore di SIGCHLD\n");
    wait(&stato);
}
/********************************************************/
typedef struct {
    char fileName[LINE_LENGTH];
} ReqUDP;

int main(int argc, char **argv) {
    struct sockaddr_in cliaddr, servaddr;
    struct hostent    *hostTcp, *hostUdp;
    int                port, listen_sd, conn_sd, udp_sd, nread, maxfdp1, len;
    const int          on = 1;
    fd_set             rset;
    ReqUDP             req;
    int                fd_sorg_udp, fd_temp_udp, count_eliminazioni;
    char               read_char, err[128], word_buffer[WORD_LENGTH];
    char charBuff[2], newDir[LINE_LENGTH], fileNameTemp[LINE_LENGTH], fileName[LINE_LENGTH], dir[LINE_LENGTH];
    DIR           *dir1, *dir2, *dir3;
    struct dirent *dd1, *dd2;

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
            printf("Ricevuta richiesta di UDP: eliminazione di occorrenze\n");
            len = sizeof(struct sockaddr_in);

            if (recvfrom(udp_sd, &req, sizeof(req), 0, (struct sockaddr *)&cliaddr, &len) < 0) {
                perror("recvfrom ");
                continue;
            }

            printf("Operazione richiesta sul file: %s\n", req.fileName);

            hostUdp = gethostbyaddr((char *)&cliaddr.sin_addr, sizeof(cliaddr.sin_addr), AF_INET);
            if (hostUdp == NULL) {
                printf("client host information not found\n");
            } else {
                printf("Operazione richiesta da: %s %i\n", hostUdp->h_name,
                       (unsigned)ntohs(cliaddr.sin_port));
            }

            /* Verifico file */
            int ris = 0;
            if ((fd_sorg_udp = open(req.fileName, O_RDONLY)) < 0) {
                perror("Errore apertura file sorgente");
                ris = -1;
            }
            fileNameTemp[0] = '\0';
            strcat(fileNameTemp, req.fileName);
            strcat(fileNameTemp, "_temp");
            if ((fd_temp_udp = open(fileNameTemp, O_CREAT | O_WRONLY, 0777)) < 0) {
                perror("Errore apertura file");
                ris = -1;
            }
            if (ris == 0) { // Both files opened successfully
                            /*** Actual operation of deleting word  ***/
                count_eliminazioni=0;
                while ((nread = read(fd_sorg_udp, &read_char, sizeof(char))) != 0) {
                    if (nread < 0) {
                        sprintf(err, "(PID %d) impossibile leggere dal file", getpid());
                        perror(err);
                        ris = -1;
                        break;
                    } else {
                        // La presenza di un separatore indica la fine di una parola. Altri
                        // separatori? Punteggiatura? Sarebbe meglio avere un array di separatori,
                        // magari letto da un file di configurazione all'avvio del server. Ci
                        // piacerebbe vedere una soluzione di questo tipo!
                        if (isConsonante(read_char)==0) {
                            write(fd_temp_udp, &read_char, 1);
                        } else { // carattere
                            count_eliminazioni++;
                        }
                    }
                }
            }
            printf("SERVER: libero e riavvio.\n");
            close(fd_sorg_udp);
            close(fd_temp_udp);

            // Rename the temp file, overwriting the original one
            remove(req.fileName);
            rename(fileNameTemp, req.fileName);

            // Send result to the client
            if (sendto(udp_sd, &count_eliminazioni, sizeof(int), 0, (struct sockaddr *)&cliaddr, len) < 0) {
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
                while ((nread = read(conn_sd, dir, sizeof(dir))) > 0) {
                    printf("Server (figlio): direttorio richiesto: %s\n", dir);

                    char risp, c, fileName[256];
                    int fd, lenght=0;
                    struct stat stat;
                    if ((dir1 = opendir(dir)) != NULL) { // direttorio presente
                        risp = 'S';
                        printf("Invio risposta affermativa al client\n");
                        write(conn_sd, &risp, sizeof(char));

                        int count_file=0;
                        while ((dd1 = readdir(dir1)) != NULL) {
                            if (strcmp(dd1->d_name, ".") != 0 && strcmp(dd1->d_name, "..") != 0 && isOk(dd1->d_name)) {
                                count_file++;
                            }
                        }
                        closedir(dir1);

                        write(conn_sd, &count_file, sizeof(int)); //gli dico quanti file aspettarsi
                        printf("Ho trovato %d file\n", count_file);

                        dir1 = opendir(dir); //so già che non fallisce
                        while ((dd1 = readdir(dir1)) != NULL) {
                            if (strcmp(dd1->d_name, ".") != 0 && strcmp(dd1->d_name, "..") != 0 && isOk(dd1->d_name)) {
                                // build new path
                                newDir[0] = '\0';
                                strcat(newDir, dir);
                                strcat(newDir, "/");
                                strcat(newDir, dd1->d_name);
                                
                                printf("\n-----------------------------------------//\n");
                                printf("Sto cercando di aprie il file: %s\n", newDir);
                                printf("\n-----------------------------------------//\n");

                                if((fd=open(newDir, O_RDONLY))<0){
                                    printf("Errore di apertura di %s\n", newDir);
                                    risp = 'N';
                                    write(conn_sd, &risp, sizeof(char));
                                }

                                //int nameLen = strlen(dd1->d_name);
                                 //write(conn_sd, &nameLen , sizeof(int)); //mando lunghezza nome file
                                 //printf("\tLunghezza nome file: %d\n", nameLen);
                                 fileName[0]='\0';
                                 strcpy(fileName, dd1->d_name);

                                 write(conn_sd, fileName, sizeof(fileName)); //mando nome file
                                 printf("Nome file: %s\n", fileName);

                                 fstat(fd, &stat);
                                 int dimFile = stat.st_size;
                                 write(conn_sd, &dimFile, sizeof(int)); //mando dim file
                                 printf("Dimensione file: %d\n", dimFile);

                                printf("Contenuto file:\n");
                                while(read(fd, &c, 1)>0){ //mando file
                                    write(conn_sd, &c, 1); 
                                    printf("%c", c);
                                }
                                close(fd);

                            }     // if not . and .. 1 livello
                        }         // while frst livello
                        closedir(dir1);
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