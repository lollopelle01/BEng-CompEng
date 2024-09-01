//
//  voli.h
//  Prova d’Esame 4A di Giovedì 15 Giugno 2017
//
//  Created by Lorenzo Pellegrino on 07/02/21.
//

#ifndef voli_h
#define voli_h

#include "lettura.h"

//Es.1
Prenotazione * leggiTutti(char * fileName, int * dim);
void stampaPrenotazioni(Prenotazione * v, int dim);
Viaggiatore leggiViaggiatore(FILE *fp);
list leggiViaggiatori(char * fileName);

//Es.2
void  merge(Prenotazione v[], int i1, int i2, int fine, Prenotazione vout[]);
void mergeSort(Prenotazione v[], int first, int last, Prenotazione vout[]);
void ordina(Prenotazione * v, int dim);
list ordinaLista(list viaggiatori);

//Es.3
Viaggiatore estraiSingolo(list viaggiatori, char * biglietto);
list estraiViaggiatori(Prenotazione * v, int dim, list viaggiatori, char * idVolo);

#endif /* voli_h */
