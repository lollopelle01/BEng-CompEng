//
//  main.c
//  Prova d’Esame 1A di Giovedì 7 Gennaio 2021
//
//  Created by Lorenzo Pellegrino on 10/02/21.
//

#include "cash.h"

int main() {
    
    {//Es.1
        Operazione* v;
        int dimL;
        list l;
        v=leggiTutteOperazioni("operazioni.txt", &dimL);
        printf("elenco operazioni:\n");
        stampaOperazioni(v, dimL);
        l=leggiNegozi("negozi.txt");
        printf("\nlista negozi:\n");
        showlist(l);
        free(v);
        freelist(l);
    }
    
    {//Es.2
        Operazione* v;
        Operazione* filtrate;
        int dimL;
        int dimL2;
        list l;
        v=leggiTutteOperazioni("operazioni.txt", &dimL);
        l=leggiNegozi("negozi.txt");
        printf("\nelenco operazioni ordinate:\n");
        ordina(v, dimL);
        stampaOperazioni(v, dimL);
        printf("\nelenco operazioni filtrate:\n");
        filtrate=filtra(v, dimL, l, &dimL2);
        stampaOperazioni(filtrate, dimL2);
        free(filtrate);
        free(v);
        freelist(l);
    }
    
    {//Es.3
        Operazione* v;
        Operazione* filtrate;
        list l;
        int dimL2;
        int dimL;
        float costo=0;
        int i;
        l=leggiNegozi("negozi.txt");
        v=leggiTutteOperazioni("operazioni.txt", &dimL);
        filtrate=filtra(v, dimL, l, &dimL2);
        ordina(filtrate, dimL2);
        
        printf("\n");
        for(i=0; i<dimL2; i++){
            if(filtrate[i].id!=filtrate[i+1].id){
                costo=spesaCliente(filtrate[i].id, filtrate, dimL2);
                printf("%d %6.2f\n", filtrate[i].id, costo);
            }
        }
        
        free(v);
        free(filtrate);
        freelist(l);
    }
}
