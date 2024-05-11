//
//  main.c
//  fibonacci
//
//  Created by Lorenzo Pellegrino on 01/11/2020.
//
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int fibonacci(int n)
{
    if (n==0||n==1)
      return n;
    if (n==2)
        return 1;
    else
      return (fibonacci(n-1)+fibonacci(n-2));
}

int main()
{
    int n, f;
    printf("scrivi il numero di fibonacci che vuoi: ");
    scanf("%d", &n);
    
    f=fibonacci(n);
    
    printf("Fibonacci(%d) = %d\n", n, f);
    return 0;
}
