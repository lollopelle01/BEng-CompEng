//
//  libri.h
//  esame
//
//  Created by Lorenzo Pellegrino on 11/02/21.
//

#ifndef libri_h
#define libri_h

#include "lettura.h"

//Es.1
list leggi(char fileName[], char select, char nome[]);
void stampaOrdini(list elenco);

//Es.2
int contaCopieOrdinate(list elenco, char * titolo);
void aggregaPerTitolo(list elenco);
void scambia(Ordine *a, Ordine *b);
void quickSortR(Ordine a[], int iniz, int fine);
void quickSort(Ordine a[], int dim);
Ordine* prepara(list elenco, int *dim);

//Es.3
list nuoviOrdini(list elencoClienti, list elencoFornitori);

#endif /* libri_h */
