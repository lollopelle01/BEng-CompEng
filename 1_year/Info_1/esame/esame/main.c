//
//  main.c
//  esame
//
//  Created by Lorenzo Pellegrino on 11/02/21.
//  matricola: 0000971455

#include "libri.h"

int main() {
    
    {//Es.1
        list L;
        L=leggi("ordini.txt", 'C', "LaTrama");
        stampaOrdini(L);
        L=leggi("ordini.txt", 'F', "LaTrama");
        stampaOrdini(L);
        freelist(L);
    }
    
    {//Es.2
        list L;
        list L1;
        list Federico;
        list Andrea;
        Ordine* v;
        int dimL;
        int copie;
        
        printf("\n");
        Andrea=leggi("ordini.txt", 'C', "Andrea");
        stampaOrdini(Andrea);
        copie=contaCopieOrdinate(Andrea, "Il nome della rosa");
        printf("Andrea: %d copie\n\n", copie);
        
        Federico=leggi("ordini.txt", 'C', "Federico");
        stampaOrdini(Federico);
        copie=contaCopieOrdinate(Federico, "Il nome della rosa");
        printf("Federico: %d copie\n", copie);
        
        
        printf("\n");
        L=leggi("ordini.txt", 'C', "LaTrama");
        aggregaPerTitolo(L);
        printf("\n");
        
        
        L1=leggi("ordini.txt", 'F', "LaTrama");
        v=prepara(L1, &dimL);
        for(int i=0; i<dimL; i++){
            printf("%s %s %d %f %s\n", v[i].cliente, v[i].fornitore, v[i].copie, v[i].prezzo, v[i].titolo);
        }
        free(v);
        freelist(L);
        freelist(L1);
        freelist(Andrea);
        freelist(Federico);
    }
    
    {//Es.3
        list F;
        list C;
        list new;
        F=leggi("ordini.txt", 'F', "LaTrama");
        C=leggi("ordini.txt", 'C', "Federico");
        new=nuoviOrdini(C, F);
        stampaOrdini(new);
    }
}
