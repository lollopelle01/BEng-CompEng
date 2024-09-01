//
//  element.h
//  Prova d’Esame 1A di Giovedì 9 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 27/01/21.
//

#ifndef element_h
#define element_h

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#define DIM 2048

typedef struct{
    int identificatore;
    char cognome[DIM];
    char nome[DIM];
    float importo;
}Loan;

typedef Loan element;

int compare(Loan l1, Loan l2);

#endif /* element_h */
