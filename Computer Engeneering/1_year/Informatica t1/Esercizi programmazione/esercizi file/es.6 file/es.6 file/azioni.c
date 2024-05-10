//
//  azioni.c
//  es.6 file
//
//  Created by Lorenzo Pellegrino on 06/12/20.
//

#include "azioni.h"

int leggi(FILE *fp, Azione dest[], int dim, char *nome){
    Azione temp;
    int result=0;
    
    while(fscanf(fp, "%s %f %f %d\n", temp.nome_titiolo, &temp.valore_apertura, &temp.valore_chiusura, &temp.giorno)==4 && result<dim){
        if(strcmp(temp.nome_titiolo, nome)==0){
            dest[result]=temp;
            result++;
        }
    }
    return result;
}
