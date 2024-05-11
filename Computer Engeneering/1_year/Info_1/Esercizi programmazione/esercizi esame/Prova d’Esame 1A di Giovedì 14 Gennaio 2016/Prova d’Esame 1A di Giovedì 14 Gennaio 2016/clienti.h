//
//  clienti.h
//  Prova d’Esame 1A di Giovedì 14 Gennaio 2016
//
//  Created by Lorenzo Pellegrino on 09/02/21.
//

#ifndef clienti_h
#define clienti_h

#include "lettura.h"

//Es.1
Soggiorno * leggiSoggiorni(char * fileName, int * dim);
list leggiTariffe(char* fileName);
void stampaSoggiorni(Soggiorno * v, int dim);
void stampaTariffe(list elenco);

//Es.2
void scambia(Soggiorno *a, Soggiorno *b);
int trovaPosMax(Soggiorno v[], int n);
void naiveSort(Soggiorno v[], int n);
void ordinaSoggiorni(Soggiorno * v, int dim);

//Es.3
float costoSoggiorno(Soggiorno uno, list elencoTariffe);
float totale(int idCliente, list elencoTariffe, Soggiorno * elencoSog, int dim);


#endif /* clienti_h */
