//
//  main.c
//  integrali2
//
//  Created by Lorenzo Pellegrino on 05/11/2020.
//
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

float f(float x)
{
   return x*x;
}

float integrale(float a, float b, float N)
{
    float a1, b1, altezza, base_minore, base_maggiore, Area, Area_base, Area_singola;
    altezza=(b-a)/N;
    a1=altezza*(N-1);
    base_minore=f(a1);
    b1=altezza*N;
    base_maggiore= f(b1);
    
    Area=((base_minore + base_maggiore)*altezza)/2.0F;
    Area_base=((f(a)+f(b))*(b-a))/2.0F;
    Area_singola=((f(a1)+f(b1))*(b1-a1))/2.0F;
    
    if (N==0)
        return (0);
    else
        return (Area_singola+integrale(a-a1, b-b1, N-1));
}

int main()
{
    float a, b, N, risultato;
    printf("scrivi il gli estremi dell'intervallo e la precisione che vuoi: ");
    scanf("%f %f %f", &a, &b, &N);
    if(a>b)
        risultato=-integrale(a, b, N);
    if(a==b)
        risultato=0;
    else
        risultato=integrale(a, b, N);
        
    
    printf("il risultato dell'integrale e': %f\n", risultato);
    return 0;
}
