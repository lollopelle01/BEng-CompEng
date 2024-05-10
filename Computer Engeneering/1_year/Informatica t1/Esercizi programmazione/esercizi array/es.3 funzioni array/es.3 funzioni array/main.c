//
//  main.c
//  es.3 funzioni array
//
//  Created by Lorenzo Pellegrino on 10/11/2020.
//

#include <stdio.h>
#define DIM 10
#define VERO 1
#define FALSO 0

int lettura1(int v[], int dimF)
{
    int num1, i=0, count=0;
    do
    {
        printf("Inserisci un elemento del primo vettore: ");
        scanf("%d", &num1);
        if (num1 !=0 && i < dimF)
            v[i] = num1;
            count++;
        i++;
    }
    while (num1 != 0 && i < dimF);
    return count;
}

int lettura2(int v[], int dimF)
{
    int num2, i = 0, count1 = 0;
    do
    {
        printf("Inserisci un elemento del secondo vettore: ");
        scanf("%d", &num2);

        if (num2 != 0 && i < dimF)
            v[i] = num2;
            count1++;
        i++;
    }
    while (num2 != 0 && i < dimF);
    return count1;
}

int main()
{
    int i=0, m, z;
    int v1[DIM];
    int v2[DIM];

    m = lettura1(v1, DIM);
    z = lettura2(v2, DIM);

    for (i = 0; i < m; i++)
    {
        if(v1[i]==v2[i])
            printf("%d\n", v1[i]);
    }
}
