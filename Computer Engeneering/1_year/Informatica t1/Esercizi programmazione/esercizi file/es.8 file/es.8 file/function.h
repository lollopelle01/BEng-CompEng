//
//  function.h
//  es.8 file
//
//  Created by Lorenzo Pellegrino on 12/12/20.
//

#ifndef function_h
#define function_h

#include <stdio.h>
#include "struct.h"

int compatibili(Persona p1, Persona p2);
void lettura(char filename[], Persona v[], int *indice);
int lettura_stringa(char v[], char separatore, FILE *f);



#endif /* function_h */
