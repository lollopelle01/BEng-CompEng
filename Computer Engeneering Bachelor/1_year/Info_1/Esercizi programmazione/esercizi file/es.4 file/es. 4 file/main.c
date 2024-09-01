//
//  main.c
//  es. 4 file
//
//  Created by Lorenzo Pellegrino on 02/12/20.
//

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct{
    char nome_cliente[31];
    float importo;
} debito;

void load(FILE *f1, FILE *f2, debito y[], int *N){
    char nome_cliente[31], citta[21], carattere;
    int codice_cliente1, codice_cliente2, numero_fattura;
    float importo_fattura;
    *N=0;
    
    while(fscanf(f1,"%d %s %s\n", &codice_cliente1, nome_cliente, citta)==3){
        while(fscanf(f2,"%d %d %f %c\n", &codice_cliente2, &numero_fattura, &importo_fattura, &carattere)==4){
            if(carattere=='n' && codice_cliente1==codice_cliente2){
                strcpy(y[*N].nome_cliente, nome_cliente);
                y[*N].importo=importo_fattura;
                (*N)++;
            }
        }
        rewind(f2);
    }
}

int main(){
    FILE *f1, *f2;
    debito debitori[100];
    int dimL, count=0;
    char nome[31];
    float importoT=0;
    
    if((f1=fopen("anagrafe.txt", "r"))==NULL){
        printf("errore di lettura di f1\n");
        exit(1);
    }
    
    if((f2=fopen("fatture.txt", "r"))==NULL){
        printf("errore di lettura di f2\n");
        exit(2);
    }
    
    load(f1, f2, debitori, &dimL);
    
    fclose(f1); fclose(f2);
    
    printf("scrivi il nome di un cliente: ");
    scanf("%s", nome);
    
    for(int i=0; i<dimL; i++){
        if(strcmp(debitori[i].nome_cliente, nome)==0){
            printf("%s deve %3.2f euro\n", nome, debitori[i].importo);
            importoT=importoT+debitori[i].importo;
            count++;
        }
    }
    printf("quindi %s non ha pagato %d fatture e in totale deve %3.2f euro\n", nome, count, importoT);
    return 0;
}
