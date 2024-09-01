#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/wait.h>
#include <unistd.h>

char Fin[200], carattere;
int pid[2], limite;

void handler1(int signum){
    printf("%s contiene più del 50%% di caratteri %c\n", Fin, carattere);
}

void handler2(int signum){
    printf("%s contiene meno del 50%% di caratteri %c\n", Fin, carattere);
}

void handler3(int signum){
    printf("%d: sto terminando per lentezza\n",getpid());
    kill(getpid(), SIGTERM);
}

void handler4(int signum){
    kill(pid[0], SIGALRM);
    kill(pid[1], SIGALRM);
    sleep(limite*2);
    kill(getpid(), SIGALRM);
    
}

int main(int argc, char** argv){ //PROBLEMA NEL KILLARE DOPO TOT TEMPO

    if(argc!=4)
        perror("errore di inserimento\n");
    char msg[200];
    
    char carattere = argv[2][0];
    char buff;
    
    int limite = atoi(argv[3]);
    
    alarm(limite);

    int fd, ricorrenze=0, lunghezza=0, fds[2], ppid, status;
    
    pipe(fds);
    
    for(int i=0; i<2; i++){
        pid[0]=fork();
        if(pid[0]==0){
            ppid=getppid(); //pid di P0
            if(i==0){//P1
                signal(SIGALRM, handler3);
                fd=open(Fin, O_RDONLY);
                while(read(fd, &buff, 1)>0){
                    if(carattere==buff)
                        ricorrenze++;
                }
                close(fd);
                close(fds[0]);
                sprintf(msg, "%d", ricorrenze);
                write(fds[1], msg, strlen(msg));
                sleep(limite + 5);
                printf("sto terminando 1\n");
                exit(0);
            }
            else{//P2
                signal(SIGALRM, handler3);
                fd=open(Fin, O_RDONLY);
                while(read(fd, &buff, 1)>0){
                    lunghezza++;
                }
                close(fd);
                close(fds[1]);
                read(fds[0], msg, 200);
                ricorrenze=atoi(msg);
                if(ricorrenze>lunghezza/2)
                    kill(ppid, SIGUSR1);
                else
                    kill(ppid, SIGUSR2);
                sleep(limite + 5);
                printf("sto terminando 2\n");
                exit(0);
            }
        }
    }
    
    for(int i=0; i<2; i++){
        if(pid[i]>0){//P0
            signal(SIGUSR1, handler1);
            signal(SIGUSR2, handler2);
            signal(SIGALRM, handler4);
            
        }
    }
    
    for(int i=0;i<2;i++){
        pid[i]=wait(&status);
    }
}
