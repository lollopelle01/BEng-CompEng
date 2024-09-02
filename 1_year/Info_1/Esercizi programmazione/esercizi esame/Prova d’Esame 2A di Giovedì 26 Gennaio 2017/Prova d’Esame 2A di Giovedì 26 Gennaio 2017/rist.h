//
//  rist.h
//  Prova d’Esame 2A di Giovedì 26 Gennaio 2017
//
//  Created by Lorenzo Pellegrino on 06/02/21.
//

#ifndef rist_h
#define rist_h

#include "lettura.h"

//Es.1
Pasto leggiPasto(FILE * fp);
Pasto * leggiTutti(char * fileName, int * dim);
void stampaPasti(Pasto * v, int dim);

//Es.2
void insOrd(Pasto v[], int pos);
void insertSort(Pasto v[], int n);
void ordina(Pasto * v, int dim);
list elenco(char * fileName);

//Es.3
float totaleAzienda(Pasto * v, int dim, list elenco, char * azienda);
void totali(Pasto * v, int dim, list elenco);

#endif /* rist_h */
