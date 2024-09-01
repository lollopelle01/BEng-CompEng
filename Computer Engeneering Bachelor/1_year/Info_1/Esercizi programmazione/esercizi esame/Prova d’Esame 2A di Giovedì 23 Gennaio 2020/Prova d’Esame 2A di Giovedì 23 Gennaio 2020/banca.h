//
//  banca.h
//  Prova d’Esame 2A di Giovedì 23 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 28/01/21.
//

#ifndef banca_h
#define banca_h

#include "list.h"
#include "lettura.h"

//Es.1
Loan leggiUnLoan(FILE * fp);
Loan * leggiLoanAttivi(char * fileName, int * dim);

//Es.2
void insOrd(Loan v[], int pos);
void insertSort(Loan v[], int n);
void ordina(Loan * elenco, int dim);
list estrai(Loan * elenco, int dim, char * cognome, char * nome);

//Es.3
void espo(Loan * elenco, int dim);

#endif /* banca_h */
