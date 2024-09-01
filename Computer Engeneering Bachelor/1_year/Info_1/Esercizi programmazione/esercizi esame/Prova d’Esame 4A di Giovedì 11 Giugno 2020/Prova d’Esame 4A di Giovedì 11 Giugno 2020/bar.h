//
//  bar.h
//  Prova d’Esame 4A di Giovedì 11 Giugno 2020
//
//  Created by Lorenzo Pellegrino on 30/01/21.
//

#ifndef bar_h
#define bar_h

#include "list.h"

//Es.1
list leggiPaste(char * fileName);

Ordine * leggiOrdini(char * fileName, int * dim);

//Es.2
void scambia(Ordine *a, Ordine *b);

void bubbleSort(Ordine* v, int n);

void ordina(Ordine * v, int dim);

int soddisfacibile(list paste, Ordine unOrdine);

//Es.3
list eseguiOrdine(list paste, Ordine unOrdine);

#endif /* bar_h */
