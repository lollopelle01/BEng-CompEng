//
//  bar.h
//  Prova d’Esame 5A di Giovedì 9 Luglio 2020
//
//  Created by Lorenzo Pellegrino on 30/01/21.
//

#ifndef bar_h
#define bar_h

#include "list.h"
//Es.1
Ingrediente * leggiIng(char * fileName, int * dim);
void stampa(Ingrediente * elenco, int dim);
Ingrediente trova(Ingrediente * elenco, int dim, char * nome);

//Es.2
void scambia(Ingrediente *a, Ingrediente *b);
void bubbleSort(Ingrediente v[], int n);
void ordina(Ingrediente * v, int dim);
list leggiPanini(char * fileName, Ingrediente * elenco, int dim);

//Es.3
list eliminaDup(list panini);

#endif /* bar_h */
