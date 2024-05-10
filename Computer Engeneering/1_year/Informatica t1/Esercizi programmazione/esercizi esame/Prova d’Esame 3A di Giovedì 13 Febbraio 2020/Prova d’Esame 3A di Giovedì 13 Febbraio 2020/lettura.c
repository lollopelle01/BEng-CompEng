//
//  lettura.c
//  Prova d’Esame 2A di Giovedì 23 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 28/01/21.
//

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
