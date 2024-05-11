//
//  element.c
//  Prova d’Esame 2A di Giovedì 23 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 28/01/21.
//

#include "element.h"

int compare(Appartamento l1, Appartamento l2){
    int result=l2.piano-l1.piano;
    if(result==0){
        if(l2.superficie>l1.superficie)
            return -11;
        else{
            if(l2.superficie==l1.superficie)
                return 0;
            if(l2.superficie<l1.superficie)
                return 1;
        }
    }
    return result;
}
