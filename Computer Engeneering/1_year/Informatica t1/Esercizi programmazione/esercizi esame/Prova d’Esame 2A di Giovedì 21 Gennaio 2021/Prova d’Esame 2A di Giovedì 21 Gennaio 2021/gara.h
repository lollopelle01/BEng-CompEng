//
//  gara.h
//  Prova d’Esame 2A di Giovedì 21 Gennaio 2021
//
//  Created by Lorenzo Pellegrino on 10/02/21.
//

#ifndef gara_h
#define gara_h

#include "lettura.h"

//Es.1
Atleta leggiSingoloAtleta(FILE *fp);
Atleta* leggiAtleti(char fileName[], int *dim);
void stampaAtleta(Atleta a);

//Es.2
int totale(Atleta a);
int compare(Atleta a1, Atleta a2, char* tipo);
void scambia(Atleta *a, Atleta *b);
void bubbleSort(Atleta v[], int n, char* tipo);
Atleta* ordinaAtletiPer(Atleta* a, int dimA, char *tipo, int* dim);

//Es.3
list atletiPremiati(Atleta* a, int dimA);
void premi(Atleta* a, int dimA);


#endif /* gara_h */
