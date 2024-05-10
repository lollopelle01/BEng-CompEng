//
//  main.c
//  f_max
//
//  Created by Lorenzo Pellegrino on 27/10/2020.
//
#define _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int mioMax(int x, int y)
{
if (x > y) return x;
else return y;
}

int max3(int x, int y, int z)
{
    if (x > y && x> z) return x;
    if (y > x && y> z) return y;
    if (z > x && z> y) return z;
}

int main(void)
{
    float n1, n2, n3;
    float M;
    printf("scrivi due o tre numeri e ti dirò il massimo: ");
    scanf("%f %f %f", &n1, &n2, &n3);
  
    M = max3(n1, n2, n3);
   
    printf("Il valore massimo tra i numeri inseriti e': %f ", M);
return (0);

}
