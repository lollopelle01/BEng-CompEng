// giulianelli nicole 0000976239
#include "RPC_xFile.h"
#include <fcntl.h>
#include <rpc/rpc.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>

//definiamo tabella
typedef struct{
    nome id;
    nome carta;
    nome brand;
    nome folder;
}RigaTabella;

//stato tabella
static int inizializzato=0;
static RigaTabella tabella[7];



// per debuggare
void stampa(){
    printf("\n\nTabella:\n");
    for(int i = 0; i < 7; i++){
        printf("Id:\t %s \tcarta:\t %s \tbrand:\t %s \tfolder:\t%s\n", tabella[i].id, tabella[i].carta, tabella[i].brand, tabella[i].folder);
    }
}

// per inizializzare la struttura dati
void inizializza(){
    printf("Sto inizializzando\n");
    int i;
    
    if(inizializzato==1){
        return;
    }
    
    //prima inizializzazione --> quella richiesta
    printf("Faccio la prima inizializzazione...\n");
    for(i=0; i<7; i++){
        strcpy(tabella[i].id, "L");
        strcpy(tabella[i].carta, "L");
        strcpy(tabella[i].brand, "L");
        strcpy(tabella[i].folder, "L");
        stampa();
    }
    
    //seconda inizializzazione --> quella utile per debug
    printf("Faccio la seconda inizializzazione...\n");
        strcpy(tabella[0].id, "AD567IH");
        strcpy(tabella[0].carta, "00005");
        strcpy(tabella[0].brand, "brand1");
        strcpy(tabella[0].folder, "AD567IH_img");

        strcpy(tabella[1].id, "ED345RT");
        strcpy(tabella[1].carta, "11114");
        strcpy(tabella[1].brand, "brand2");
        strcpy(tabella[1].folder, "ED345RT_img");

        strcpy(tabella[2].id, "PK876ED");
        strcpy(tabella[2].carta, "L");
        strcpy(tabella[2].brand, "brand1");
        strcpy(tabella[2].folder, "PK876ED_img");

        strcpy(tabella[3].id, "RF999TG");
        strcpy(tabella[3].carta, "76548");
        strcpy(tabella[3].brand, "brand1");
        strcpy(tabella[3].folder, "RF999TG_img");
        
        strcpy(tabella[4].id, "FE000GH");
        strcpy(tabella[4].carta, "09823");
        strcpy(tabella[4].brand, "brand1");
        strcpy(tabella[4].folder, "FE000GH_img");

        strcpy(tabella[5].id, "TG678TG");
        strcpy(tabella[5].carta, "77743");
        strcpy(tabella[5].brand, "brand1");
        strcpy(tabella[5].folder, "TG678TG_img");

        strcpy(tabella[6].id, "AJ657HF");
        strcpy(tabella[6].carta, "55554");
        strcpy(tabella[6].brand, "brand2");
        strcpy(tabella[6].folder, "AJ657HF_img");

      

    stampa();
    printf("Inizializzato correttamente\n");
    inizializzato=1;
    
    //int dim_max_colonna=22; //candidatoAProfile.txt
    //stampa(dim_max_colonna);
    //printf("\n\n");
    
    return;
}

/*
Metodo 1
static Output !!
*/
Output * visualizza_prenotazione_1_svc(char * brand, struct svc_req * rq){

    static Output result;
    int count = 0;
    char prefisso[3];

    result.errCode = -1;
    for(int i = 0; i < 7; i++){

        prefisso[0] = tabella[i].id[0];
        prefisso[1] = tabella[i].id[1];
        prefisso[2] ='\0';
        if((strcmp(brand, tabella[i].brand)== 0) && count < 6 && (strcmp(prefisso, "ED") >= 0)){
            strcpy(result.lista[count], tabella[i].id);
            count++;
        }
    }
    result.errCode = count;

    return (&result);
}


/*
Metodo 2
static Output !!
*/
int * elimina_prenotazioni_1_svc(char * id, struct svc_req * rq){
    static int result;
    DIR *d;
    struct dirent *dir;
    int res;
    nome newDir;

    for(int i = 0; i < 7; i++){
        if((strcmp(id, tabella[i].id) == 0) && (strcmp(tabella[i].carta, "L") == 0)){
            strcpy(tabella[i].id, "L");
            strcpy(tabella[i].carta, "L");
            strcpy(tabella[i].brand, "L");
            result = 1;

            if(d = opendir(tabella[i].folder)!= NULL){
                while((dir=readdir(d)) != NULL){
                    if(dir->d_type == DT_REG){
                        newDir[0] = '\0';
                        strcat(newDir, tabella[i].folder);
                        strcat(newDir, "/");
                        strcat(newDir, dir->d_name);
                        res = remove(dir->d_name);
                        if(res != 0){
                            result = -1;
                        }
                    }
                }
            }else{
                result = -1;
            }
            strcpy(tabella[i].folder, "L");
        }
    }
    return (&result);
}