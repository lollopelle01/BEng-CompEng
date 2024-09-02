//
//  main.c
//  Prova d’Esame 1A di Giovedì 12 Gennaio 2017
//
//  Created by Lorenzo Pellegrino on 05/02/21.
//

#include "pref.h"

int main() {
    
    {//Es.1
        Preferenza* vett;
        int dimL;
        vett=leggiTutte("preferenze.txt", &dimL);
        printf("elenco:\n");
        stampaPref(vett, dimL);
        free(vett);
    }
    
    {//Es.2
        Preferenza* vett;
        list L;
        int dimL;
        vett=leggiTutte("preferenze.txt", &dimL);
        ordina(vett, dimL);
        printf("elenco ordinato:\n");
        stampaPref(vett, dimL);
        L=filtra(vett, dimL);
        printf("lista:\n");
        showlist(L);
        freelist(L);
        free(vett);
    }
    
    {//Es.3
        Preferenza* vett;
        list L;
        int dimL;
        vett=leggiTutte("preferenze.txt", &dimL);
        L=filtra(vett, dimL);
        L=totali(vett, dimL, L);
        printf("le preferenze alla fine sono:\n");
        showlist(L);
        free(vett);
        freelist(L);
    }
}
