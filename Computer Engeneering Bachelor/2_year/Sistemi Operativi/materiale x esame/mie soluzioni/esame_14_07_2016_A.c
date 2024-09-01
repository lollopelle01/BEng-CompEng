#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/wait.h>
#include <unistd.h>

int N, A, pid1, pid2;

void handler(int signum){
    if(signum==SIGUSR1){
        printf("la riga %d contiene meno di %d caratteri\n", N, A);
        kill(pid1, SIGKILL);
        kill(pid2, SIGKILL);
        exit(0);
    }
    if(signum==SIGUSR2){
        printf("la riga %d contiene più di %d caratteri\n", N, A);
        kill(pid1, SIGKILL);
        kill(pid2, SIGKILL);
        exit(0);
    }
}

void main(int argc, char** argv){
    
    char Fin[200];
    
    //CONTROLLO ARGOMENTI
    if(argc!=4)
        printf("errore num argomenti\n");
    if(argv[1][0]!='/')
        printf("Fin non è un path assoluto\n");
    if(atoi(argv[2])<0)
        printf("N deve essere positivo\n");
    if(atoi(argv[3])<0)
        printf("A deve essere positivo\n");
    
    N=atoi(argv[2]);
    A=atoi(argv[3]);
    strcpy(Fin, argv[1]);
    
    //GENERAZIONE FIGLI
    int pid1, pid2, fd, fds[2], caratteri=0, righe=0;
    char buff;
    
    pipe(fds);
    
    signal(SIGUSR1, handler);
    signal(SIGUSR2, handler);
    
    printf("sono P0: %d\n", getpid());
    
    pid1=fork();
    if(pid1==0){ //P1
        printf("sono P1: %d\n", getpid()); 
        close(fds[0]);
        close(1);
        dup(fds[1]);
        close(fds[1]);
        //printf("%d: sto per eseguire execlp\n", getpid());
        execlp("rev", "rev", Fin, NULL);
        printf("exec error\n");
    }
    else;
    
    pid2=fork();
    if(pid2==0){ //P2
        printf("sono P2: %d\n", getpid());
        close(fds[1]);
        caratteri=0;
        int r;
        int i=0;
        while(r=read(fds[0], &buff, 1)>0){
            caratteri++;
            printf("%d) %c\n", i, buff);
            i++;
            if(buff=='\n')
                righe++;
            if(righe==N){
                if(caratteri<A)
                    kill(getppid(), SIGUSR1);
                else
                    kill(getppid(), SIGUSR2);
            }
            else if(buff=='\n')
                caratteri=0;
        }
        printf("%d: sono terminato\n", getpid());
        exit(0);
    }
    else;
    
    int pid_P_terminato, stato;
    for(int i=0; i<2; i++){
        pid_P_terminato=wait(&stato);
    }
}
