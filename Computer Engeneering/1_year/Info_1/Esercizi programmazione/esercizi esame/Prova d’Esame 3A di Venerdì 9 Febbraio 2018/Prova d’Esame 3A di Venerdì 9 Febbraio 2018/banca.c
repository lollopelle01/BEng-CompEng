//
//  banca.c
//  Prova d’Esame 3A di Venerdì 9 Febbraio 2018
//
//  Created by Lorenzo Pellegrino on 03/02/21.
//

#include "banca.h"

//Es.1
Cliente leggiCliente(FILE *fp){
    Cliente temp;
    int i=0;
    char ch;
    if(fscanf(fp, "%d", &temp.idC)==1){
        if(lettura_stringa(temp.nomeC, ';', fp)>0)
            if(lettura_stringa(temp.cognome, ';', fp)>0){
                ch=fgetc(fp);
                while(ch!=' ' && i<15){
                    temp.telefono[i]=ch;
                    i++;
                    ch=fgetc(fp);
                }
                temp.cognome[i]='\0';
                fscanf(fp, "%f\n", &temp.saldo);
            }
    }
    else{
        temp.idC=-1;
    }
    return temp;
}

list leggiClienti(char * fileName){
    list result=emptylist();
    FILE *fp;
    Cliente temp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        temp=leggiCliente(fp);
        while(temp.idC!=-1){
            result=cons(temp, result);
            temp=leggiCliente(fp);
        }
        fclose(fp);
    }
    return result;
}

Prodotto * leggiProd(char * fileName, int * dim){
    Prodotto* result=NULL;
    Prodotto temp;
    *dim=0;
    int i=0;
    FILE *fp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%d %s %f", &temp.idP, temp.nomeP, &temp.costo)==3){
            (*dim)++;
        }
        if(*dim>0){
            result=(Prodotto*)malloc(sizeof(Prodotto)*(*dim));
            rewind(fp);
            while(fscanf(fp, "%d %s %f", &temp.idP, temp.nomeP, &temp.costo)==3){
                result[i]=temp;
                i++;
            }
        }
        fclose(fp);
    }
    return result;
}

//Es.2
void scambia(Prodotto *a, Prodotto *b){
    Prodotto tmp = *a;
    *a = *b;
    *b = tmp;
}

void bubbleSort(Prodotto v[], int n){
  int i, ordinato = 0;
  while (n>1 && !ordinato){
     ordinato = 1;
     for (i=0; i<n-1; i++)
       if (compare(v[i],v[i+1])>0) {
            scambia(&v[i],&v[i+1]);
            ordinato = 0;
    }
    n--;
  }
}

void ordina(Prodotto * v, int dim){
    bubbleSort(v, dim);
}

int conta(Cliente c, list l){
    int result=0;
    while(!empty(l)){
        if(c.idC==head(l).idC)
            result++;
        l=tail(l);
    }
    return result;
}

list eliminaDup(list clienti){
    list temp=clienti;
    list result=emptylist();
    while(!empty(temp)){
        if(conta(head(temp), temp)==1)
            result=cons(head(temp), result);
        temp=tail(temp);
    }
    return result;
}

//Es.3
void proposte(list clienti, Prodotto * v, int dim){
    Prodotto maxP;
    char risposta;
    float newSaldo;;
    while(!empty(clienti)){
        printf("il saldo del cliente %d e': %6.2f\n", head(clienti).idC, head(clienti).saldo);
        newSaldo=head(clienti).saldo;
        for( int i=0; i<dim; i++){
            if(newSaldo>=v[i].costo){
                maxP=v[i];
                printf("il cliente %d si può permettere il prodotto %s di costo %6.2f\n", head(clienti).idC, maxP.nomeP, maxP.costo);
                printf("vuoi acquistare il prodotto %s? (y-si oppure n-no)\n", maxP.nomeP);
                scanf("%c%*c", &risposta);
                if(risposta=='y'){
                    newSaldo=newSaldo-maxP.costo;
                    printf("il nuovo saldo e' %4.2f\n\n", newSaldo);
                }
                
                if(risposta=='n'){
                    printf("il saldo rimane invariato\n\n");
                }
            }
        }
        
        clienti=tail(clienti);
    }
}

