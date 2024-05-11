//
//  registratore.c
//  es.5 file
//
//  Created by Lorenzo Pellegrino on 03/12/20.
//

#include "registratore.h"

int leggi(FILE* fp, Scontrino* dest) {
    return fread(dest, sizeof(Scontrino), 1, fp);
}
int scrivi(FILE* fp, Scontrino src) {
    return fwrite(&src, sizeof(Scontrino), 1, fp);
}
