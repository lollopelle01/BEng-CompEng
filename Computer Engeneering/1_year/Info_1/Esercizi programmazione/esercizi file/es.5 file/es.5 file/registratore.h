//
//  registratore.h
//  es.5 file
//
//  Created by Lorenzo Pellegrino on 03/12/20.
//

#include <stdio.h>

typedef struct {
    float val;
    int num;
} Scontrino;

int leggi(FILE* fp, Scontrino* dest);
int scrivi(FILE* fp, Scontrino src);

 

