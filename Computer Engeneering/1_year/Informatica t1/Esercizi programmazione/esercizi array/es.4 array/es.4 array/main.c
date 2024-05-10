//
//  main.c
//  es.4 array
//
//  Created by Lorenzo Pellegrino on 09/11/2020.
//


#include <stdio.h>
#define DIM 10

int main()
{
    int i=0, num, k, valore;
    int v[DIM];
    do
    {
        printf("inserisci un numero: ");
        scanf("%d", &num);
        if(num!=0 && i<DIM)
            v[i]=num;
        i++;
        if(i>DIM)
            printf("errore, inserire 0 per terminare.\n");
    }
    while(num!=0);
    printf("I valori pari con indice pari sono:\n");
    for(k=0; k<DIM; k++)
    {
        valore=v[k];
        if(valore%2==0 && k%2==0)
            printf("%d, ", valore);
    }
    
}
