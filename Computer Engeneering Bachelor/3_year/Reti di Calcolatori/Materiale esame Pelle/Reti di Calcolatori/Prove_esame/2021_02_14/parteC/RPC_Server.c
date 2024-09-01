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

//definiamo tabella
typedef struct{
    nome id_seriale;
    nome carta_id;
    nome brand;
    nome folder;
}RigaTabella;

//stato tabella
static int inizializzato=0;
static RigaTabella tabella[N];

//per debug
void stampa(){
    for(int i=0; i<N; i++){
        printf("%s\n", tabella[i].id_seriale);
        printf("%s\n", tabella[i].carta_id);
        printf("%s %d %d\n", tabella[i].brand, sizeof(tabella[i].brand), strlen(tabella[i].brand));
        printf("%s\n", tabella[i].folder);
        printf("/--------------------------------------------------------------/\n");
    }
}

// per inizializzare la struttura dati
void inizializza(){
    printf("Sto inizializzando\n");
    int i, j, categoria;
    char buff[2], stringa[40];
    
    if(inizializzato==1){
        return;
    }
    
    //prima inizializzazione --> quella richiesta
    printf("Faccio la prima inizializzazione...\n");
    for(i=0; i<N; i++){
        strcpy(tabella[i].id_seriale, "L");
        strcpy(tabella[i].carta_id, "L");
        strcpy(tabella[i].brand, "L");
        strcpy(tabella[i].folder, "L");
    }
    
    //seconda inizializzazione --> quella utile per debug
    printf("Faccio la seconda inizializzazione...\n");
    i=1;
    strcpy(tabella[i].id_seriale, "AN745NL");
    strcpy(tabella[i].carta_id, "00003");
    strcpy(tabella[i].brand, "brand1");
    strcpy(tabella[i].folder, "AN745NL_img");

    i=3;
    strcpy(tabella[i].id_seriale, "NU547PL");
    strcpy(tabella[i].carta_id, "L");
    strcpy(tabella[i].brand, "brand1");
    strcpy(tabella[i].folder, "NU547PL_img");

    i=5;
    strcpy(tabella[i].id_seriale, "MD506DW");
    strcpy(tabella[i].carta_id, "00100");
    strcpy(tabella[i].brand, "brand3");
    strcpy(tabella[i].folder, "MD506DW_img");

    stampa();

    printf("Inizializzato correttamente\n");
    inizializzato=1;
    
    return;
}

/*
Metodo 1
static Output !!
*/
int * elimina_monopattino_1_svc(char * id, struct svc_req * rq){
    if(inizializzato==0){inizializza();}

    static int result=3;
    int i;
    DIR* dir;
    struct dirent* dirent;
    char newDir[MAX_LEN + 256];

    for(i=0; i<N; i++){
        if(strcmp(id, tabella[i].id_seriale)==0 && strcmp(tabella[i].carta_id, "L")==0){
            if((dir=opendir(tabella[i].folder))!=NULL){
                result=0;
                while((dirent=readdir(dir))!=NULL){
                    if(dirent->d_type==DT_REG){
                        newDir[0]='\0';
                        strcpy(newDir, tabella[i].folder);
                        strcat(newDir, "/");
                        strcat(newDir, dirent->d_name);

                        result += remove(newDir); //0 se tutto va bene, altrimenti <0
                    }
                }
            }
            else{
                result = 1; // non esiste cartella
            }
        }
        else if(strcmp(id, tabella[i].id_seriale)==0 && strcmp(tabella[i].carta_id, "L")!=0){result = 2;}
    }

    stampa();

    if(result < 0){result = -1;} //almeno un errore nell'eliminazione di file

    return (&result);
}

/*
Metodo 2
static Output !!
*/
Output * visualizza_prenotazioni_1_svc(char * brand, struct svc_req * rq){
    if(inizializzato==0){inizializza();}

    static Output out;
    int i, count=0;
    char buff[3]; buff[2]='\0';
    for(i=0; i<N && count<6; i++){
        buff[0]=tabella[i].id_seriale[0];
        buff[1]=tabella[i].id_seriale[1];
        if(strcmp(tabella[i].brand, brand)==0 && strcmp(buff, "ED")>0){
            strcpy(out.ids[count], tabella[i].id_seriale);
            count++;
        }
    }

    return (&out);
}