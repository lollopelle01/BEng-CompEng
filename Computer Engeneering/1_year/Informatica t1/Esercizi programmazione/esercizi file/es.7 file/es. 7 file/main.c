//
//  main.c
//  es. 7 file
//
//  Created by Lorenzo Pellegrino on 07/12/20.
//

#include <stdio.h>
#include "registratore.h"
#include <stdlib.h>
#include <string.h>

int main(){
    FILE *fb, *ft;
    char nome1[129], nome2[129], filename[133];
    Transaction temp;
    int counter;
   
    
    if((fb=fopen("log.dat", "wb"))==NULL){  //inizio parte non presente in soluzioni
        printf("errore di lettura del file binario");
        exit(1);
    }
    
    printf("inserisci nome del cliente: ");
    scanf("%s", nome1);
    while(strcmp(nome1, "fine")!=0){
        
        strcpy(temp.customer, nome1);
        printf("inserire transactionID e importo: ");
        scanf("%d %f", &temp.transactionId, &temp.value);
        fwrite(&temp, sizeof(Transaction), 3, fb);
        
        printf("inserisci nome del cliente: ");
        scanf("%s", nome1);
    }
    fclose(fb); //fine parte non presente in soluzioni
    
    if((fb=fopen("log.dat", "rb"))==NULL){
        printf("errore di lettura del file binario");
        exit(2);
    }
    
    printf("di quale cliente vuoi avere i dati?\n");
    scanf("%s", nome2);
    strcpy(filename, nome2);
    strcat(filename, ".txt");
    
    if((ft=fopen(filename, "w"))==NULL){
        printf("errore di lettura del file testo");
        exit(3);
    }
    
    copy(fb, ft, nome2, &counter);
    
    fclose(fb); fclose(ft);
    
    printf("sono stati caricati %d importi su %s\n", counter, filename);
    
}
