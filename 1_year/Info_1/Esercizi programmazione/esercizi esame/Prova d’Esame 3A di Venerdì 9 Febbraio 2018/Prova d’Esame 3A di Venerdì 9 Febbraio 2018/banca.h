//
//  banca.h
//  Prova d’Esame 3A di Venerdì 9 Febbraio 2018
//
//  Created by Lorenzo Pellegrino on 03/02/21.
//

#ifndef banca_h
#define banca_h

#include "lettura.h"

//Es.1
Cliente leggiCliente(FILE *fp);
list leggiClienti(char * fileName);
Prodotto * leggiProd(char * fileName, int * dim);

//Es.2
void scambia(Prodotto *a, Prodotto *b);
void bubbleSort(Prodotto v[], int n);
void ordina(Prodotto * v, int dim);
list eliminaDup(list clienti);

//Es.3
void proposte(list clienti, Prodotto * v, int dim);

#endif /* banca_h */
