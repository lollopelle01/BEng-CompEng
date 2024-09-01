//
//  catene.c
//  Prova d’Esame 1A di Giovedì 11 Gennaio 2018
//
//  Created by Lorenzo Pellegrino on 02/02/21.
//

#include "catene.h"

//Es.1
Catena leggiUno(FILE * fp){
    Catena temp;
    int i=0;
    if(fscanf(fp, "%d%d%f", &temp.diametro, &temp.larghezza, &temp.prezzo)==3){
        i=lettura_stringa(temp.azienda, '\n', fp);
    }
    else{
        temp.diametro=0;
        temp.larghezza=0;
        temp.prezzo=0.0F;
        strcpy(temp.azienda, "");
    }
    return temp;;
}

Catena * leggiTutte(char * fileName, int * dim){
    FILE *fp;
    Catena temp;
    Catena *vett=NULL;
    *dim=0;
    int i=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        temp=leggiUno(fp);
        while(temp.diametro!=0 && temp.larghezza!=0 && temp.prezzo!=0.0F && strcmp(temp.azienda, "")!=0){
            (*dim)++;
            temp=leggiUno(fp);
        }
        if(*dim>0){
            vett=(Catena*)malloc(sizeof(Catena)*(*dim));
            rewind(fp);
            temp=leggiUno(fp);
            while(temp.diametro!=0 && temp.larghezza!=0 && temp.prezzo!=0.0F && strcmp(temp.azienda, "")!=0){
                vett[i]=temp;
                temp=leggiUno(fp);
            }
        }
        fclose(fp);
    }
    return vett;
}

void stampa(Catena * v, int dim){
    for(int i=0; i<dim; i++){
        printf("%d %d %6.2f %s\n", v[i].diametro, v[i].larghezza, v[i].prezzo, v[i].azienda);
    }
}

//Es.2
void scambia(Catena *a, Catena *b)
{
    Catena tmp = *a;
    *a = *b;
    *b = tmp;
}

int trovaPosMax(Catena v[], int n){
  int i, posMax=0;
  for (i=1; i<n; i++)
      if (compare(v[posMax], v[i])>0)
          posMax=i;
  return posMax;
}

void naiveSort(Catena v[], int n){
   int p;
   while (n>1) {
     p = trovaPosMax(v,n);
     if (p<n-1) scambia(&v[p],&v[n-1]);
     n--;
   }
}

void ordina(Catena * v, int dim){
    naiveSort(v, dim);
}

list conta(Catena * v, int dim){
    list result=emptylist();
    Prodotto temp;
    ordina(v, dim);;
    
    if(dim>0){
        temp.quantita=1;
        for(int i=0; i<dim; i++){
            temp.tipo=v[i];
            if(compare(v[i], v[i+1])==0){
                temp.quantita++;
            }
            else{
                result=cons(temp, result);
                temp.tipo=v[i+1];
                temp.quantita=1;
            }
        }
        result=cons(temp, result);
    }
    return result;
}


//Es.3
list filtra(int diametro, int larg, list disponibili){
    list result=emptylist();
    while(!empty(disponibili)){
        if(compatibili(diametro, larg, head(disponibili).tipo)){
            result=insord_p(head(disponibili), result);
        }
        disponibili=tail(disponibili);
    }
    return result;
}
