//
//  rist.c
//  Prova d’Esame 2A di Giovedì 26 Gennaio 2017
//
//  Created by Lorenzo Pellegrino on 06/02/21.
//

#include "rist.h"

//Es.1
Pasto leggiPasto(FILE * fp){
    Pasto result;
    if(fscanf(fp, "%s %d", result.cognome, &result.n_persone)==2){
        if(fscanf(fp, "%f", &result.importo)==1){
            return result;
        }
        else{
            return result=leggiPasto(fp);
        }
    }
    else{
        strcpy(result.cognome, "NULL");
        return result;
    }
}

Pasto * leggiTutti(char * fileName, int * dim){
    FILE *fp;
    Pasto temp;
    Pasto* result=NULL;
    int i=0;
    *dim=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        temp=leggiPasto(fp);
        while(strcmp(temp.cognome, "NULL")!=0){
            (*dim)++;
            temp=leggiPasto(fp);
        }
        if(*dim>0){
            result=(Pasto*)malloc(sizeof(Pasto)*(*dim));
            rewind(fp);
            temp=leggiPasto(fp);
            while(strcmp(temp.cognome, "NULL")!=0){
                result[i]=temp;
                i++;
                temp=leggiPasto(fp);
            }
        }
        fclose(fp);
    }
    return result;
}

void stampaPasti(Pasto * v, int dim){
    if(dim>0){
        for(int i=0; i<dim; i++){
            printf("%s %d %6.2f\n", v[i].cognome, v[i].n_persone, v[i].importo);
        }
    }
}

//Es.2
void insOrd(Pasto v[], int pos){
    int i = pos-1;
    Pasto x = v[pos];
  while (i>=0 && compare(x,v[i])<0) {
    v[i+1]= v[i]; /* crea lo spazio */
    i--;
  }
  v[i+1]=x; /* inserisce l’elemento */
}

void insertSort(Pasto v[], int n){
  int k;
  for (k=1; k<n; k++)
      insOrd(v,k);
}

void ordina(Pasto * v, int dim){
    insertSort(v, dim);
}

list elenco(char * fileName){
    list result=emptylist();
    FILE *fp;
    Dipendente temp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%s %s", temp.cognome, temp.azienda)==2){
            result=insord_p(temp, result);
        }
        fclose(fp);
    }
    return result;
}

//Es.3
float totaleAzienda(Pasto * v, int dim, list elenco, char * azienda){
    float totale=0;
    list temp=elenco;
    while(!empty(temp)){
        if(strcmp(head(temp).azienda, azienda)==0){
            for(int i=0; i<dim; i++){
                if(strcmp(head(temp).cognome, v[i].cognome)==0){
                    totale=totale+v[i].importo;
                }
            }
        }
        temp=tail(temp);
    }
    return totale;
}

void totali(Pasto * v, int dim, list elenco){
    float tot;
    list temp=elenco;
    while(!empty(temp)){
        if(empty(tail(temp)) || strcmp(head(temp).azienda, head(tail(temp)).azienda)!=0){
            tot=totaleAzienda(v, dim, elenco, head(temp).azienda);
            printf("l'azienda %s deve pagare %6.2f\n", head(temp).azienda, tot);
        }
        temp=tail(temp);
    }
}
