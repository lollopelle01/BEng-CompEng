#include <fcntl.h>
#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <time.h>

#define MAX_OUTPUT 500

char *fout;
int pid[2];

void p1(int in, char c);
void p2(int out, char *fin);
void handler(int sgn);
void wait_child();

int main(int argc, char *argv[]) {
	char c;
	int t, p[2], i;
	//Controllo parametri
	
	if(argc != 5) {
		printf("Usage: %s Fin Fout C T\n", argv[0]);
		exit(1);
	}
	if(argv[1][0] != '/') {
        printf("%s non e' un percorso assoluto!\n", argv[3]);
        exit(-2);
    }
	if(argv[2][0] != '/') {
        printf("%s non e' un percorso assoluto!\n", argv[3]);
        exit(-2);
    }

	c = argv[3][0];
	if(c != 'P' && c != 'D') {
		printf("C può valere solo P o D\n");
		exit(2);
	}
	
	t = atoi(argv[4]);
	if(t <= 0) {
		printf("T deve essere un intero positivo\n");
		exit(2);
	}



	signal(SIGUSR1, handler);
	signal(SIGUSR2, handler);
	signal(SIGALRM, handler);
	
	if(pipe(p) < 0) {  //un unica pipe tra P1 e P2!
		printf("P0: Impossibile creare pipe\n");
		exit(3);
	}
	
	fout = argv[2];
	
	for(i = 0; i < 2; i++) {
		pid[i] = fork();
		if(pid[i] < 0) {
			printf("P0: Impossibile creare P%d\n", i+1);
			exit(3);
		} else if(pid[i] == 0) {
			if(i == 0) {  //P1
				close(p[1]);
				p1(p[0], c);
			} else {      //P2
				close(p[0]);
				p2(p[1], argv[1]);
			}
			exit(0);
		}
	}
	//P0

	close(p[0]);
	close(p[1]);
	alarm(t);
	
	wait_child();
	wait_child();
	
	return 0;
}

void p1(int in, char c) {
	int n, sgn = -1;
	//sleep(10); //solo per controllare il funzionamento della alarm
	printf("P1: attesa conteggio righe...\n");
	if(read(in, &n, sizeof(int)) != sizeof(int)) {
		printf("P1: impossibile comunicare con P2\n");
		exit(5);
	}
	
	printf("P1: conteggio ricevuto: %d\n", n);
	
	if(n % 2 == 0 && c == 'P') sgn = SIGUSR1;
	if(n % 2 == 1 && c == 'D') sgn = SIGUSR2;
	
	if(sgn != -1) kill(getppid(), sgn);
	
	close(in);
	exit(0);
}

void p2(int out, char *fin) {
	char c;
	int n, in = open(fin, O_RDONLY);
	if(in < 0) {
		printf("P2: impossibile aprire file di input\n");
		exit(4);
	}
	
	n = 0;
	while(read(in, &c, sizeof(char)) > 0) {
		if(c == '\n') n++;
	}
	if(c != '\n') n++;//Caso della riga finale che non termina con \n
	close(in);
	
	printf("P2: contate %d righe\n", n);
	if(write(out, &n, sizeof(int)) != sizeof(int)) {
		printf("P2: impossibile comunicare con P1\n");
		exit(5);
	}
	close(out);
	exit(0);
}

void handler(int sgn) {
	if(sgn == SIGALRM) {
		printf("P0: timout scaduto\n");
		kill(pid[0], SIGKILL);
		//exit(6);
	}
	if(sgn == SIGUSR1 || sgn == SIGUSR2) {
		char outStr[MAX_OUTPUT];
		int out = creat(fout, 00640);
		printf("P0: ricevuto segnale SIGUSR1/2\n");
		if(out < 0) {
			printf("P0: impossibile aprire file di output\n");
			exit(7);
		}
		
		sprintf(outStr, "%s contiene un numero %spari di righe\n", fout, sgn == SIGUSR1 ? "" : "dis");
		write(out, outStr, sizeof(char) * strlen(outStr));
		close(out);
		//exit(0);
	}
}

void wait_child() {
    int pid_terminated,status;
    pid_terminated=wait(&status);
    if (pid_terminated < 0)    {
        fprintf(stderr, "P0: errore in wait. \n");
        exit(EXIT_FAILURE);
    }
    if(WIFEXITED(status)){
        printf("P0: terminazione volontaria del figlio %d con stato %d\n",pid_terminated, WEXITSTATUS(status));
        if (WEXITSTATUS(status) == EXIT_FAILURE){
            fprintf(stderr, "P0: errore nella terminazione del figlio pid_terminated\n");
            exit(EXIT_FAILURE);
        }
    }else if(WIFSIGNALED(status)){
        fprintf(stderr, "P0: terminazione involontaria del figlio %d a causa del segnale %d\n", pid_terminated,WTERMSIG(status));
        exit(EXIT_FAILURE);
    }
}