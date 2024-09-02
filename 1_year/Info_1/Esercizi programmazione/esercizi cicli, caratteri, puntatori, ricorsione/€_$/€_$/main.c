//
//  main.c
//  €_$
//
//  Created by Lorenzo Pellegrino on 28/10/2020.
//
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

float €_to_dollari (float money)
{
    float d;
    d=money/0.98;
    return d;
}

float €_to_lire (float money)
{
    float L;
    L=money*1936.27;
    return L;
}

int main()
{
    float e, d, L;
    printf("dammi il valore in euro e io lo convertiro' in lire e dollari: ");
    scanf("%f", &e);
    d=€_to_dollari(e);
    L=€_to_lire(e);
    printf("il valore in dollari e': %f\nmentre quello in lire e': %f\n", d, L);
}
