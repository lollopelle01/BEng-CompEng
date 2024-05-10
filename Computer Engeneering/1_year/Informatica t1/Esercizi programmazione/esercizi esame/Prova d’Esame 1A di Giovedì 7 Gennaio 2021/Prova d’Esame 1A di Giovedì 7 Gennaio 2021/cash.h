//
//  cash.h
//  Prova d’Esame 1A di Giovedì 7 Gennaio 2021
//
//  Created by Lorenzo Pellegrino on 10/02/21.
//

#ifndef cash_h
#define cash_h

#include "lettura.h"

//Es.1
Operazione * leggiTutteOperazioni(char * fileName, int * dim);
list leggiNegozi(char * fileName);
void stampaOperazioni(Operazione * v, int dim);

//Es.2
void scambia(Operazione *a, Operazione *b);
void bubbleSort(Operazione v[], int n);
void ordina(Operazione * v, int dim);
int negozioFisico(char * nomeNegozio, list negozi);
Operazione * filtra(Operazione * v, int dim, list negozi, int * dimLog);

//Es.3
float spesaCliente(int idCliente, Operazione * v, int dim);


#endif /* cash_h */
