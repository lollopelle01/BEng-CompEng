//
//  cassa.c
//  Prova d’Esame 2A di Giovedì 24 Gennaio 2019
//
//  Created by Lorenzo Pellegrino on 01/02/21.
//

#include "cassa.h"

//Es.1
Pasto leggiPasto(FILE *fp){
    Pasto temp;
    if(fscanf(fp, "%d ", &temp.id)==1){
        temp.primo=0;
        temp.secondo=0;
        temp.contorno=0;
        temp.frutta=0;
        temp.dolce=0;
        char pasto[DIM];
        int fine=0;
        while(!fine && fscanf(fp, "%s", pasto)){
            if(strcmp(pasto, "@@@")==0){
                fine=1;
            }
        }
    }
    else{
        temp.id=-1;
    }
    return temp;
}

Pasto * leggiPasti(char * fileName, int * dim){
    FILE *fp;
    Pasto *vett=NULL;
    Pasto temp;
    *dim=0;
    int i=0;
    int fine;
    char pasto[DIM];
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        temp=leggiPasto(fp);
        while(temp.id!=-1){
            (*dim)++;
            temp=leggiPasto(fp);
        }
        if(*dim>0){
            vett=(Pasto*)malloc(sizeof(Pasto)*(*dim));
            rewind(fp);
            temp=leggiPasto(fp);
            while(temp.id!=-1 && i<*dim ){
                fine=0;
                while(!fine && fscanf(fp, "%s", pasto)==1){
                    if(strcmp(pasto, "primo")==0)
                        temp.primo++;
                    if(strcmp(pasto, "secondo")==0)
                        temp.secondo++;
                    if(strcmp(pasto, "frutta")==0)
                        temp.frutta++;
                    if(strcmp(pasto, "contorno")==0)
                        temp.contorno++;
                    if(strcmp(pasto, "dolce")==0)
                        temp.dolce++;
                    if(strcmp(pasto, "@@@")==0)
                        fine++;
                    if(fine){
                        vett[i]=temp;
                        i++;
                    }
                }
            }
        }
        fclose(fp);
    }
    return vett;
}

void stampaPasti(Pasto * elenco, int dim){
    if(dim<=0)
        return;
    else{
        printf("id: %d\nprimo: %d\nsecondo: %d\nfrutta: %d\ncontorno: %d\ndolce: %d\n", elenco[dim-1].id, elenco[dim-1].primo, elenco[dim-1].secondo, elenco[dim-1].frutta, elenco[dim-1].contorno, elenco[dim-1].dolce);
        stampaPasti(elenco, dim-1);
    }
}

list leggiTariffe(char * fileName){
    FILE *fp;
    list result=emptylist();
    Tariffa temp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%s %f", temp.pietanza, &temp.costo)==2){
            result=cons(temp, result);
        }
        fclose(fp);
    }
    return result;
}

//Es.3
void  merge(Pasto v[], int i1, int i2, int fine, Pasto vout[]){
 int i=i1, j=i2, k=i1;
 while(i<=i2-1&& j<=fine){
  if (compare(v[i], v[j])>0)
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

void mergeSort(Pasto v[], int first, int last, Pasto vout[]){
int mid;
 if ( first < last ) {
   mid = (last + first) / 2;
     mergeSort(v, first, mid,  vout);
     mergeSort(v, mid+1, last, vout);
     merge(v, first, mid+1, last, vout);
 }
}

void ordina(Pasto * v, int dim){
    Pasto* temp=NULL;
    temp=(Pasto*)malloc(sizeof(Pasto)*dim);
    mergeSort(v, 0, dim-1, temp);
    free(temp);
}

list filtra(list elenco){
    list result=emptylist();
    list temp1=elenco;
    int trovato=0;
    while(!empty(elenco)){
        trovato=0;
        while(trovato==0 && !empty(temp1)){
            if(head(elenco).costo>head(temp1).costo && strcmp(head(elenco).pietanza, head(temp1).pietanza)==0){
                trovato=1;
                result=cons(head(elenco), result);
            }
            temp1=tail(temp1);
        }
        elenco=tail(elenco);
    }
    freelist(temp1);
    return result;
}

//Es.3
float trovaCosto(char *pietanza, list tariffe){
    int trovato=0;
    float costo=0;
    while(!empty(tariffe) && trovato==0){
        if(strcmp(pietanza, head(tariffe).pietanza)==0){
            trovato=1;
            costo=head(tariffe).costo;
        }
        tariffe=tail(tariffe);
    }
    return costo;
}

float spesa(Pasto * pasti, int dim, list tariffe, int id){
    float spesaT=0;
    for(int i=0; i<dim; i++){
        if(pasti[i].id==id){
            spesaT=pasti[i].primo*trovaCosto("primo", tariffe);
            spesaT=spesaT+pasti[i].secondo*trovaCosto("secondo", tariffe);
            spesaT=spesaT+pasti[i].frutta*trovaCosto("frutta", tariffe);
            spesaT=spesaT+pasti[i].contorno*trovaCosto("contorno", tariffe);
            spesaT=spesaT+pasti[i].dolce*trovaCosto("dolce", tariffe);
        }
    }
    return spesaT;
}
