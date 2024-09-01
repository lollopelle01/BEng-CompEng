#include <stdio.h>

int main(){
int pid1 = 0, pid2 = 0, pid3 = 0, n = 1;
pid1 = fork();
if(pid1) pid2 = fork();
pid3 = fork();
if(pid2 && pid3){
   n--;
   printf("P%d: PIPPO, %d\n", getpid(), n);
}
if(!n) printf("P%d: PLUTO \n", getpid());   
else printf("P%d: PAPERINO \n", getpid());
return 0;
}
