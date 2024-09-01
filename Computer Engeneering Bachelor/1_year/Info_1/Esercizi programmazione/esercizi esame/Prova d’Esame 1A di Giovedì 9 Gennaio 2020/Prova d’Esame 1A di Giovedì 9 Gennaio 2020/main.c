//
//  main.c
//  Prova d’Esame 1A di Giovedì 9 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 27/01/21.
//

#include "negozio.h"

int main() {
    list result;
    int target;
    int result2;
    Complex result3;
    //Es.1
    result=leggiTuttiSet("negozio.txt");
    showlist(result);
    freelist(result);
    
    //Es.2
    result3=trovaComplex("complessi.txt", 95645);
    printf("id: %d\ncomsizione: ", result3.identificativo);
    for(int i=0; i<result3.dimL; i++){
        printf("%d ", result3.composizione[i]);
    }
    printf("\n");
    
    //Es.4
    result=leggiTuttiSet("negozio.txt");
    printf("che set vuoi trovare?\n");
    scanf("%d", &target);
    
    result2=disp(result, target, "complessi.txt");
    if(result==0)
        printf("non trovato\n");
    else
        printf("trovato\n");
    freelist(result);
}
