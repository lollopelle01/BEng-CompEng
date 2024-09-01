//
//  main.c
//  sequenza_di_0_e_1
//
//  Created by Lorenzo Pellegrino on 26/10/2020.
//
#include <stdio.h>
int main(void)
{

    char bit;
    int cont = 0, maxlung = 0;

    printf( "Inserisci la sequenza\n" );

    do
    {
            bit = getchar();
            getchar(); //consumo il newline
                if (bit == '0')
                    {
                            cont++;
                            if(cont > maxlung)
                                maxlung = cont;
                    }
                else
                    {
                        cont = 0;
                    }
    }
    while(bit == '0' || bit == '1');
    

printf("Lunghezza massima sotto-sequenza di 0: %d\n", maxlung);

    return(0);
}

