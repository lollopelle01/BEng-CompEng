//
//  libri.c
//  Prova d’Esame 5A di Giovedì 12 Luglio 2018
//
//  Created by Lorenzo Pellegrino on 04/02/21.
//

#include "libri.h"

//Es.1
list leggi(char * fileName){
    FILE *fp;
    list result=emptylist();
    Libro temp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%d %d %s %f", &temp.id_ordine, &temp.id_utente, temp.titolo, &temp.prezzo)==4){
            result=cons(temp, result);
        }
        fclose(fp);
    }
    return result;
}

void stampaLibri(list elencoLibri){
    Libro temp;
    if(empty(elencoLibri))
        return;
    else{
        printf("%d %d %s %6.2f\n", temp.id_ordine, temp.id_utente, temp.titolo, temp.prezzo);
        stampaLibri(tail(elencoLibri));
        
    }
}

list filtra(list elencoLibri){
    list result=emptylist();
    while(!empty(elencoLibri)){
        if(!member(head(elencoLibri), result))
            result=cons(head(elencoLibri), result);
        elencoLibri=tail(elencoLibri);
    }
    return result;
}

//Es.2
Ordine estrai(list elencoLibri, int idOrdine){
    Ordine result;
    
    result.elencoLibri=emptylist();
    result.id_ordine=idOrdine;
    while(!empty(elencoLibri)){
        if(head(elencoLibri).id_ordine==idOrdine){
            result.elencoLibri=cons(head(elencoLibri), result.elencoLibri);
            result.id_utente=head(elencoLibri).id_utente;
        }
    }
    return result;;
}

int compare(Ordine o1, Ordine o2){
    int result=o1.id_utente-o2.id_utente;
    if(result==0){
        result=length(o2.elencoLibri)-length(o1.elencoLibri);
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
       if (compare(v[i],v[i+1])>0) {
            scambia(&v[i],&v[i+1]);
            ordinato = 0;
    }
    n--;
  }
}

void ordina(Ordine * v, int dim){
    bubbleSort(v, dim);
}

int contiene(int ordine, list elenco){
    if(empty(elenco))
        return 0;
    else{
        if(head(elenco).id_ordine==ordine)
            return 1;
        else
            return contiene(ordine, tail(elenco));
    }
}
//Es.3
Ordine * elenco(list elencoLibri, int * dim){
    Ordine* result;
    list temp=elencoLibri;
    int i=0;
    *dim=0;
    while(!empty(temp)){
        if(!contiene(head(temp).id_ordine, tail(temp))){
            (*dim)++;
        }
        temp=tail(temp);
    }
    if(*dim>0){
        result=
    }
    return result;
}
