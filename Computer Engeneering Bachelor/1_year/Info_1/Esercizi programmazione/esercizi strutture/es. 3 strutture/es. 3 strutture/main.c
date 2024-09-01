//
//  main.c
//  es. 3 strutture
//
//  Created by Lorenzo Pellegrino on 29/11/20.
//

#include <stdio.h>
#include <string.h>
#define MAX_ITEM 200
#define DIM 15

struct item{
    char name[50];
    float old_price;
    float new_price;
};

typedef struct item item;

int lettura(item prezzi[], int max, int* num ){
    int i=0;
    char nome[DIM];
    *num=0;
    
    printf("inserisci il nome del prodotto: ");
    scanf("%s", nome);
    
    while((i<max) && strcmp(nome, "fine")){
        if(strcmp(nome, "fine")){
            strcpy(prezzi[i].name, nome);
            printf("inserisci il vecchio prezzo del prodotto: ");
            scanf("%f", &prezzi[i].old_price);
            printf("inserisci il nuovo prezzo del prodotto: ");
            scanf("%f", &prezzi[i].new_price);
            printf("\n");
            
            i++;
        
            printf("inserisci il nome del prodotto: ");
            scanf("%s", nome);
        }
    }
    *num=i;
    return 0;
}

int main(){
    item v[MAX_ITEM];
    int dimL;
    float inflazione;
    int result;
    result=lettura(v, MAX_ITEM, &dimL);
    if(result!=0){
        printf("sono stati riscontrati dei problemi nella lettura");
    }
    if(result==0){
        for(int i=0; i<dimL; i++){
            inflazione=(v[i].new_price/(v[i].old_price)-1)*100;
            printf("l'inflazione di %s e' %f\n", v[i].name, inflazione);
        }
    }
    return 0;
}
