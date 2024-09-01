//
//  main.c
//  Prova d’Esame 1A di Giovedì 11 Gennaio 2018
//
//  Created by Lorenzo Pellegrino on 02/02/21.
//

#include "catene.h"

int main() {
    
    {//Es.1
        Catena *vett;
        int dimL;
        vett=leggiTutte("catene.txt", &dimL);
        printf("\nlista catene:\n");
        stampa(vett, dimL);
        free(vett);
    }
    
    {//Es.2
        Catena *vett;
        int dimL;
        list L;
        vett=leggiTutte("catene.txt", &dimL);
        printf("\nlista catene ordinata:\n");
        ordina(vett, dimL);
        stampa(vett, dimL);
        L=conta(vett, dimL);
        printf("\nlista catene disponibili:\n");
        showlist(L);
        free(vett);
        freelist(L);
    }
    
    {//Es.3
        Catena *vett;
        int dimL;
        list L;
        vett=leggiTutte("catene.txt", &dimL);
        L=conta(vett, dimL);
        printf("\nlista catene disponibili:\n");
        L=filtra(65, 165, L);
        showlist(L);
        free(vett);
        freelist(L);
    }
}
