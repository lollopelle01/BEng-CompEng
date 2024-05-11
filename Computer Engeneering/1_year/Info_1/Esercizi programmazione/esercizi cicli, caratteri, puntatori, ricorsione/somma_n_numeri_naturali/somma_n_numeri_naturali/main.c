//
//  main.c
//  somma_n_numeri_naturali
//
//  Created by Lorenzo Pellegrino on 02/11/2020.
//
#define _CRT_SECURE_NO_WARNINGS
#include<stdio.h>

int ric(int x)
{
if (x == 0)
return 0;
else
return (x + ric(x - 1));
}

int main()
{
int n, s;
printf("di quanti numeri vuoi la somma:");
scanf("%d", &n);

s = ric(n);

printf("la somma di %d numeri e': %d\n", n, s);


return 0;

}
