//
//  main.c
//  es.8 file
//
//  Created by Lorenzo Pellegrino on 12/12/20.
//

#include <stdio.h>
#include "struct.h"
#include "function.h"

int main(){
    FILE *fb;
    int dimL=0;
    Persona elenco[20], utente;
    
    lettura("PEOPLE.txt", elenco, &dimL);
    
    printf("inserisci nome, cognome, giorno/mese/anno di nascita e sesso(M o F) di una persona:");
    scanf("%s %s %d/%d/%d %c", utente.nome, utente.cognome, &utente.giorno, &utente.mese, &utente.anno, &utente.sesso);

    if((fb=fopen("partners.dat", "wb"))==NULL){
        printf("errore di apertura del file binario\n");
        exit(2);
    }
    
    printf("%s %s e' compatibile con :\n", utente.nome, utente.cognome);
    for(int i=0; i<dimL; i++){
        if(compatibili(utente, elenco[i])){
            fwrite(&elenco[i], sizeof(Persona), 1, fb);
            printf("%s %s %d/%d/%d di sesso %c\n", elenco[i].nome, elenco[i].cognome, elenco[i].giorno, elenco[i].mese, elenco[i].anno, elenco[i].sesso);
        }
    }
    fclose(fb);
           
}


