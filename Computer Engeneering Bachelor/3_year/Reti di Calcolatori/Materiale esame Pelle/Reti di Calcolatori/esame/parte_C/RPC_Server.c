// pellegrino lorenzo 0000971455
#include "RPC_xFile.h"
#include <rpc/rpc.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <netdb.h>
#include <signal.h>
//#include <sys/ttycom.h>
#include <memory.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <syslog.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/errno.h>
#include <rpc/pmap_clnt.h>
#include <dirent.h>
#include <sys/stat.h>
#include <string.h>

int * conta_occorrenze_1_svc(Input_conta * input, struct svc_req * rq){ //input->fileName e input->linea\       

    int fd, i;
    char c, buffLinea[MAXNAMELENGHT];
    static int esito = -1;

    if((fd=open(input->fileName, O_RDONLY))<0){ 
        return &esito; 
    }
    
    i=0; esito=0;
    while(read(fd, &c, 1)>0){
        if(c=='\n'){ // fine della linea
            buffLinea[i]='\0';
            if(strcmp(buffLinea, input->linea)==0){esito++;}
            i=0;
        }
        else{
            buffLinea[i]=c;
            i++;
        }
    }

    return &esito;
}

Output * lista_file_carattere_1_svc(Input_lista * input, struct svc_req * rq){ // input->dirName input->carattere
    static Output out;

    DIR* dir;
    struct dirent* dirent;
    int numFile, lenName;
    
    if((dir=opendir(input->dirName))!=NULL){
        numFile=0;
        while((dirent=readdir(dir))!=NULL && numFile<=5){
            if(dirent->d_type==DT_REG && (lenName=strlen(dirent->d_name))>4 && strcmp(dirent->d_name +(lenName-4), ".txt")==0 && dirent->d_name[0]==input->carattere){
                strcpy(out.lista[numFile], dirent->d_name);
            }
        }
    }
    else{
        out.errNum = -1;
    }

    return (&out);
}