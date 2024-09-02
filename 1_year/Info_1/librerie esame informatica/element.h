//
//  element.h
// 
//
//  Created by Lorenzo Pellegrino on 11/02/21.
// matricola: 0000971455

#ifndef element_h
#define element_h

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#define DIM 64

typedef struct{
    char cliente[DIM];
    char fornitore[DIM];
    int copie;
    float prezzo;
    char titolo[2*DIM];
} Ordine;

typedef Ordine element;

int compare(Ordine o1, Ordine o2);

#endif /* element_h */
