//
//  main.c
//  es. 2 stringhe
//
//  Created by Lorenzo Pellegrino on 19/11/20.
//
#define CRT_SECURE_NO_DEPRECATE
#define CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <string.h>
#include "Header.h"
#define DIM 15

int main(){
    char C[DIM], N[DIM], dest[3*DIM];
    int value;
    
    printf("scrivi il tuo cognome: ");
    scanf("%s", &C);
    printf("scrivi il tuo nome: ");
    scanf("%s", &N);
    
    value=copiatura_stringa(C, N, dest, 3*DIM);
    
    printf("%s\n", dest);

}
