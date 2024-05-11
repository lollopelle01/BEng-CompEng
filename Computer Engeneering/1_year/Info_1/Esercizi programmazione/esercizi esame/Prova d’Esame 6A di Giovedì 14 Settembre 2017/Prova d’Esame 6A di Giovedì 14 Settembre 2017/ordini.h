//
//  ordini.h
//  Prova d’Esame 6A di Giovedì 14 Settembre 2017
//
//  Created by Lorenzo Pellegrino on 08/02/21.
//

#ifndef ordini_h
#define ordini_h

#include "lettura.h"

//Es.1
list leggiOrdini(char * fileName);
void stampaOrdini(list v);
int contaOccorrenze(list ordini, Ordine unOrdine);
list filtra(list ordini);

//Es.2
Ordine * perCliente(list ordini, int cliente, int * dim);
void scambia(Ordine *a, Ordine *b);
void bubbleSort(Ordine v[], int n);
void ordina(Ordine * v, int dim);

//Es.3
float importo_totale(Ordine* v, int dim);
int migliore(list ordini);

#endif /* ordini_h */
