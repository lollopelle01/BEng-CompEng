//
//  main.c
//  Prova d’Esame 3A di Giovedì 14 Febbraio 2019
//
//  Created by Lorenzo Pellegrino on 02/02/21.
//

#include "crediti.h"

int main() {
    
    {//Es.1
        Acquisto *vett;
        int dimL;
        vett=leggiCrediti("credito.txt", &dimL);
        stampaCrediti(vett, dimL);
        free(vett);
    }
    
    {//Es.2
        Acquisto *vett;
        int dimL;
        vett=leggiCrediti("credito.txt", &dimL);
        list L;
        
        printf("lista articoli di SValentino:\n");
        L=trovaArticoli(vett, dimL, "SValentino");
        showlist(L);
        
        printf("\nlista articoli ordinati di SValentino:\n");
        L=ordinaArticoli(L);
        showlist(L);
        
        printf("\nlista ordinata dei crediti:\n");
        ordina(vett, dimL);
        stampaCrediti(vett, dimL);
        free(vett);
        freelist(L);
    }
    
    {//Es.3
        Acquisto *vett;
        int dimL;
        vett=leggiCrediti("credito.txt", &dimL);
        printf("\n\nspese totali:\n");
        totali(vett, dimL);
        free(vett);
    }
}
