//
//  main.c
//  es. 1 strutture
//
//  Created by Lorenzo Pellegrino on 28/11/20.
//

#include <stdio.h>
#include <stdlib.h>

struct struttura{
    char denominazione[70];
    char cognome[70];
    int iscritti;
}corso;

int leggi(struct struttura v[], int dimF){
    int i=0;
    for(i=0; i<dimF; i++){
        printf("inserisci il nome del corso: ");
        scanf("%s", v[i].denominazione);
        printf("inserisci il cognome del docente: ");
        scanf("%s", v[i].cognome);
        printf("inserisci il numero dei partecipandi: ");
        scanf("%d", &v[i].iscritti);
        
        printf("\n");
    }
    return i;
}

int main(){
    int dimF;
    
    printf("scrivi quanti corsi vuoi inserire: ");
    scanf("%d", &dimF);
    
    struct struttura v[dimF];
    int dimL, somma_iscritti=0, i, media;
    
    dimL=leggi(v, dimF);
    
    for (i=0; i<dimL; i++){
        somma_iscritti=somma_iscritti+v[i].iscritti;
    }
    media=somma_iscritti/(i);
    
    printf("\n\ni corsi con un numero di partecipanti superiore alla media sono: \n\n");
    
    for (i=0; i<dimL; i++){
        if(v[i].iscritti>media){
            printf("%s\n", v[i].denominazione);
            printf("%s\n", v[i].cognome);
            printf("%d\n", v[i].iscritti);
            printf("\n");
        }
    }
    return 0;
}
