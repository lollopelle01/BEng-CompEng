//
//  bar.c
//  Prova d’Esame 5A di Giovedì 13 Luglio 2017
//
//  Created by Lorenzo Pellegrino on 08/02/21.
//

#include "bar.h"

//Es.1
Operazione * leggiTutte(char * fileName, int * dim){
    Operazione* result=NULL;
    Operazione temp;
    Cliente temp1;
    *dim=0;
    int i=0;
    FILE *fp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s\n", fileName);
    }
    else{
        while(fscanf(fp, "%s %d %s %f", temp1.nome, &temp1.ombrellone, temp.prodotto, &temp.importo)==4){
            (*dim)++;
        }
        if(*dim>0){
            result=(Operazione*)malloc(sizeof(Operazione)*(*dim));
            rewind(fp);
            while(fscanf(fp, "%s %d %s %f", temp1.nome, &temp1.ombrellone, temp.prodotto, &temp.importo)==4){
                temp.c=temp1;
                result[i]=temp;
                i++;
            }
        }
        fclose(fp);
    }
    return result;
}

void stampaOperazioni(Operazione * v, int dim){
    for(int i=0; i<dim; i++){
        printf("%s %d %s %6.2f\n", v[i].c.nome, v[i].c.ombrellone, v[i].prodotto, v[i].importo);
    }
}

void  merge(Operazione v[], int i1, int i2, int fine, Operazione vout[]){
 int i=i1, j=i2, k=i1;
 while(i<=i2-1&& j<=fine){
  if (compare(v[i],v[j])>0)
      vout[k] = v[i++];
  else
      vout[k] = v[j++];
  k++;
 }
 while (i<=i2-1){
  vout[k] = v[i++];
  k++;
 }
 while (j<=fine){
  vout[k] = v[j++];
  k++;
 }
 for (i=i1; i<=fine; i++)
  v[i] = vout[i];
}

void mergeSort(Operazione v[], int first, int last, Operazione vout[]){
int mid;
 if ( first < last ) {
   mid = (last + first) / 2;
     mergeSort(v, first, mid,  vout);
     mergeSort(v, mid+1, last, vout);
     merge(v, first, mid+1, last, vout);
 }
}

void ordina(Operazione * v, int dim){
    Operazione* temp;
    temp=(Operazione*)malloc(sizeof(Operazione)*dim);
    mergeSort(v, 0, dim-1, temp);
    free(temp);
}

//Es.2
list clienti(Operazione * v, int dim){
    list result=emptylist();
    list temp=result;
    Cliente c;
    
    for(int i=0; i<dim; i++){
        c.ombrellone=v[i].c.ombrellone;
        strcpy(c.nome, v[i].c.nome);
        temp=cons(c, temp);
    }
    
    while(!empty(temp)){
        if(!member(head(temp), result)){
            result=cons(head(temp), result);
        }
        temp=tail(temp);
    }
    return result;
}

list ordinaLista(list clienti){
    list result=emptylist();
    while (!empty(clienti)) {
        result=insord_p(head(clienti), result);
        clienti=tail(clienti);
    }
    return result;
}

//Es.3
float totale(Operazione*v, int dim, char* nome){
    ordina(v, dim);
    float result=0;
    for(int i=0; i<dim; i++){
        if(strcmp(v[i].c.nome, nome)==0){
            if(strcmp(v[i].prodotto, "PAGATO")==0){
                result=result+v[i].importo;
            }
            else{
                result=result-v[i].importo;
            }
        }
    }
    return result;
}

void saldi(Operazione * v, int dim){
    list c=clienti(v, dim);
    ordina(v, dim);
    float saldo=0;
    char peggiore[DIM];
    float peggioreS=0;

    while (!empty(c)) {
        saldo=0;
        saldo=totale(v, dim, head(c).nome);
        printf("il saldo per %s e' %6.2f\n", head(c).nome, saldo);
        if(saldo<peggioreS){
            peggioreS=saldo;
            strcpy(peggiore, head(c).nome);
        }
        c=tail(c);
    }
    printf("\nil peggiore e' %s con %6.2f\n", peggiore, peggioreS);
    freelist(c);
}
