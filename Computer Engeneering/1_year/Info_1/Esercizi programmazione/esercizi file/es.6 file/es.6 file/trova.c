//
//  trova.c
//  es.6 file
//
//  Created by Lorenzo Pellegrino on 06/12/20.
//

#include "trova.h"

Azione trovaMin(Azione src[], int dim, float* val){
    Azione result;
    
    result=src[0];
    if(result.valore_apertura<result.valore_chiusura)
        *val=result.valore_apertura;
    if(result.valore_apertura>result.valore_chiusura)
        *val=result.valore_chiusura;
    
    for(int i=0; i<dim; i++){
        if(src[i].valore_apertura<*val){
            *val=src[i].valore_apertura;
            result=src[i];
        }
        if(src[i].valore_chiusura<*val){
            *val=src[i].valore_chiusura;
            result=src[i];
        }
    }
    return result;
}

Azione trovaMax(Azione src[], int dim, float* val){
    Azione result;
    
    result=src[0];
    if(result.valore_apertura>result.valore_chiusura)
        *val=result.valore_apertura;
    if(result.valore_apertura<result.valore_chiusura)
        *val=result.valore_chiusura;
    
    for(int i=0; i<dim; i++){
        if(src[i].valore_apertura>*val){
            *val=src[i].valore_apertura;
            result=src[i];
        }
        if(src[i].valore_chiusura>*val){
            *val=src[i].valore_chiusura;
            result=src[i];
        }
    }
    return result;
}

float media(Azione lista[], int dim){
    float media, tot=0;
    for(int i=0; i<dim; i++){
        tot=tot+lista[i].valore_apertura+lista[i].valore_chiusura;
    }
    media=tot/(2*dim);
    
    return media;
}
