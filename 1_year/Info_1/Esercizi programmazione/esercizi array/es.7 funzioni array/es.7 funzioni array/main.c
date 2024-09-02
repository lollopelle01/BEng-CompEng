//
//  main.c
//  es.7 funzioni array
//
//  Created by Lorenzo Pellegrino on 10/11/2020.
//

#include <stdio.h>
#define DIM 10
#define ERRORE -1

int definizione_array(int v[], int dimF) {
    int num, i=0;
    do {
        printf("inserisci l'elemento del vettore: ");
        scanf("%d", &num);
        if(num!=0 && i<DIM) {
            v[i]=num;
            i++;
        }
    }
    while(num!=0 && i<DIM);
    return i;
}

int trovaPos(int v[], int dimL, int k)
{
    int i, trovato;
    trovato=ERRORE;
    for(i=0; i<dimL && trovato<0; i++){
        if(v[i]==k)
            trovato=i;
    }
    
    return trovato;
}

int main()
{
    int dimL, v[DIM], i;
    dimL=definizione_array(v, DIM);
    for(i=0; i<dimL; i++)
    {
        if((trovaPos(&v[i+1], dimL-i-1, v[i]))>=0) // perché?
            if((trovaPos(v, i, v[i])<0)) // perché?
                printf("%d\n", v[i]); // perchè?
            
        
    }
    
}
