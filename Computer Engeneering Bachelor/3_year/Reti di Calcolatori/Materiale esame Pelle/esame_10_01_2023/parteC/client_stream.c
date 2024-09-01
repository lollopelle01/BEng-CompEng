// pellegrino lorenzo 0000971455 prova2
#include <dirent.h>
#include <errno.h>
#include <fcntl.h>
#include <netdb.h>
#include <netinet/in.h>
#include <regex.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/select.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <sys/stat.h>
#include <unistd.h>

#define LINE_LENGTH 256

int main(int argc, char *argv[]) {
    int                port, nread, sd, nwrite;
    char               directoryName[LINE_LENGTH], buffChar;
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
    printf("Inserire nome del direttorio, EOF per terminare: ");
    char fileName[256], c;
    int fd, dimFile, i;
    while (scanf("%s", directoryName) == 1) {
        printf("Invio il nome del direttorio: %s\n", directoryName);
        nwrite = write(sd, directoryName, sizeof(directoryName));

        // Lettura risposta dal server
        read(sd, &buffChar, sizeof(char));
        if (buffChar == 'S') {
            printf("Ricevo e stampo i file nel direttorio remoto:\n");
            while (read(sd, fileName, sizeof(fileName)) > 0 && strcmp(fileName, "fine")!=0) {
                fd = open(fileName, O_CREAT | O_RDWR, 0644);
                read(sd, &dimFile, sizeof(int));
                printf("Scarico %s di %d byte...\n", fileName, dimFile);
                for(i=0; i<dimFile; i++){
                    read(sd, &c, 1);
                    write(fd, &c, 1);
                }
            }
        } else {
            printf("directory non presente sul server\n");
        }
        printf("Nome del direttorio, EOF per terminare: ");
    }
    /* Chiusura socket in ricezione */
    close(sd);
    printf("\nClient: termino...\n");
    exit(0);
}