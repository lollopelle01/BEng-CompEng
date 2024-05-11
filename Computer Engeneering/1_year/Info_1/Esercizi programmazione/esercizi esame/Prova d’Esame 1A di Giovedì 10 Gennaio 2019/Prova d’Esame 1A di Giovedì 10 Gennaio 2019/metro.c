//
//  metro.c
//  Prova d’Esame 1A di Giovedì 10 Gennaio 2019
//
//  Created by Lorenzo Pellegrino on 31/01/21.
//

#include "metro.h"

//Es.1
Evento leggiUno(FILE * fp){
    Evento temp;
    int k;
    if(fscanf(fp, "%d", &temp.id)==1){
        if(lettura_stringa(temp.s_ingresso, '@', fp)>0)
            k=lettura_stringa(temp.s_uscita, '\n', fp);
    }
    else{
        temp.id=-1;
    }
    return temp;
}

list leggiTutti(char * fileName){
    FILE *fp;
    list result=emptylist();
    Evento temp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        temp=leggiUno(fp);
        while(temp.id!=-1){
            result=cons(temp, result);
            temp=leggiUno(fp);
        }
        fclose(fp);
    }
    return result;
}

Tariffa leggiTariffa(FILE*fp){
    Tariffa temp;
    if(lettura_stringa(temp.s_ingresso, '@', fp)>0){
        if(lettura_stringa(temp.s_uscita, '@', fp)>0)
            fscanf(fp, "%f\n", &temp.costo);
    }
    else{
        strcpy(temp.s_ingresso, "NULL");
    }
    return temp;
}

Tariffa * leggiTariffe(char * fileName, int * dim){
    FILE *fp;
    Tariffa *vett=NULL;
    Tariffa temp;
    *dim=0;
    int i=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        temp=leggiTariffa(fp);
        while(strcmp(temp.s_ingresso, "NULL")!=0){
            (*dim)++;
            temp=leggiTariffa(fp);
        }
        if(*dim>0){
            vett=(Tariffa*)malloc(sizeof(Tariffa)*(*dim));
            rewind(fp);
            
            temp=leggiTariffa(fp);
            while(strcmp(temp.s_ingresso, "NULL")!=0){
                vett[i]=temp;
                i++;
                temp=leggiTariffa(fp);
            }
        }
        fclose(fp);
    }
    return vett;
}

//Es.2
void scambia(Tariffa *a, Tariffa *b){
    Tariffa tmp = *a;
    *a = *b;
    *b = tmp;
}

void quickSortR(Tariffa a[], int iniz, int fine)
{
    int i, j, iPivot;
    Tariffa pivot;
    if (iniz < fine)
    {
        i = iniz;
        j = fine;
        iPivot = fine;
        pivot = a[iPivot];
        do  /* trova la posizione del pivot */
        {
            while (i < j && compare(a[i], pivot)<=0)
        i++;
            while (j > i && compare(a[j], pivot)>=0)
        j--;
            if (i < j)
        scambia(&a[i], &a[j]);
    }
    while (i < j);

         /* determinati i due sottoinsiemi */
        /* posiziona il pivot */

        if (i != iPivot && compare(a[i], a[iPivot]))
        {
            scambia(&a[i], &a[iPivot]);
        iPivot = i;
    }

        /* ricorsione sulle sottoparti, se necessario */

        if (iniz < iPivot - 1)
            quickSortR(a, iniz, iPivot - 1);
        if (iPivot + 1 < fine)
            quickSortR(a, iPivot + 1, fine);

    } /* (iniz < fine) */
} /* quickSortR */

void quickSort(Tariffa a[], int dim){
    quickSortR(a, 0, dim - 1);
}

void ordina(Tariffa * v, int dim){
    quickSort(v, dim);
}

float ricerca(Tariffa * v, int dim, char * ingresso, char * uscita){
    float costo=0;
    int i=0;
    int trovato=0;
    while(i<dim && trovato==0){
        if(strcmp(v[i].s_ingresso, ingresso)==0 && strcmp(v[i].s_uscita, uscita)==0){
            trovato=1;
            costo=v[i].costo;
        }
        i++;
    }
    return costo;
}

//Es.3
void totali(Tariffa * tariffe, int dim, list eventi){
    list temp;
    list temp2;
    float totale;
    int value;
    temp=eventi;
    while(!empty(temp)){
        value=member(head(temp), tail(temp));
        if(!value){
            temp2=temp;
            totale=0;
            while(!empty(temp2)){
                if(head(temp2).id==head(temp).id)
                    totale=totale+ricerca(tariffe, dim, head(temp2).s_ingresso, head(temp2).s_uscita);
                temp2=tail(temp2);
            }
            printf("per l'ID: %d bisogna pagare %6.2f\n", head(temp).id, totale);
        }
        temp=tail(temp);
    }
}
