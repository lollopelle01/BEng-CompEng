//
//  main.c
//  es 14 array funzioni
//
//  Created by Lorenzo Pellegrino on 16/11/20.
//

#include <stdio.h>
#define DIM 10

int leggi(int v[], int dimF){
    int i=0;
    do{
        printf("inserisci v[%d]: ", i);
        scanf("%d", &v[i]);
        i++;
    }
    while(i<dimF);
    return i;
}

void somma2(int v[], int dimL){
    if(dimL<3)
        return;
    else
        if(v[0]==v[1]+v[2]){
            printf("%d\n", v[0]);
        }
            somma2(&v[1], dimL-1);
        
}

int main(){
    int v[DIM], dimL;
    dimL=leggi(v, DIM);
    
    somma2(v, dimL);
}
