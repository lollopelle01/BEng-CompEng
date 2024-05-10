//
//  main.c
//  quadrato_x
//
//  Created by Lorenzo Pellegrino on 28/10/2020.
//
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

float square(float x)
{
    float s;
    s=x*x;
    return s;
}

float cube(float x)
{
    float c;
    c=square(x)*x;
    return c;
}

int main()
{
    float n,s, c;
    printf("scrivi un numero e ti do' il quadrato e il cubo: ");
    scanf("%f", &n);
    
    s=square(n);
    
    c=cube(n);
    printf("il cubo e il quadrato sono rispettivamente: %f\n %f\n", c, s);
    
}
