#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#define DIM 100

int pid1, pid2;

void handler(int signum){
    if(signum==SIGUSR1)
        printf("%d ha atteso la terminazione di %d\n", getpid(), pid2);
}

void main(int argc, char** argv){

    if(argc!=4){
        printf("errore numero argomenti\n");
        exit(1);
    }
    
    char Fin[100], C;
    int n;
    
    //CONTROLLO E INSERISCO PARAMETRI
    if(argv[1][0]!='/'){
        printf("%s non è un path assoluto\n", argv[1]);
        exit(2);
    }
    if(atoi(argv[2])<0){
        printf("Il 3° argomento deve essere un intero positivo\n");
        exit(2);
    }
    strcpy(Fin, argv[1]);
    n=atoi(argv[2]);
    C=argv[3][0];
    
    //CREZIONE FIGLI
    int fd, fdt, fds[2];
    char buff;
    
    pipe(fds);
    
    pid1=fork();
    if(pid1==0){ //P1
        int pid1_terminato, status;
        char carattere[2];
        pid1_terminato=wait(&status);
        sprintf(carattere, "%c", C);
        close(fds[0]);
        close(1);
        dup(fds[1]);
        close(fds[1]);
        execlp("grep", "grep", carattere, "/tmp/temp");
        printf("execlp error\n");
        exit(3);
    }
    
    pid2=fork();
    if(pid2==0){ //P2
        close(fds[1]);
        close(fds[0]);
        int righe=-1, caratteri=0;
        fd=open(Fin, O_RDONLY);
        fdt=open("/tmp/temp", O_WRONLY | O_CREAT | O_TRUNC, 0777);
        while(read(fd, &buff, 1)>0){
            caratteri++;
            if(buff=='\n'){
                righe++;
                printf("Riga %d con %d caratteri:", righe, caratteri);
                if(righe%n==0){
                    printf(" selezionata");
                    char buffer[caratteri];
                    lseek(fd, -caratteri, 1);
                    read(fd, buffer, caratteri);
                    printf(" , contenuto: %s\n", buffer);
                    write(fdt, buffer, caratteri);
                }
                else{
                    printf(" non selezionata\n");
                    caratteri=0;
                }
            }
        }
        close(fd);
        close(fdt);
        exit(0);
    }
    
    close(fds[1]);
    while(read(fds[0], &buff, 1)>0){
        printf("%c", buff);
    }
}
