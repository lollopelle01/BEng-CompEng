//
//  element.c
// 
//
//  Created by Lorenzo Pellegrino on 11/02/21.
//  matricola: 0000971455

#include "element.h"

int compare(Ordine o1, Ordine o2){
    float importo1, importo2;
    int result;
    
    importo1=o1.copie*o1.prezzo;
    importo2=o2.copie*o2.prezzo;
    
    result=importo1-importo2;
    if(result==0){
        result=strcmp(o1.titolo, o2.titolo);
    }
    return result;;
}
