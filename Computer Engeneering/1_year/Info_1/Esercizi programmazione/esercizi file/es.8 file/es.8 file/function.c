//
//  function.c
//  es.8 file
//
//  Created by Lorenzo Pellegrino on 12/12/20.
//

#include "function.h"
#include "struct.h"
#include <math.h>

int lettura_stringa(char v[], char separatore, FILE *f){
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

void lettura(char filename[], Persona v[], int *indice){
    Persona temp;
    FILE *f;
    int boolean=1;
    *indice=0;
    
    if((f=fopen(filename, "r"))==NULL){
        printf("errore di lettura di %s\n", filename);
        exit(1);
    }
    do{
        boolean=lettura_stringa(temp.cognome, ';', f);
        boolean=boolean && lettura_stringa(temp.nome, ';', f);
        boolean=boolean && fscanf(f, "%d/%d/%d %c\n", &temp.giorno, &temp.mese, &temp.anno, &temp.sesso);
        
        if(boolean){
            v[*indice]=temp;
            (*indice)++;
        }
    }
    while(boolean);
    fclose(f);
}

int compatibili(Persona p1, Persona p2){
    int boolean=0;
    int differenza= abs(p1.anno-p2.anno);
    if( p1.sesso!=p2.sesso && differenza<=5){
        boolean=1;
    }
    return boolean;
}


