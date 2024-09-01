#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#define DIM 100

int N, pid1, pid2;
char B;

void handler(int signum){
    if(signum==SIGUSR1){
        printf("La riga %d contiene il carattere %c\n", N, B);
        kill(pid1, SIGKILL);
        kill(pid2, SIGKILL);
        
    }
    if(signum==SIGUSR2){
        printf("La riga %d non contiene il carattere %c\n", N, B);
        kill(pid1, SIGKILL);
        kill(pid2, SIGKILL);
    }
    
}

void main(int argc, char** argv){
    if(argc!=4)
        printf("errore numero argomenti\n");
    
    char Fin[DIM];
    
    //CONTROLLO ED ESTRAZIONE DATI
    if(argv[1][0]!='/')
        printf("%s non è un path assoluto\n", argv[1][0]);
    if(atoi(argv[2])<0)
        printf("inserimento di numero negativo\n");
    
    strcpy(Fin, argv[1]);
    N=atoi(argv[2]);
    B=argv[3][0];
    
    int pid1, pid2, fds[2];
    char buff;
    
    signal(SIGUSR1, handler);
    signal(SIGUSR2, handler);
    
    pipe(fds);
    
    //CREAZIONE FIGLI
    pid1=fork();
    if(pid1==0){//P1
        close(fds[0]);
        close(1);
        dup(fds[1]);
        close(fds[1]);
        execlp("sort", "sort", Fin, NULL);
        printf("execlp error\n");
    
    }

    pid2=fork();
    if(pid2==0){//P2
        int righe=0, caratteri=0;
        close(fds[1]);
        while(read(fds[0], &buff, 1)>0){
            caratteri++;
            if(buff=='\n'){
                righe++;
            }
            if(righe!=N && buff=='\n')
                caratteri=0;
            else if(righe==N){
                char buffer[caratteri];
                int trovato=0;
                lseek(fds[0], caratteri, 1);
                read(fds[0], buffer, caratteri);
                for(int i=0; i<caratteri && !trovato; i++){
                    if(buffer[i]==B)
                        trovato=1;
                }
                if(trovato==1)
                    kill(getppid(), SIGUSR1);
                else
                    kill(getppid(), SIGUSR2);
            }
        }
    }
    
    int pid_terminato, status;
    for(int i=0; i<2; i++){
        pid_terminato=wait(&status);
    }
    
    
    
    
    
}
