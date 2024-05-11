//
//  main.c
//  es.6 file
//
//  Created by Lorenzo Pellegrino on 06/12/20.
//

#include <stdio.h>
#include <stdlib.h>
#include "trova.h"

int main(){
    FILE *fp;
    Azione lista[100], temp1, temp2;
    char nome[64];
    float min, max, med, vol1, vol2;
    int dimL;
    
    if((fp==fopen("azioni.txt", "r"))==NULL){
        printf("errore di lettura di f1\n");
        exit(1);
    }
    printf("inserisci il nome del titolo azionario: ");
    scanf("%s", nome);
    
    dimL=leggi(fp, lista, 100, nome);
    temp1=trovaMin(lista, dimL, &min);
    temp2=trovaMax(lista, dimL, &max);
    
    printf("il valore minimo di %s e' stato nel giorno %d con %f", nome, &temp1.giorno, &min);
    printf("il valore massimo di %s e' stato nel giorno %d con %f", nome, &temp2.giorno, &max);
    
    med=media(lista, dimL);
    printf("il valore medio di %s e' stato: %f\n", nome, med);
    
    vol1=(min-med)/med*100;
    vol2=(max-med)/med*100;
    printf("la volatilità di %s e' tra %f percentuale e %f percentuale", nome, vol1, vol2);
}
