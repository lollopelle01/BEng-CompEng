//
//  main.c
//  sequenze_di_cifre
//
//  Created by Lorenzo Pellegrino on 26/10/2020.
//

#include <stdio.h>

int main()
{
    char num;
    int curVal=0, oldVal=0, somma=0;
    int sommaMax=0, totM=0, tot2M=0;
    printf("scrivi una sequenza di cifre: \n");
    
    do
    {
        scanf("%c", &num);
        num=getchar();
        getchar();
        curVal = num - '0';
        
        if(curVal <= oldVal)
        {
            if(somma > sommaMax)
            { sommaMax = somma; }
            somma = curVal;
            totM=sommaMax;
        }
        else
            somma = somma + curVal;
        
        oldVal = curVal;
    }
    while(curVal != '0');
    tot2M=totM;
    printf("la somma massima di sequenze decrescenti è: %d\n", tot2M);
    
    return 0;   
}
