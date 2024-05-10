//
//  main.c
//  Prova d’Esame 2A di Giovedì 26 Gennaio 2017
//
//  Created by Lorenzo Pellegrino on 06/02/21.
//

#include "rist.h"

int main() {
    
    {//Es.1
        Pasto* vett;
        int dimL;
        vett=leggiTutti("pasti.txt", &dimL);
        printf("\nelenco pasti:\n");
        stampaPasti(vett, dimL);
        free(vett);
    }
    
    {//Es.2
        Pasto* vett;
        list L;
        int dimL;
        vett=leggiTutti("pasti.txt", &dimL);
        printf("\nelenco pasti ordinati:\n");
        ordina(vett, dimL);
        stampaPasti(vett, dimL);
        L=elenco("aziende.txt");
        printf("\nlista dipendenti ordinati:\n");
        showlist(L);
        free(vett);
        freelist(L);
    }
    
    {//Es.3
        Pasto* vett;
        list L;
        int dimL;
        vett=leggiTutti("pasti.txt", &dimL);
        L=elenco("aziende.txt");
        totali(vett, dimL, L);
        free(vett);
        freelist(L);
    }
}
