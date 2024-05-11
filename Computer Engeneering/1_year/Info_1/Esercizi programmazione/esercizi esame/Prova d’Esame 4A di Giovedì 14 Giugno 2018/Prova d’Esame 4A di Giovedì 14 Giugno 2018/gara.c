//
//  gara.c
//  Prova d’Esame 4A di Giovedì 14 Giugno 2018
//
//  Created by Lorenzo Pellegrino on 04/02/21.
//

#include "gara.h"

//Es.1
Gara leggiUno(FILE * fp){
    Gara temp;
    temp.controlloStart=0;
    temp.controlloA=0;
    temp.controlloB=0;
    temp.controlloC=0;
    temp.controlloEnd=0;
    char parola[DIM];
    char ch;
    
    if(lettura_stringa(temp.nome, ';', fp)>0){
        if(lettura_stringa(temp.squadra, ';', fp)>0){
            fscanf(fp, "%s", parola);
            while(temp.controlloEnd==0){
                if(strcmp(parola, "controlloStart")==0)
                    temp.controlloStart=1;
                if(strcmp(parola, "controlloA")==0)
                    temp.controlloA=1;
                if(strcmp(parola, "controlloB")==0)
                    temp.controlloB=1;
                if(strcmp(parola, "controlloC")==0)
                    temp.controlloC=1;
                if(strcmp(parola, "controlloEnd")==0)
                    temp.controlloEnd=1;
                
                if(temp.controlloEnd==0)
                    fscanf(fp, "%s", parola);
                else if(temp.controlloEnd==1)
                    ch=fgetc(fp);
            }
        }
    }
    else{
        strcpy(temp.squadra, "");
    }
    return temp;
}

list leggi(char * fileName){
    list result=emptylist();
    Gara temp;
    FILE *fp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s",fileName);
    }
    else{
        temp=leggiUno(fp);
        while(strcmp(temp.squadra, "")!=0){
            result=cons(temp, result);
            temp=leggiUno(fp);
        }
        fclose(fp);
    }
    return result;
}

int punti(Gara g){
    int result=0;
    if(g.controlloStart==1 && g.controlloA==1 && g.controlloEnd==1){
        result=50;
        if(g.controlloB==1){
            result=105;
            if(g.controlloC==1)
                result=157;
        }
    }
    return result;
}

//Es.2
void scambia(Squadra *a, Squadra *b)
{
    Squadra tmp = *a;
    *a = *b;
    *b = tmp;
}

void bubbleSort(Squadra v[], int n){
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

void ordina(Squadra * v, int dim){
    bubbleSort(v, dim);
}

int appartiene(char* squadra, list elenco){
    if(empty(elenco))
        return 0;
    else{
        if(strcmp(squadra, head(elenco).squadra)==0)
            return 1;
        else
            return appartiene(squadra, tail(elenco));
    }
}

Squadra * estrai(list elenco, int * dim){
    Squadra* result=NULL;
    list temp=elenco;
    *dim=0;
    int i=0;
    
    while(!empty(temp)){
        if(!appartiene(head(temp).squadra, tail(temp))){
            (*dim)++;
        }
           temp=tail(temp);
    }
    if(*dim>0){
        result=(Squadra*) malloc(sizeof(Squadra)*(*dim));
        while(!empty(elenco)){
            if(!appartiene(head(elenco).squadra, tail(elenco))){
                strcpy(result[i].nome, head(elenco).squadra);
                result[i].punteggio=0;
                i++;
            }
               temp=tail(temp);
        }
    }
    return result;
}

//Es.3
void totale(Squadra * classifica, int dim, list elenco){
    list temp=elenco;
    for(int i=0; i<dim; i++){
        while(!empty(temp)){
            if(strcmp(classifica[i].nome, head(temp).squadra)==0){
                classifica[i].punteggio+=punti(head(temp));
            }
            temp=tail(temp);
        }
    }
    ordina(classifica, dim);
}
