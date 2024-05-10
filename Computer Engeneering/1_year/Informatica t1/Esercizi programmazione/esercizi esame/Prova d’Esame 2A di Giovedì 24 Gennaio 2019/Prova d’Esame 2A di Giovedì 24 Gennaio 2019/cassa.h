//
//  cassa.h
//  Prova d’Esame 2A di Giovedì 24 Gennaio 2019
//
//  Created by Lorenzo Pellegrino on 01/02/21.
//

#ifndef cassa_h
#define cassa_h

#include "lettura.h"

//Es.1
Pasto leggiPasto(FILE *fp);

Pasto * leggiPasti(char * fileName, int * dim);

void stampaPasti(Pasto * elenco, int dim);

list leggiTariffe(char * fileName);

#endif /* cassa_h */
