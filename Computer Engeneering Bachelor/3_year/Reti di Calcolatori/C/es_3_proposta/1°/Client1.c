#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>

#define LENGHT 256

int main(int argc, char** argv){ // argv = {./nomeCodice, nome_nodo, porta}
    
    // VARIABILI
    struct hostent *host; //x info sul nodo
    struct sockaddr_in clientaddr, servaddr; // indirizzi socket
    int port, sd, len, ris=0, i=0, ok;
    char fileName[LENGHT];
    
    if(argc!=3){ // controllo argomenti
        printf("Errore: numero di argomenti\n");
        exit(1);
    }
    
    // inizializzazione indirizzi
    memset((char *)&clientaddr, 0, sizeof(struct sockaddr_in));
	clientaddr.sin_family = AF_INET;
	clientaddr.sin_addr.s_addr = INADDR_ANY;
    clientaddr.sin_port = 0;
    
    memset((char *)&servaddr, 0, sizeof(struct sockaddr_in));
	servaddr.sin_family = AF_INET;
	host = gethostbyname (argv[1]);
    
    /* VERIFICA INTERO */
	while( argv[2][i]!= '\0' ){
		if( (argv[2][i] < '0') || (argv[2][i] > '9') ){
			printf("Secondo argomento non intero\n");
			printf("Error:%s serverAddress serverPort\n", argv[0]);
			exit(2);
		}
		i++;
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
	
	/* CREAZIONE SOCKET ---------------------------------- */
	sd=socket(AF_INET, SOCK_DGRAM, 0);
	if(sd<0) {perror("apertura socket"); exit(1);}
	printf("Client: creata la socket sd=%d\n", sd);
    
    /* BIND SOCKET, a una porta scelta dal sistema --------------- */
	if(bind(sd,(struct sockaddr *) &clientaddr, sizeof(clientaddr))<0)
	{perror("bind socket "); exit(1);}
	printf("Client: bind socket ok, alla porta %i\n", clientaddr.sin_port);
    
    printf("Che file vuoi cercare?\n");
    
    while((ok=scanf("%s", fileName)) != EOF){
        printf("Stringa letta: %s\n", fileName);
        
        /* richiesta operazione */
		len=sizeof(servaddr);
		if(sendto(sd, fileName, sizeof(fileName), 0, (struct sockaddr *)&servaddr, len)<0){
			perror("sendto\n");
			continue;
		}
		
		/* ricezione della esistenza file (-1 o lunghezza parola trovata)*/
		printf("Attesa del risultato...\n");
		if (recvfrom(sd, &ris, sizeof(ris), 0, (struct sockaddr *)&servaddr, &len)<0){
			perror("recvfrom\n"); 
            continue;
        }
        if(ris==-1) {printf("File non esiste lato Server\n");}
        else {printf("File trovato: la lunghezza della parola più lunga è %d\n", ris);}
        
        printf("Che file vuoi cercare?\n");
    }
    close(sd);
	printf("\nClient: termino...\n");  
	exit(0);
}
