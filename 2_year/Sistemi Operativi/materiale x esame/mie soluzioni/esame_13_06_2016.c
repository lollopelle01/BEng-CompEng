#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/wait.h>
#include <unistd.h>
#define DIM 200

char Fin[DIM], Fout[DIM];
int fdOut, pid1;

void handler(int signum){
    if(signum==SIGALRM){
        printf("P1 terminato, troppo lento\n");
        kill(pid1, SIGKILL);
    }
    else{
        char buffer[DIM];
        fdOut=creat(Fout, 00640);
        if(signum==SIGUSR1){
            sprintf(buffer, "%s contiene un numero pari di righe", Fin);
            printf("%s contiene un numero pari di righe\n", Fin);
        }
        if(signum==SIGUSR2){
            sprintf(buffer, "%s contiene un numero dispari di righe", Fin);
            printf("%s contiene un numero dispari di righe\n", Fin);
        }
            
        write(fdOut, buffer, strlen(buffer));
        printf("handler partito\n");
    }
}

void main(int argc, char** argv){

    if(argc!=5)
        printf("errore di inserimento\n");
    
    char C;
    int T;

    //ESTRAGGO E CONTROLLO DATI
    strcpy(Fin, argv[1]); 
    strcpy(Fout, argv[2]);
    C=argv[3][0];
    T=atoi(argv[4]);
    
    if(C!='P' && C!='D')
        printf("il carattere non è nè P nè D\n");
    if(argv[1][0]!='/')
        printf("%s non è un path assoluto\n", Fin);
    if(argv[2][0]!='/')
        printf("%s non è un path assoluto\n", Fout);
    if(T<0)
        printf("tempo negativo\n");
        
    //CREAZIONE FIGLI
    int pid1, pid2, fdIn, linee=0, fds[2];
    char buff, buffer[DIM];
    
    pipe(fds);
    
    signal(SIGUSR1, handler);
    signal(SIGUSR2, handler);
    signal(SIGALRM, handler);
    
    alarm(T);
    
    pid1=fork();
    if(pid1==0){ //P1
        printf("P1 è %d\n", getpid());
        sleep(10);
        close(fds[1]);
        read(fds[0], buffer, strlen(buffer));
        linee=atoi(buffer);
        if(C=='P' && linee%2==0)
            kill(getppid(), SIGUSR1);
        if(C=='D' && linee%2!=0)
            kill(getppid(), SIGUSR2);
        exit(0);
    }
    
    pid2=fork();
    if(pid2==0){ //P2
        printf("P2 è %d\n", getpid());
        fdIn=open(Fin, O_RDONLY);
        while(read(fdIn, &buff, 1)>0){
            if(buff=='\n')
                linee++;
        }
        linee++;
        printf("linee: %d\n", linee);
        close(fdIn);
        
        close(fds[0]);
        sprintf(buffer, "%d", linee);
        write(fds[1], buffer, strlen(buffer));
        exit(0);
    }
    else
    
    //PADRE P0
    close(fds[0]);
    close(fds[1]);
    
    int processo_terminato, status;
    for(int i=0; i<2; i++){
        processo_terminato=wait(&status);
    }
}
