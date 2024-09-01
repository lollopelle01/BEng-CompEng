// pellegrino lorenzo 0000971455 compito1
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

#define LINE_LENGTH 256
#define LENGHT_NAME 30
#define N 5
#define K 3

int main(int argc, char *argv[]) {
    int                port, nread, sd, nwrite;
    char               buffChar;
    struct hostent    *host;
    struct sockaddr_in servaddr;

    // per algoritmo
    char comando, buff[ LENGHT_NAME + 1 + (2+1) + 1 + K*(LENGHT_NAME+1)];
    int i;

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
    printf("Inserire S per visualizzare tabella, EOF per terminare: ");
    while (scanf("%c", &comando) == 1) {
        nwrite = write(sd, &comando, 1);

        // Lettura risposta dal server
        read(sd, &buffChar, sizeof(char));
        if (buffChar == 'S') {
            for(i=0; i<N; i++){
                read(sd, buff, sizeof(buff));
                write(1, buff, sizeof(buff));
            }    
        }
        else {
            printf("Errore nella tabella\n");
        }
        gets(buffChar);
        printf("Inserire S per visualizzare tabella, EOF per terminare: ");
    }
    /* Chiusura socket in ricezione */
    close(sd);
    printf("\nClient: termino...\n");
    exit(0);
}