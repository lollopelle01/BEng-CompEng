//
//  registratore.c
//  es. 7 file
//
//  Created by Lorenzo Pellegrino on 07/12/20.
//

#include "registratore.h"
#include <string.h>

void copy(FILE* source, FILE* dest, char *name, int *result){
    Transaction temp;
    *result=0;
    while(fread(&temp, sizeof(Transaction), 3, dest)>0){
        if(strcmp(temp.customer, name)==0){
            fprintf(dest, "%f ", temp.value);
            (*result)++;
            
        }
        fprintf(dest, "\n");
    }
}





