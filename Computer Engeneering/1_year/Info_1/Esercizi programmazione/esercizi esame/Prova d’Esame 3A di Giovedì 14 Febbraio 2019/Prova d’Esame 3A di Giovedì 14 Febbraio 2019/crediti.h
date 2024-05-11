//
//  crediti.h
//  Prova d’Esame 3A di Giovedì 14 Febbraio 2019
//
//  Created by Lorenzo Pellegrino on 02/02/21.
//

#ifndef crediti_h
#define crediti_h

#include "lettura.h"

//Es.1
Acquisto leggiUno(FILE * fp);
Acquisto * leggiCrediti(char * fileName, int * dim);
void stampaCrediti(Acquisto * crediti, int dim);

//Es.2
list trovaArticoli(Acquisto * crediti, int dim, char * nomeAzienda);
list ordinaArticoli(list articoli);
void  merge(Acquisto v[], int i1, int i2, int fine, Acquisto vout[]);
void mergeSort(Acquisto v[], int first, int last, Acquisto vout[]);
void ordina(Acquisto * v, int dim);

//Es.3
float costo(Acquisto * crediti, int dim, char * nomeAzienda);
void totali(Acquisto * v, int dim);

#endif /* crediti_h */
