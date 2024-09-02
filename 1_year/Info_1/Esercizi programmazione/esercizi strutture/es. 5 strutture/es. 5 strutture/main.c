//
//  main.c
//  es. 5 strutture
//
//  Created by Lorenzo Pellegrino on 23/11/20.
//

#include <stdio.h>
#include <string.h>
#define DIM 3000
#define MAX_ITEM 200

typedef struct{
    char nome_cliente[1024];
    int num_posto;
}booking;

int leggi(booking *dest) {
    char name[1024];
    
    printf("inserisci il nome del cliente: ");
    scanf("%s", name);
    
    if (strcmp(name, "stampa")==0)
        return 0;
    if (strcmp(name, "fine")==0)
        return -1;
    else{
        strcpy((*dest).nome_cliente, name);
        printf("inserisci il numero del posto: ");
        scanf("%d", &(*dest).num_posto);
        return 1;
    }
}

int assegna(booking list[], int dim, int* lengthList, char* name, int pref) {
    int free=0;
    for(int i=0; i<*lengthList; i++){
        if(list[i].num_posto==pref)
            free=1;
    }
    if (!free && *lengthList<dim) {
        list[*lengthList].num_posto = pref;
        strcpy(list[*lengthList].nome_cliente, name);
        (*lengthList)++;
        return 1;
    }
    else {
        return 0;
    }
}

int main() {
    booking lista_prenotazioni[DIM], richiesta;
    int result1, result2, dimL=0;
    do{
        result1=leggi(&richiesta);
        
        if(result1==1){
            result2=assegna(lista_prenotazioni, DIM, &dimL, richiesta.nome_cliente, richiesta.num_posto);
            if(result2==0){
                printf("prenotazione non possibile.\n");
            }
            else
                printf("prenotazione effettuata correttamente\n");
        }
        if (result1==0){
            for(int i=0; i<dimL; i++){
                printf("%s ha prenotato il posto %d\n", lista_prenotazioni[i].nome_cliente, lista_prenotazioni[i].num_posto);
            }
        }
    }
    while(result1!=0 && result1!=-1 && dimL<DIM);
    
}
