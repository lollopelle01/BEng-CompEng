//
//  main.c
//  es. 6 stringhe
//
//  Created by Lorenzo Pellegrino on 22/11/20.
//

#include <stdio.h>
#include <string.h>
#define DIM 20

char identifica( char *A, char *B, char *C){
    while(*A!='\0' && *B!='\0'){
        if(*A==*B){
            B++;
            *C=*A-32;
        }
        else
            *C=*A;
        A++;
        C++;
        
    }
        
    if(!*C){
        while(*A!='\0'){
            *C=*A;
            C++;
            A++;
        }
    }
    return (!*C);
}

int main(){
    char A[]={"arruffa"}, B[]={"arrusffa"}, C[DIM];
    identifica(A, B, C);
    printf("%s\n", C);
}

