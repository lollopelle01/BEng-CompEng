//
//  lettura.c
//  
//
//  Created by Lorenzo Pellegrino on 11/02/21.
//  matricola: 0000971455

#include "lettura.h"
int lettura_stringa(char v[], char separatore, FILE *f){  //lettura di una stringa con spazi e separatore
    int i=0;
    char ch=fgetc(f);
    while(ch!=EOF && ch!=separatore && ch!='\n'){
        v[i]=ch;
        i++;
        ch=fgetc(f);
    }
    v[i]='\0';
    return i;
}
