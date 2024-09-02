//
//  ordini.c
//  Prova d’Esame 6A di Giovedì 14 Settembre 2017
//
//  Created by Lorenzo Pellegrino on 08/02/21.
//

#include "ordini.h"

//Es.1
list leggiOrdini(char * fileName){
    list result=emptylist();
    FILE *fp;
    Ordine temp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s\n", fileName);
    }
    else{
        while(fscanf(fp, "%d/%d/%d %d %s %f", &temp.d.a, &temp.d.m, &temp.d.g, &temp.id, temp.modello, &temp.prezzo)==6){
            lettura_stringa(temp.testo, '\n', fp);
            result=cons(temp, result);
        }
        fclose(fp);
    }
    return result;
}

void stampaOrdini(list v){
    Ordine temp;
    if(empty(v))
        return;
    else{
        temp=head(v);
        printf("%d/%d/%d %d %s %f %s\n", temp.d.a, temp.d.m, temp.d.g, temp.id, temp.modello, temp.prezzo, temp.testo);
        stampaOrdini(tail(v));
    }
}

int contaOccorrenze(list ordini, Ordine unOrdine){
    int result=0;
    Ordine t;
    while(!empty(ordini)){
        t=head(ordini);
        if(t.d.a==unOrdine.d.a && t.d.m==unOrdine.d.m && t.d.g==unOrdine.d.g && t.id==unOrdine.id && strcmp(t.modello, unOrdine.modello)==0){
            result++;
        }
        ordini=tail(ordini);
    }
    return result;
}

list filtra(list ordini){
    list result=emptylist();
    while (!empty(ordini)) {
        if(contaOccorrenze(ordini, head(ordini))==1){
            result=cons(head(ordini), result);
        }
        ordini=tail(ordini);
    }
    return result;
}

//Es.2
Ordine * perCliente(list ordini, int cliente, int * dim){
    Ordine* result=NULL;
    list tempL=ordini;
    *dim=0;
    int i=0;
    
    while(!empty(tempL)){
        if(cliente==head(tempL).id){
            (*dim)++;
        }
        tempL=tail(tempL);
    }
    if(*dim>0){
        result=(Ordine*)malloc(sizeof(Ordine)*(*dim));
        while(!empty(ordini)){
            if(cliente==head(ordini).id){
                result[i]=head(ordini);
                i++;
            }
            ordini=tail(ordini);
        }
    }
    return result;
}

void scambia(Ordine *a, Ordine *b){
    Ordine tmp = *a;
    *a = *b;
    *b = tmp;
}

void bubbleSort(Ordine v[], int n){
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

void ordina(Ordine * v, int dim){
    bubbleSort(v, dim);
}

//Es.3
float importo_totale(Ordine* v, int dim){
    float result=0;
    for(int i=0; i<dim; i++){
        result=result+v[i].prezzo;
    }
    return result;
}

int migliore(list ordini){
    int migliore;
    float importoM=0;
    list clienti=filtra(ordini);
    Ordine* temp1;
    int tempDim;
    
    while(!empty(clienti)){
        temp1=perCliente(ordini, head(clienti).id, &tempDim);
        if(importo_totale(temp1, tempDim)>importoM){
            migliore=head(clienti).id;
            importoM=importo_totale(temp1, tempDim);
        }
        free(temp1);
        clienti=tail(clienti);
    }
    freelist(clienti);
    return migliore;
}
