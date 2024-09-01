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

void gestore(int signo){
  int stato;
  printf("esecuzione gestore di SIGCHLD\n");
  wait(&stato);
}

int main(int argc, char** argv){ // argv={./nomeProgramma, porta}
    int  listen_sd, conn_sd;
    int port, len, num, riga, righe=1;
    const int on = 1;
    struct sockaddr_in cliaddr, servaddr;
    struct hostent *host;
    char c;
    
    /* CONTROLLO ARGOMENTI ---------------------------------- */
    if(argc!=2){
        printf("Error: %s port\n", argv[0]);
        exit(1);
    }
    else{
        num=0;
        while( argv[1][num]!= '\0' ){
            if( (argv[1][num] < '0') || (argv[1][num] > '9') ){
                printf("Secondo argomento non intero\n");
                exit(2);
            }
            num++;
        }
        port = atoi(argv[1]);
        if (port < 1024 || port > 65535){
            printf("Error: %s port\n", argv[0]);
            printf("1024 <= port <= 65535\n");
            exit(2);
        }
    }
    
    /* INIZIALIZZAZIONE INDIRIZZO SERVER ----------------------------------------- */
    memset ((char *)&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY;
    servaddr.sin_port = htons(port);
    
    /* CREAZIONE E SETTAGGI SOCKET D'ASCOLTO --------------------------------------- */
    listen_sd=socket(AF_INET, SOCK_STREAM, 0);
    if(listen_sd <0)    {perror("creazione socket "); exit(1);}
    printf("Server: creata la socket d'ascolto per le richieste di ordinamento, fd=%d\n", listen_sd);
    
    if(setsockopt(listen_sd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on))<0)  {perror("set opzioni socket d'ascolto"); exit(1);}
    printf("Server: set opzioni socket d'ascolto ok\n");
    
    if(bind(listen_sd,(struct sockaddr *) &servaddr, sizeof(servaddr))<0)   {perror("bind socket d'ascolto"); exit(1);}
    printf("Server: bind socket d'ascolto ok\n");
    
    if (listen(listen_sd, 5)<0){ //creazione coda d'ascolto
        perror("listen"); exit(1);
    }
    
    signal(SIGCHLD, gestore);
    
    for(;;){
        
        printf("\n\nServer: listen ok\n");
        
        len=sizeof(cliaddr);
        if((conn_sd=accept(listen_sd,(struct sockaddr *)&cliaddr,&len))<0){
            if (errno==EINTR){
                perror("Forzo la continuazione della accept");
                continue;
            }
            else exit(1);
        }
        
        if(read(conn_sd, &riga, sizeof(int))<0) {printf("Errore lettura riga\n"); continue;}
        
        righe=1;
        while((num=read(conn_sd, &c, 1))>0){
            if(c=='\n') {righe++;}
            if(righe!=riga) {write(conn_sd, &c, 1); write(1, &c, 1);}
        }
        
        close(conn_sd);
        
        printf("\nServer: completato invio\n");
    }
}
