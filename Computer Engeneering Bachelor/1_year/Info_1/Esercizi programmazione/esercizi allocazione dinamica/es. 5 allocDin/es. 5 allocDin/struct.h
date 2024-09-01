//
//  struct.h
//  es. 5 allocDin
//
//  Created by Lorenzo Pellegrino on 17/12/20.
//

#ifndef struct_h
#define struct_h

#include <stdio.h>

typedef struct {
    char dicitura[36];
    int numero_crediti;
    int voto;
}Esame;

typedef struct{
    int dim;
    Esame *v;
}V_esame;

#endif /* struct_h */
