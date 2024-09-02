#include<stdio.h>
int main(){
int PID1 = 0;
int PID2 = 0;
int X = 0;

PID1 = fork();

if(!PID1 && !X){
	X++;
	PID1 = X;
}
else
	X = 0;
if(X) 
	PID2 = fork();

if(!PID1 || !PID2){
	printf("P%d: GRECIA %d\n",getpid(), X);
}
else {
	printf("P%d: EUROPA %d\n", getpid(), X);
}
return 0;
}


   
