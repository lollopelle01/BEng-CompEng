#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <time.h>

// soluzione compito syscall del 11 settembre 2018

int pid[2];
char Fout[256];

void p1(int out, char *fin, int N);
void p0(int in);
void handler(int sig);
void wait_child();



int main(int argc, char *argv[]) {
	int p[2], i;

	// controllo parametri
	if(argc != 4) {
		printf("Sintassi errata\nSintassi : %s Fin N Fout\n", argv[0]);
		exit(1);
	}
	if (argv[1][0] != '/') {
		printf("%s deve essere un nome assoluto\n", argv[1]);
		exit(-1);
	}

	int N;
	N = atoi(argv[2]);
	if (N <= 0) {
		printf("N deve essere un intero positivo\n");
		exit(-2);
	}
	printf("N=%d\n",N);

	if (argv[3][0] != '/') {
		printf("%s deve essere un nome assoluto\n", argv[3]);
		exit(-3);
	}
	strcpy(Fout, argv[3]);

	// pipe tra P1 e P0 :
	if(pipe(p) < 0) {
		printf("P0: Impossibile creare pipe\n");
		exit(3);
	}

	signal(SIGUSR1, handler);
	// creo i due figli P1 e P2 :
	for (i = 0; i < 2; i++) {
		pid[i] = fork();
		if (pid[i] < 0) {
			printf("P0: Impossibile creare processo figlio P%d\n", i + 1);
			exit(3);
		}
		else if (pid[i] == 0) {
			if (i == 0) {  // P1
				close(p[0]);	// chiudo pipe in lettura con P0
				p1(p[1], argv[1], N);
			}
			else if (i == 1)  {      // P2
				close(p[0]);
				close(p[1]);  
				pause();
				exit(0);
			}
			
		}
	}
	// P0
	close(p[0]);
	signal(SIGUSR1, SIG_DFL);
	
	p0(p[0]);
	close(p[1]);

	// terminazione figli :
	wait_child();
	wait_child();

	return 0;

}


// P1: legge Fin e invia a P0 i char in posizione multipla di N
void p1(int out, char *fin, int N) {
printf("Sono P1: fin=%s \n", fin);
	char c;
	int fd, count=0;

	fd = open(fin, O_RDONLY);
	if (fd < 0) {
		perror("open");
		exit(1);
	}
printf("P1: fin=%s Letto %c",fin, c);
		
	while (read(fd, &c, sizeof(char)) > 0) {
		printf("P1: fin=%s Letto %c",fin, c);
		if( (count%N )==0){
			if (write(out, &c, sizeof(char)) != sizeof(char)) {
				printf("P1: impossibile comunicare con P0, errore nella write\n");
				exit(5);
			}
			printf("P1: scritto %c",c);
		}
		count++;
	}
	
	close(out);
	close(fd);
	exit(0);
}


// P0: stampa su Fout quello che riceve da P1, inserendo 'A' prima di ogni char
void p0(int in) {
	int fd;
    	char c, a='A';
	char buff[50];

	if ((fd = open(Fout, O_CREAT|O_TRUNC|O_WRONLY, 0755)) < 0 ) {
		sprintf(buff, "P0: errore open di %s\n",Fout);		
		perror(buff);
		exit(1);
	}
	 
	printf("P0: scrivo su %s quello che mi ha inviato P1\n",Fout);
	while (read(in, &c, sizeof(char)) > 0) {
		printf("Writing char: A%c\n",c);
		write(fd, &a, sizeof(char));
		write(fd, &c, sizeof(char));
	}
	//P1 ha finito le cose da inviarmi e ha chiusto la pipe. Posso avvisare P2
	kill(pid[1], SIGUSR1);

	close(in);
	close(fd);
	exit(0);
}


// handler del segnale SIGUSR1 inviato da P0 a P2 :
void handler(int sig) {
	printf("P2: ricevuto segnale da P0, posso procedere\n");
	
	if (execlp("cat", "cat", Fout,(char *) 0) < 0) {
		printf("P2: errore exec\n");
		exit(1);
	}

} // handler


void wait_child() {
    int pid_terminated, status;
    pid_terminated = wait(&status);
    if (pid_terminated < 0)    {
        fprintf(stderr, "P0: errore in wait. \n");
        exit(EXIT_FAILURE);
    }
    if (WIFEXITED(status)) {
        printf("P0: terminazione volontaria del figlio %d con stato %d\n", pid_terminated, WEXITSTATUS(status));
        if (WEXITSTATUS(status) == EXIT_FAILURE) {
            fprintf(stderr, "P0: errore nella terminazione del figlio pid_terminated\n");
            exit(EXIT_FAILURE);
        }
    }
    else if (WIFSIGNALED(status)) {
        fprintf(stderr, "P0: terminazione involontaria del figlio %d a causa del segnale %d\n", pid_terminated,WTERMSIG(status));
        exit(EXIT_FAILURE);
    }
} // wait_child


