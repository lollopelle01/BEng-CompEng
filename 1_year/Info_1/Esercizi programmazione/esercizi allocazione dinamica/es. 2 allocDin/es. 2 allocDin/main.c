//
//  main.c
//  es. 2 allocDin
//
//  Created by Lorenzo Pellegrino on 16/12/20.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define DIM 32

typedef struct {
  char name[DIM];
  int points;
} User;

int readPoints (char usersFile[], User results[], int maxDim, int minPoints){
    FILE *fb;
    User temp;
    int dimL=0;
    if((fb=fopen(usersFile, "rb"))==NULL){
        printf("errore lettura di %s", usersFile);
        exit(1);
        return -1;
    }
    else{
        do{
            fread(&temp, sizeof(User), 1, fb);
            if(temp.points>=minPoints){
                results[dimL]=temp;
                dimL++;
            }
        }
        while(dimL<maxDim && fread(&temp, sizeof(User), 1, fb)>0);
        fclose(fb);
        return dimL;
    }
}

int main(){
    int num_clienti, punteggio_minimo, dimL;
    User *V;
    
    printf("scrivi quanti clienti sono presenti nel file: ");
    scanf("%d", &num_clienti);
    
    V=(User*) malloc(num_clienti*sizeof(User));
    
    printf("scrivi qual e' il punteggio minimo: ");
    scanf("%d", &punteggio_minimo);
    
    dimL=readPoints("punti.dat", V, num_clienti, punteggio_minimo);
    
    for(int i=0; i<dimL; i++){
        if(!strcmp(V[i].name, "Me"))
            printf("%s\n%d", V[i].name, V[i].points);
    }
    free(V);
    
}
