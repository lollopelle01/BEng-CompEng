//
//  main.c
//  es.5 funzioni array
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


int intersezione(int v1[], int v2[], int v3[], int dimL1, int dimL2)
{
    int i, k, u=0;
    
    for (i = 0; i < dimL1; i++)
    {
        for (k = 0; k < dimL2; k++)
        {
            if (v2[k] == v1[i])
               {
                   v3[u] = v2[k];
                    u++;
               }
               
               
               
        }
    }
    
    return u;
    
    
}

int main()
{
    int i=0, k=0, dimL1, dimL2, dimL3, u=0;
    int v1[DIM];
    int v2[DIM];
    int v3[DIM];

    dimL1 = lettura1(v1, DIM);
    dimL2 = lettura2(v2, DIM);
    dimL3=intersezione(v1,v2,v3,dimL1,dimL2);
    

    for(i=0;i<dimL3;i++)
    {
        printf("%d\n", v3[i]);
    }
}
