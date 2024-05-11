//
//  voli.c
//  Prova d’Esame 4A di Giovedì 15 Giugno 2017
//
//  Created by Lorenzo Pellegrino on 07/02/21.
//

#include "voli.h"

//Es.1
Prenotazione * leggiTutti(char * fileName, int * dim){
    Prenotazione* result=NULL;
    Prenotazione temp;
    FILE *fp;
    *dim=0;
    int i=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%s %s", temp.id_volo, temp.id_biglietto)==2){
            (*dim)++;
        }
        if(*dim>0){
            result=(Prenotazione*)malloc(sizeof(Prenotazione)*(*dim));
            rewind(fp);
            while(fscanf(fp, "%s %s", temp.id_volo, temp.id_biglietto)==2){
                result[i]=temp;
                i++;
            }
        }
        fclose(fp);
    }
    return result;
}

void stampaPrenotazioni(Prenotazione * v, int dim){
    for(int i=0; i<dim; i++){
        printf("%s %s\n", v[i].id_volo, v[i].id_biglietto);
    }
}

Viaggiatore leggiViaggiatore(FILE *fp){
    Viaggiatore result;
    if(lettura_stringa(result.id_biglietto, ';', fp)>0){
        lettura_stringa(result.nome, ';', fp);
        fscanf(fp, "%c%*c", &result.check);
    }
    else{
        strcpy(result.id_biglietto, "");
    }
    return result;
}

list leggiViaggiatori(char * fileName){
    FILE *fp;
    list result=emptylist();
    Viaggiatore temp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s\n", fileName);
    }
    else{
        temp=leggiViaggiatore(fp);
        while(strcmp(temp.id_biglietto, "")!=0){
            result=cons(temp, result);
            temp=leggiViaggiatore(fp);
        }
        fclose(fp);
    }
    return result;
}

//Es.2
void  merge(Prenotazione v[], int i1, int i2, int fine, Prenotazione vout[]){
 int i=i1, j=i2, k=i1;
 while(i<=i2-1&& j<=fine){
  if (compare(v[i],v[j])<0)
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

void mergeSort(Prenotazione v[], int first, int last, Prenotazione vout[]){
int mid;
 if ( first < last ) {
   mid = (last + first) / 2;
     mergeSort(v, first, mid,  vout);
     mergeSort(v, mid+1, last, vout);
     merge(v, first, mid+1, last, vout);
 }
}

void ordina(Prenotazione * v, int dim){
    Prenotazione* temp;
    temp=(Prenotazione*)malloc(sizeof(Prenotazione)*dim);
    mergeSort(v, 0, dim-1, temp);
    free(temp);
}

list ordinaLista(list viaggiatori){
    list result=emptylist();
    while(!empty(viaggiatori)){
        result=insord_p(head(viaggiatori), result);
        viaggiatori=tail(viaggiatori);
    }
    return result;;
}

//Es.3
Viaggiatore estraiSingolo(list viaggiatori, char * biglietto){
    Viaggiatore result;
    int trovato=0;
    while (!empty(viaggiatori) && trovato==0) {
        if(strcmp(head(viaggiatori).id_biglietto, biglietto)==0){
            trovato=1;
            result=head(viaggiatori);
        }
        viaggiatori=tail(viaggiatori);
    }
    if(trovato==0){
        strcpy(result.nome, "NULL");
    }
    return result;
}

list estraiViaggiatori(Prenotazione * v, int dim, list viaggiatori, char * idVolo){
    list result=emptylist();
    for(int i=0; i<dim; i++){
        if(strcmp(v[i].id_volo, idVolo)==0){
            result=cons(estraiSingolo(viaggiatori, v[i].id_biglietto), result);
        }
    }
    return result;
}
