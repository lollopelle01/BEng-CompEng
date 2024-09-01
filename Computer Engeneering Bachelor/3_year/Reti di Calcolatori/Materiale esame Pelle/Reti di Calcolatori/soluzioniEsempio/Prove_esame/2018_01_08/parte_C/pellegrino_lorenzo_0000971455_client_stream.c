#include "lib.h"

int main(int argc, char *argv[]) {
    int                port, nread, sd, nwrite;
    char               directoryName[LINE_LENGTH], buffChar[256];
    struct hostent    *host;
    struct sockaddr_in servaddr;

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
     char tipo[8];
     int quantita;

    printf("Inserire il tipo di calza, EOF per terminare: ");
    while (scanf("%s", tipo) == 1) {
        printf("Invio il tipo di calza: %s\n", tipo);
        nwrite = write(sd, tipo, sizeof(tipo));

        // Lettura quantità calze dal server
        read(sd, &quantita, sizeof(quantita));
        if (quantita > 0) {
            Nome nomi[quantita];
            read(sd, &nomi, sizeof(nomi));
            printf("\nID trovati:\n");
            for(int i=0; i<quantita; i++){
                printf("%s\n", nomi[i]);
            }
        } else if(quantita==0){
            printf("Non ce ne sono :(\n");
        }
        else{
            printf("Errore\n");
        }
        printf("Tipo di calza, EOF per terminare: ");
    }
    /* Chiusura socket in ricezione */
    close(sd);
    printf("\nClient: termino...\n");
    exit(0);
}