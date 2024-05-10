//
//  main.c
//  Prova d’Esame 4A di Giovedì 14 Giugno 2018
//
//  Created by Lorenzo Pellegrino on 04/02/21.
//

#include "gara.h"

int main() {
    
    {//Es.1
        list L;
        int result;
        L=leggi("gara.txt");
        printf("lista di partecipanti:\n");
        showlist(L);
        while(!empty(L)){
            result=punti(head(L));
            printf("%s ha %d punti\n", head(L).nome, result);
            L=tail(L);
        }
        freelist(L);
    }
    
    {//Es.2
        list L;
        Squadra* vett;
        int dimL;
        L=leggi("gara.txt");
        vett=estrai(L, &dimL);
        
        printf("lista squadre :\n");
        for(int i=0; i<dimL; i++){
            printf("%s: %d\n", vett[i].nome, vett[i].punteggio);
        }
        
        ordina(vett, dimL);
        printf("lista squadre ordinata:\n");
        for(int i=0; i<dimL; i++){
            printf("%s: %d\n", vett[i].nome, vett[i].punteggio);
        }
        
        free(vett);
        freelist(L);
    }
    
    {//Es.3
        list L;
        Squadra* vett;
        int dimL;
        L=leggi("gara.txt");
        vett=estrai(L, &dimL);
        totale(vett, dimL, L);
        free(vett);
        freelist(L);
    }
}
