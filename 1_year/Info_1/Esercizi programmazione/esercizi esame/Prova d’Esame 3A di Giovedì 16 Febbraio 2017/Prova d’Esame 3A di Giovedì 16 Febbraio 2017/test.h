//
//  test.h
//  Prova d’Esame 3A di Giovedì 16 Febbraio 2017
//
//  Created by Lorenzo Pellegrino on 07/02/21.
//

#ifndef test_h
#define test_h

#include "lettura.h"

//Es.1
Test leggiUnTest(FILE * fp);
Test * leggiTutti(char * fileName, int * dim);
void stampaTest(Test * v, int dim);

//Es.2
void scambia(Test *a, Test *b);
void bubbleSort(Test v[], int n);
void ordina(Test * v, int dim);
list pref(char * fileName);

//Es.3
Test testMilgliore(char* matricola, Test* v, int dim);
void ammessi(Test * v, int dim, list elenco, int postiDisponibili, char* corso);

#endif /* test_h */
