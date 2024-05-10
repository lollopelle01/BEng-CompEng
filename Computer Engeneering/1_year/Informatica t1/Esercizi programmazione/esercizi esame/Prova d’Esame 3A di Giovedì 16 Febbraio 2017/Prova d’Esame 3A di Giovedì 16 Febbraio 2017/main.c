//
//  main.c
//  Prova d’Esame 3A di Giovedì 16 Febbraio 2017
//
//  Created by Lorenzo Pellegrino on 07/02/21.
//

#include "test.h"

int main() {
   
    {//Es.1
        Test* vett;
        int dimL;
        vett=leggiTutti("risultati.txt", &dimL);
        printf("elenco dei Test:\n");
        stampaTest(vett, dimL);
        free(vett);
    }
    
    {//Es.2
        Test* vett;
        list L;
        int dimL;
        vett=leggiTutti("risultati.txt", &dimL);
        printf("\nelenco dei Test ordinati:\n");
        ordina(vett, dimL);
        stampaTest(vett, dimL);
        L=pref("preferenze.txt");
        printf("\nlista delle preferenze :\n");
        showlist(L);
        free(vett);
        freelist(L);
    }
    
    {//Es.3
        Test* vett;
        list L;
        int dimL;
        vett=leggiTutti("risultati.txt", &dimL);
        L=pref("preferenze.txt");
        printf("elenco degli ammessi:\n");
        ammessi(vett, dimL, L, 5, "Ingegneria");
        free(vett);
        freelist(L);
    }
}
