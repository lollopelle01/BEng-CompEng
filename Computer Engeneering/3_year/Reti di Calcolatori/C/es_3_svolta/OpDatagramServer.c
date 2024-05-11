//
//  OpDatagramServer.c
//  es_3_svolta
//
//  Created by Lorenzo Pellegrino on 07/11/22.
//

#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <libc.h>
#include <netdb.h>

#define LINE_LENGHT 256

typedef struct{
    int op1;
    int op2;
    char tipoOp;
} Request;

int main(int argc, char** argv){
    int sd, port, len, num1, num2, ris = 0;
    const int on = 1;
    struct sockaddr_in cliaddr, servaddr;
    struct hostent *clienthost;
    Request* req =(Request*)malloc(sizeof(Request));
    
    if(argc != 2){
        perror("Numero argomenti\n");
        exit(1);
    }
    else{
        num1 = 0;
        while(argv[2][num1] != '\0'){ // Controllo che porta sia fatta solo da interi
            if( (argv[2][num1] < '0') || (argv[2][num1] > '9') ){
                printf("Secondo argomento non intero\n"); exit(2);
            }
            num1++;
        }
        port = atoi(argv[1]);
        if (port < 1024 || port > 65535){
            printf("Error: %s port\n", argv[0]);
            exit(2);
        }
    }
    
    memset((char *)&servaddr, 0, sizeof(servaddr)); //riempiamo di 0 l'indirizzo di clientaddr
    servaddr.sin_family = AF_INET; //è della famiglia degli indirizzi internet
    servaddr.sin_addr.s_addr = INADDR_ANY; //agganciamo a indirizzo ip non specifico
    servaddr.sin_port = htons(port); //porta_socket_server=porta
    
    sd=socket(AF_INET, SOCK_DGRAM, 0); //creazione della socket
    if(sd < 0){
        perror("Apertura della socket\n");
        exit(2);
    }
    printf("Creata la socket: %d\n", sd);
    if(setsockopt(sd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on))<0) { //settiamo socket in reuse_address
        perror("set opzioni socket ");
        exit(1);
    }
    if(bind(sd,(struct sockaddr *) &servaddr, sizeof(servaddr))<0){ // abbino socket e server
        perror("bind socket ");
        exit(1);
    }
    printf("Server: bind socket ok\n");
    
    for(;;){ //Ciclo infinito
        len=sizeof(struct sockaddr_in);
        if (recvfrom(sd, req, sizeof(Request), 0,(struct sockaddr *)&cliaddr, &len)<0) {
            perror("recvfrom ");
            continue;
        }
        num1=ntohl(req->op1);
        num2=ntohl(req->op2);
        
        printf("Operazione richiesta: %i %c %i\n", num1, req->tipoOp, num2);
        
        clienthost=gethostbyaddr((char *)&cliaddr.sin_addr, sizeof(cliaddr.sin_addr), AF_INET);
        if (clienthost == NULL)
            printf("client host not found\n");
        else
            printf("Operazione richiesta da: %s %i\n",clienthost->h_name, (unsigned)ntohs(cliaddr.sin_port));
        
        if(req->tipoOp=='+')    {ris = num1 + num2;}
        else if(req->tipoOp=='-')    {ris = num1 - num2;}
        else if(req->tipoOp=='*')    {ris = num1 * num2;}
        else if(req->tipoOp=='/')    {if(num2 != 0) ris = num1 / num2;}
        
        ris=htonl(ris);
        if (sendto(sd, &ris, sizeof(ris), 0,(struct sockaddr *)&cliaddr, len)<0) {
            perror("sendto ");
            continue;
        }
    } //for
} //main
