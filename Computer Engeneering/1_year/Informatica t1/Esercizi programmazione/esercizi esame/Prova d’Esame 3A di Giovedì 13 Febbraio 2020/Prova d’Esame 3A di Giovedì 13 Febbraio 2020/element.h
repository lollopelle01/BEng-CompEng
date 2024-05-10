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
    int identificativo;
    char natura;
    float superficie;
    int piano;
    int vani;
    float superficieXvano[5];
}Appartamento;

typedef struct{
    int identificartivo;
    char nome_cognome[DIM];
    float valore;
}Offerta;

typedef Appartamento element;

int compare(Appartamento l1, Appartamento l2);

//int compare(Loan l1, Loan l2);

#endif /* element_h */
