//
//  f_max.c
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
    if (x > y && x > z)
        return x;
    else if (y > x && y > z)
        return y;
    else if (z > x && z > y)
        return z;
}//il programma va lo stesso sul altri compilatori

int main(void)
{
    int n1, n2, n3;
    int M;
    printf("scrivi tre numeri e ti dirò il massimo: ");
    scanf("%d %d %d", &n1, &n2, &n3);
  
    M = max3(n1, n2, n3);
   
    printf("Il valore massimo tra i numeri inseriti e': %d ", M);
return (0);

}

