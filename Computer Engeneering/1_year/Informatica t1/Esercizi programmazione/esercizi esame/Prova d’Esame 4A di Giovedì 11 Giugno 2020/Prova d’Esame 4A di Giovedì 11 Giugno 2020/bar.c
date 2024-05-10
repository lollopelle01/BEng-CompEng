//
//  bar.c
//  Prova d’Esame 4A di Giovedì 11 Giugno 2020
//
//  Created by Lorenzo Pellegrino on 30/01/21.
//

#include "bar.h"
#include "lettura.h"

//Es.1
list leggiPaste(char * fileName){
    FILE *fp;
    list result;
    Pasta temp;
    
    result=emptylist();
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errorre di lettura di %s", fileName);
        return result;
    }
    else{
        while(fscanf(fp, "%f ", &temp.costo)==1){
            if(lettura_stringa(temp.tipo, ';', fp)>0)
            fscanf(fp, "%d", &temp.calorico);
            result=cons(temp, result);
        }
        fclose(fp);
        return result;
    }
}

Ordine * leggiOrdini(char * fileName, int * dim){
    FILE *fp;
    Ordine *vett=NULL;
    Ordine temp;
    *dim=0;
    int i=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
        return vett;
    }
    else{
        while(fscanf(fp, "%s %d", temp.ingrediente, &temp.n_paste)==2){
            (*dim)++;
        }
        if(*dim>0){
            vett=(Ordine*)malloc(sizeof(Ordine)*(*dim));
            rewind(fp);
            while(fscanf(fp, "%s %d", temp.ingrediente, &temp.n_paste)==2 && i<(*dim)){
                vett[i]=temp;
                i++;
            }
        }
        fclose(fp);
        return vett;
    }
}

//Es.2
void scambia(Ordine *a, Ordine *b)
{
    Ordine tmp = *a;
    *a = *b;
    *b = tmp;
}

void bubbleSort(Ordine* v, int n){
  int i, ordinato = 0;
  while (n>1 && !ordinato){
     ordinato = 1;
     for (i=0; i<n-1; i++)
       if (compare(v[i], v[i+1])>0) {
           scambia(&v[i], &v[i+1]);
            ordinato = 0;
    }
    n--;
  }
}

void ordina(Ordine * v, int dim){
    bubbleSort(v, dim);
}

int soddisfacibile(list paste, Ordine unOrdine){
    int counter=0;
    while(!empty(paste)){
        if(strstr(head(paste).tipo, unOrdine.ingrediente)!=NULL){
            counter++;
        }
        paste=tail(paste);
    }
    if(counter>=unOrdine.n_paste)
        return 1;
    else
        return 0;
}

//Es.3
list eseguiOrdine(list paste, Ordine unOrdine){
    int num=0;
    list result=emptylist();
    if(soddisfacibile(paste, unOrdine)){
        while(!empty(paste)){
            if(strstr(head(paste).tipo, unOrdine.ingrediente)==NULL && num<=unOrdine.n_paste){
                result=cons(head(paste), result);
                num++;
            }
            paste=tail(paste);
        }
        return result;
    }
    else
        return result;
}
