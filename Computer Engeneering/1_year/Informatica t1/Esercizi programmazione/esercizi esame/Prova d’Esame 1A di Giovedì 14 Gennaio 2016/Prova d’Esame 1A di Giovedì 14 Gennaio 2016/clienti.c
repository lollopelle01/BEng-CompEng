//
//  clienti.c
//  Prova d’Esame 1A di Giovedì 14 Gennaio 2016
//
//  Created by Lorenzo Pellegrino on 09/02/21.
//

#include "clienti.h"

//Es.1
Soggiorno * leggiSoggiorni(char * fileName, int * dim){
    Soggiorno* result=NULL;
    Soggiorno temp;
    *dim=0;
    int i=0;
    FILE *fp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while (fscanf(fp, "%d %d/%d/%d %d %s", &temp.id, &temp.inizio.a, &temp.inizio.m, &temp.inizio.g, &temp.durata, temp.tipo)==6) {
            (*dim)++;
        }
        if(*dim>0){
            result=(Soggiorno*)malloc(sizeof(Soggiorno)*(*dim));
            rewind(fp);
            while (fscanf(fp, "%d %d/%d/%d %d %s", &temp.id, &temp.inizio.a, &temp.inizio.m, &temp.inizio.g, &temp.durata, temp.tipo)==6) {
                result[i]=temp;
                i++;
            }
        }
        fclose(fp);
    }
    return result;
}

list leggiTariffe(char* fileName){
    list result=emptylist();
    FILE *fp;
    Tariffa temp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errorre di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%s %d/%d/%d %d/%d/%d %f", temp.tipo, &temp.inizio_v.a, &temp.inizio_v.m, &temp.inizio_v.g, &temp.fine_v.a, &temp.fine_v.m, &temp.fine_v.g, &temp.prezzo)==8){
            result=cons(temp, result);
        }
        fclose(fp);
    }
    return result;
}

void stampaSoggiorni(Soggiorno * v, int dim){
    for(int i=0; i<dim; i++){
        printf("%d %d/%d/%d %d %s\n", v[i].id, v[i].inizio.a, v[i].inizio.m, v[i].inizio.g, v[i].durata, v[i].tipo);
    }
}

void stampaTariffe(list elenco){
    Tariffa temp;
    if(empty(elenco))
        return;
    else{
        temp=head(elenco);
        printf("%s %d/%d/%d %d/%d/%d %6.2f\n", temp.tipo, temp.inizio_v.a, temp.inizio_v.m, temp.inizio_v.g, temp.fine_v.a, temp.fine_v.m, temp.fine_v.g, temp.prezzo);
        stampaTariffe(tail(elenco));
    }
}

//Es.2
void scambia(Soggiorno *a, Soggiorno *b){
    Soggiorno tmp = *a;
    *a = *b;
    *b = tmp;
}

int trovaPosMax(Soggiorno v[], int n){
  int i, posMax=0;
  for (i=1; i<n; i++)
      if (compare(v[posMax],v[i])<0)
          posMax=i;
  return posMax;
}

void naiveSort(Soggiorno v[], int n){
   int p;
   while (n>1) {
     p = trovaPosMax(v,n);
     if (p<n-1) scambia(&v[p],&v[n-1]);
     n--;
   }
}

void ordinaSoggiorni(Soggiorno * v, int dim){
    naiveSort(v, dim);
}

//Es.3
float costoSoggiorno(Soggiorno uno, list elencoTariffe){
    float tot=0;
    int trovato=0;
    while(!empty(elencoTariffe) && trovato==0){
        if(strcmp(uno.tipo, head(elencoTariffe).tipo)==0 && compareData(uno.inizio,head(elencoTariffe).inizio_v)>=0 && compareData(uno.inizio,head(elencoTariffe).fine_v)<=0){
            tot=uno.durata*head(elencoTariffe).prezzo;
            trovato=1;
        }
        elencoTariffe=tail(elencoTariffe);
    }
    return tot;
}

float totale(int idCliente, list elencoTariffe, Soggiorno * elencoSog, int dim){
    float result=0;
    for(int i=0; i<dim; i++){
        if(idCliente==elencoSog[i].id){
            result=result+costoSoggiorno(elencoSog[i], elencoTariffe);
        }
    }
    return result;
}
