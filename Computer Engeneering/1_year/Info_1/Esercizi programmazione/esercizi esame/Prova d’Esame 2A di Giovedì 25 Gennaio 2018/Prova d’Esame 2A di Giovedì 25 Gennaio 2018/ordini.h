//
//  ordini.h
//  Prova d’Esame 2A di Giovedì 25 Gennaio 2018
//
//  Created by Lorenzo Pellegrino on 03/02/21.
//

#ifndef ordini_h
#define ordini_h

#include "lettura.h"

//Es.1
Ordine leggiUno(FILE *fp);
Ordine * leggiTutti(char * fileName, int * dim);
void stampa(Ordine * v, int dim);

//Es.2
void  merge(Ordine v[], int i1, int i2, int fine, Ordine vout[]);
void mergeSort(Ordine v[], int first, int last, Ordine vout[]);
void ordina(Ordine * v, int dim);
list leggiCosti(char * fileName);

//Es.3
float trovaCosto(list costi, char *gusto);
void fatture(Ordine * v, int dim, list costi);

#endif /* ordini_h */
