//
//  main.c
//  es.7 array
//
//  Created by Lorenzo Pellegrino on 09/11/2020.
//

#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#define DIM 3
int main()
{
    int v1[DIM];
    int v2[DIM];
    int v3[2 * DIM];
    int i = 0;
    do
        {
            printf("inserisci un valore del primo e del secondo vettore: ");
            scanf("%d %d", &(v1[i]), &(v2[i]));
            i++;
        }
    while (i < DIM);
    
    for (int k=0; k<DIM; k++)
    {
            v3[2*k]=v1[k];
            v3[2*k+1]=v2[k];
    }
    
    for (i = 0; i < 2*DIM; i++)
    {
        printf("%d\n", v3[i]);
    }

    return 0;
}


