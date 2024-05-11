// pellegrino lorenzo 0000971455
#include "RPC_xFile.h"

int * conta_occorrenze_linea_1_svc(InputFile * input, struct svc_req * rq){
    static int result = 0;
    int fd, lunghezza_src = strlen(input->line), lunghezza_dest=0, i, temp=0;
    char c, buffLinea[lunghezza_src + 1];
    if((fd=open(input->fileName, O_RDONLY))>=0){ 
        buffLinea[0] = '\0';
        while(read(fd, &c, 1)>0){
            if(c=='\n'){ // nuova linea
                buffLinea[0] ='\0';
                lunghezza_dest = 0;
            }
            else{
                if(lunghezza_dest == lunghezza_src){

                    for(i=1; i<lunghezza_src; i++){
                        buffLinea[i-1] = buffLinea[i]; // scalo il buffer di 1 carattere
                    }
                    lunghezza_dest--; //lo mantengo a "lunghezza_src"
                }
                buffLinea[lunghezza_dest] = c;
                buffLinea[lunghezza_dest+1] = '\0';
                lunghezza_dest++;
                if(strcmp(buffLinea, input->line)==0){result++;}
                printf("BuffLinea: %s\toccorrenze di '%s' = %d\n", buffLinea, input->line, result);
            }
        }
    }
    else{
        result = -1; //errore lettura del file
    }
    return (&result);
}

Output * lista_file_prefisso_1_svc(InputDir * input, struct svc_req * rq){

    static Output result;
    DIR *d;
    struct dirent *dir;
    int countFile = 0, lenPrefix = strlen(input->prefisso), i;
    char typeFile[5], prefix[lenPrefix+1], *prefisso; 

    printf("richiesta scansione di %s con prefisso = '%s' di lunghezza %d\n", input->dirName, input->prefisso, lenPrefix);
    if((d = opendir(input->dirName))!=NULL){
        while((dir=readdir(d))!=NULL && countFile<6){
            if(strlen(dir->d_name)>(4 + lenPrefix)){ //dimensione compatibile
                strncpy(typeFile, dir->d_name + (strlen(dir->d_name) - 4), 4); // estraggo gli ultimi 4 caratteri + terminatore dal nome
                typeFile[4]='\0';

                strncpy(prefix, dir->d_name + 0, lenPrefix); // estraggo il prefisso
                prefix [lenPrefix] = '\0';

                printf("Nome:%s\t Prefisso:%s\t Tipo:%s\n", dir->d_name, prefix, typeFile);

                if(dir->d_type==DT_REG && strcmp(typeFile, ".txt")==0 && strcmp(prefix, input->prefisso)==0){ //solo se è un file di testo ed ha il giusto prefisso
                    strcpy(result.listaNomi[countFile], dir->d_name);
                    countFile++;
                }
            }
        }
    }
    else{
        result.errNum = -1;
    }
    return (&result);
}