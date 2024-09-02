#include "proposta.h"
#include <dirent.h>
#include <fcntl.h>
#include <rpc/rpc.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/types.h>

OutputFileScan * file_scan_1_svc(char **fileName, struct svc_req *rp){
    static OutputFileScan result;
    int fd;
    char c;
    
    //inizializzazione
    result.num_char = 0;
    result.num_parole = 0;
    result.num_linee = 0;
    result.errorCode = -1;
    
    printf("Nome di file ricevuto: %s\n", *(fileName));
    
    if((fd = open(*(fileName), O_RDONLY)) < 0){
        result.errorCode=1;
    }
    
    while(read(fd, &c, 1) > 0){
        result.num_char++;
        if(c==' ' || c=='\n'){result.num_parole++;}
        if(c=='\n'){result.num_linee++;}
    }
    
    close(fd);
    return (&result);
}

OutputDirScan * dir_scan_1_svc(IntputDirScan* input, struct svc_req *rp){
    DIR *dir1;
    struct dirent *dd1;
    struct stat st;
    int fd;
    char fullpath[256];
    
    static OutputDirScan output;
    output.intero=0;
    
    if( (dir1=opendir(input->dirName))!=NULL ){
        printf("Richiesto il direttorio %s\n", input->dirName);//debug
        while( (dd1=readdir(dir1))!=NULL && output.intero<8){
            printf("\tLeggo %s\n", dd1->d_name);//debug
            
            snprintf(fullpath, sizeof(fullpath), "%s/%s", input->dirName, dd1->d_name); //creo path del file
            
            printf("\t--> Apro in lettura %s ",fullpath);//debug
            
            fd=open(fullpath, O_RDONLY);
            if(fd < 0){
                printf("--> Errore di lettura\n");
                perror("Lettura file: ");
                output.intero = -1;
                return (&output);
            }
            fstat(fd, &st);
            
            printf("--> dim: %lld", st.st_size);//debug
            
            if(st.st_size >= input->num_soglia && S_ISREG(st.st_mode)){
                printf("--> inserito in lista\n");//debug
                strcpy(output.names[output.intero].name, dd1->d_name);
                output.intero++;
            }
            close(fd);
            memset(fullpath, 0, 256);
        }
    }
    else{
        output.intero = -1;
    }
    closedir(dir1);
    return (&output);
}
