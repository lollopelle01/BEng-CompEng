//
//  main.c
//  ipotenusa
//
//  Created by Lorenzo Pellegrino on 28/10/2020.
//
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <math.h>

float perimetro(float a, float b, float c)
{
    float p=a+b+c;
    return p;
}

float area(float a, float b, float c)
{
    float A;
    float sp= perimetro(a, b, c)/2.0F;
    
    A=sqrtf(sp*(sp-a)*(sp -b)*(sp -c));
    return A;
}

int main()
{
    float a, b, c, p, A;
    printf("scrivi tre valori che rappresentano i lati del triangolo e io ti dico perimetro ed area:");
    scanf("%f %f %f", &a, &b, &c);
    if (((a+b)<=c)||((a+c)<=b)||((b+c)<=a))
        printf("non è un triangolo\n");
    else
    {
        p=perimetro(a, b, c);
        A=area(a, b, c);
        printf("il perimetro e': %f\nl'area e': %f\n", p, A);
    }
    return 0;
}

