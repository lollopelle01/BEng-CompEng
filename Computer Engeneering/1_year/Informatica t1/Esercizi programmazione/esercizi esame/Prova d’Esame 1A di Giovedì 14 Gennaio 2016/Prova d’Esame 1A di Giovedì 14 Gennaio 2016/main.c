//
//  main.c
//  Prova d’Esame 1A di Giovedì 14 Gennaio 2016
//
//  Created by Lorenzo Pellegrino on 09/02/21.
//

#include "clienti.h"

int main() {
    
    {//Es.1
        list L;
        Soggiorno* v;
        int dimL;
        L=leggiTariffe("tariffe.txt");
        v=leggiSoggiorni("clienti.txt", &dimL);
        printf("\nelenco soggiorni:\n");
        stampaSoggiorni(v, dimL);
        printf("\nlista tariffe:\n");
        stampaTariffe(L);
        free(v);
        freelist(L);
    }
    
    {//Es.2
        Soggiorno* v;
        int dimL;
        v=leggiSoggiorni("clienti.txt", &dimL);
        printf("\nelenco soggiorni ordinato:\n");
        ordinaSoggiorni(v, dimL);
        stampaSoggiorni(v, dimL);
        free(v);
    }
    
    {//Es.3
        list L;
        Soggiorno* v;
        int dimL;
        float costo;
        v=leggiSoggiorni("clienti.txt", &dimL);
        L=leggiTariffe("tariffe.txt");
        printf("\nil costo dovuto dal cliente 12 e': ");
        costo=totale(12, L, v, dimL);
        printf("%6.2f\n", costo);
        free(v);
        freelist(L);
    }
}
