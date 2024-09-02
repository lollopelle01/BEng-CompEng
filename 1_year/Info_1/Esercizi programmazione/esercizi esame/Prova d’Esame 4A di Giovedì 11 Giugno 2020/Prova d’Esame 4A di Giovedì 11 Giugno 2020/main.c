//
//  main.c
//  Prova d’Esame 4A di Giovedì 11 Giugno 2020
//
//  Created by Lorenzo Pellegrino on 30/01/21.
//

#include "bar.h"

int main() {
    list L=emptylist(), L2=emptylist();
    int dimL;
    Ordine *vett;
    
    
    L=leggiPaste("paste.txt");
    showlist(L);
    vett=leggiOrdini("ordini.txt", &dimL);
    for(int i=0; i<dimL; i++){
        printf("%s %d\n", vett[i].ingrediente, vett[i].n_paste);
    }
    free(vett);
    freelist(L);
    
    printf("\n\n");
    L=leggiPaste("paste.txt");
    vett=leggiOrdini("ordini.txt", &dimL);
    ordina(vett, dimL);
    for(int i=0; i<dimL; i++){
        printf("%s %d\n", vett[i].ingrediente, vett[i].n_paste);
    }
    if(soddisfacibile(L, vett[2]))
        printf("l'ordine %s %d è soddisfacibile\n", vett[0].ingrediente, vett[0].n_paste);
    else
        printf("l'ordine %s %d non è soddisfacibile\n", vett[0].ingrediente, vett[0].n_paste);
    printf("\n\n");
    L2=eseguiOrdine(L, vett[1]);
    showlist(L2);
    free(vett);
    freelist(L);
    freelist(L2);
    
}
