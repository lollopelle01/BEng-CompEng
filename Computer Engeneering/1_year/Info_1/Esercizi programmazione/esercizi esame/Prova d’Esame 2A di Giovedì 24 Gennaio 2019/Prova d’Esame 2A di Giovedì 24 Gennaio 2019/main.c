//
//  main.c
//  Prova d’Esame 2A di Giovedì 24 Gennaio 2019
//
//  Created by Lorenzo Pellegrino on 01/02/21.
//

#include "cassa.h"

int main() {
    
    {//Es.1
        Pasto *vett;
        list l;
        int dimL;
        vett=leggiPasti("cassa.txt", &dimL);
        stampaPasti(vett, dimL);
        l=leggiTariffe("tariffe.txt");
        showlist(l);
        free(vett);
        freelist(l);
    }
}
