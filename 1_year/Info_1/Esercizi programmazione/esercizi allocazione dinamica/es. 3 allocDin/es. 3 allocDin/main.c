//
//  main.c
//  es. 3 allocDin
//
//  Created by Lorenzo Pellegrino on 16/12/20.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define DIM 65
 typedef struct {
    int cd_code;
    char renter[DIM];
    int days;
 } Rent;

int readRented(char filename[], char name[], Rent *p, int maxdim){
    FILE *ft;
    int i=0;
    
    if((ft=fopen(filename, "r"))==NULL){
        printf("errore di lettura di %s", filename);
        exit(1);
    }
    
    while(fscanf(ft, "%d %s %d", &p[i].cd_code, p[i].renter, &p[i].days)==3 && i<maxdim){
        if(!strcmp(name, p[i].renter)){
            i++;
        }
    }
    fclose(ft);
    return i;
}

int main(){
    int num_elementi, num_record;
    float tot=0;
    char nome_cliente[DIM];
    Rent *v;
    
    
    printf("inserire numero di elementi massimo e nome del cliente: ");
    scanf("%d %s", &num_elementi, nome_cliente);
    
    v=(Rent*) malloc(sizeof(Rent)*num_elementi);
    
    num_record=readRented("RentedLog.txt", nome_cliente, v, num_elementi);
    
    for(int i=0; i<num_record; i++){
        printf("%s ha noleggiato il CD %d per %d giorni\n", v[i].renter, v[i].cd_code, v[i].days);
        tot=tot+v[i].days;
    }
    
    printf("il tempo medio del noleggio e' stato di %f giorni\n", tot/num_record);
    free(v);
}
