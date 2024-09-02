// pellegrino lorenzo 0000971455

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

#define LINE_LENGTH 256
#define WORD_LENGTH 30

int main(int argc, char *argv[]) {
    int                port, nread, sd, nwrite;
    struct hostent    *host;
    struct sockaddr_in servaddr;

    // per servizi
    char id[LINE_LENGTH], folder[2*LINE_LENGTH + 5], c; // può essere al massimo id + / + id + _img
    int fd;
    DIR * dir;
    struct dirent dd;
    struct stat stat;

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
    printf("Inserire l'id , EOF per terminare: ");
    while (gets(id)) {

        /* Invio parametri e ricezione risultato*/
        folder[0]='\0';
        strcat(folder, id);
        strcat(folder, "/");
        if((dir=opendir(folder))!=NULL){
            folder[0]='\0';
            strcat(folder, id);
            strcat(folder, "/");
            while((dd=readdir(dir))!=NULL){
                if(dd->d_type==DT_REG){
                    strcat(folder, dd->d_name);
                    fd=open(folder, O_RDONLY);
                    fstat(fd, &stat);
                    write(sd, dd->d_name, 256);
                    write(sd, &stat->st_size, sizeof(stat->st_size));
                    while(read(fd, &c, 1)>0){
                        write(sd, &c, 1);
                    }
                }
            }
            strcp(folder, "fine.txt");
            write(sd, folder, 256);
        }
        else{
            printf("%s inesistente\n", folder);
        }


        printf("Inserire l'id , EOF per terminare: ");
    }
    /* Chiusura socket in ricezione */
    close(sd);
    printf("\nClient: termino...\n");
    exit(0);
}