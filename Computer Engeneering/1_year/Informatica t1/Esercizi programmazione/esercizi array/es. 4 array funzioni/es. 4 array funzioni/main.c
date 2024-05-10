//
//  main.c
//  es. 4 array funzioni
//
//  Created by Lorenzo Pellegrino on 15/11/2020.
//

#include <stdio.h>
#define DIM 10

void intDisPar(int v[], int dimL, int *pari, int *dispari){
    *pari=0;
    *dispari=0;
    for(int i=0; i<dimL; i++){
        if((v[i]%2)==0)
            (*pari)++;
        else
            (*dispari)++;
    }
}

int leggi(int v[], int dimF)
{
    int num, i=0;
    do{
        printf("scrivi il valore in v[%d] : ", i);
        scanf("%d", &num);
        
        if(num>0 && i<dimF){
            v[i]=num;
            i++;
        }
        
    }
    while(i<dimF && num!=0);
    return i;
}

int main()
{
    int v[DIM], dimL_v, numPari, numDispari;
    dimL_v=leggi(v, DIM);
    intDisPar(v, dimL_v, &numPari, &numDispari);
    printf("nel vettore ci sono %d numeri pari\n", numPari);
    printf("nel vettore ci sono %d numeri dispari\n", numDispari);
}
