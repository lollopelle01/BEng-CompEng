//
//  cash.c
//  Prova d’Esame 1A di Giovedì 7 Gennaio 2021
//
//  Created by Lorenzo Pellegrino on 10/02/21.
//

#include "cash.h"

//Es.1
Operazione * leggiTutteOperazioni(char * fileName, int * dim){
    Operazione* result=NULL;
    Operazione temp;
    FILE *fp;
    *dim=0;
    int i=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%d %f %s", &temp.id, &temp.importo, temp.negozio)==3){
            (*dim)++;
        }
        if(*dim>0){
            result=(Operazione*)malloc(sizeof(Operazione)*(*dim));
            rewind(fp);
            while(fscanf(fp, "%d %f %s", &temp.id, &temp.importo, temp.negozio)==3){
                result[i]=temp;
                i++;
            }
        }
        fclose(fp);
    }
    return result;
}

list leggiNegozi(char * fileName){
    FILE *fp;
    list result=emptylist();
    Negozio temp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%s %c", temp.negozio, &temp.tipo)==2){
            result=cons(temp, result);
        }
        fclose(fp);
    }
    return result;
}

void stampaOperazioni(Operazione * v, int dim){
    if(dim<=0)
        return;
    else{
        printf("%d %6.2f %s\n", v[dim-1].id, v[dim-1].importo, v[dim-1].negozio);
        stampaOperazioni(v, dim-1);
    }
}

//Es.2
void scambia(Operazione *a, Operazione *b){
    Operazione tmp = *a;
    *a = *b;
    *b = tmp;
}

void bubbleSort(Operazione v[], int n){
  int i, ordinato = 0;
  while (n>1 && !ordinato){
     ordinato = 1;
     for (i=0; i<n-1; i++)
       if (compare(v[i],v[i+1])>0){ 
            scambia(&v[i],&v[i+1]);
            ordinato = 0;
    }
    n--;
  }
}

void ordina(Operazione * v, int dim){
    bubbleSort(v, dim);
}

int negozioFisico(char * nomeNegozio, list negozi){
    int result=0;
    int trovato=0;
    while(!empty(negozi) && trovato==0){
        if(strcmp(nomeNegozio, head(negozi).negozio)==0){
            trovato=1;
            if(head(negozi).tipo=='F')
                result=1;
            else
                result=0;
        }
        negozi=tail(negozi);
    }
    return result;
}

Operazione * filtra(Operazione * v, int dim, list negozi, int * dimLog){
    Operazione*result;
    result=(Operazione*)malloc(sizeof(Operazione)*dim);
    int i=0;
    
    for(int j=0; j<dim; j++){
        if(negozioFisico(v[j].negozio, negozi)){
            result[i]=v[j];
            i++;
        }
    }
    *dimLog=i;
    return result;
}

//Es.3
float spesaCliente(int idCliente, Operazione * v, int dim){
    float result=0;
    for(int i=0; i<dim; i++){
        if(idCliente==v[i].id){
            result=result+v[i].importo;
        }
    }
    return result;
}
