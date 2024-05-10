//
//  main.c
//  es. 1 stringhe
//
//  Created by Lorenzo Pellegrino on 19/11/20.
//

#include <stdio.h>
#define DIM1 5
#define DIM2 3

int conc(char A[], char B[], char C[]){
    int k;
    for(int i=0; i<DIM1; i++){
        C[i]=A[i];
    }
    for(k=DIM1; k<DIM2+DIM1; k++){
        C[k]=B[k-DIM1];
    }
    return k;
}

int main(){
    char A[]={'p', 'o', 'r', 'c', 'o' }, B[]={'d', 'i', 'o'}, C[10];
    int n;
    n=conc(A, B, C);
    
    printf("%s\n", C);
}
