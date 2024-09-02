#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#define LINE_LENGTH 256
typedef char nome[13]; // 13 è la dimensione massima di una stringa

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
    nome targa;
    char fileName[256], buff;
    int num_immagini, fd;
    long dim_file;
    printf("Inserire nome della targa, EOF per terminare: ");
    while (scanf("%s", targa) == 1) {
        printf("Invio la targa: %s\n", targa);
        nwrite = write(sd, targa, strlen(targa));

        // Lettura risposta dal server
        read(sd, &buffChar, sizeof(char));
        if (buffChar == 'S') {
            printf("entro nel folder:\n");
            read(sd, &num_immagini, sizeof(int));
            if(num_immagini > 0){
                printf("Entro in %s_img:\n", targa);
                for(int i=0; i<num_immagini; i++){
                    read(sd, fileName, sizeof(fileName));
                    printf("%d) Scarico %s ...\n", i+1, fileName);
                    fd = open(fileName, O_CREAT | O_RDWR, 0644);
                    read(sd, &dim_file, sizeof(long));
                    for(long j=0; j<dim_file; j++){
                        read(sd, &buff, 1);
                        write(fd, &buff, 1);
                    }
                    printf("   SCARICATO\n");
                }
            }
            else{
                printf("Non ci sono immagini in %s_img\n", targa);
            }

        } else {
            printf("targa non presente sul server\n");
        }
        printf("Nome della targa, EOF per terminare: ");
    }
    /* Chiusura socket in ricezione */
    close(sd);
    printf("\nClient: termino...\n");
    exit(0);
}