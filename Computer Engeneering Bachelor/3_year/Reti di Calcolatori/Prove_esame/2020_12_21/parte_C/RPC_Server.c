// pellegrino lorenzo 0000971455
#include "RPC_xFile.h"
#include <fcntl.h>
#include <rpc/rpc.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>

//stato tabella
static int inizializzato=0;
static Riga tabella[N];

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
        strcpy(tabella[i].id, "L");
        strcpy(tabella[i].tipo, "L");
        strcpy(tabella[i].partenza, "L");
        strcpy(tabella[i].arrivo, "L");
        tabella[i].hh = -1;
        tabella[i].mm = -1;
        tabella[i].ritardo = -1;
        strcpy(tabella[i].audio, "L");
    }
    

    //seconda inizializzazione --> quella utile per debug
        strcpy(tabella[2].id, "id1");
        strcpy(tabella[2].tipo, "arrivo");
        strcpy(tabella[2].partenza, "L");
        strcpy(tabella[2].arrivo, "L");
        tabella[2].hh = 9;
        tabella[2].mm = 15;
        tabella[2].ritardo = 0;
        strcpy(tabella[2].audio, "L");

        strcpy(tabella[4].id, "id2");
        strcpy(tabella[4].tipo, "partenza");
        strcpy(tabella[4].partenza, "L");
        strcpy(tabella[4].arrivo, "L");
        tabella[4].hh =12;
        tabella[4].mm = 30;
        tabella[4].ritardo = 25;
        strcpy(tabella[4].audio, "L");
    //int dim_max_colonna=22; //candidatoAProfile.txt
    //stampa(dim_max_colonna);
    //printf("\n\n");
    
    return;
}

Output * visualizza_lista_1_svc(char * tipo, struct svc_req * rq){
    if(inizializzato==0){inizializza();}
    
    static Output result;
    result.dimL=0;

    for(int i = 0; i < N  && result.dimL < 6; i++){
        if(strcmp(tipo, tabella[i].tipo) == 0){
            strcpy(result.corse[result.dimL], tabella[i].id);
            result.dimL++;
        }
    }
    return (&result);
}

int * esprimi_voto_1_svc(Input * in, struct svc_req * rq){
    if(inizializzato==0){inizializza();}

    static int result1 = -1;

    for(int i = 0; i < N && result1==-1; i++){
        if(strcmp(in->id, tabella[i].id) == 0){
            tabella[i].ritardo = in->newRitardo;
            result1 = 0;
        }
    }

    return (&result1);
}


/*
Metodo 2
static Output !!
*/