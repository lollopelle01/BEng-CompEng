//
//  main.c
//  Prova d’Esame 1A di Giovedì 10 Gennaio 2019
//
//  Created by Lorenzo Pellegrino on 31/01/21.
//

#include "metro.h"

int main() {
    {//Es.1
        int dimL;
        list L=emptylist();
        Tariffa *vett;
        L=leggiTutti("eventi.txt");
        printf("\nla lista degli eventi:\n");
        showlist(L);
        
        vett=leggiTariffe("tariffe.txt", &dimL);
        printf("\nla lista delle tariffe:\n");
        for(int i=0; i<dimL; i++){
            printf("%s %s %f\n", vett[i].s_ingresso, vett[i].s_uscita, vett[i].costo);
        }
        free(vett);
        freelist(L);
        
    }
    
    {//Es.2
        Tariffa *vett;
        int dimL;
        float costo;
        
        vett=leggiTariffe("tariffe.txt", &dimL);
        ordina(vett, dimL);
        printf("\nla lista delle tariffe ordinate:\n");
        for(int i=0; i<dimL; i++){
            printf("%s %s %f\n", vett[i].s_ingresso, vett[i].s_uscita, vett[i].costo);
        }
        
        costo=ricerca(vett, dimL, "Gloucester Road", "North Ealing");
        printf("\nil costo del tragitto: %6.2f\n", costo);
        
        costo=ricerca(vett, dimL, "Boston Manor", "Heathrow Terminal");
        printf("\nil costo del tragitto: %6.2f\n", costo);
        
        costo=ricerca(vett, dimL, "Gloucester Road", "Boston Manor");
        printf("\nil costo del tragitto: %6.2f\n", costo);
        
        costo=ricerca(vett, dimL, "Heathrow Terminal 5", "Gloucester Road");
        printf("\nil costo del tragitto: %6.2f\n", costo);
        
        costo=ricerca(vett, dimL, "", "");
        printf("\nil costo del tragitto: %6.2f\n", costo);
        
        free(vett);
    }
    
    {//Es.3
        list L=emptylist();
        Tariffa *vett;
        int dimL;
        
        L=leggiTutti("eventi.txt");
        vett=leggiTariffe("tariffe.txt", &dimL);
        totali(vett, dimL, L);
        free(vett);
        freelist(L);
    }
}
