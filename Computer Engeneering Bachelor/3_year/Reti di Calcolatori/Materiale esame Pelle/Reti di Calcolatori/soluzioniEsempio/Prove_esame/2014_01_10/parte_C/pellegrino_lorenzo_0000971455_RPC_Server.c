#include "pellegrino_lorenzo_0000971455_RPC_xFile.h"

//stato tabella
static int inizializzato=0;
static Riga tabella[N];

// funzione di debug
void stampa(){
    printf("\nTabella:\n");
    for(int i=0; i<N; i++){
        printf("%d) ---------------------------------------------------/\n", i+1);
        printf("Descrizione: %s\n", tabella[i].Descrizione);
        printf("Tipo: %s\n", tabella[i].Tipo);
        printf("Data: %s\n", tabella[i].Data);
        printf("Luogo: %s\n", tabella[i].Luogo);
        printf("Disponibilita: %s\n", tabella[i].Disponibilita);
        printf("Prezzo: %s\n", tabella[i].Prezzo);
    }
    printf("\n\n");
}

//inizializzazione tabella
void init(){
    for(int i=0; i<N; i++){
        strcpy(tabella[i].Descrizione, "L");
        strcpy(tabella[i].Tipo, "L");
        strcpy(tabella[i].Data, "L");
        strcpy(tabella[i].Luogo, "L");
        strcpy(tabella[i].Disponibilita, "L");
        strcpy(tabella[i].Prezzo, "L");
    }

    // per non stare ad inserire valori dopo
    nome descrizione[] = {"Id1", "Id2", "Id3"};
    nome disponibilita[] = {"0", "1", "2"};
    for(int i=0; i<N; i++){
        if(i%2==0){ //solo righe pari
            strcpy(tabella[i].Descrizione, descrizione[i%3]);
            strcpy(tabella[i].Disponibilita, disponibilita[i%3]);
        }
    }
    
    inizializzato=1;
    printf("Inizializzazione effettuata\n");
    stampa();
}

// funzione di utilizzo
int isDoppione(Riga * riga){
    int doppione = 0;
    for(int i=0; i<N && doppione==0; i++){
        if(strcmp(tabella[i].Descrizione, riga->Descrizione)==0){
            doppione = 1;
        }
    }
    return doppione;
}

int * inserimento_evento_1_svc(Riga * riga, struct svc_req * rq){
    if(inizializzato==0){init();}

    static int result = -1;

    for(int i=0; i<N && result==-1; i++){
        if(strcmp(tabella[i].Descrizione, "L")!=0 && !isDoppione(riga)){
            strcpy(tabella[i].Descrizione, riga->Descrizione);
            strcpy(tabella[i].Tipo, riga->Tipo);
            strcpy(tabella[i].Data, riga->Data);
            strcpy(tabella[i].Luogo, riga->Luogo);
            strcpy(tabella[i].Disponibilita, riga->Disponibilita);
            strcpy(tabella[i].Prezzo, riga->Prezzo);

            result=0;

            // Debug
            // printf("Elemento inserito alla riga %d\n", i+1);
            // stampa();
        }
    }

    return (&result);
}

int * acquista_biglietti_1_svc(InputBuy * richiesta, struct svc_req * rq){
    if(inizializzato==0){init();}

    static int result = -1, newValue;

    for(int i=0; i<N && result==-1; i++){
        if(strcmp(tabella[i].Descrizione, richiesta->Descrizione)==0){
            newValue = atoi(tabella[i].Disponibilita) - richiesta->tiketDesiderati;
            if(newValue >= 0){
                sprintf(tabella[i].Disponibilita, "%d", newValue);
                result = 0;
            }
        }
    }

    return (&result);
}