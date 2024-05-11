//
//  main.c
//  MaxMinRicorsivo
//
//  Created by Lorenzo Pellegrino on 04/11/2020.
//

#include <stdio.h>

int miomax(int a, int b)
{
    if (a<b)
        return b;
    if (a>b)
        return a;
}

int miomin(int a, int b)
{
    if (a<b)
        return a;
    if (a>b)
        return b;
}

int main()
{
    int a, b=0, mi, Mi, mf, Mf=0; //le inizializzazioni di b ed mi condizionano l'mf//
    do
    {
        printf("Inserisci un numero intero, si termina con '0': ");
        scanf("%d", &a);
        
        mi= miomin(a, b);
        Mi= miomax(a, b);
        
        if(mi<=mf && mi>0)
            mf=mi;
        if(Mi>=Mf)
            Mf=Mi;
        b=a;
    }
    while(a!=0);
    printf("il massimo della sequenza e' : %d\nil minimo e' : %d\n", Mf, mf);
    return 0;
}
