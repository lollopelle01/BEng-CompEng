//
//  bar.c
//  Prova d’Esame 5A di Giovedì 9 Luglio 2020
//
//  Created by Lorenzo Pellegrino on 30/01/21.
//

#include "bar.h"
//Es.1
Ingrediente * leggiIng(char * fileName, int * dim){
    FILE *fp;
    Ingrediente* vett=NULL;
    Ingrediente temp;
    *dim=0;
    int i=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%s %d %f", temp.nome, &temp.quantità, &temp.costo)==3){
            (*dim)++;
        }
        if(*dim>0){
            vett=(Ingrediente*)malloc(sizeof(Ingrediente)*(*dim));
            rewind(fp);
            while(fscanf(fp, "%s %d %f", temp.nome, &temp.quantità, &temp.costo)==3){
                vett[i]=temp;
                i++;
            }
        }
        fclose(fp);
    }
    return vett;
}

void stampa(Ingrediente * elenco, int dim){
    if(dim<=0)
        return;
    else{
        stampa(elenco, dim-1);
        printf("%s %d %f\n", elenco[dim-1].nome, elenco[dim-1].quantità, elenco[dim-1].costo);
    }
}


Ingrediente trova(Ingrediente * elenco, int dim, char * nome){
    Ingrediente result=elenco[0];
    int trovato=0;
    int i=0;
    while(trovato==0 && i<dim){
        if(strcmp(elenco[i].nome, nome)==0){
            result=elenco[i];
            trovato=1;
        }
        i++;
    }
    if(trovato==0)
        strcpy(result.nome, "NULL");
    return result;
}

//Es.2
void scambia(Ingrediente *a, Ingrediente *b){
    Ingrediente tmp = *a;
    *a = *b;
    *b = tmp;
}

void bubbleSort(Ingrediente v[], int n){
  int i, ordinato = 0;
  while (n>1 && !ordinato){
     ordinato = 1;
     for (i=0; i<n-1; i++)
       if (compare(v[i], v[i+1])>0) {
            scambia(&v[i],&v[i+1]);
            ordinato = 0;
    }
    n--;
  }
}

void ordina(Ingrediente * v, int dim){
    bubbleSort(v, dim);
}

list leggiPanini(char * fileName, Ingrediente * elenco, int dim){
    FILE *fp;
    list result=emptylist();
    Panino temp;
    char nome_ingrediente[DIM];
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%s", temp.nome_cliente)==1){
            temp.n_ingredienti=0;
            fscanf(fp, "%s", nome_ingrediente);
            while(temp.n_ingredienti<3 && strcmp(nome_ingrediente, "fine")!=0){
                strcpy(temp.ingredienti[temp.n_ingredienti].nome, nome_ingrediente);
                temp.n_ingredienti++;
                fscanf(fp, "%s\n", nome_ingrediente);
            }
            result=cons(temp, result);
        }
        fclose(fp);
    }
    return result;
}

//Es.3
list eliminaDup(list panini){
    list result;
    Panino temp;
    result = emptylist();
    while (!empty(panini)) {
        temp = head(panini);
        if (!member(temp, result))
            result = cons(temp, result);
        panini = tail(panini);
    }
    return result;
}

