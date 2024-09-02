//
//  main.c
//  es.5 file
//
//  Created by Lorenzo Pellegrino on 02/12/20.
//
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include "registratore.h"

int main(void) {
    Scontrino temp;
    FILE* fp;

    if ((fp = fopen("reg.dat", "wb")) == NULL)
        exit(-1);
    do {
        printf("Inserisci totale speso: ");
        scanf("%f", &(temp.val));
        printf("\nInserisci numero di articoli acquistati: ");
        scanf("%d", &(temp.num));
        printf("\n");
        if (temp.val != 0 || temp.num != 0)
            scrivi(fp, temp);
    } while (temp.val != 0 || temp.num != 0);
    fclose(fp);

    if ((fp = fopen("reg.dat", "rb")) == NULL)
        exit(-1);
    while (leggi(fp, &temp) > 0)
        printf("\nPrezzo totale degli articoli: %f, Numero articoli acquistati: %d\n", temp.val, temp.num);
    fclose(fp);
    return (0);
}
