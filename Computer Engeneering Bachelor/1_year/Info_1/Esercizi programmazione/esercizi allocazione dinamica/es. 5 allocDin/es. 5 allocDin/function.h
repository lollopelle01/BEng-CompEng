//
//  function.h
//  es. 5 allocDin
//
//  Created by Lorenzo Pellegrino on 17/12/20.
//

#ifndef function_h
#define function_h

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "struct.h"

int leggiEsamiTxt(char *nomeFile, V_esame* vett);
int leggiEsamiBin(char *nomeFile, V_esame* vett);
void stampaEsami(V_esame vett);
float media(V_esame vett);
V_esame filtra(V_esame vett, char *pattern);
int salvaReport(V_esame vett, char* nomeFile);


#endif /* function_h */
