// pellegrino lorenzo 0000971455
#include "RPC_xFile.h"
#include <fcntl.h>
#include <rpc/rpc.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>

/*
Metodo 1
static Output !!
*/
int * conta_occorrenze_1_svc(Input_conta * input, struct svc_req * rq){ //input->fileName e input->linea\       
    static int result; // -1 se errore
    int fd, i;
    char c, buffLinea[MAMAXNAMELENGHTX];

    if((fd=open(input->fileName, O_RDONLY))<0){
        result=-1;
        return (&result);
    }
    
    i=0; result=0;
    while(read(fd, &c, 1)>0){
        if(c=='\n'){ // fine della linea
            buffLinea[i]='\0';
            if(strcmp(buffLinea, input->linea)==0){result++;}
            i=0;
        }
        else{
            buffLinea[i]=c;
            i++;
        }
    }

    return (&result);
}

/*
Metodo 2
static Output !!
*/