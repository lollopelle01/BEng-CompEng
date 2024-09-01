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
Output * lista_file_carattere_1_svc(Input * input, struct svc_req * rq){
    static Output out;

    int occorrenze, fd, numFile=0, nameLen;
    DIR* dir;
    struct dirent* dirent;
    char newDir[2*256+1+1], c;

    if((dir=opendir(input->dirName))!=NULL){
        while((dirent=readdir(dir))!=NULL && numFile<6){

            if(dirent->d_type==DT_REG && (nameLen=strlen(dirent->d_name))>4 && 
            (
                dirent->d_name[nameLen-1]== 't' &&
                dirent->d_name[nameLen-2]== 'x' &&
                dirent->d_name[nameLen-3]== 't' &&
                dirent->d_name[nameLen-4]== '.' 
            )
            ){ 
                newDir[0]='\0';
                strcpy(newDir, input->dirName);
                strcat(newDir, "/");
                strcat(newDir, dirent->d_name);
                fd=open(newDir, O_RDONLY);

                occorrenze=0;
                while(read(fd, &c, 1) > 0){
                    if(c==input->carattere){occorrenze++;}
                }
                if(occorrenze>=input->numOcc){
                    strcpy(out.files[numFile], dirent->d_name);
                    numFile++;
                }

                close(fd);
            }
        }
        out.numErrore = 0;
    }
    else{
        out.numErrore = -1;
    }

    return (&out);
}

/*
Metodo 2
static Output !!
*/
int * elimina_occorrenze_1_svc(char * fileName, struct svc_req * rq){
    static int result;

    int fd, fd_temp;
    char c, fileNameTemp[256 + 5];

    if((fd=open(fileName, O_RDONLY)) < 0){
        result = -1;
        return (&result);
    }
    printf("Apro %s\n", fileName);

    fileNameTemp[0]='\0';
    strcpy(fileNameTemp, fileName);
    strcat(fileNameTemp, "_temp");

    printf("Provo a creare %s\n", fileNameTemp);
    if((fd_temp=open(fileNameTemp, O_CREAT | O_RDWR, 0644)) < 0){
        result = -1;
        return (&result);
    }
    printf("Creo %s\n", fileNameTemp);

    result=0;
    while(read(fd, &c, 1) > 0){
        if(c>='0' && c<='9'){
            result++;
        }
        else{
            write(fd_temp, &c, 1);
        }
    }

    remove(fileName);
    rename(fileNameTemp, fileName);

    close(fd); close(fd_temp);

    return(&result);
}

