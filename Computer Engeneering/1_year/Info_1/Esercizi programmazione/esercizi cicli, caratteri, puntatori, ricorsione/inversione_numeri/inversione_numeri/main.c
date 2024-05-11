//
//  main.c
//  inversione_numeri
//
//  Created by Lorenzo Pellegrino on 02/11/2020.
//
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <math.h>

int inversione(int x, int part)
{
if (x == 0)
return part;
else
{
return inversione(x / 10, part * 10 + x % 10);
}
}

int main()
{
int n, i;
printf("scrivi un numero da invertire: ");
scanf("%d", &n);

i = inversione(n, 0);

printf("il numero inverito e': %d\n", i);

return 0;
}
