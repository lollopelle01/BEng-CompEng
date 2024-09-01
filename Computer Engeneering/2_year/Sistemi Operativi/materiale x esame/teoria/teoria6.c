void main(){
int pid1=100,pid2=100, k=1;
pid1 = fork();
if (pid1) k--;
pid2 = fork();
if (pid2==0)
{ printf("Juventus %d \n", k);
k++; }
if (!k) printf("Barcelona\n");
printf(”RISULTATO: %d \n", k);
}