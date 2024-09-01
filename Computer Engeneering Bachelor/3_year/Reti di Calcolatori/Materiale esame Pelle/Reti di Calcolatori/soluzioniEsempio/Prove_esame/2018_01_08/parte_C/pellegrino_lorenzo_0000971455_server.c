#include "lib.h"
/********************************************************/
void gestore(int signo) {
    int stato;
    printf("esecuzione gestore di SIGCHLD\n");
    wait(&stato);
}
/********************************************************/

int main(int argc, char **argv) {
    struct sockaddr_in cliaddr, servaddr;
    struct hostent    *hostTcp, *hostUdp;
    int                port, listen_sd, conn_sd, udp_sd, nread, maxfdp1, len;
    const int          on = 1;
    fd_set             rset;
    RigaTabella        req;
    int                fd_sorg_udp, fd_temp_udp, count_letters;
    char               read_char, err[128], word_buffer[WORD_LENGTH];
    char charBuff[2], newDir[LINE_LENGTH], fileNameTemp[LINE_LENGTH], fileName[LINE_LENGTH],
        dir[LINE_LENGTH];
    DIR           *dir1, *dir2, *dir3;
    struct dirent *dd1, *dd2;

    Tabella tabella;

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

    /* INIZIALIZZAZIONE STRUTTURA ----------------------------------------- */
    for(int i=0; i<N; i++){
        strcpy(tabella.righe[i].identificatore, "L");
        strcpy(tabella.righe[i].tipo, "L");
        tabella.righe[i].carbone='L';
        strcpy(tabella.righe[i].citta, "L");
        strcpy(tabella.righe[i].via, "L");
        strcpy(tabella.righe[i].messaggio, "L");
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
            printf("Ricevuta richiesta di UDP: eliminazione di una parola\n");
            len = sizeof(struct sockaddr_in);

            if (recvfrom(udp_sd, &req, sizeof(req), 0, (struct sockaddr *)&cliaddr, &len) < 0) {
                perror("recvfrom ");
                continue;
            }

            printf("Richiesta di inserimento per:\n");
            printf("Identificatore: %s\n", req.identificatore);
            printf("Tipo: %s\n", req.tipo);
            printf("Carbone: %c\n", req.carbone);
            printf("Citta: %s\n", req.citta);
            printf("Via: %s\n", req.via);
            printf("Messaggio: %s\n", req.messaggio);

            hostUdp = gethostbyaddr((char *)&cliaddr.sin_addr, sizeof(cliaddr.sin_addr), AF_INET);
            if (hostUdp == NULL) {
                printf("client host information not found\n");
            } else {
                printf("Operazione richiesta da: %s %i\n", hostUdp->h_name,
                       (unsigned)ntohs(cliaddr.sin_port));
            }
            
            char ris = 'N';
            int ok = isUnico(tabella, req.identificatore);
            // inserimento in tabella
            for(int i=0; i<N && ris=='N'; i++){
                if(tabella.righe[i].carbone=='L' && ok==1){
                    strcpy(tabella.righe[i].identificatore, req.identificatore);
                    strcpy(tabella.righe[i].tipo, req.tipo);
                    tabella.righe[i].carbone = req.carbone;
                    strcpy(tabella.righe[i].citta, req.citta);
                    strcpy(tabella.righe[i].via, req.via);
                    strcpy(tabella.righe[i].messaggio, req.messaggio);
                    ris='S';

                    printf("Richiesta inserita nella riga %d\n", i);
                }
            }

            // Send result to the client
            if (sendto(udp_sd, &ris, sizeof(ris), 0, (struct sockaddr *)&cliaddr, len) < 0) {
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
                char tipo[8];
                int trovati;
                while(read(conn_sd, tipo, sizeof(tipo)) > 0){
                    trovati=0;
                    printf("Server (figlio): tipo di calza: %s\n", tipo);

                    for(int i=0; i<N; i++){
                        if(strcmp(tabella.righe[i].tipo, tipo)==0){
                            printf("%d) tipo: %s \t carbone=%c\n", i+1, tabella.righe[i].tipo, tabella.righe[i].carbone);
                            if(tabella.righe[i].carbone=='N' || tabella.righe[i].carbone=='S'){
                                trovati++;
                                printf("Contato!\n");
                            }
                        }
                    }
                    printf("Ne ho trovati %d\n", trovati);
                    write(conn_sd, &trovati, sizeof(trovati));

                    if(trovati > 0){
                        Nome nomi[trovati];
                        int count=0;
                        for(int i=0; i<N; i++){
                            if(strcmp(tabella.righe[i].tipo, tipo)==0){
                                if(tabella.righe[i].carbone=='N' || tabella.righe[i].carbone=='S'){
                                    strcpy(nomi[count], tabella.righe[i].identificatore);
                                    count++;
                                }
                            }
                        }
                        //invio risultato
                        write(conn_sd, &nomi, sizeof(nomi));
                        free(nomi);
                    } //while
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