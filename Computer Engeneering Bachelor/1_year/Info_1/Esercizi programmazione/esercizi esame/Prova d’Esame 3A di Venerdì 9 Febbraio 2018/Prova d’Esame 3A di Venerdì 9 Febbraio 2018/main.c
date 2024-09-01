//
//  main.c
//  Prova d’Esame 3A di Venerdì 9 Febbraio 2018
//
//  Created by Lorenzo Pellegrino on 03/02/21.
//

#include "banca.h"

int main() {
    
    {//Es.1
        list L;
        Prodotto *vett;
        int dimL;
        L=leggiClienti("clienti.txt");
        vett=leggiProd("prodotti.txt", &dimL);
        printf("lista clienti:\n");
        showlist(L);
        printf("lista dei prodotti:\n");
        for(int i=0; i<dimL; i++){
            printf("%d %s %6.2f\n", vett[i].idP, vett[i].nomeP, vett[i].costo);
        }
        free(vett);
        freelist(L);
    }
    
    {//Es.2
        list L, L1;
        Prodotto *vett;
        int dimL;
        vett=leggiProd("prodotti.txt", &dimL);
        ordina(vett, dimL);
        printf("\nlista dei prodotti ordinati:\n");
        for(int i=0; i<dimL; i++){
            printf("%d %s %6.2f\n", vett[i].idP, vett[i].nomeP, vett[i].costo);
        }
        L=leggiClienti("clienti.txt");
        L1=eliminaDup(L);
        printf("\nlista clienti filtrata:\n");
        showlist(L1);
        free(vett);
        freelist(L);
        freelist(L1);
    }
    
    {//Es.3
        list L;
        Prodotto *vett;
        int dimL;
        vett=leggiProd("prodotti.txt", &dimL);
        L=leggiClienti("clienti.txt");
        proposte(L, vett, dimL);
        free(vett);
        freelist(L);
    }
}
