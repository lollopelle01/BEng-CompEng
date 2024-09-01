#include<stdio.h>

int main(){
int pid1,pid2,k=0;
k++;
printf("P%d: sono il padre, k: %d, pid2:%d\n", getpid(), k, pid2);
pid1 = fork();
k-=2;
if (k) pid2 = fork();
if (pid2==0)
{ printf("P%d: messaggio 1.., k: %d, pid2: %d\n", getpid(), k, pid2);}
if (k>=0) printf("P%d: messaggio 2.., k: %d, pid2: %d\n", getpid(), k, pid2);
return 0;
}
