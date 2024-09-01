//
//  OpDatagramClient.c
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

int main(int argc, char** argv){ // argv = { ./nomeCodice, nome_nodo, porta}
    struct hostent *host; //info precise su un nodo
    struct sockaddr_in clientaddr, servaddr; //indirizzi in entrata
    int port, sd, num1, num2, len, ris;
    char okstr[LINE_LENGHT];
    char c; int ok;
    Request req;
    
    if(argc!=3){
        printf("Error: %s serverAddress serverPort\n", argv[0]);
        exit(1);
    }
    
    memset((char *)&clientaddr, 0, sizeof(struct sockaddr_in)); //riempiamo di 0 l'indirizzo di clientaddr
    clientaddr.sin_family = AF_INET; //è della famiglia degli indirizzi internet
    clientaddr.sin_addr.s_addr = INADDR_ANY; //agganciamo a indirizzo ip non specifico
    clientaddr.sin_port = 0; //porta_socket_cliente=0
    
    memset((char *)&servaddr, 0, sizeof(struct sockaddr_in)); //riempiamo di 0 l'indirizzo di servaddr
    servaddr.sin_family = AF_INET;
    host = gethostbyname(argv[1]); //prende nome del nodo ed estrae informazioni
    printf("Nome host: %s\n", host->h_name); //per verifica mia, non del prof
    num1 = 0; // uso num1 come contatore temporaneamente
    
    while(argv[2][num1] != '\0'){ // Controllo che porta sia fatta solo da interi
        if( (argv[2][num1] < '0') || (argv[2][num1] > '9') ){
            printf("Secondo argomento non intero\n"); exit(2);
        }
        num1++;
    }
    
    port = atoi(argv[2]); //estraggo porta
    if (port < 1024 || port > 65535){ // controllo porta sia legal
        printf("porta scorretta...\b");
        exit(2);
    }
    
    if (host==NULL){
        printf("Host not found\n");
        exit(2);
    }
    else{
        servaddr.sin_addr.s_addr = ((struct in_addr*)host->h_addr_list)->s_addr; // assegno indirizzo ipv4
        // Cosa succede?
        // Chiedilo al prof, speriamo
        servaddr.sin_port = htons(port); //converto i byte se necessario
    }
    
    sd=socket(AF_INET, SOCK_DGRAM, 0); //creazione della socket
    if(sd < 0){
        perror("Errore apertura socket");
        exit(1);
    }
    if(bind(sd, (struct sockaddr *) &clientaddr, sizeof(clientaddr)) < 0){ //abbino socket e cliente
        perror("Errore binding");
        exit(1);
    }
    printf("Inserisci il primo operando (int), EOF per terminare:");
    
    while((ok = scanf("%i", &num1))!=EOF){
        if(ok != 1){
            do{
                c=getchar();
                printf("%c", c);
            }while(c!='\n');
            printf("Inserisci il primo operando (int), EOF per terminare: ");
            continue;
        }
        req.op1 = htonl(num1); // ?
        gets(okstr); // consumo resto della linea
        
        printf("Inserisci il secondo operando (int), EOF per terminare: ");
        while (scanf("%i", &num2) != 1){
            do{
                c=getchar();
                printf("%c ", c);
                
            } while (c!= '\n');
            printf("Secondo operando (intero): ");
        }
        req.op2 = htonl(num2); // ?
        gets(okstr); // consumo resto della linea
        printf("Stringa letta: %s\n", okstr); //?
        
        do{
            printf("Operazione (+ = addizione, - = sottrazione, * = motiplicazione, / = divisione\n");
            c = getchar();
        } while (c!='+' && c !='-' && c!='*' && c !='/');
        req.tipoOp=c;
        gets(okstr); //consumo resto della linea
        
        printf("Operazione richiesta: %ld %c %ld \n", ntohl(req.op1), req.tipoOp, ntohl(req.op2));
        
        len = sizeof(servaddr);
        if(sendto(sd, &req, sizeof(Request), 0, (struct sockaddr *) &servaddr, len) < 0){
            perror("sendto");
            continue;
        }
        
        printf("Attesa del risultato...\n");
        if(recvfrom(sd, &ris, sizeof(ris), 0, (struct sockaddr *) &servaddr, &len) < 0){
            perror("recvfrom");
            continue;
        }
        
        printf("Esito dell'operazione: %i\n", ntohl(ris));
        
        printf("Inserisci il primo operando (int), EOF per terminare:");
    }
    
    close(sd);
    printf("\nCliente: termino ...\n");
    exit(0);
    
}
