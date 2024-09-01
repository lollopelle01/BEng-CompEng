//
//  negozio.c
//  Prova d’Esame 1A di Giovedì 9 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 27/01/21.
//

#include "negozio.h"

//Es.1
Set leggiUnSet(FILE * fp){
    Set temp;
    if(fscanf(fp, "%d %s %c %d %f", &temp.identificativo, temp.contenuto, &temp.tipo, &temp.disponibili, &temp.costo)==5){
        for(int i=0; i<strlen(temp.contenuto); i++){
            if(temp.contenuto[i]=='_')
                temp.contenuto[i]=' ';
        }
    }
    else{
        temp.identificativo=-100;
    }
    return temp;
}

list leggiTuttiSet(char * fileName){
    FILE *fp;
    list result;
    Set temp;
    result=emptylist();
    
    fp=fopen(fileName, "rt");
    if(fp==NULL){
        printf("errore di lettura di %s\n", fileName);
    }
    else{
        temp=leggiUnSet(fp);
        while (temp.identificativo != -1000) {
            result = cons(temp, result);
            temp = leggiUnSet(fp);
        }
        fclose(fp);
    }
    return result;
}

//Es.2
Complex leggiUnComplex(FILE * fp){
    Complex temp;
    int i=0;
    if(fscanf(fp, "%d", &temp.identificativo)==1){
        temp.dimL=0;
        while(fscanf(fp, "%d ", &i)==1 && i!=-1000){
            temp.composizione[temp.dimL]=i;
            temp.dimL++;
        }
    }
    else{
        temp.identificativo=-1000;
    }
    return temp;
}

int confronta(int i1, int i2){
    return i2-i1;
}

void scambia(int *a, int *b)
{
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

void quickSortR(int a[], int iniz, int fine)
{
    int i, j, iPivot, pivot;
    if (iniz < fine)
    {
        i = iniz;
        j = fine;
        iPivot = fine;
        pivot = a[iPivot];
        do  /* trova la posizione del pivot */
        {
            while (i < j && confronta(a[i], pivot)<=0)
                i++;
            while (j > i && confronta(a[j], pivot)>=0)
                j--;
            if (i < j)
        scambia(&a[i], &a[j]);
    }
    while (i < j);

         /* determinati i due sottoinsiemi */
        /* posiziona il pivot */

        if (i != iPivot && a[i] != a[iPivot])
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

void quickSort(int a[], int dim){
    quickSortR(a, 0, dim - 1);
}

Complex trovaComplex(char * fileName, int target){
    FILE *fp;
    fp=fopen(fileName, "rt");
    int trovato=0;
    Complex result;
    if(fp==NULL){
        printf("errore di lettura di %s\n", fileName);
        result.identificativo=-1000;
    }
    else{
        result=leggiUnComplex(fp);
        while((result.identificativo!=-1000) && (trovato=0)){
            if(result.identificativo==target){
                quickSort(result.composizione, result.dimL);
                trovato=1;
            }
            result=leggiUnComplex(fp);
            
        }
        fclose(fp);
        if(trovato==0)
            result.identificativo=-1000;
    }
    return result;
}

//Es.3
int check(list elenco, int target, Set * theSet){
    int trovato=0;
    while(!empty(elenco) && (trovato==0)){
        if(head(elenco).identificativo==target){
            *theSet=head(elenco);
            trovato=1;
        }
        elenco=tail(elenco);
    }
    return trovato;
}

int disp(list elenco, int target, char * fileName){
    Complex e1;
    Set e2;
    e1=trovaComplex(fileName, target);
    int found=0;
    
    if(check(elenco, target, &e2)==0)
        return 0;
    else{
        if(e2.disponibili>0)
            return 1;
        else{
            if(e2.tipo=='S')
                return 0;
            else{
                for(int i=0; i<e1.dimL; i++){
                    found=found+check(elenco, e1.composizione[i], &e2);
                }
                if(found==e1.dimL)
                    return 1;
                else
                    return 0;
            }
        }
    }
}
