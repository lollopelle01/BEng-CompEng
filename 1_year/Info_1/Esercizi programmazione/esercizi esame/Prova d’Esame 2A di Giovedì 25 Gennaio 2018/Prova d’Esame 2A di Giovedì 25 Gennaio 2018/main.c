//
//  main.c
//  Prova d’Esame 2A di Giovedì 25 Gennaio 2018
//
//  Created by Lorenzo Pellegrino on 03/02/21.
//

#include "ordini.h"

int main() {
    
    {//Es.1
        Ordine *vett;
        int dimL;
        vett=leggiTutti("ordini.txt", &dimL);
        printf("\nlista ordini:\n");
        stampa(vett, dimL);
        free(vett);
    }
    
    {//Es.2
        Ordine *vett;
        int dimL;
        list L;
        vett=leggiTutti("ordini.txt", &dimL);
        ordina(vett, dimL);
        printf("\nlista ordini riordinata:\n");
        stampa(vett, dimL);
        L=leggiCosti("costi.txt");
        printf("\nlista costi:\n");
        showlist(L);
        freelist(L);
        free(vett);
    }
    
    {//Es.3
        Ordine *vett;
        int dimL;
        list L;
        vett=leggiTutti("ordini.txt", &dimL);
        L=leggiCosti("costi.txt");
        fatture(vett, dimL, L);
        freelist(L);
        free(vett);
    }
}
