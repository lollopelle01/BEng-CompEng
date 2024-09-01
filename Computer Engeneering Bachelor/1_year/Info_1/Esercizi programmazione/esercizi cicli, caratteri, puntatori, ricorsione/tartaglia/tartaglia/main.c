//
//  main.c
//  tartaglia
//
//  Created by Lorenzo Pellegrino on 06/11/2020.
//

#include <stdio.h>
#include "Header.h"

int fattoriale(int n);

int coefficiente_binomiale(int n, int k);

int main()
{
    int n, k, i;
    printf("inserisci il massimo livello del triangolo: ");
    scanf("%d", &n);
    
    
        for(i=1; i<n; i++)
        {
            for(k=0; k<=n; k++)
            {
                printf("%d ", coefficiente_binomiale(n, k));
            }
            printf("\n");
        }
    
    return 0;
}
