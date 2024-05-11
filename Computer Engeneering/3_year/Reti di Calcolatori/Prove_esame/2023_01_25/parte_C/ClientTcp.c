//Messori Noemi 0000977938 Compito1
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <errno.h>
#include <fcntl.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <sys/select.h>

#define h_addr h_addr_list[0]
#define DIM_BUFF 1024
#define WORD_LENGHT 20



int main(int argc, char *argv[])
{
    int sd, port, nread, fd;
    
    char id[WORD_LENGHT];
    struct hostent *host;
    struct sockaddr_in servaddr;

    char folder[DIM_BUFF];
    DIR *mainDir;
    struct dirent *cur;
    int fileSize=-1, filefd, nread1;
    char nome_image[WORD_LENGHT];
    char buff[DIM_BUFF];

    /* CONTROLLO ARGOMENTI ---------------------------------- */
    if (argc != 3)
    {
        printf("Error:%s serverAddress serverPort\n", argv[0]);
        exit(1);
    }

    /* INIZIALIZZAZIONE INDIRIZZO SERVER -------------------------- */
    memset((char *)&servaddr, 0, sizeof(struct sockaddr_in));
    servaddr.sin_family = AF_INET;
    host = gethostbyname(argv[1]);

    /*VERIFICA INTERO*/
    nread = 0;
    while (argv[2][nread] != '\0')
    {
        if ((argv[2][nread] < '0') || (argv[2][nread] > '9'))
        {
            printf("Secondo argomento non intero\n");
            exit(2);
        }
        nread++;
    }
    port = atoi(argv[2]);

    /* VERIFICA PORT e HOST */
    if (port < 1024 || port > 65535)
    {
        printf("%s = porta scorretta...\n", argv[2]);
        exit(2);
    }
    if (host == NULL)
    {
        printf("%s not found in /etc/hosts\n", argv[1]);
        exit(2);
    }
    else
    {
        servaddr.sin_addr.s_addr = ((struct in_addr *)(host->h_addr))->s_addr;
        servaddr.sin_port = htons(port);
    }
    /* CREAZIONE SOCKET ------------------------------------ */
    sd = socket(AF_INET, SOCK_STREAM, 0);
    if (sd < 0)
    {
        perror("apertura socket");
        exit(1);
    }
    printf("Client: creata la socket sd=%d\n", sd);

    /* Operazione di BIND implicita nella connect */
    if (connect(sd, (struct sockaddr *)&servaddr, sizeof(struct sockaddr)) < 0)
    {
        perror("connect");
        exit(1);
    }
    printf("Client: connect ok\n");

    /* CORPO DEL CLIENT:
	ciclo di accettazione di richieste da utente ------- */
    char *operazione = "Inserire id, EOF per terminare: ";
    printf("%s", operazione);

    while (gets(id))
    {
        printf("upload immagini relative a %s...\n", id);

        write(sd, id, strlen(id) + 1);
        printf("Inviato nome id...\n");


        strcpy(folder, id);
        strcat(folder,"_img");

        mainDir = opendir(folder);
        if (mainDir == NULL)
        {
            printf("Directory folder non valida\n");
            
            write(sd,&fileSize,4); //inizializzata a -1
        }
        else
        {
            while ((cur = readdir(mainDir)) != NULL)
            {
                
                    strcpy(nome_image, folder);
                    strcat(strcat(nome_image, "/"), cur->d_name);
                    //directory del folder

                    filefd=open(nome_image,O_RDONLY);                    
                    fileSize=lseek(filefd,0,SEEK_END);

                    //per prima cosa mando dimensione
                    write(sd,&fileSize,sizeof(fileSize));
                    lseek(filefd,0,SEEK_SET);

                    fileSize=strlen(cur->d_name)+1;

                    write(sd,&fileSize,4); //scrivo la size del nome file-immagine
                    write(sd,cur->d_name,strlen(cur->d_name)+1); //scrivo il nome del file-immagine

                    printf("Trasferisco:\t%s\t%d\n", nome_image,fileSize);
                    
                    while((nread1=read(filefd,buff,sizeof(buff))) > 0){
                        write(sd,buff,nread1);
                    }
                  
                
            }

            // Finiti i files
            fileSize=-1;
            write(sd,&fileSize,4); // Print carattere terminatore
            printf("File terminati\n\n");
        }

        printf("Operazione terminata\n\n");

        printf("%s", operazione);
    } //while

    close(sd);
    printf("\nClient: termino...\n");
    exit(0);
}