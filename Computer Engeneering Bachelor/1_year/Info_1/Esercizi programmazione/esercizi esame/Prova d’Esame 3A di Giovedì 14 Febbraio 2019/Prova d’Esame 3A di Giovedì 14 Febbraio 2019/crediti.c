//
//  crediti.c
//  Prova d’Esame 3A di Giovedì 14 Febbraio 2019
//
//  Created by Lorenzo Pellegrino on 02/02/21.
//

#include "crediti.h"

//Es.1
Acquisto leggiUno(FILE * fp){
    Acquisto temp;
    Articolo temp2;
    if(fscanf(fp, "%s", temp.nome_azienda)==1){
        temp.num_articoli=0;
        fscanf(fp, "%s", temp2.nome_articolo);
        while(temp.num_articoli<32 && strcmp(temp2.nome_articolo, "fine")!=0){
            fscanf(fp, "%f", &temp2.costo);
            temp.articoli[temp.num_articoli]=temp2;
            (temp.num_articoli)++;
            fscanf(fp, "%s", temp2.nome_articolo);
        }
    }
    else{
        strcpy(temp.nome_azienda, "@@@");
    }
    return temp;
}

Acquisto * leggiCrediti(char * fileName, int * dim){
    Acquisto *result=NULL;
    Acquisto temp;
    *dim=0;
    FILE *fp;
    int i=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        temp=leggiUno(fp);
        while(strcmp(temp.nome_azienda, "@@@")!=0){
            (*dim)++;
            temp=leggiUno(fp);
        }
        if(*dim>0){
            result=(Acquisto*)malloc(sizeof(Acquisto)*(*dim));
            rewind(fp);
            temp=leggiUno(fp);
            while(strcmp(temp.nome_azienda, "@@@")!=0){
                result[i]=temp;
                i++;
                temp=leggiUno(fp);
            }
        }
        fclose(fp);
    }
    return result;
}

void stampaCrediti(Acquisto * crediti, int dim){
    if(dim<=0)
        return;
    else{
        printf("\n\nnome azienda: %s\n", crediti[dim-1].nome_azienda);
        printf("\nelenco articoli:\n");
        for(int i=0; i<crediti[dim-1].num_articoli; i++){
            printf("%s %6.2f\n", crediti[dim-1].articoli[i].nome_articolo, crediti[dim-1].articoli[i].costo);
        }
        stampaCrediti(crediti, dim-1);
    }
}

//Es.2
list trovaArticoli(Acquisto * crediti, int dim, char * nomeAzienda){
    list result=emptylist();
    for(int i=0; i<dim; i++){
        if(strcmp(crediti[i].nome_azienda, nomeAzienda)==0){
            for(int j=0; j<crediti[i].num_articoli; j++){
                result=cons(crediti[i].articoli[j], result);
            }
        }
    }
    return result;
}

list ordinaArticoli(list articoli){
    list result=emptylist();
    while(!empty(articoli)){
        result=insord_p(head(articoli), result);
        articoli=tail(articoli);
    }
    return result;
}

void  merge(Acquisto v[], int i1, int i2, int fine, Acquisto vout[]){
 int i=i1, j=i2, k=i1;
 while(i<=i2-1&& j<=fine){
  if (compare2(v[i], v[j])>0)
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

void mergeSort(Acquisto v[], int first, int last, Acquisto vout[]){
int mid;
 if ( first < last ) {
   mid = (last + first) / 2;
     mergeSort(v, first, mid,  vout);
     mergeSort(v, mid+1, last, vout);
     merge(v, first, mid+1, last, vout);
 }
}

void ordina(Acquisto * v, int dim){
    Acquisto *temp;
    temp=(Acquisto*)malloc(sizeof(Acquisto)*dim);
    mergeSort(v, 0, dim-1, temp);
    free(temp);
}

//Es.3
float costo(Acquisto * crediti, int dim, char * nomeAzienda){
    float result=0;
    list temp=emptylist();
    temp=trovaArticoli(crediti, dim, nomeAzienda);
    while(!empty(temp)){
        result=result+head(temp).costo;
        temp=tail(temp);
    }
    return result;
}

void totali(Acquisto * v, int dim){
    char nome[DIM];
    float tot;
    ordina(v, dim);
    for(int i=0; i<dim; i++){
        if(strcmp(nome, v[i].nome_azienda)!=0){
            tot=0;
            strcpy(nome, v[i].nome_azienda);
            tot=costo(v, dim, nome);
            printf("l'azienda %s ha speso %6.2f\n", nome, tot);
        }
    }
}
