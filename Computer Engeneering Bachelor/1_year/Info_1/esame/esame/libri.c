//
//  libri.c
//  esame
//
//  Created by Lorenzo Pellegrino on 11/02/21.
//

#include "libri.h"

//Es.1
list leggi(char fileName[], char select, char nome[]){
    list result=emptylist();
    list tempL=result;
    Ordine temp;
    FILE *fp;
    char ch;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%s %s %d %f", temp.cliente, temp.fornitore, &temp.copie, &temp.prezzo)==4){
            ch=fgetc(fp);
            lettura_stringa(temp.titolo, '\n', fp);
            tempL=cons(temp, tempL);
        }
        fclose(fp);
    }
    if(select=='C'){
        while(!empty(tempL)){
            if(strcmp(head(tempL).cliente, nome)==0){
                result=cons(head(tempL), result);
            }
            tempL=tail(tempL);
        }
    }
    if(select=='F'){
        while(!empty(tempL)){
            if(strcmp(head(tempL).fornitore, nome)==0){
                result=cons(head(tempL), result);
            }
            tempL=tail(tempL);
        }
    }
    freelist(tempL);
    return result;
}

void stampaOrdini(list elenco){
    Ordine temp;
    if(empty(elenco))
        return;
    else{
        temp=head(elenco);
        printf("%s %s %d %6.2f %s\n", temp.cliente, temp.fornitore, temp.copie, temp.prezzo, temp.titolo);
        stampaOrdini(tail(elenco));
    }
}

//Es.2
int contaCopieOrdinate(list elenco, char * titolo){
    list temp=elenco;
    int result=0;
    while(!empty(temp)){
        if(strcmp(head(temp).titolo, titolo)==0){
            result=result+head(temp).copie;
        }
        temp=tail(temp);
    }
    freelist(temp);
    return result;
}

void aggregaPerTitolo(list elenco){
    list temp=emptylist();
    list temp2=elenco;
    int copie;
    
    while(!empty(temp2)){
        if(!member(head(temp2), temp)){
            temp=cons(head(temp2), temp);
        }
        temp2=tail(temp2);
    }
    
    while(!empty(temp)){
        copie=contaCopieOrdinate(elenco, head(temp).titolo);
        printf("%s copie: %d\n", head(temp).titolo, copie);
        temp=tail(temp);
    }
    freelist(temp);
    freelist(temp2);
}

void scambia(Ordine *a, Ordine *b){
    Ordine tmp = *a;
    *a = *b;
    *b = tmp;
}


void quickSortR(Ordine a[], int iniz, int fine)
{
    int i, j, iPivot;
    Ordine pivot;
    if (iniz < fine)
    {
        i = iniz;
        j = fine;
        iPivot = fine;
        pivot = a[iPivot];
        do  /* trova la posizione del pivot */
        {
            while (i < j && compare(a[i], pivot)<0)
        i++;
            while (j > i && compare(a[j], pivot)>0)
        j--;
            if (i < j)
        scambia(&a[i], &a[j]);
    }
    while (i < j);

         /* determinati i due sottoinsiemi */
        /* posiziona il pivot */

        if (i != iPivot && compare(a[i],a[iPivot]))
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

void quickSort(Ordine a[], int dim){
    quickSortR(a, 0, dim - 1);
}

Ordine* prepara(list elenco, int *dim){
    Ordine* result=NULL;
    list temp=elenco;
    *dim=0;
    int i=0;
    
    *dim=length(temp);
    if(*dim>0){
        result=(Ordine*)malloc(sizeof(Ordine)*(*dim));
        while(!empty(elenco)){
            result[i]=head(elenco);
            i++;
            elenco=tail(elenco);
        }
    }
    freelist(temp);
    quickSort(result, *dim);
    return result;
}

//Es.3
list nuoviOrdini(list elencoClienti, list elencoFornitori){
    Ordine temp;
    list result=emptylist();
    while(!empty(elencoClienti)){
        while (!empty(elencoFornitori)) {
            if(strcmp(head(elencoFornitori).titolo,head(elencoClienti).titolo)==0){
                strcpy(temp.cliente, "LaTrama");
                strcpy(temp.fornitore, "IGNOTO");
                temp.copie=contaCopieOrdinate(elencoClienti, head(elencoClienti).titolo)-contaCopieOrdinate(elencoFornitori, head(elencoFornitori).titolo);
                temp.prezzo=head(elencoClienti).prezzo;
                strcpy(temp.titolo, head(elencoClienti).titolo);
                if(temp.copie!=0){
                    result=cons(temp, result);
                }
            }
            elencoFornitori=tail(elencoFornitori);
        }
        
        elencoClienti=tail(elencoClienti);
    }
    return result;
}
