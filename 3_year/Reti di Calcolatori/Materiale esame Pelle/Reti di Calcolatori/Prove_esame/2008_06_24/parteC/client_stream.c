// pellegrino lorenzo 0000971455

#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

#define LINE_LENGTH 256
#define WORD_LENGTH 30

int main(int argc, char *argv[]) {
    int                port, nread, sd, nwrite;
    struct hostent    *host;
    struct sockaddr_in servaddr;

    // per servizi

    /* CONTROLLO ARGOMENTI ---------------------------------- */
    if (argc != 3) {
        printf("Error:%s serverAddress serverPort\n", argv[0]);
        exit(1);
    }

    /* INIZIALIZZAZIONE INDIRIZZO SERVER -------------------------- */
    memset((char *)&servaddr, 0, sizeof(struct sockaddr_in));
    servaddr.sin_family = AF_INET;
    host                = gethostbyname(argv[1]);
    if (host == NULL) {
        printf("%s not found in /etc/hosts\n", argv[1]);
        exit(1);
    }

    nread = 0;
    while (argv[2][nread] != '\0') {
        if ((argv[2][nread] < '0') || (argv[2][nread] > '9')) {
            printf("Secondo argomento non intero\n");
            exit(2);
        }
        nread++;
    }

    port = atoi(argv[2]);
    if (port < 1024 || port > 65535) {
        printf("Porta scorretta...");
        exit(2);
    }

    servaddr.sin_addr.s_addr = ((struct in_addr *)(host->h_addr))->s_addr;
    servaddr.sin_port        = htons(port);

    /* CREAZIONE SOCKET ------------------------------------ */
    sd = socket(AF_INET, SOCK_STREAM, 0);
    if (sd < 0) {
        perror("apertura socket");
        exit(3);
    }
    printf("Client: creata la socket sd=%d\n", sd);

    /* Operazione di BIND implicita nella connect */
    if (connect(sd, (struct sockaddr *)&servaddr, sizeof(struct sockaddr_in)) < 0) {
        perror("connect");
        exit(3);
    }
    printf("Client: connect ok\n");

    /* CORPO DEL CLIENT:
     ciclo di accettazione di richieste da utente ------- */
    char fileName[LINE_LENGTH], parola[WORD_LENGTH], linea[LINE_LENGTH];
    printf("Inserire nome del file , EOF per terminare: ");
    while (gets(fileName)) {

        printf("Inserisci la parola: ");
        gets(parola);

        write(sd, fileName, sizeof(fileName));
        write(sd, parola, sizeof(parola));

        /* Invio parametri e ricezione risultato*/
        while(read(sd, linea, sizeof(linea))>0 && strcmp(linea, "fine_stream_file")!=0 && strcmp(linea, "errore_lettura_file")!=0){
            printf("%s", linea);
        }
        if(strcmp(linea, "errore_lettura_file")==0){printf("Errore di lettura del file\n");}
        printf("\n/-------------------------------------------------------------/\n");

        printf("Inserire nome del file , EOF per terminare: ");
    }
    /* Chiusura socket in ricezione */
    close(sd);
    printf("\nClient: termino...\n");
    exit(0);
}