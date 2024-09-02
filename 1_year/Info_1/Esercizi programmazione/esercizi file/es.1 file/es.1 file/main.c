//
//  main.c
//  es.1 file
//
//  Created by Lorenzo Pellegrino on 30/11/20.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE

int main() {
    FILE* fp ;
    char parola[63];
    
    if ((fp= fopen("prova.txt", "a")) == NULL){
        exit(1);
    }
    
    do {
         scanf("%s", parola);
         if (strcmp("fine", parola) != 0)
             fprintf(fp, "%s ", parola);
       } while (strcmp("fine", parola) != 0);

    fclose(fp);
}
