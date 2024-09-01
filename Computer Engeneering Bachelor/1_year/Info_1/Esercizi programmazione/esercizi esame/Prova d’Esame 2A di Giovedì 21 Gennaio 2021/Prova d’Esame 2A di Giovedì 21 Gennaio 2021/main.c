//
//  main.c
//  Prova d’Esame 2A di Giovedì 21 Gennaio 2021
//
//  Created by Lorenzo Pellegrino on 10/02/21.
//

#include "gara.h"

int main() {
   
    {//Es.1
        Atleta* v;
        int dimL;
        v=leggiAtleti("risultati.txt", &dimL);
        printf("elenco atleti:\n");
        for(int i=0; i<dimL; i++){
            stampaAtleta(v[i]);
        }
        free(v);
    }
    
    {//Es.2
        Atleta* v;
        Atleta* vT;
        Atleta* vN;
        Atleta* vB;
        Atleta* vC;
        int dimL;
        int dimT;
        int dimN;
        int dimB;
        int dimC;
        v=leggiAtleti("risultati.txt", &dimL);
        
        vT=ordinaAtletiPer(v, dimL, "Totale", &dimT);
        printf("\nelenco atleti ordinati per totale:\n");
        for(int i=0; i<dimT; i++){
            stampaAtleta(vT[i]);
        }
        
        vN=ordinaAtletiPer(v, dimL, "Nuoto", &dimN);
        printf("\nelenco atleti ordinati per nuoto:\n");
        for(int i=0; i<dimN; i++){
            stampaAtleta(vN[i]);
        }
        
        vB=ordinaAtletiPer(v, dimL, "Bici", &dimB);
        printf("\nelenco atleti ordinati per bici:\n");
        for(int i=0; i<dimB; i++){
            stampaAtleta(vB[i]);
        }
        
        vC=ordinaAtletiPer(v, dimL, "Corsa", &dimC);
        printf("\nelenco atleti ordinati per corsa:\n");
        for(int i=0; i<dimC; i++){
            stampaAtleta(vC[i]);
        }
        
        free(v);
        free(vT);
        free(vN);
        free(vB);
        free(vC);
    }
    
    {//Es.3
        Atleta* v;
        int dimL;
        list L;
        v=leggiAtleti("risultati.txt", &dimL);
        printf("\nlista atleti:\n");
        L=atletiPremiati(v, dimL);
        showlist(L);
        printf("\nelenco atleti premiati:\n");
        premi(v, dimL);
        free(v);
        freelist(L);
    }
}
