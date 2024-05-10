//
//  main.c
//  es.2 array
//
//  Created by Lorenzo Pellegrino on 09/11/2020.
//
#include <stdio.h>
#define DIM 10
int main()
{
    int i=0, num, k, z;
    int v[DIM];
    int v2[DIM];
    do
    {
        printf("inserisci un numero: ");
        scanf("%d", &num);
        if(num!=0 && num>0)
            v[i]=num;
        i++;
    }
    while(num!=0 && i<=DIM);
    
    for(z=0; z<=DIM; z++)
    {
        v2[z]=v[z];
    }
    
    for(k=0; k<=DIM; k++ )
    {
        if(v2[k]==k)
            printf("%d\n", v2[k]);
    }
}
