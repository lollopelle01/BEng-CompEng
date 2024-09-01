#include <dirent.h>
#include <errno.h>
#include <fcntl.h>
#include <netdb.h>
#include <netinet/in.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#define LENGTH_FILE_NAME 256
#define max(a, b)        ((a) > (b) ? (a) : (b))

void gestore(int signo) {
    int stato;
    printf("esecuzione gestore di SIGCHLD\n");
    wait(&stato);
}

int main(int argc, char **argv){
    int       listenfd, connfd, udpfd, fd_file, nready, maxfdp1;
    const int on   = 1;
    char      zero = 0, c;
    
    char    buff[LENGTH_FILE_NAME],
            file_e_parola[LENGTH_FILE_NAME],
            file[LENGTH_FILE_NAME],
            parolaCanc[LENGTH_FILE_NAME],
            nome_dir[LENGTH_FILE_NAME],
            parola[LENGTH_FILE_NAME],
            direttorio[LENGTH_FILE_NAME];
    
    fd_set    rset;
    int       len, nread, nwrite, num, port;
    struct sockaddr_in cliaddr, servaddr;
    DIR *dir1, *dir2, *dir3;
    struct dirent *dd1, *dd2, *dd3;
    
    /* CONTROLLO ARGOMENTI ---------------------------------- */
    if (argc != 2) {
        printf("Error: %s port\n", argv[0]);
        exit(1);
    }
    nread = 0;
    while (argv[1][nread] != '\0') {
        if ((argv[1][nread] < '0') || (argv[1][nread] > '9')) {
            printf("Terzo argomento non intero\n");
            exit(2);
        }
        nread++;
    }
    port = atoi(argv[1]);
    if (port < 1024 || port > 65535) {
        printf("Porta scorretta...");
        exit(2);
    }
    
    /* INIZIALIZZAZIONE INDIRIZZO SERVER ----------------------------------------- */
    memset((char *)&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family      = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY;
    servaddr.sin_port        = htons(port);

    printf("Server avviato\n");
    
    /* CREAZIONE SOCKET TCP ------------------------------------------------------ */
    listenfd = socket(AF_INET, SOCK_STREAM, 0);
    if (listenfd < 0) {
        perror("apertura socket TCP ");
        exit(1);
    }
    printf("Creata la socket TCP d'ascolto, fd=%d\n", listenfd);

    if (setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0) {
        perror("set opzioni socket TCP");
        exit(2);
    }
    printf("Set opzioni socket TCP ok\n");

    if (bind(listenfd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0) {
        perror("bind socket TCP");
        exit(3);
    }
    printf("Bind socket TCP ok\n");

    if (listen(listenfd, 5) < 0) {
        perror("listen");
        exit(4);
    }
    printf("Listen ok\n");
    
    /* CREAZIONE SOCKET UDP ------------------------------------------------ */
    udpfd = socket(AF_INET, SOCK_DGRAM, 0);
    if (udpfd < 0) {
        perror("apertura socket UDP");
        exit(5);
    }
    printf("Creata la socket UDP, fd=%d\n", udpfd);

    if (setsockopt(udpfd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0) {
        perror("set opzioni socket UDP");
        exit(6);
    }
    printf("Set opzioni socket UDP ok\n");

    if (bind(udpfd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0) {
        perror("bind socket UDP");
        exit(7);
    }
    printf("Bind socket UDP ok\n");
    
    /* AGGANCIO GESTORE PER EVITARE FIGLI ZOMBIE -------------------------------- */
    signal(SIGCHLD, gestore);

    /* PULIZIA E SETTAGGIO MASCHERA DEI FILE DESCRIPTOR ------------------------- */
    FD_ZERO(&rset);
    maxfdp1 = max(listenfd, udpfd) + 1;
    
    /* CICLO DI RICEZIONE EVENTI DALLA SELECT ----------------------------------- */
    for (;;) {
        printf("Aspetto richieste...\n");
        FD_SET(listenfd, &rset);
        FD_SET(udpfd, &rset);
        
        if ((nready = select(maxfdp1, &rset, NULL, NULL, NULL)) < 0) {
            if (errno == EINTR)
                continue;
            else {
                perror("select");
                exit(8);
            }
        }
        
        /* GESTIONE RICHIESTE DI GET DI UNA LISTA DI DIRETTORI ------------------------------------- */
        if (FD_ISSET(listenfd, &rset)) {
            printf("Ricevuta richiesta di get di un direttorio\n");
            len = sizeof(struct sockaddr_in);
            if ((connfd = accept(listenfd, (struct sockaddr *)&cliaddr, &len)) < 0) {
                if (errno == EINTR)
                    continue;
                else {
                    perror("accept");
                    exit(9);
                }
            }
            
            if (fork() == 0) { /* processo figlio che serve la richiesta di operazione */
                close(listenfd);
                printf("Dentro il figlio, pid=%i\n", getpid());
                
                for (;;) {
                    if ((read(connfd, &nome_dir, sizeof(nome_dir))) <= 0) {
                        perror("read");
                        break;
                    }
                    printf("Richiesta directory: %s\n", nome_dir);
                    
                    dir1 = opendir(nome_dir); //prendo il direttorio
                    if (dir1 == NULL) {
                        printf("Directory inesistente\n");
                        write(connfd, "N", 1);
                    } else {
                        write(connfd, "S", 1);
            
                        
                        /* lettura dei file/dir (a blocchi) e scrittura sulla socket */
                        printf("Leggo e invio la lista di file richieste\n");
                        while((dd1 = readdir(dir1)) != NULL){ //scorro nel direttorio
                            if(dd1->d_type==DT_DIR && strcmp(dd1->d_name, ".")!=0 && strcmp(dd1->d_name, "..")!=0){ //prendo i sottodirettori
                                
                                strcpy(direttorio, nome_dir);
                                strcat(direttorio, "/");
                                strcat(direttorio, dd1->d_name);
                                
                                dir2 = opendir(direttorio);
                                printf("Siamo nella sottodirectory 1°: %s\n", direttorio);
                                while((dd2 = readdir(dir2)) != NULL){
                                    if(dd2->d_type==DT_REG){ //prendo i file
                                        write(connfd, dd2->d_name, strlen(dd2->d_name));
                                        write(connfd, "\t", 1); //invio
                                        write(1, dd2->d_name, strlen(dd2->d_name));
                                        write(1, "\n", 1);
                                    }
                                }
                                closedir(dir2);
                            }
                            else if(dd1->d_type==DT_REG){ //prendo i file
                                write(connfd, dd1->d_name, strlen(dd1->d_name)); //invio
                                write(connfd, "\t", 1); //invio
                                write(1, dd1->d_name, strlen(dd1->d_name)); //stampo
                                write(1, "\n", 1);
                            }
                            else{} //ignoro altri casi
                        }
                        closedir(dir1);
                        printf("Terminato invio dei file\n");
                        
                        /* invio al client segnale di terminazione: zero binario */
                        write(connfd, &zero, 1);
                        close(fd_file);
                    } // else
                }     // for
                printf("Figlio %i: chiudo connessione e termino\n", getpid());
                close(connfd);
                exit(0);
            } // figlio
            /* padre chiude la socket dell'operazione */
            close(connfd);
        } /* fine gestione richieste di file */
        
        /* GESTIONE RICHIESTE DI CONTEGGIO ------------------------------------------ */
        
        if (FD_ISSET(udpfd, &rset)) {
            printf("Server: ricevuta richiesta di eliminazione parola\n");
            len = sizeof(struct sockaddr_in);
            if (recvfrom(udpfd, &file_e_parola, sizeof(file_e_parola), 0, (struct sockaddr *)&cliaddr, &len) < 0) {
                perror("recvfrom");
                continue;
            }
            
            // VA BENE -> VENGONO MANDATE STRINGHE CON TERMINATORE DENTRO
            strcpy(file, strtok(file_e_parola, " "));
            strcpy(parolaCanc, strtok(NULL, " "));
            printf("file: %s\nparola: %s\n", file, parolaCanc);
            
            fd_file = open(file, O_RDONLY);
            int fd_file_temp = open("temp", O_WRONLY|O_CREAT, 0644);
            
            int count = 0, i=0;
            strcpy(parola, ""); //resetto parola
            while((nread=read(fd_file, &c, 1))>0){
                if(c==' ' || c=='\n'){ //ho appena letto una parola
                    parola[i] = '\0';
                    i=0;
                    if(strcmp(parola, parolaCanc)!=0){ //se non voglio cancellarla
                        write(fd_file_temp, parola, strlen(parola)); //scrivo parola su nuovo file
                        write(1, parola, strlen(parola)); //stampo a video
                    }
                    else { //elimino parola non scrivendola
                        count++; //conto parole cancellate
                    }
                    write(fd_file_temp, &c, 1); // in ogni caso metto ' ' o '\n'
                    write(1, &c, 1); //stampo a video
                    strcpy(parola, ""); //pronto per nuova parola
                }
                else{ //assemblo la parola
                    parola[i]=c;
                    i++;
                    //sprintf(parola, "%s%c", parola, c);
                }
            }
            
            { //se ultima parola era terminata da EOF
                parola[i] = '\0';
                if(strcmp(parola, parolaCanc)!=0){ //se non voglio cancellarla
                    write(fd_file_temp, parola, strlen(parola)); //scrivo parola su nuovo file
                    write(1, parola, strlen(parola)); //stampo a video
                }
                else { //elimino parola non scrivendola
                    count++; //conto parole cancellate
                }
            }
            
            remove(file);
            rename("temp", file);
            
            printf("\nHo eliminato %s da %s %d volte\n", parolaCanc, file, count);
            
            if (sendto(udpfd, &count, sizeof(count), 0, (struct sockaddr *)&cliaddr, len) < 0) {
                perror("sendto");
                continue;
            }
            
        } /* fine gestione richieste di conteggio */
    }
}
