//
//  ordini.c
//  Prova d’Esame 2A di Giovedì 25 Gennaio 2018
//
//  Created by Lorenzo Pellegrino on 03/02/21.
//

#include "ordini.h"

//Es.1
Ordine leggiUno(FILE *fp){
    Ordine result;
    if(fscanf(fp, "%d/%d/%d%s%s%d%d\n", &result.data.anno, &result.data.mese, &result.data.giorno, result.tipo, result.gusto, &result.numero, &result.cliente)==7)
        return result;
    else{
        result.data.giorno=0;
        return result;
    }
    return result;
}

Ordine * leggiTutti(char * fileName, int * dim){
    FILE *fp;
    Ordine* result=NULL;
    Ordine temp;
    *dim=0;
    int i=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        temp=leggiUno(fp);
        while(temp.data.giorno!=0){
            if(temp.numero>0 && temp.cliente>0 && (strcmp(temp.tipo, "cialda")==0 || strcmp(temp.tipo, "capsula")==0))
               (*dim)++;
            temp=leggiUno(fp);
        }
        if(*dim>0){
            result=(Ordine*)malloc(sizeof(Ordine)*(*dim));
            rewind(fp);
            temp=leggiUno(fp);
            while(temp.data.giorno!=0){
                if(temp.numero>0 && temp.cliente>0 && (strcmp(temp.tipo, "cialda")==0 || strcmp(temp.tipo, "capsula")==0)){
                    result[i]=temp;
                    i++;
                }
                temp=leggiUno(fp);
            }
        }
        fclose(fp);
    }
    return result;
}

void stampa(Ordine * v, int dim){
    if(dim>0){
        for (int i=0; i<dim; i++) {
            printf("%d/%d/%d %s %s %d %d\n", v[i].data.anno, v[i].data.mese, v[i].data.giorno, v[i].tipo, v[i].gusto, v[i].numero, v[i].cliente);
        }
    }
    else
        printf("vettore vuoto\n");
}

//Es.2
void  merge(Ordine v[], int i1, int i2, int fine, Ordine vout[]){
 int i=i1, j=i2, k=i1;
 while(i<=i2-1&& j<=fine){
  if (compare(v[i], v[j])<0)
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

void mergeSort(Ordine v[], int first, int last, Ordine vout[]){
int mid;
 if ( first < last ) {
   mid = (last + first) / 2;
     mergeSort(v, first, mid,  vout);
     mergeSort(v, mid+1, last, vout);
     merge(v, first, mid+1, last, vout);
 }
}

void ordina(Ordine * v, int dim){
    Ordine *temp;
    temp=(Ordine*)malloc(sizeof(Ordine)*dim);
    mergeSort(v, 0, dim-1, temp);
    free(temp);
}

list leggiCosti(char * fileName){
    list result=emptylist();
    Costo temp;
    FILE *fp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%s%s%f", temp.tipo, temp.gusto, &temp.costo)==3){
            result=cons(temp, result);
        }
        fclose(fp);
    }
    return result;
}

//Es.3
float trovaCosto(list costi, char *gusto){
    float result=0;
    int trovato=0;
    while(!empty(costi) && trovato==0){
        if(strcmp(head(costi).gusto, gusto)==0){
            trovato=1;
            result=head(costi).costo;
        }
        costi=tail(costi);
    }
    return result;
}

void fatture(Ordine * v, int dim, list costi){
    ordina(v, dim);
    Ordine temp;
    float costoT;
    temp=v[0];
    costoT=temp.numero*trovaCosto(costi, temp.gusto);
    for(int i=1; i<dim; i++){
        if(temp.cliente==v[i].cliente && temp.data.anno==v[i].data.anno && temp.data.mese==v[i].data.mese){
            costoT=costoT+v[i].numero*trovaCosto(costi, v[i].gusto);
        }
        else{
            printf("cliente: %d, mese: %d/%d, Totale dovuto: %6.2f\n", temp.cliente, temp.data.anno, temp.data.mese, costoT);
            temp=v[i];
            costoT=temp.numero*trovaCosto(costi, temp.gusto);
        }
    }
    printf("cliente: %d, mese: %d/%d, Totale dovuto: %6.2f\n", temp.cliente, temp.data.anno, temp.data.mese, costoT);
}
                    
