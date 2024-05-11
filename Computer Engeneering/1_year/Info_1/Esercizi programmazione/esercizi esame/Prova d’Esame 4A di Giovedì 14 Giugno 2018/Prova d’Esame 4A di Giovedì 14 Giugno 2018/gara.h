//
//  gara.h
//  Prova d’Esame 4A di Giovedì 14 Giugno 2018
//
//  Created by Lorenzo Pellegrino on 04/02/21.
//

#ifndef gara_h
#define gara_h

#include "lettura.h"

//Es.1
Gara leggiUno(FILE * fp);
list leggi(char * fileName);
int punti(Gara g);

//Es.2
void scambia(Squadra *a, Squadra *b);
void bubbleSort(Squadra v[], int n);
void ordina(Squadra * v, int dim);
int appartiene(char* squadra, list elenco);
Squadra * estrai(list elenco, int * dim);

//Es.3
void totale(Squadra * classifica, int dim, list elenco);

#endif /* gara_h */
