//
//  main.c
//  somma_ricorsiva
//
//  Created by Lorenzo Pellegrino on 02/11/2020.
//

#include <stdio.h>
double somma(double a, int n)
{
    if (n==1)
        return (a-1/a);
    else
        return ((a-n/a)+ somma(a, n-1));
}

int main()
{
    int n;
    double a, s;
    printf("scrivi la base e l'inidce di cui vuoi la sommatoria: ");
    scanf("%lf %d", &a, &n);
    
    s=somma(a, n);
    
    printf("la somma e': %lf", s);
    return 0;
}

