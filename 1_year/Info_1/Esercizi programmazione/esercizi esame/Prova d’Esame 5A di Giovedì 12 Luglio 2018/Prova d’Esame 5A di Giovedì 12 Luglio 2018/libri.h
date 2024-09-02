//
//  libri.h
//  Prova d’Esame 5A di Giovedì 12 Luglio 2018
//
//  Created by Lorenzo Pellegrino on 04/02/21.
//

#ifndef libri_h
#define libri_h

#include "lettura.h"

typedef struct{
    int id_ordine;
    int id_utente;
    list elencoLibri;
}Ordine;

//Es.1
list leggi(char * fileName);
void stampaLibri(list elencoLibri);
list filtra(list elencoLibri);

//Es.2
Ordine estrai(list elencoLibri, int idOrdine);
int compare(Ordine o1, Ordine o2);
void scambia(Ordine *a, Ordine *b);
void bubbleSort(Ordine v[], int n);
void ordina(Ordine * v, int dim);



#endif /* libri_h */
