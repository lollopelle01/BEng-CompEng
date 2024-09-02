//Messori Noemi 0000977938 Compito1

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <errno.h>
#include <fcntl.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <sys/select.h>

#define DIM_BUFF 1024
#define LENGTH_FILE_NAME 20
#define WORD_LENGHT 20
#define MAX_BICI 10
#define max(a, b) ((a) > (b) ? (a) : (b))


typedef struct stutturaDati
{
    char id[WORD_LENGHT];
    char scadenza[11]; 
    char brand[WORD_LENGHT];
    char folder[DIM_BUFF];
}Prenotazione;

typedef struct
{
    char id[WORD_LENGHT];
    char image[WORD_LENGHT];
} request;

// Modificare se necessario
void gestore(int signo)
{
    int stato;
    printf("esecuzione gestore di SIGCHLD\n");
    wait(&stato);
}




int main(int argc, char **argv)
{
    int listenfd, connfd, udpfd, nready, maxfdp1;
    const int on = 1;

    
   
    //char end[1];
	//end[0] = (char)4;

    fd_set rset;
    int len, nread, num, ris, port;
    struct sockaddr_in cliaddr, servaddr;

    DIR *mainDir;
    struct dirent *cur;

    //per udp
    char nome_image[DIM_BUFF], id[WORD_LENGHT];
    char folder[DIM_BUFF];
    request req;
   
    int fd;
    char buff[DIM_BUFF];
    //per tcp
    DIR *mainDir1;
    int size,stringSize;
    int nread1, num1;
    char nomeFile[256];
    
    char nome_dir[256];
    // CONTROLLO ARGOMENTI
    if (argc != 2)
    {
        printf("Error: %s port\n", argv[0]);
        exit(1);
    }

    nread = 0;
    while (argv[1][nread] != '\0')
    {
        if ((argv[1][nread] < '0') || (argv[1][nread] > '9'))
        {
            printf("Terzo argomento non intero\n");
            exit(2);
        }
        nread++;
    }
    port = atoi(argv[1]);
    if (port < 1024 || port > 65535)
    {
        printf("Porta scorretta...");
        exit(2);
    }

    /* INIZIALIZZAZIONE INDIRIZZO SERVER E BIND ---------------------------- */
    memset((char *)&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY;
    servaddr.sin_port = htons(port);
    printf("Server avviato\n");

    /*INIZIALIZZAZIONE STRUTTURA DATI*/
    Prenotazione prenotazioni[MAX_BICI];
    for(int i=0;i<MAX_BICI;i++)
    {
        strcpy(prenotazioni[i].id,"L");
        strcpy(prenotazioni[i].scadenza,"-1/-1/-1");
        strcpy(prenotazioni[i].brand,"L");
        strcpy(prenotazioni[i].folder,"L");
    }
    strcpy(prenotazioni[0].id,"AN745NL");
    strcpy(prenotazioni[0].scadenza,"12/10/2022");
    strcpy(prenotazioni[0].brand,"brand1");
    strcpy(prenotazioni[0].folder,"AN745NL_img");

    strcpy(prenotazioni[1].id,"CD234ER");
    strcpy(prenotazioni[1].scadenza,"22/12/2025");
    strcpy(prenotazioni[1].brand,"brand2");
    strcpy(prenotazioni[1].folder,"CD234ER_img");
        
    strcpy(prenotazioni[2].id,"AD345OO");
    strcpy(prenotazioni[2].scadenza,"10/10/2030");
    strcpy(prenotazioni[2].brand,"brand3");
    strcpy(prenotazioni[2].folder,"AD345OO_img");


    /* CREAZIONE SOCKET TCP ------------------------------------------------ */
    listenfd = socket(AF_INET, SOCK_STREAM, 0);
    if (listenfd < 0)
    {
        perror("apertura socket TCP ");
        exit(1);
    }
    printf("Creata la socket TCP d'ascolto, fd=%d\n", listenfd);

    if (setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0)
    {
        perror("set opzioni socket TCP");
        exit(2);
    }
    printf("Set opzioni socket TCP ok\n");

    if (bind(listenfd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0)
    {
        perror("bind socket TCP");
        exit(3);
    }
    printf("Bind socket TCP ok\n");

    if (listen(listenfd, 5) < 0)
    {
        perror("listen");
        exit(4);
    }
    printf("Listen ok\n");


    /* CREAZIONE SOCKET UDP ------------------------------------------------ */
    udpfd = socket(AF_INET, SOCK_DGRAM, 0);
    if (udpfd < 0)
    {
        perror("apertura socket UDP");
        exit(5);
    }
    printf("Creata la socket UDP, fd=%d\n", udpfd);

    if (setsockopt(udpfd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0)
    {
        perror("set opzioni socket UDP");
        exit(6);
    }
    printf("Set opzioni socket UDP ok\n");

    if (bind(udpfd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0)
    {
        perror("bind socket UDP");
        exit(7);
    }
    printf("Bind socket UDP ok\n");

    
    signal(SIGCHLD, gestore);

    
    FD_ZERO(&rset);
    maxfdp1 = max(listenfd, udpfd) + 1;

    /* CICLO DI RICEZIONE EVENTI DALLA SELECT ----------------------------------- */
    for (;;)
    {
        FD_SET(listenfd, &rset);
        FD_SET(udpfd, &rset);

        if ((nready = select(maxfdp1, &rset, NULL, NULL, NULL)) < 0)
        {
            if (errno == EINTR)
                continue;
            else
            {
                perror("select");
                exit(8);
            }
        }

        
        if (FD_ISSET(listenfd, &rset))
        {
            printf("Ricevuta richiesta di download di immagini\n");
            len = sizeof(struct sockaddr_in);
            if ((connfd = accept(listenfd, (struct sockaddr *)&cliaddr, &len)) < 0)
            {
                if (errno == EINTR)
                    continue;
                else
                {
                    perror("accept");
                    exit(9);
                }
            }

       
            if (fork() == 0)
            { 
                close(listenfd);
				printf("Dentro il figlio, pid=%i\n", getpid());


                //di qua ricevo
                
                while ((num1 = read(connfd, id, sizeof(id))) > 0)
				{                   
					printf("Richiesto trasferimento immagini\n");
                    
                   
                    
                    strcpy(nome_dir, id);
                    strcat(nome_dir,"_img");

                    
                    while (read(connfd, &size, 4) > 0 && size > 0)
                    {
                        //nel momento in cui sono finiti i file-immagini passero' una dimensione negativa


                        read(connfd,&stringSize,4);
                        printf("%ld\n", read(connfd, nomeFile, stringSize));

                        //Ricevuto nome file-immagine
                        printf("Download file \"%s\"\tsize: %i b\n", nomeFile, size);

                        nome_dir[0] = '\0';
                        strcat(nome_dir, ".");
                        strcat(nome_dir, "/");
                        strcat(nome_dir, nomeFile); //ricostruisco il path
                        if ((fd = open(nome_dir, O_CREAT | O_TRUNC | O_WRONLY)) < 0)
                        {
                            perror("Apertura file nel direttorio:");
                        }

                        while (size > 0)
                        {
                            if (size < DIM_BUFF)
                            {
                                nread1 = read(connfd, buff, size);
                                write(fd, buff, nread);
                            }
                            else
                            {
                                nread1 = read(connfd, buff, DIM_BUFF);
                                write(fd, buff, nread);
                            }
                            size -= nread1;
                        }
                    }
                    
					
				}

                printf("Figlio %i: termino\n", getpid());
				shutdown(connfd, 0);
				shutdown(connfd, 1);
				close(connfd);
				exit(0);
            }
        }

        /* GESTIONE RICHIESTE DI ELIMINAZIONE DI IMMAGINE ------------------------------------------ */
        if (FD_ISSET(udpfd, &rset))
        {
            num=0;
            len = sizeof(struct sockaddr_in);
            if (recvfrom(udpfd, &req, sizeof(req), 0, (struct sockaddr *)&cliaddr, &len) < 0)
            {
                perror("recvfrom");
                continue;
            }

            printf("Richiesta eliminazione dell'immagine %s, dato l'id %s\n",req.image, req.id);


            for(int i=0;i<MAX_BICI && num<1;i++){
                if(strcmp(prenotazioni[i].id,req.id)==0){
                    //ho trovato l'id -- quindi è presente all'interno della mia struttura                   
                    num=1;                   
                }
            }

            if(num==1){
                printf("ho trovato id\n");
                printf("Richiesto eliminazione immagine da %s\n", folder);

                strcpy(folder,req.id);
                strcat(folder,"_img");

                mainDir = opendir(folder);

                strcpy(nome_image, folder);
                strcat(strcat(nome_image, "/"), req.image);

                if (mainDir == NULL)
                {
                    printf("Directory non valida\n");
                    ris= -1;
                } else {
                    while ((cur = readdir(mainDir)) != NULL)
                    {
                            //sono all'interno del folder e scorro i file-immagini all'interno
                                                       
                            //devo eliminare immagine dal folder
                            if(strcmp(cur->d_name, req.image) == 0){
                                //nome immagine è lo stesso allora e' quello che devo eliminare


                                //elimino utilizzando tutto il percorso coi file
                                if ((remove(nome_image)) <= 0)
                                {

                                    // Fallimento
                                    ris=-2;
                                    perror("Errore durante rimozione immagine");
                                    
                                } else {
                                    ris = 0;
                                }
                            }                                                                                  
                    }

                }

            } else {
                printf("non e' stato possibile trovare l'id della bici scelto\n");
                ris = -1;
            }
            
            //ris = num;
            if (sendto(udpfd, &ris, sizeof(ris), 0, (struct sockaddr *)&cliaddr, len) < 0)
            {
                perror("sendto");
                continue;
            }
        }

    } /* ciclo for della select */
    /* NEVER ARRIVES HERE */
    exit(0);
}
