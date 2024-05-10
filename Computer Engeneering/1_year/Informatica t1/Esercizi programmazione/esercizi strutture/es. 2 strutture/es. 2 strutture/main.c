//
//  main.c
//  es. 2 strutture
//
//  Created by Lorenzo Pellegrino on 28/11/20.
//

#include <stdio.h>

struct struttura{
    char nome[100];
    int codice;
    int gol_fatti;
    int gol_subiti;
}squadra;

typedef struct struttura campionato;

int leggiclub(campionato v[], int dimF){
    int i;
    for(i=0; i<dimF; i++){
        printf("inserisci il nome della squadra: ");
        scanf("%s", v[i].nome);
        printf("inserisci il codice della squadra: ");
        scanf("%d", &v[i].codice);
        printf("inserisci i gol fatti dalla squadra: ");
        scanf("%d", &v[i].gol_fatti);
        printf("inserisci i gol subiti dalla squadra: ");
        scanf("%d", &v[i].gol_subiti);
        
        printf("\n");
    }
    return i;
}

int main(){
    int dimF, dimL, codice1;
    
    printf("scrivi quante squadre ci sono nel campionato: \n");
    scanf("%d", &dimF);
    
    campionato v[dimF];
    
    dimL=leggiclub(v, dimF);
    
    for(int i=0; i<dimL; i++){
        if(v[i].gol_fatti>v[i].gol_subiti){
            printf("%s\n", v[i].nome);
            printf("%d\n", v[i].codice);
        }
    }
    
    printf("\nscrivi il codice di una squadra di cui vuoi sapere i dati: \n");
    scanf("%d", &codice1);
    
    for(int i=0; i<dimL; i++){
        if(v[i].codice==codice1){
            printf("il suo nome e': %s\n", v[i].nome);
            printf("ha fatto %d gol\n", v[i].gol_fatti);
            printf("ha subito %d goal\n", v[i].gol_subiti);
        }
    }
    return 0;
}
