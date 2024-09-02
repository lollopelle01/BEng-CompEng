#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>


int S,G,pid[2];
char x;
void leggi_comunica(int fpd,char* file);
void father_handler(int signo);
void wait_child();
void moltiplica(int pfdP1,int pfdP0);
void verifica(int pfdP2);

int main(int argc,char**argv){
	int i,fd,pfd[2],pfd2[2];
	//Controllo parametri
	
	if(argc!=5){
		fprintf(stderr,"Numero di argomenti errato\n");
        fprintf(stderr,"Usage:\n\t%s S Fin x G\n", argv[0]);
        exit(1);
	}	
    if((S=atoi(argv[1]))<0 || (G=atoi(argv[4]))<0){
        fprintf(stderr,"Intero S e G devono essere positivi\n");
        exit(1);
    }
    
    if(argv[2][0] != '/') {
        printf("%s non e' un percorso assoluto!\n", argv[3]);
        exit(-2);
    }
    
    if(strlen(argv[3])>1){
        fprintf(stderr,"X deve essere un carattere\n");
        exit(1);
    }
    x=argv[3][0];
    
    if(pipe(pfd)<0){
        fprintf(stderr,"Errore nella creazione della pipe tra P1 e P2\n");
        exit(1);
    }
    if(pipe(pfd2)<0){
        fprintf(stderr,"Errore nella creazione della pipe tra P0 e P1\n");
        exit(1);
    }
    
    signal(SIGALRM,father_handler);
            
    pid[0]=fork();
    if(pid[0]==0){//P1
        close(pfd[0]);//chiudo canale lettura con P2
        close(pfd2[0]);//chiudo canale lettura tra P2 e P0
        close(pfd2[1]);//chiudo canale scrittura tra P2 e P0
        leggi_comunica(pfd[1],argv[2]);
    }
    else if(pid[0]>0){//P0
        pid[1]=fork();
        if(pid[1]==0){//P2
            close(pfd[1]);//chiudo canale scrittura con P1
            close(pfd2[0]);//chiudo canale lettura con P0
            moltiplica(pfd[0],pfd2[1]);
        }
        else if(pid[1]>0){//P0
            alarm(S);
            close(pfd[0]);//chiudo canale lettura con P1
            close(pfd[1]);//chiudo canale scrittura con P1
            close(pfd2[1]);//chiudo canale scrittura con P2
            verifica(pfd2[0]);
            for(i=0;i<2;i++)
                wait_child();
            
        }
        else{
            fprintf(stderr,"P0: errore nella fork per P2\n");
            exit(1); 
        }
    }
    else{
       fprintf(stderr,"P0: errore nella fork per P1\n");
        exit(1); 
    }
    
}

void leggi_comunica(int pfd,char* file){
    int fd,nread,written,count;
    char temp;
    printf("\nSono P1,agisco.\n");
    fd=open(file,O_RDONLY);
    if(fd<0){
        fprintf(stderr,"P1: Errore apertura file %s\n",file);
        exit(1); 
    }
    count=0;
    while((nread=read(fd,&temp,sizeof(char)))>0){
        //printf("%c",temp);
        if(temp=='\n'){
            printf("\nP1: Letta fine riga,valore count: %d\n",count);
            written=write(pfd,&count,sizeof(int));
            if(written<0){
                fprintf(stderr,"P1: Errore scrittura su file %s\n",file);
                exit(1); 
            }
            count=0;
        }
        else{
            if(temp==x)
                count++;
        }
    }
    if(nread<0){
        fprintf(stderr,"P1: Errore nella lettura da file %s\n",file);
        close(fd);
        exit(1); 
    }
    close(pfd);
    close(fd);
    exit(0); 
}

void father_handler(int signo){
    if(signo==SIGALRM){
        printf("\nSono P0,ho ricevuto SIGALRM,termino P2.\n");
        kill(pid[1],SIGKILL);
    }
}

void wait_child() {
    int pid_terminated,status;
    pid_terminated=wait(&status);
    if(WIFEXITED(status))
        printf("\nP0: terminazione volontaria del figlio %d con stato %d\n",
                pid_terminated, WEXITSTATUS(status));
    else if(WIFSIGNALED(status))
        printf("\nP0: terminazione involontaria del figlio %d a causa del segnale %d\n",
                pid_terminated,WTERMSIG(status));
}

void moltiplica(int pfdP1,int pfdP0){
    int N,M,nread,written;
    M=1;
    //sleep(10); //solo per testare il funzionamento della alarm
    printf("\nSono P2,agisco.\n");
    while((nread=read(pfdP1,&N,sizeof(int)))>0){
        if(N!=0){
            M = M * N;
        }
    }
    if(nread<0){
        fprintf(stderr,"P2: Errore nella lettura da pipe\n");
        close(pfdP1);
        exit(1); 
    }
    close(pfdP1);
    written=write(pfdP0,&M,sizeof(int));
    if(written<0){
        fprintf(stderr,"P2: Errore scrittura su pipe\n");
        close(pfdP0);
        exit(1); 
    }
    close(pfdP0);
    exit(0);
}

void verifica(int pfdP2){
    int nread,M;
    nread=read(pfdP2,&M,sizeof(int));
    if(M==G)
        printf("P0: Hai indovinato. M vale %d\n",M);
    else
        printf("P0: No! M valeva %d! Ritenta, sarai più fortunato.\n",M);
}