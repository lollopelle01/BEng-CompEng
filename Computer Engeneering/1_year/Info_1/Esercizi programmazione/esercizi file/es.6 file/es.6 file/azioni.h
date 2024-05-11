//
//  azioni.h
//  es.6 file
//
//  Created by Lorenzo Pellegrino on 06/12/20.
//

#ifndef azioni_h
#define azioni_h

#include <stdio.h>
#include <string.h>

typedef struct{
    char nome_titiolo[64];
    float valore_apertura;
    float valore_chiusura;
    int giorno;
}Azione;

int leggi(FILE *fp, Azione dest[], int dim, char *nome);

#endif /* azioni_h */
