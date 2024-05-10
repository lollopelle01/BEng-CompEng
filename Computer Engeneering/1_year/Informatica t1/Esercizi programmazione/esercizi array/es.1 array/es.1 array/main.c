//
//  main.c
//  es.1 array
//
//  Created by Lorenzo Pellegrino on 09/11/2020.
//

#include <stdio.h>
#define DIM 9
int main()
{
    int i=0, num, k;
    int v[DIM];
    do
    {
        printf("inserisci un numero: ");
        scanf("%d", &num);
        if(num!=0)
            v[i]=num;
        i++;
    }
    while(num!=0);
    
    for(k=0; k<=DIM; k++ )
    {
        if(v[k]==v[k+1])
            printf("%d\n", v[k]);
    }
}

