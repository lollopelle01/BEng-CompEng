//
//  metro.h
//  Prova d’Esame 1A di Giovedì 10 Gennaio 2019
//
//  Created by Lorenzo Pellegrino on 31/01/21.
//

#ifndef metro_h
#define metro_h

#include "lettura.h"

//Es.1
Evento leggiUno(FILE * fp);
list leggiTutti(char * fileName);
Tariffa leggiTariffa(FILE*fp);
Tariffa * leggiTariffe(char * fileName, int * dim);

//Es.2
void scambia(Tariffa *a, Tariffa *b);
void quickSortR(Tariffa a[], int iniz, int fine);
void quickSort(Tariffa a[], int dim);
void ordina(Tariffa * v, int dim);
float ricerca(Tariffa * v, int dim, char * ingresso, char * uscita);

//Es.3
void totali(Tariffa * tariffe, int dim, list eventi);

#endif /* metro_h */
