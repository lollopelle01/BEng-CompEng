#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <errno.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>

#define LENGHT 256

int main(int argc, char **argv){ // argv = { ./nomeCodice, porta}
	int sd, port, len, ris, fd, ok, lunghezza_max = 0, num1;
	const int on = 1;
	struct sockaddr_in cliaddr, servaddr;
	struct hostent *clienthost;
	char fileName[LENGHT], c;
    
    /* CONTROLLO ARGOMENTI ---------------------------------- */
	if(argc!=2){
		printf("Error: %s port\n", argv[0]);
		exit(1);
	}
	else{
		num1=0;
		while( argv[1][num1]!= '\0' ){
			if((argv[1][num1] < '0') || (argv[1][num1] > '9')){
				printf("Secondo argomento non intero\n");
				printf("Error: %s port\n", argv[0]);
				exit(2);
			}
			num1++;
		}  	
	  	port = atoi(argv[1]);
  		if (port < 1024 || port > 65535){
		      printf("Error: %s port\n", argv[0]);
		      printf("1024 <= port <= 65535\n");
		      exit(2);  	
  		}
	}
	
	/* INIZIALIZZAZIONE INDIRIZZO SERVER ---------------------------------- */
	memset ((char *)&servaddr, 0, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = INADDR_ANY;  
	servaddr.sin_port = htons(port);  

	/* CREAZIONE, SETAGGIO OPZIONI E CONNESSIONE SOCKET -------------------- */
	sd=socket(AF_INET, SOCK_DGRAM, 0);
	if(sd <0){perror("creazione socket "); exit(1);}
	printf("Server: creata la socket, sd=%d\n", sd);

	if(setsockopt(sd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on))<0)
	{perror("set opzioni socket "); exit(1);}
	printf("Server: set opzioni socket ok\n");

	if(bind(sd,(struct sockaddr *) &servaddr, sizeof(servaddr))<0)
	{perror("bind socket "); exit(1);}
	printf("Server: bind socket ok\n");
    
    /* CICLO DI RICEZIONE RICHIESTE ------------------------------------------ */
	for(;;){
		len=sizeof(struct sockaddr_in);
		if (recvfrom(sd, fileName, sizeof(fileName), 0, (struct sockaddr *)&cliaddr, &len)<0)
		{perror("recvfrom "); continue;}
        
        if((fd=open(fileName, O_RDONLY))<0){
            ris = -1;
            printf("File %s non esistente nel file system\n", fileName);
            continue;
        }
        
		while((ok=read(fd, &c, 1))>0){
            ris++;
            if(c=='\n' || c==' ') {
                if(ris>lunghezza_max)   {lunghezza_max = ris;}
                ris = 0;
            }
        }
        lunghezza_max--; //non considero '\0'
        
        printf("La parola più lunga di %s aveva %d caratteri\n", fileName, lunghezza_max);
        
		if (sendto(sd, &lunghezza_max, sizeof(int), 0, (struct sockaddr *)&cliaddr, len)<0)
		{perror("sendto "); continue;}
	} //for
}
