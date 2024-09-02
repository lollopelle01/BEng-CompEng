//
//  negozio.h
//  Prova d’Esame 1A di Giovedì 9 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 27/01/21.
//

#ifndef negozio_h
#define negozio_h

#include "list.h"

//Es.1
Set leggiUnSet(FILE * fp);
list leggiTuttiSet(char * fileName);

//Es.2
Complex leggiUnComplex(FILE * fp);
void scambia(int *a, int *b);
void quickSortR(int a[], int iniz, int fine);
void quickSort(int a[], int dim);
Complex trovaComplex(char * fileName, int target);

//Es.3
int check(list elenco, int target, Set * theSet);
int disp(list elenco, int target, char * fileName);


#endif /* negozio_h */
