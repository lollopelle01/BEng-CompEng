#include<stdio.h>

int main(){
int pid1 = 99, pid2 = 99, pid3 = 99, k = 0;
pid1 = fork();
if(pid1) k++;
pid2 = fork();
if(pid2 == 0){
    printf("P%d: Bologna %d\n", getpid(), k);
    k--;
}
if(!k) printf("P%d: Pescara\n", getpid());
printf("P%d: Uno a uno %d\n", getpid(), k);
}
