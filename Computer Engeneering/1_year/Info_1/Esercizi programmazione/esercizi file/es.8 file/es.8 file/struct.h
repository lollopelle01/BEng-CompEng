//
//  struct.h
//  es.8 file
//
//  Created by Lorenzo Pellegrino on 12/12/20.
//

#ifndef struct_h
#define struct_h

#include <stdio.h>
#include <stdlib.h>

#define DIM 21
typedef struct{
    char cognome[DIM];
    char nome[DIM];
    int giorno;
    int mese;
    int anno;
    char sesso;
    
}Persona;

#endif /* struct_h */
