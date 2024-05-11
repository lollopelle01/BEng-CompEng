//
//  main.c
//  Prova d’Esame 3A di Giovedì 13 Febbraio 2020
//
//  Created by Lorenzo Pellegrino on 28/01/21.
//

#include "palazzo.h"

int main(){
    list L;
    L=leggiTuttiAppartamenti("palazzo.txt");
    showlist(L);
    freelist(L);
    printf("ci sei?");
    
}
