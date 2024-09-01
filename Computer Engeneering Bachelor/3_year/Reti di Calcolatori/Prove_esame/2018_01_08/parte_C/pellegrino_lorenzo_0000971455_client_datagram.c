#include "lib.h"

int main(int argc, char **argv) {
    struct hostent    *host;
    struct sockaddr_in clientaddr, servaddr;
    int                port, nread, sd, len = 0;
    char               ris, nomeFile[LINE_LENGTH], parola[LINE_LENGTH];
    RigaTabella          req;

    /* CONTROLLO ARGOMENTI ---------------------------------- */
    if (argc != 3) {
        printf("Error:%s serverAddress serverPort\n", argv[0]);
        exit(1);
    }

    /* INIZIALIZZAZIONE INDIRIZZO CLIENT -------------------------- */
    memset((char *)&clientaddr, 0, sizeof(struct sockaddr_in));
    clientaddr.sin_family = AF_INET;
    clientaddr.sin_addr.s_addr == INADDR_ANY;
    clientaddr.sin_port = 0;

    /* INIZIALIZZAZIONE INDIRIZZO SERVER -------------------------- */
    memset((char *)&servaddr, 0, sizeof(struct sockaddr_in));
    servaddr.sin_family = AF_INET;
    host                = gethostbyname(argv[1]);

    /* CONTROLLO PARAMETRI SERVER -------------------------- */
    nread = 0;
    while (argv[2][nread] != '\0') {
        if ((argv[2][nread] < '0') || (argv[2][nread] > '9')) {
            printf("Secondo argomento non intero\n");
            printf("Error:%s serverAddress serverPort\n", argv[0]);
            exit(2);
        }
        nread++;
    }
    port = atoi(argv[2]);
    if (port < 1024 || port > 65535) {
        printf("Port scorretta...");
        exit(2);
    }
    // controllo su host
    if (host == NULL) {
        printf("%s not found in /etc/hosts\n", argv[1]);
        exit(2);
    } else {
        servaddr.sin_addr.s_addr = ((struct in_addr *)(host->h_addr))->s_addr;
        servaddr.sin_port        = htons(port);
    }

    /* CREAZIONE SOCKET ---------------------------------- */
    sd = socket(AF_INET, SOCK_DGRAM, 0);
    if (sd < 0) {
        perror("apertura socket");
        exit(1);
    }
    printf("Client: creata la socket sd=%d\n", sd);

    /* BIND SOCKET, a una porta a caso ------------------- */
    if (bind(sd, (struct sockaddr *)&clientaddr, sizeof(clientaddr)) < 0) {
        perror("bind socket ");
        exit(1);
    }
    printf("Client: bind socket ok, alla porta %i\n", clientaddr.sin_port);

    /* CORPO DEL CLIENT:
     ciclo di accettazione di richieste da utente ------- */

    printf("Dammi l'identificatore, EOF per terminare: ");
    char buff[256];
    while (scanf("%s", req.identificatore) == 1) {
        printf("\nTipo: ");
        scanf("%s", req.tipo);
        gets(buff); //pulisco il buffer

        printf("\nCarbone: ");
        scanf("%c", &req.carbone);
        if(req.carbone=='\n') {printf("dio bastardo");}

        printf("\nCittà: ");
        scanf("%s", req.citta);

        printf("\nVia: ");
        scanf("%s", req.via);
        gets(buff);

        printf("\nMessaggio: ");
        gets(req.messaggio);

        /* invio richiesta */
        len = sizeof(servaddr);
        if (sendto(sd, &req, sizeof(req), 0, (struct sockaddr *)&servaddr, len) < 0) {
            perror("sendto");
            // se questo invio fallisce il client torna all'inzio del ciclo
            printf("Dammi l'identificatore, EOF per terminare: ");
            continue;
        }
        /* ricezione del risultato */
        if (recvfrom(sd, &ris, sizeof(ris), 0, (struct sockaddr *)&servaddr, &len) < 0) {
            perror("recvfrom");
            // se questo invio fallisce il client torna all'inzio del ciclo
            printf("Dammi l'identificatore, EOF per terminare: ");
            continue;
        }

        if (ris=='S') {
            printf("Aggiunta effettuata\n");
        } else {
            printf("Aggiunta fallita\n");
        }
        printf("Dammi l'identificatore, EOF per terminare: ");
    } // while
} // main