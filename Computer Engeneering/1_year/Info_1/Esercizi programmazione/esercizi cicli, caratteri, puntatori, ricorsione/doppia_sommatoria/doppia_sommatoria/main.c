//
//  main.c
//  doppia_sommatoria
//
//  Created by Lorenzo Pellegrino on 28/10/2020.
//

#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int somma(int n)
{
int sum=0, k;
for (k = 1; k <= n; k++)
sum = sum + k;
return sum;
}

int somma2(int m)
{
    int n=1, sum2=0, i, S=0;
    for (i = 1; i <= m; i++)
    sum2 = sum2 + somma(i);
    return sum2;
}

int main()
{
int num,sommatoria;
printf("inserisci un pedice di sommatoria: \n");
scanf("%d", &num );


    sommatoria = somma2(num);

printf("la somma e': %d\n", sommatoria);
    
    


}
