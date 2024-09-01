//
//  main.c
//  media_ricorsiva
//
//  Created by Lorenzo Pellegrino on 04/11/2020.
//

#include <stdio.h>

double mediaricorsiva(double n, double sommaparziale)
{
    double num;
    printf("scrivi un numero e termina con 0: ");
    scanf("%lf", &num);
    if(num==0)
        return sommaparziale/n;
    else
        return mediaricorsiva(n+1, sommaparziale+num);
}

int main()
{
    double m;
    m=mediaricorsiva(0, 0);
    printf("la media e' : %lf\n", m);
    return 0;
}

