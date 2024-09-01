//
//  main.c
//  num-primo
//
//  Created by Lorenzo Pellegrino on 28/10/2020.
//
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int isPrimo(int x)
{
    int n, K,j=0;
    for(n=1; n<=x; n++)
    {
        int resto;
        resto=x%n;
        if(resto==0) K=1;
        else K=0;
        j=j+K;
    }
    
    if (j==2) return 1;
    if (j>=3) return 0;
}

int main()
{
    int N, p, i;
    printf("scrivi un numero N fino al quale vuoi trovare numeri primi:");
    scanf("%d", &N);
    
    for(i=1; i<=N; i++)
    {
        p=isPrimo(i);
        if(p==1)
            printf("%d\n", i);
    }




