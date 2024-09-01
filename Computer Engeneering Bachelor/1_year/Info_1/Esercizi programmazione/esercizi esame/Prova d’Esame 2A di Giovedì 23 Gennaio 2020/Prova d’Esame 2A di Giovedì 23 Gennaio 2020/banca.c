//
//  banca.c
//  Prova d’Esame 2A di Giovedì 23 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 28/01/21.
//

#include "banca.h"

//Es.1
Loan leggiUnLoan(FILE * fp){
    Loan temp;
    if(fscanf(fp, "%d ", &temp.identificatore)==1){
        if(lettura_stringa(temp.cognome, ';', fp)>0)
            if(lettura_stringa(temp.nome, ';', fp)>0)
                fscanf(fp, "%f\n", &temp.importo);
    }
    else{
        temp.identificatore=0;
    }
    return temp;
}

Loan * leggiLoanAttivi(char * fileName, int * dim){
    FILE *fp;
    Loan *vett;
    Loan temp;
    int i=0;
    
    *dim=0;
    vett=NULL;
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s\n", fileName);
        return vett;
    }
    else{
        temp=leggiUnLoan(fp);
        while(temp.identificatore!=0){
            (*dim)++;
        }
        if(*dim>0){
            vett=(Loan*)malloc(sizeof(Loan)*(*dim));
            rewind(fp);
            temp=leggiUnLoan(fp);
            while(temp.identificatore!=0){
                if(strstr(temp.cognome, "ESTINTO")==NULL){
                    vett[i]=temp;
                    i++;
                }
                temp=leggiUnLoan(fp);
            }
            fclose(fp);
        }
        return vett;
    }
}

//Es.2
void insOrd(Loan v[], int pos){
    int i = pos-1;
    Loan x = v[pos];
  while (i>=0 && compare(x, v[i])<0) {
    v[i+1]= v[i]; /* crea lo spazio */
    i--;
  }
  v[i+1]=x; /* inserisce l’elemento */
}

void insertSort(Loan v[], int n){
  int k;
  for (k=1; k<n; k++)
      insOrd(v,k);
}

void ordina(Loan * elenco, int dim){
    insertSort(elenco, dim);
}

list estrai(Loan * elenco, int dim, char * cognome, char * nome){
    list l=emptylist();
    for(int i=0; i<dim; i++){
        if(!strcmp(elenco[i].cognome, cognome))
            if(!strcmp(elenco[i].nome, nome)){
                l=cons(elenco[i], l);
            }
                
    }
    return l;
}

//Es.3
void espo(Loan * elenco, int dim){
    float esposizione_tot;
    list temp=emptylist();
    
    ordina(elenco, dim);
    
    for(int i=0; i<dim; i++){
        if(i==0 || (strcmp(elenco[i].cognome, elenco[i-1].cognome)==0 && strcmp(elenco[i].nome, elenco[i-1].nome)==0)){
            temp=estrai(elenco, dim, elenco[i].cognome, elenco[i].nome);
            esposizione_tot=0;
            while(!empty(temp)){
                esposizione_tot=esposizione_tot+head(temp).importo;
                temp=tail(temp);
            }
            temp=emptylist();
            printf("%s, %s ha fatto %f esposizioni totali", elenco[i].cognome, elenco[i].nome, esposizione_tot);
        }
    }
    free(temp);
}


