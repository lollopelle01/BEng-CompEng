//
//  test.c
//  Prova d’Esame 3A di Giovedì 16 Febbraio 2017
//
//  Created by Lorenzo Pellegrino on 07/02/21.
//

#include "test.h"

//Es.1
Test leggiUnTest(FILE * fp){
    Test result;
    char ch;
    if(fscanf(fp, "%d/%d/%d %s", &result.d.g, &result.d.m, &result.d.a, result.matricola)==4){
        for(int i=0; i<4; i++){
            fscanf(fp, "%s %d", result.punti[i].materia, &result.punti[i].punteggio);
        }
        ch=fgetc(fp);
    }
    else{
        strcpy(result.matricola, "NULL");
    }
    return result;
}

Test * leggiTutti(char * fileName, int * dim){
    FILE *fp;
    Test* result=NULL;
    Test temp;
    *dim=0;
    int i=0;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        temp=leggiUnTest(fp);
        while(strcmp(temp.matricola, "NULL")!=0){
            (*dim)++;
            temp=leggiUnTest(fp);
        }
        if(*dim>0){
            result=(Test*)malloc(sizeof(Test)*(*dim));
            rewind(fp);
            temp=leggiUnTest(fp);
            while(strcmp(temp.matricola, "NULL")!=0){
                result[i]=temp;
                i++;
                temp=leggiUnTest(fp);
            }
        }
        fclose(fp);
    }
    return result;
}

void stampaTest(Test * v, int dim){
    for(int i=0; i<dim; i++){
        printf("%d/%d/%d %s ", v[i].d.g, v[i].d.m, v[i].d.a, v[i].matricola);
        for(int j=0; j<4; j++){
            printf("%s %d ", v[i].punti[j].materia, v[i].punti[j].punteggio);
        }
        printf("\n");
    }
}

//Es.2
void scambia(Test *a, Test *b){
    Test tmp = *a;
    *a = *b;
    *b = tmp;
}

void bubbleSort(Test v[], int n){
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

void ordina(Test * v, int dim){
    bubbleSort(v, dim);
}

list pref(char * fileName){
    list result=emptylist();
    list temp1=result;
    FILE *fp;
    Preferenza temp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s", fileName);
    }
    else{
        while(fscanf(fp, "%s %s", temp.matricola, temp.corso)==2){
            temp1=cons(temp, temp1);
        }
        while(!empty(temp1)){
            if(!member(head(temp1), result))
                result=cons(head(temp1), result);
            temp1=tail(temp1);
        }
        fclose(fp);
    }
    return result;
}


//Es.3
Test testMilgliore(char* matricola, Test* v, int dim){
    Test result;
    ordina(v, dim);
    for(int i=0; i<dim; i++){
        if(strcmp(matricola, v[i].matricola)==0){
            result=v[i];
        }
    }
    return result;
}

void ammessi(Test * v, int dim, list elenco, int postiDisponibili, char* corso){
    Test* migliori=NULL;
    int dimL=0;
   
    migliori=(Test*)malloc(sizeof(Test)*dim);
    
    while(!empty(elenco)){
        if(strcmp(head(elenco).corso, corso)==0){
            migliori[dimL]=testMilgliore(head(elenco).matricola, v, dim);
            dimL++;
        }
        elenco=tail(elenco);
    }
    ordina(migliori, dimL);
    for(int j=0; j<postiDisponibili && j<dimL; j++){
        printf("%s\n", migliori[j].matricola);
    }
    free(migliori);
}
