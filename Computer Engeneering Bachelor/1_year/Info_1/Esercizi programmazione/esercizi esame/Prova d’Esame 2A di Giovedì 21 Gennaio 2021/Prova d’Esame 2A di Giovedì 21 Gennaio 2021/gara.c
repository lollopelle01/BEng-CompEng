//
//  gara.c
//  Prova d’Esame 2A di Giovedì 21 Gennaio 2021
//
//  Created by Lorenzo Pellegrino on 10/02/21.
//

#include "gara.h"

//Es.1
Atleta leggiSingoloAtleta(FILE *fp){
    Atleta result;
    if(fscanf(fp, "%s %s", result.codiceAtleta, result.nomeAtleta)==2){
        result.tempiDim=0;
        while(fscanf(fp, "%d", &result.tempi[result.tempiDim])==1){
            result.tempiDim++;
        }
    }
    else{
        strcpy(result.codiceAtleta, "0000");
    }
    return result;
}

Atleta* leggiAtleti(char fileName[], int *dim){
    Atleta* result=NULL;
    Atleta temp;
    *dim=0;
    int i=0;
    FILE *fp;
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di apertura di %s\n", fileName);
    }
    else{
        temp=leggiSingoloAtleta(fp);
        while(strcmp(temp.codiceAtleta, "0000")!=0){
            (*dim)++;
            temp=leggiSingoloAtleta(fp);
        }
        if(*dim>0){
            result=(Atleta*)malloc(sizeof(Atleta)*(*dim));
            rewind(fp);
            temp=leggiSingoloAtleta(fp);
            while(strcmp(temp.codiceAtleta, "0000")!=0){
                result[i]=temp;
                i++;
                temp=leggiSingoloAtleta(fp);
            }
        }
        fclose(fp);
    }
    return result;
}

void stampaAtleta(Atleta a){
    int tempoT=0;
    printf("%s %s ", a.codiceAtleta, a.nomeAtleta);
    if(a.tempiDim<3){
        printf("-1\n");
    }
    else{
        for(int i=0; i<a.tempiDim; i++){
            tempoT=tempoT+a.tempi[i];
            printf("%d ", a.tempi[i]);
        }
        printf("\t tot: %d\n", tempoT);
    }
}

//Es.2
int totale(Atleta a){
    int result=0;
    for(int i=0; i<a.tempiDim; i++){
        result=result+a.tempi[i];
    }
    return result;
}
int compare(Atleta a1, Atleta a2, char* tipo){
    int result;
    if(strcmp(tipo, "Totale")==0){
        if(a1.tempiDim<3 || a2.tempiDim<3){
            printf("uno dei due non ha completato una prova\n");
            result=0;
        }
        if(totale(a1)==totale(a2))
            result=0;
        if(totale(a1)>totale(a2))
            result=-1;
        if(totale(a1)<totale(a2))
            result=1;
    }
    if(strcmp(tipo, "Nuoto")==0){
        if(a1.tempiDim<1 || a2.tempiDim<1){
            printf("uno dei due non ha completato la prova\n");
            result=0;
        }
        if(a1.tempi[0]==a2.tempi[0])
            result=0;
        if(a1.tempi[0]>a2.tempi[0])
            result=-1;
        if(a1.tempi[0]<a2.tempi[0])
            result=1;
    }
    if(strcmp(tipo, "Bici")==0){
        if(a1.tempiDim<2 || a2.tempiDim<2){
            printf("uno dei due non ha completato la prova\n");
            result=0;
        }
        if(a1.tempi[1]==a2.tempi[1])
            result=0;
        if(a1.tempi[1]>a2.tempi[1])
            result=-1;
        if(a1.tempi[1]<a2.tempi[1])
            result=1;
    }
    if(strcmp(tipo, "Corsa")==0){
        if(a1.tempiDim<3 || a2.tempiDim<3){
            printf("uno dei due non ha completato la prova\n");
            result=0;
        }
        if(a1.tempi[2]==a2.tempi[2])
            result=0;
        if(a1.tempi[2]>a2.tempi[2])
            result=-1;
        if(a1.tempi[2]<a2.tempi[2])
            result=1;
    }
    return result;
}

void scambia(Atleta *a, Atleta *b){
    Atleta tmp = *a;
    *a = *b;
    *b = tmp;
}

void bubbleSort(Atleta v[], int n, char* tipo){
  int i, ordinato = 0;
  while (n>1 && !ordinato){
     ordinato = 1;
     for (i=0; i<n-1; i++)
       if (compare(v[i],v[i+1],tipo)<0){
            scambia(&v[i],&v[i+1]);
            ordinato = 0;
    }
    n--;
  }
}

Atleta* ordinaAtletiPer(Atleta* a, int dimA, char *tipo, int* dim){
    Atleta* result=NULL;
    *dim=0;
    int i=0;
    
    for(int j=0; j<dimA; j++){
        if(a[j].tempiDim==3){
            (*dim)++;
        }
    }
    if(*dim>0){
        result=(Atleta*)malloc(sizeof(Atleta)*(*dim));
        for(int j=0; j<dimA; j++){
            if(a[j].tempiDim==3){
                result[i]=a[j];
                i++;
            }
        }
        bubbleSort(result, *dim, tipo);
    }
    return result;
}

//Es.3
list atletiPremiati(Atleta* a, int dimA){
    list result=emptylist();
    list temp=result;
    Atleta* vT;
    Atleta* vN;
    Atleta* vB;
    Atleta* vC;
    int dimT;
    int dimN;
    int dimB;
    int dimC;
    
    vT=ordinaAtletiPer(a, dimA, "Totale", &dimT);
    vN=ordinaAtletiPer(a, dimA, "Nuoto", &dimN);
    vB=ordinaAtletiPer(a, dimA, "Bici", &dimB);
    vC=ordinaAtletiPer(a, dimA, "Corsa", &dimC);
    
    for(int i=0; i<5; i++){
        temp=cons(vT[i], temp);
    }
    for(int i=0; i<3; i++){
        temp=cons(vN[i], temp);
    }
    for(int i=0; i<3; i++){
        temp=cons(vB[i], temp);
    }
    for(int i=0; i<3; i++){
        temp=cons(vC[i], temp);
    }
    
    while(!empty(temp)){
        if(!member(head(temp), result))
            result=cons(head(temp), result);
        temp=tail(temp);
    }
    free(vT);
    free(vN);
    free(vB);
    free(vC);
    return result;
}

void premi(Atleta* a, int dimA){
    Atleta temp;
    list atleti=atletiPremiati(a, dimA);
    Atleta* vT;
    Atleta* vN;
    Atleta* vB;
    Atleta* vC;
    int dimT;
    int dimN;
    int dimB;
    int dimC;
    vT=ordinaAtletiPer(a, dimA, "Totale", &dimT);
    vN=ordinaAtletiPer(a, dimA, "Nuoto", &dimN);
    vB=ordinaAtletiPer(a, dimA, "Bici", &dimB);
    vC=ordinaAtletiPer(a, dimA, "Corsa", &dimC);
    float premio;
    
    while(!empty(atleti)){
        temp=head(atleti);
        premio=0;
        for(int i=0; i<5; i++){
            if(strcmp(temp.codiceAtleta, vT[i].codiceAtleta)==0){
                premio=premio+100*(5-i);
            }
        }
        for(int i=0; i<3; i++){
            if(strcmp(temp.codiceAtleta, vN[i].codiceAtleta)==0){
                premio=premio+100*(3-i);
            }
        }
        for(int i=0; i<3; i++){
            if(strcmp(temp.codiceAtleta, vB[i].codiceAtleta)==0){
                premio=premio+100*(3-i);
            }
        }
        for(int i=0; i<3; i++){
            if(strcmp(temp.codiceAtleta, vC[i].codiceAtleta)==0){
                premio=premio+100*(3-i);
            }
        }
        printf("l'atleta %s ha ottenuto un premio di %6.2f\n", temp.codiceAtleta, premio);
        atleti=tail(atleti);
    }
    free(vT);
    free(vN);
    free(vB);
    free(vC);
    freelist(atleti);
}
