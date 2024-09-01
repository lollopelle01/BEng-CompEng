//
//  main.c
//  es. 1 allocDim
//
//  Created by Lorenzo Pellegrino on 16/12/20.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int readLength(FILE *f, int *even, int *odd){
    *even=0;
    *odd=0;
    int number;
    int counter1=0, counter2=0;
    
    while(fread(&number, sizeof(int), 1, f)==1){
        if(number%2==0)
            counter1++;
        else
            counter2++;
    }
    *odd=counter2;
    *even=counter1;
    return counter1+counter2;
}

int main(){
    FILE *fb;
    int numPD, numP, numD, *v, number, valore=0;
    int counter1=0, counter2=0;
    
    if((fb=fopen("valori.dat", "wb"))==NULL){
        printf("errore creazione del file binario\n");
        exit(1);
    }
    
    while(valore!=2020){
        if(valore!=2020){
            printf("scrivi un valore (2020 per terminare): ");
            scanf("%d", &valore);
            fwrite(&valore, sizeof(int), 1, fb);
        }
    }
    fclose(fb);
    
    if((fb=fopen("valori.dat", "rb"))==NULL){
        printf("errore lettura del file binario\n");
        exit(2);
    }
    
    numPD=readLength(fb, &numP, &numD);
    
    rewind(fb);
    
    v= (int*)malloc((numPD)*sizeof(int));
    
    while(fread(&number, sizeof(int), 1, fb)==1){
        if(number%2==0){
            v[counter1]=number;
            counter1++;
        }
        else{
            v[numP+counter2]=number;
            counter2++;
        }
    }
    fclose(fb);
    
    for(int i=0; i<numPD; i++){
        printf("%d ", v[i]);
    }
    printf("\n");
    free(v);
}
                            

