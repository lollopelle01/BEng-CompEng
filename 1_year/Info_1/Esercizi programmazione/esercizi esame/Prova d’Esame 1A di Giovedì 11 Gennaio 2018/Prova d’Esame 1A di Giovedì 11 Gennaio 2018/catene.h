//
//  catene.h
//  Prova d’Esame 1A di Giovedì 11 Gennaio 2018
//
//  Created by Lorenzo Pellegrino on 02/02/21.
//

#ifndef catene_h
#define catene_h

#include "lettura.h"

//Es.1
Catena leggiUno(FILE * fp);
Catena * leggiTutte(char * fileName, int * dim);
void stampa(Catena * v, int dim);

//Es.2
void scambia(Catena *a, Catena *b);
int trovaPosMax(Catena v[], int n);
void naiveSort(Catena v[], int n);
void ordina(Catena * v, int dim);
list conta(Catena * v, int dim);

//Es.3
list filtra(int diametro, int larg, list disponibili);


#endif /* catene_h */
