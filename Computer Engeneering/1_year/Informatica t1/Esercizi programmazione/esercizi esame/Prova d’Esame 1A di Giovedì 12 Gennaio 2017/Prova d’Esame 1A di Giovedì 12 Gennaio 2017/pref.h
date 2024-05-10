//
//  pref.h
//  Prova d’Esame 1A di Giovedì 12 Gennaio 2017
//
//  Created by Lorenzo Pellegrino on 05/02/21.
//

#ifndef pref_h
#define pref_h

#include "lettura.h"

//Es.1
Preferenza leggiPref(FILE * fp);
Preferenza * leggiTutte(char * fileName, int * dim);
void stampaPref(Preferenza * v, int dim);

//Es.2
void scambia(Preferenza *a, Preferenza *b);
int trovaPosMax(Preferenza v[], int n);
void naiveSort(Preferenza v[], int n);
void ordina(Preferenza * v, int dim);
list filtra(Preferenza * v, int dim);

//Es.3
list totali(Preferenza * v, int dim, list titoli);


#endif /* pref_h */
