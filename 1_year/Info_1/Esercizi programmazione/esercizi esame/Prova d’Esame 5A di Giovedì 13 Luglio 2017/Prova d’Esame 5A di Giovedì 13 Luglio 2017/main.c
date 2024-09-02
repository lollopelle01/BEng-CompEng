//
//  main.c
//  Prova d’Esame 5A di Giovedì 13 Luglio 2017
//
//  Created by Lorenzo Pellegrino on 08/02/21.
//

#include "bar.h"

int main() {
    
    {//Es.1
        Operazione* vett;
        int dimL;
        vett=leggiTutte("bar.txt", &dimL);
        printf("elenco delle operazioni:\n");
        stampaOperazioni(vett, dimL);
        ordina(vett, dimL);
        printf("\n\nelenco delle operazioni ordinate:\n");
        stampaOperazioni(vett, dimL);
    }
    
    {//Es.2
        Operazione* vett;
        int dimL;
        list L;
        vett=leggiTutte("bar.txt", &dimL);
        L=clienti(vett, dimL);
        printf("\nlista dei clienti:\n");
        showlist(L);
        L=ordinaLista(L);
        printf("\nlista dei clienti ordinati:\n");
        showlist(L);
        free(vett);
        freelist(L);
    }
    
    {//Es.3
        Operazione* vett;
        int dimL;
        vett=leggiTutte("bar.txt", &dimL);
        saldi(vett, dimL);
        free(vett);
    }
}
