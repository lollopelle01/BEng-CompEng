//
//  main.c
//  Prova d’Esame 5A di Giovedì 12 Luglio 2018
//
//  Created by Lorenzo Pellegrino on 04/02/21.
//

#include "libri.h"

int main() {
    
    {//Es.1
        list L;
        L=leggi("libri.txt");
        printf("lista libri:\n");
        stampaLibri(L);
        L=filtra(L);
        printf("lista libri filtrata:\n");
        stampaLibri(L);
        freelist(L);
    }
}
