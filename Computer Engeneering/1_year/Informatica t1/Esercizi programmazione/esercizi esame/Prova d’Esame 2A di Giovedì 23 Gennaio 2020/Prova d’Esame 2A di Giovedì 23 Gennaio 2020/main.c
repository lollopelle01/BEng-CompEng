//
//  main.c
//  Prova d’Esame 2A di Giovedì 23 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 28/01/21.
//

#include "banca.h"

int main() {
    Loan *vett;
    int dimL;
    
    //Es.1
    vett=leggiLoanAttivi("prestiti.txt", &dimL);
    for(int i=0; i<dimL; i++){
        printf("id: %d\ncogn: %s\nnome: %s\nimporto: %f\n\n", vett[i].identificatore, vett[i].cognome, vett[i].nome, vett[i].importo);
    }
    free(vett);
    
    //ES.2
}
