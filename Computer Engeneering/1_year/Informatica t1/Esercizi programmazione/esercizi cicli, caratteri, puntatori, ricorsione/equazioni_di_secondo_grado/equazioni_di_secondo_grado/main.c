//
//  main.c
//  equazioni_di_secondo_grado
//
//  Created by Lorenzo Pellegrino on 08/11/2020.
//

#include <stdio.h>
#include <math.h>
#include "Header.h"

void soluzione(float a, float b, float c, float *x1, float *x2);

int main()
{
    float a, b, c, sol1, sol2, delta;
    
    printf("scrivi a, b, c della funzione ax^2 + bx + c = 0 : ");
    scanf("%f %f %f", &a, &b, &c);
    delta=b*b-4*a*c;
    soluzione(a, b, c, &sol1, &sol2);
    if (delta>=0)
        printf("la soluzione e': \n%f\n%f\n", sol1, sol2);
    return 0;
}


