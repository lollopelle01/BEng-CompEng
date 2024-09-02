#include<stdio.h>

int main(){
int pid1 = -1, pid2 = -1, i, x = 0;

for(i = 0; i < 2; i++){
   if(!x){
   pid1 = fork();
   }else{
    pid2 = fork();
   }
   if((!pid1) || (!pid2)) x++;
 }
 if(x) printf("P%d: Risultato %d\n", getpid(), x);
 return 0;
}
