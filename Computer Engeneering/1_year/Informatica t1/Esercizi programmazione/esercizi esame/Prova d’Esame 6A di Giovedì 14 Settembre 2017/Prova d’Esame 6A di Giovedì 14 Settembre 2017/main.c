//
//  main.c
//  Prova d’Esame 6A di Giovedì 14 Settembre 2017
//
//  Created by Lorenzo Pellegrino on 08/02/21.
//

#include "ordini.h"

int main() {
  
    {//Es.1
        list L;
        L=leggiOrdini("ordini.txt");
        printf("\nlista degli ordini:\n");
        stampaOrdini(L);
        printf("\nlista degli ordini filtrata:\n");
        L=filtra(L);
        stampaOrdini(L);
        freelist(L);
    }
    
    {//Es.2
        list L;
        Ordine* vett;
        int dimL;
        L=leggiOrdini("ordini.txt");
        vett=perCliente(L, 34, &dimL);
        printf("\nelenco degli ordini del cliente 34:\n");
        for(int i=0; i<dimL; i++){
            printf("%d/%d/%d %d %s %f %s\n", vett[i].d.a, vett[i].d.m, vett[i].d.g, vett[i].id, vett[i].modello, vett[i].prezzo, vett[i].testo);
        }
        ordina(vett, dimL);
        printf("\nelenco ordinato degli ordini del cliente 34:\n");
        for(int i=0; i<dimL; i++){
            printf("%d/%d/%d %d %s %f %s\n", vett[i].d.a, vett[i].d.m, vett[i].d.g, vett[i].id, vett[i].modello, vett[i].prezzo, vett[i].testo);
        }
        free(vett);
        freelist(L);
    }
    
    {//Es.3
        list L;
        int best;
        L=leggiOrdini("ordini.txt");
        best=migliore(L);
        printf("il migliore e': %d\n", best);
        freelist(L);
    }
}
