#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

#define DIM_BUFF 256

int main(int argc, char** argv){ // argv={./nomeProgramma, ip, porta}
    int sd, port, fd, nread, riga;
    char buff[DIM_BUFF], fileName[FILENAME_MAX+1], c;
    // FILENAME_MAX: lunghezza massima nome file. Costante di sistema.
    struct hostent *host;
    struct sockaddr_in servaddr;
    
    if(argc!=3){
        printf("Errore: numero di argomenti\n");
        exit(1);
    }
    
    /* INIZIALIZZAZIONE INDIRIZZO SERVER -------------------------- */
    memset((char *)&servaddr, 0, sizeof(struct sockaddr_in));
    servaddr.sin_family = AF_INET;
    host = gethostbyname(argv[1]);
    
    /*VERIFICA PORTA INTERA*/
    nread=0;
    while( argv[2][nread]!= '\0' ){
        if( (argv[2][nread] < '0') || (argv[2][nread] > '9') ){
            printf("Secondo argomento non intero\n");
            exit(2);
        }
        nread++;
    }
    port = atoi(argv[2]);
    
    /* VERIFICA PORT e HOST */
    if (port < 1024 || port > 65535){
        printf("%s = porta scorretta...\n", argv[2]);
        exit(2);
    }
    if (host == NULL){
        printf("%s not found in /etc/hosts\n", argv[1]);
        exit(2);
    }else{
        servaddr.sin_addr.s_addr=((struct in_addr *)(host->h_addr))->s_addr;
        servaddr.sin_port = htons(port);
    }

    printf("Quale file vuoi considerare?\n");
    if((nread=scanf("%s", fileName))<0) {printf("Errore: lettura nome file\n"); exit(3);} //lettura nome file
    
    printf("Quale riga vuoi eliminare in %s ?\n", fileName);
    if((nread=scanf("%d", &riga))<0) {printf("Errore: lettura riga da eliminare\n");exit(3);} //lettura riga

    if((fd=open(fileName, O_RDONLY))<0) { printf("Errore: apertura file iniziale\n"); exit(3);} //apertura file (pieno)
                
    /* CREAZIONE SOCKET ------------------------------------ */
    sd=socket(AF_INET, SOCK_STREAM, 0);
    if(sd<0) {perror("apertura socket"); exit(1);}
    printf("Client: creata la socket sd=%d\n", sd);
    
    /* Operazione di BIND implicita nella connect */
    if(connect(sd,(struct sockaddr *) &servaddr, sizeof(struct sockaddr))<0)    {perror("connect"); exit(1);}
    printf("Client: connect ok\n");
    
    /*INVIO RIGA*/
    write(sd,&riga,sizeof(int));
    /*INVIO File*/
    printf("Client: stampo e invio file da modificare\n");
    while((nread=read(fd, &c, 1))>0){
        write(1,&c,1);    //stampa
        write(sd,&c,1);    //invio
    }
    printf("\nClient: file inviato\n");
    close(fd);
    
    /* Chiusura socket in spedizione -> invio dell'EOF */
    shutdown(sd,1);
    
    //SOVRASCRITTURA FILE
    if((fd=open(fileName, O_WRONLY|O_TRUNC))<0) { printf("Errore: apertura file finale\n"); exit(3);}
    
    //if((fd=open("altro_nome", O_WRONLY|O_CREAT, 0644))<0) { printf("Errore: apertura file finale\n"); exit(3);} //prova con altro file
    
    /*RICEZIONE File*/
    printf("Client: ricevo e stampo file modificato\n");
    while((read(sd,&c,1))>0){
        write(fd,&c,1); //sovrascrivo file
        write(1,&c,1); //stampo
    }
    printf("\nTrasferimento terminato\n");
    close(fd);
    
    /* Chiusura socket in ricezione */
    shutdown(sd, 0);
    
    /* Chiusura socket */
    close(sd);
}
