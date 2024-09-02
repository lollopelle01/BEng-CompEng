//
//  main.c
//  es. 2 file
//
//  Created by Lorenzo Pellegrino on 30/11/20.
//

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct{
    int matricola;
    int cdl;
}Dati;

typedef struct{
    int matricola;
    char nome[21];
    char cognome[31];
    char via[31];
    char citta[31];
    int cap;
    
}Indirizzo;

typedef struct{
    int matricola;
    char nome[21];
    char cognome[31];
    char via[31];
    char citta[31];
    int cap;
    int cdl;
} Elemento;

Elemento riempiel(Dati d, Indirizzo i){
    Elemento e;
    e.matricola=d.matricola;
    strcpy(e.nome, i.nome);
    strcpy(e.cognome, i.cognome);
    strcpy(e.via, i.via);
    strcpy(e.citta, i.citta);
    e.cap=i.cap;
    e.cdl=d.cdl;
    return e;
}

typedef Elemento Tabella[50];

int main(){
    Dati d;
    Indirizzo i;
    Elemento e;
    Tabella t;
    FILE *f1, *f2;
    int trovato, ins=0, C, counter=0;
    
    if((f1=fopen("dati.txt", "r"))==NULL ||( f2=fopen("indirizzo.txt", "r"))==NULL){
        exit(1);
    }
    else{
    
        while(fscanf(f1, "%d %d\n", &d.matricola, &d.cdl)==2){
            trovato=0;
            rewind(f2);
            while(fscanf(f2, "%d %s %s %s %s %d\n", &i.matricola, i.nome, i.cognome, i.via, i.citta, &i.cap)==6 && !trovato){
                if(i.matricola==d.matricola){
                    trovato=1;
                    e=riempiel(d, i);
                    t[ins]=e;
                    ins++;
                }
            }
        }
        fclose(f1);
        fclose(f2);
        
        printf("inserisci un corso di laurea: ");
        scanf("%d", &C);
        for(int i=0; i<ins; i++){
            if(t[i].cdl==C)
                counter++;
        }
        printf("in quel corso di laurea c'e' il %3.2f percento delle matricole\n\n", (float)counter*100/(ins));
        
        if((f1=fopen("bologna.txt", "w"))==NULL)
            exit(2);
        else{
        fprintf(f1, "Tra tutti gli studenti, quelli che abitano a bologna sono: \n");
        for(int i=0; i<ins; i++){
            if(strcmp(t[i].citta, "bologna")==0){
                fprintf(f1 ,"%s %s %d\n", t[i].nome, t[i].cognome, t[i].matricola);
            }
        }
        fclose(f1);
        }
    }
}
