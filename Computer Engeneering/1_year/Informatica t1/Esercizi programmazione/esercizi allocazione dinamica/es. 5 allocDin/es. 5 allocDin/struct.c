//
//  struct.c
//  es. 5 allocDin
//
//  Created by Lorenzo Pellegrino on 17/12/20.
//

#include "struct.h"
typedef struct {
    char dicitura[36];
    int numero_crediti;
    int voto;
}Esame;

typedef struct{
    int dim;
    Esame *v;
}V_esame;

