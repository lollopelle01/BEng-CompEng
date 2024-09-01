#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/wait.h>
#include <unistd.h>
char Fin[200], Fout[200], C;
int Tout, pid1, pid2, fdIn, fdOut, fdTemp, fds[2], caratteri=0, righe=0;;

void handler(int signum){
    if(signum==SIGALRM)
        kill(pid1, SIGUSR1);
    if(signum==SIGUSR1){
        printf("P1: in totale ho inviato %d volte il carattere newline al processo P2\n", righe);
        kill(pid1, SIGKILL);
        kill(pid2, SIGKILL);
    }
    
}

void main(int argc, char** argv){
    if(argc!=4){
        printf("Errore numero argomenti\n");
        exit(-1);
    }
    
    if(atoi(argv[2])<0){
        printf("il numero deve essere positivo\n");
        exit(-2);
    }
    
    Tout=atoi(argv[2]);
    strcpy(Fin, argv[1]);
    strcpy(Fout, argv[3]);
    
    signal(SIGALRM, handler);
    alarm(Tout);
    
   int p=pipe(fds);
    if(p<0) {printf("pipe error\n");}
    
    pid1=fork();
    if(pid1==0){ //P1
        
        close(fds[0]);
        signal(SIGUSR1, handler);
        
        fdIn=open(Fin, O_RDONLY);
        if(fdIn<0) {printf("%s non esiste\n", Fin);}
        int seek, r;
        
        while(1){ //all'infinito
            seek=lseek(fdIn, -1, 2);
            while(seek>0 && (r=read(fdIn, &C, 1))>0){
                if(C=='\n')
                    righe++;
                write(fds[1], &C, 1);
                seek=lseek(fdIn, -2, 1);
            }
        }
    }
    
    pid2=fork();
    if(pid2==0){ //P2
        close(fds[1]);
        
        fdOut=open(Fout, O_CREAT | O_WRONLY, 077);

        close(0); //chiudo sdtin 
        dup(fds[0]); //leggo dal lato lettura della pipe
        
        close(1); //chiudo stdout
        dup(fdOut); //scrivo in Fout
        
        execl("/bin/cat", "cat", NULL);
        printf("Execlp error\n");
        exit(-4);
    }
    
    while(1){
        printf("%d\n", getpid());
        sleep(2);
    }
}
