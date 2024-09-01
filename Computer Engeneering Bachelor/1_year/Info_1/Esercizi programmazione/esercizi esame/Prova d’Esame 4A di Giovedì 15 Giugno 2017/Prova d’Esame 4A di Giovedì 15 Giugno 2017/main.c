//
//  main.c
//  Prova d’Esame 4A di Giovedì 15 Giugno 2017
//
//  Created by Lorenzo Pellegrino on 07/02/21.
//

#include "voli.h"

int main() {
    
    {//Es.1
        Prenotazione* vett;
        int dimL;
        list L;
        vett=leggiTutti("prenotazioni.txt", &dimL);
        printf("elenco prenotazioni:\n");
        stampaPrenotazioni(vett, dimL);
        L=leggiViaggiatori("viaggiatori.txt");
        printf("\nlista viaggiatori:\n");
        showlist(L);
        free(vett);
        freelist(L);
        
    }
    
    {//Es.2
        Prenotazione* vett;
        int dimL;
        list L;
        vett=leggiTutti("prenotazioni.txt", &dimL);
        printf("elenco prenotazioni ordinate:\n");
        ordina(vett, dimL);
        stampaPrenotazioni(vett, dimL);
        L=leggiViaggiatori("viaggiatori.txt");
        printf("\nlista viaggiatori ordinata:\n");
        L=ordinaLista(L);
        showlist(L);
        free(vett);
        freelist(L);
    }
    
    {//Es.3
        Prenotazione* vett;
        int dimL;
        list L;
        vett=leggiTutti("prenotazioni.txt", &dimL);
        L=leggiViaggiatori("viaggiatori.txt");
        printf("i viaggiatori del volo AZ2456 sono:\n");
        L=estraiViaggiatori(vett, dimL, L, "AZ2456");
        showlist(L);
        free(vett);
        freelist(L);
    }
}
