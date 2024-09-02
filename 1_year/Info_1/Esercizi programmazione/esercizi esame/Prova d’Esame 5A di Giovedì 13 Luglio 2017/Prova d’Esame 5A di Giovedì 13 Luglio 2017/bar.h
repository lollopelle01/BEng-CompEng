//
//  bar.h
//  Prova d’Esame 5A di Giovedì 13 Luglio 2017
//
//  Created by Lorenzo Pellegrino on 08/02/21.
//

#ifndef bar_h
#define bar_h

#include "lettura.h"

//Es.1
Operazione * leggiTutte(char * fileName, int * dim);
void stampaOperazioni(Operazione * v, int dim);
void  merge(Operazione v[], int i1, int i2, int fine, Operazione vout[]);
void mergeSort(Operazione v[], int first, int last, Operazione vout[]);
void ordina(Operazione * v, int dim);

//Es.2
list clienti(Operazione * v, int dim);
list ordinaLista(list clienti);

//Es.3
float totale(Operazione*v, int dim, char* nome);
void saldi(Operazione * v, int dim);

#endif /* bar_h */
