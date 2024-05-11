//
//  registratore.h
//  es. 7 file
//
//  Created by Lorenzo Pellegrino on 07/12/20.
//

#ifndef registratore_h
#define registratore_h

#include <stdio.h>

typedef struct {
     char customer[129];
     int transactionId;
     float value;
} Transaction;

void copy(FILE* source, FILE* dest, char *name, int *result);


#endif /* registratore_h */
