//
//  element.c
//  Prova d’Esame 2A di Giovedì 23 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 28/01/21.
//

#include "element.h"

int compare(Loan l1, Loan l2){
    int result=strcmp(l2.cognome, l1.cognome);
    if(result==0){
        result=strcmp(l2.nome, l1.nome);
        if(result==0){
            if(l1.importo<l2.importo)
                return 1;
            else{
                if(l1.importo==l2.importo)
                    return 0;
                if(l1.importo>l2.importo)
                    return -1;
            }
        }
    }
    return result;
}
