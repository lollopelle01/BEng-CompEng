//
//  main.c
//  es.3 array
//
//  Created by Lorenzo Pellegrino on 09/11/2020.
//

#include <stdio.h>
#define DIM 9
int main()
{
    int i=0, num, k;
    int v[DIM];
    int v2[DIM];
    do
    {
        printf("inserisci un numero: ");
        scanf("%d", &num);
        if(num!=0)
            v[i]=num;
        i++;
    }
    while(num!=0 && i<=DIM);
    
    for(k=0; k<=DIM; k++ )
    {
        v2[k]=v[DIM-k];
        printf("%d\n", v2[k]);
    }
}
