//
//  pref.c
//  Prova d’Esame 1A di Giovedì 12 Gennaio 2017
//
//  Created by Lorenzo Pellegrino on 05/02/21.
//

#include "pref.h"

//Es.1
Preferenza leggiPref(FILE * fp){
    Preferenza result;
    if(fscanf(fp, "%d:%d:%d", &result.orario.h, &result.orario.m, &result.orario.s)==3){
        lettura_stringa(result.numero, '@', fp);
        lettura_stringa(result.titolo, '\n', fp);
    }
    else{
        result.orario.h=0;
        result.orario.m=0;
        result.orario.s=0;
        strcpy(result.titolo, "");
    }
    return result;
}

Preferenza * leggiTutte(char * fileName, int * dim){
    FILE *fp;
    Preferenza* result=NULL;
    Preferenza temp;
    int i=0;
    *dim=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        temp=leggiPref(fp);
        while(strcmp(temp.titolo, "")!=0){
            (*dim)++;
            temp=leggiPref(fp);
        }
        if(*dim>0){
            result=(Preferenza*)malloc(sizeof(Preferenza)*(*dim));
            rewind(fp);
            temp=leggiPref(fp);
            while(strcmp(temp.titolo, "")!=0){
                result[i]=temp;
                i++;
                temp=leggiPref(fp);
            }
        }
        fclose(fp);
    }
    return result;
}

void stampaPref(Preferenza * v, int dim){
    if(dim>0){
        for(int i=0; i<dim; i++){
            printf("%d:%d:%d, %s, %s\n", v[i].orario.h, v[i].orario.m, v[i].orario.s, v[i].numero, v[i].titolo);
        }
    }
}

//Es.2
void scambia(Preferenza *a, Preferenza *b)
{
    Preferenza tmp = *a;
    *a = *b;
    *b = tmp;
}

int trovaPosMax(Preferenza v[], int n){
  int i, posMax=0;
  for (i=1; i<n; i++)
      if (compare(v[posMax],v[i])>0) 
          posMax=i;
  return posMax;
}

void naiveSort(Preferenza v[], int n){
   int p;
   while (n>1) {
     p = trovaPosMax(v,n);
     if (p<n-1) scambia(&v[p],&v[n-1]);
     n--;
   }
}

void ordina(Preferenza * v, int dim){
    naiveSort(v, dim);
}

list filtra(Preferenza * v, int dim){
    list result=emptylist();
    list temp=result;
    Canzone tempC;
    
    for(int i=0; i<dim; i++){
        strcpy(tempC.titolo, v[i].titolo);
        tempC.n_preferenze=0;
        temp=cons(tempC, temp);
    }
    
    while(!empty(temp)){
        if(!member(head(temp), result)){
            result=cons(head(temp), result);
        }
           temp=tail(temp);
    }
    freelist(temp);
    return result;
}

//Es.3
list totali(Preferenza * v, int dim, list titoli){
    Canzone temp;
    list result=emptylist();
    ordina(v, dim);
    while(!empty(titoli)){
        temp=head(titoli);
        for(int i=0; i<dim; i++){
            if(strcmp(v[i].titolo, temp.titolo)==0 && (i==dim-1 || strcmp(v[i].numero, v[i+1].numero)!=0))
                temp.n_preferenze++;
        }
        result=cons(temp, result);
        titoli=tail(titoli);
    }
    return result;
}
