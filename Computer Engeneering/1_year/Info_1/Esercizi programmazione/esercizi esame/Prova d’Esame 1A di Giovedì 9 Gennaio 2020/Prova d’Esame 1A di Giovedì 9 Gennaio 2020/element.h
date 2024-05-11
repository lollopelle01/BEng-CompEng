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
    char contenuto[DIM];
    char tipo;
    int disponibili;
    float costo;
}Set;

typedef struct{
    int identificativo;
    int composizione[10];
    int dimL;
}Complex;

typedef Set element;

#endif /* element_h */
