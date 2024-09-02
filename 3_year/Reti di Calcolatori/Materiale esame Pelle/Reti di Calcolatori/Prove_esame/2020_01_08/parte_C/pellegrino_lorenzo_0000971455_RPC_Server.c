#include "pellegrino_lorenzo_0000971455_RPC_xFile.h"

//per quando si rigenera il .h
#include <rpc/rpc.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <netdb.h>
#include <signal.h>
#include <sys/ttycom.h>
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

int * elimina_occorrenze_1_svc(char * fileName, struct svc_req * rq){
    static int result = 0;
    int fd1, fd2, ascii;
    char c;

    fd1 = open(fileName, O_RDONLY);
    if(fd1 < 0){
        printf("Errore nella lettura di %s\n", fileName);
        result = -1;
        return (&result);
    }

    fd2 = open("temp.txt", O_WRONLY | O_CREAT, 0644);
    if(fd2 < 0){
        printf("Errore nella creazione del file temporaneo\n");
        result = -1;
        return (&result);
    }

    while(read(fd1, &c, 1)>0){ //riscrittura file
        ascii = (int) c; //estraggo valore ascii del carattere
        if(ascii >= 48 && ascii <= 57){ //se è un numero --> elimino (non riscrivo)
            result++;
        }
        else{ //non è un numero --> non elimino (riscrivo)
            write(fd2, &c, 1);
        }
    }
    close(fd1);
    close(fd2);

    if(remove(fileName) != 0){
        printf("Errore nel cancellare il vecchio file\n");
        result = -1;
        return (&result);
    }

    if(rename("temp.txt", fileName) != 0){
        printf("Errore nel rinominare il nuovo file\n");
        result = -1;
        return (&result);
    }

    return (&result);
}

Output * lista_file_carattere_1_svc(Input * in, struct svc_req * rq){
    // in->direttorio ; in->carattere ; in->num_occorrenze
    // out.files
    static Output out;
    out.errCode = 0; //tutto ok

    int fileCount = 0, occCount, fd;
    char c, path[256];

    DIR* dir;
    struct dirent* dd;

    if((dir = opendir(in->direttorio)) != NULL){
        while((dd = readdir(dir)) != NULL && fileCount < 6){
            occCount = 0;
            if(dd->d_type == DT_REG){
                sprintf(path, "%s/%s", in->direttorio, dd->d_name);
                fd = open(path, O_RDONLY);
                while(read(fd, &c, 1) > 0){
                    if(c == in->carattere) {occCount++;}
                }
                if(occCount >= in->num_occorrenze){
                    strcpy(out.files[fileCount], dd->d_name);
                    fileCount++;
                }
                memset(path, 0, 256);
            }
        }
        out.errCode = fileCount; //per avere dim_logica nel client
    }
    else{
        printf("Direttorio %s inesistente\n", in->direttorio);
        out.errCode = -1;
    }

    return (&out);
}
