//
//  palazzo.c
//  Prova d’Esame 3A di Giovedì 13 Febbraio 2020
//
//  Created by Lorenzo Pellegrino on 28/01/21.
//

#include "palazzo.h"

//Es.1
Appartamento leggiUnAppartamento(FILE * fp){
    Appartamento temp;
    if(fscanf(fp, "%d %c %f %d %d ", &temp.identificativo, &temp.natura, &temp.superficie, &temp.piano, &temp.vani)==5){
        for(int i=0; i<temp.vani; i++){
            fscanf(fp, "%f ", &temp.superficieXvano[i]);
        }
    }
    else{
        temp.identificativo=-1;
    }
    return temp;
}

list leggiTuttiAppartamenti(char * fileName){
    list L;
    FILE *fp;
    L=emptylist();
    Appartamento temp;
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s\n", fileName);
        L=NULL;
    }
    else{
        temp=leggiUnAppartamento(fp);
        while(temp.identificativo!=-1){
            if(temp.identificativo!=-1){
                L=cons(temp, L);
            }
        }
        fclose(fp);
    }
    return L;
}

//Es.2
void scambia(Appartamento *a, Appartamento *b)
{
    Appartamento tmp = *a;
    *a = *b;
    *b = tmp;
}

void bubbleSort(Appartamento v[], int n){
  int i, ordinato = 0;
  while (n>1 && !ordinato){
     ordinato = 1;
     for (i=0; i<n-1; i++)
       if (compare(v[i], v[i+1])>0) {
            scambia(&v[i],&v[i+1]);
            ordinato = 0;
    }
    n--;
  }
}

Appartamento * trovaAppartamento(list appartamenti, int pianoMin, float mqMin, int* dim){
    Appartamento *vett=NULL;
    int counter=0;
    *dim=0;
    while(!empty(appartamenti)){
        if(head(appartamenti).piano>=pianoMin && head(appartamenti).superficie>=mqMin)
            counter++;
        appartamenti=tail(appartamenti);
    }
    if(counter>0){
        vett=(Appartamento*)malloc(sizeof(Appartamento)*counter);
        while(!empty(appartamenti)){
            if(head(appartamenti).piano>=pianoMin && head(appartamenti).superficie>=mqMin && (*dim)<counter){
            vett[(*dim)]=head(appartamenti);
            (*dim)++;
            }
            appartamenti=tail(appartamenti);
        }
        
        bubbleSort(vett, counter);
    }
    return vett;
}

//Es.3
Offerta leggiUnOfferta(FILE * fp){
    Offerta temp;
    char ch;
    int i=0;
    if(fscanf(fp, "%d ", &temp.identificartivo)==1){
        do{
            ch=fgetc(fp);
        }
        while(ch!='"');
        ch=fgetc(fp);
        while(ch!='"' && i<DIM-1){
            temp.nome_cognome[i]=ch;
            ch=fgetc(fp);
            i++;
        }
        temp.nome_cognome[i]='\0';
        fscanf(fp, " %f", &temp.valore);
    }
    else
        temp.identificartivo=-1;
    return temp;
}

int registraOfferta(int idAppartamento, char* cliente, float importo){
    FILE *fp;
    int result=0;
    Offerta temp;
    int stessoCliente=0;
    int offertaPiuAlta=0;
    
    fp=fopen("offerte.txt", "at");
    if(fp==NULL){
        printf("errore di lettura di offerte.txt");
        result=-1;
    }
    else{
        temp=leggiUnOfferta(fp);
        while(!stessoCliente && temp.identificartivo!=-1){
            if(strcmp(cliente, temp.nome_cognome)==0 && temp.identificartivo==idAppartamento)
                stessoCliente=1;
            if(temp.identificartivo==idAppartamento && temp.valore>=importo)
                offertaPiuAlta=1;
            if(!stessoCliente)
                temp=leggiUnOfferta(fp);
            }
        fclose(fp);
        if(stessoCliente){
            printf("è già stata fatta un'offerta per %d da %s", idAppartamento, cliente);
            result=-3;
        }
        if(!stessoCliente && offertaPiuAlta){
            printf("è già stata fatta un'offerta più alta");
            result=-5;
        }
        if(!stessoCliente && !offertaPiuAlta){
            fprintf(fp, "%d %s %f", temp.identificartivo, temp.nome_cognome, temp.valore);
            result=0;
        }
    }
    return result;
}
