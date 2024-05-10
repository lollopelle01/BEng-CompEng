//
//  main.c
//  es.2 funzioni array
//
//  Created by Lorenzo Pellegrino on 10/11/2020.
//

#include <stdio.h>
#define DIM 10
#define VERO 1
#define FALSO 0

int cerca(int v[], int dimF, int k, int *z)
{
    int num, i=0, count=0, count2=0;
    do
    {
        printf("Inserisci un elemento del vettore:");
        scanf("%d", &num);
        if (num !=0 && i < dimF)
        v[i] = num;
        count = count + i;
        i++;

    }
    while (num != 0 && i < dimF);

    for (i = 0; i < count; i++)
        {
            if (v[i] == k)
            {
                count2++;
                *z = i;
            }
        }
    if (count2 > 0)
    
        return VERO;
    else
        return FALSO;
}

int main()
{
    int i=0, k, m, z;
    int v[DIM];

    printf("inerisci un valore da ricercare: ");
    scanf("%d", &k);
    
    m=cerca(v, DIM, k, &z);
    if(m==VERO)
    {
        printf("l'elemento %d e' presente in %d\n", k, z);
    }
}
