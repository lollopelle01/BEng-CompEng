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
#include <sys/stat.h>

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
    char carattere;
    int numOccorrenze;
} ReqUDP;

int main(int argc, char **argv) {
    struct sockaddr_in cliaddr, servaddr;
    struct hostent    *hostTcp, *hostUdp;
    int                port, listen_sd, conn_sd, udp_sd, nread, maxfdp1, len;
    const int          on = 1;
    fd_set             rset;
    ReqUDP             req;

    // per servizi
    int fd, ris, lenght, ok, count, inizioRiga;
    DIR *dir, *dir1;
    struct dirent *dirent, *dirent1;
    char c;
    struct stat stat;

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
        /* GESTIONE RICHIESTE UDP  */
            if (FD_ISSET(udp_sd, &rset)) {
            printf("Ricevuta richiesta di UDP: -- \n");
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
            if((dir = opendir("."))!=NULL){
                ris = 0;
                while((dirent=readdir(dir))!=NULL){
                    if(dirent->d_type==DT_REG && (lenght=strlen(dirent->d_name))>4 && !strcmp(dirent->d_name + (lenght-4), ".txt")){
                        fd=open(dirent->d_name, O_RDONLY);
                        count=0;
                        ok=0;
                        inizioRiga=1;
                        while(read(fd, &c, 1)>0){
                            if(inizioRiga==1 && (c>='a' && c<='z')){ok=1;}
                            inizioRiga=0;
                            if(c=='\n'){ // fine linea
                                if(ok==1 && count==req.numOccorrenze){ ris++; }
                                ok=0;
                                count=0;
                                inizioRiga=1;
                            }
                            else{
                                if(c==req.carattere){count++;}
                            }
                        }
                        if(ok==1 && count==req.numOccorrenze){ ris++; } // se finisco riga con EOF

                        close(fd);
                    }
                }
            }
            else{ris = -1;}
            
            
            // Send result to the client
            if (sendto(udp_sd, &ris, sizeof(int), 0, (struct sockaddr *)&cliaddr, len) < 0) {
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
                char dirName[LINE_LENGTH], ris;
                char newDir[2*(LINE_LENGTH + 1)];
                long dimFile;
                char fileName[LINE_LENGTH];
                while ((nread = read(conn_sd, dirName, sizeof(dirName))) > 0) {

                    /*Gestione richiesta*/
                    if((dir=opendir(dirName))!=NULL){ // direttorio 1°

                        while((dirent=readdir(dir))!=NULL){
                            ris='S';
                            write(conn_sd, &ris, 1);
                            if(strcmp(dirent->d_name, ".")!=0 && strcmp(dirent->d_name, "..")!=0){ // guardo solo file e sottocartelle
                                // costruisco direttorio
                                newDir[0]='\0';
                                strcpy(newDir, dirName);
                                strcat(newDir, "/");
                                strcat(newDir, dirent->d_name);

                                if(dirent->d_type==DT_REG && (lenght=strlen(dirent->d_name))>4 && !strcmp(dirent->d_name + (lenght-4), ".txt")){ // se file di testo
                                    // apro file e lo analizzo
                                    fd=open(newDir, O_RDONLY);
                                    fstat(fd, &stat);
                                    
                                    // invio file
                                    strcpy(fileName, dirent->d_name);
                                    write(conn_sd, fileName, sizeof(fileName));
                                    dimFile=stat.st_size;
                                    write(conn_sd, &dimFile, sizeof(dimFile));
                                    while(read(fd, &c, 1)>0){
                                        write(conn_sd, &c, 1);
                                    }
                                    close(fd);
                                }
                                else if(dirent->d_type==DT_DIR){ // se è direttorio 2°
                                    dir1=opendir(newDir);
                                    while((dirent1=readdir(dir1))!=NULL){ 
                                        if(dirent1->d_type==DT_REG && (lenght=strlen(dirent1->d_name))>4 && !strcmp(dirent1->d_name + (lenght-4), ".txt")){ // se file di testo
                                            // ricostruisco direttorio
                                            newDir[0]='\0';
                                            strcpy(newDir, dirName);
                                            strcat(newDir, "/");
                                            strcat(newDir, dirent->d_name);
                                            strcat(newDir, "/");
                                            strcat(newDir, dirent1->d_name);
                                            
                                            // apro file e lo analizzo
                                            fd=open(newDir, O_RDONLY);
                                            fstat(fd, &stat);

                                            // invio file
                                            strcpy(fileName, dirent1->d_name);
                                            write(conn_sd, fileName, sizeof(fileName));
                                            dimFile=stat.st_size;
                                            write(conn_sd, &dimFile, sizeof(dimFile));
                                            while(read(fd, &c, 1)>0){
                                                write(conn_sd, &c, 1);
                                            }
                                            close(fd);
                                        }
                                    }
                                    closedir(dir1);
                                }
                            }
                        }
                        strcpy(fileName, "fine");
                        write(conn_sd, fileName, sizeof(fileName));
                    }
                    else{
                        ris='N';
                        write(conn_sd, &ris, 1);
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