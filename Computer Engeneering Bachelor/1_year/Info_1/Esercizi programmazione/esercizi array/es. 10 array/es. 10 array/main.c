//
//  main.c
//  es. 10 array
//
//  Created by Lorenzo Pellegrino on 09/11/2020.
//

#include <stdio.h>
#define DIM 5
int main()
{
    int codice_localita, cm_neve, i=0, k;
    int valore, somma_valori=0, z;
    int v1[DIM], v2[DIM], media;
    
    do
    {
        printf("Scrivere il codice e i cm di neve di una localita': ");
        scanf("%d %d", &codice_localita, &cm_neve);
        if(codice_localita!=0)
            v1[i]=codice_localita;
            v2[i]=cm_neve;
        i++;
    }
    while(codice_localita!=0 && i<=DIM);
    
    for(k=0; k<=DIM; k++)
    {
        valore=v2[k];
        somma_valori=somma_valori+valore;
    }
    
    media=somma_valori/(DIM+1);
    
    for(z=0; z<=DIM; z++)
    {
        if(v2[z]<media)
            printf("%d\n", v1[z]);
    }
    
    return 0;
}
