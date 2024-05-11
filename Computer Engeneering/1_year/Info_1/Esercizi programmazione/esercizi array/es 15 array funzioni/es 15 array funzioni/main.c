//
//  main.c
//  es 15 array funzioni
//
//  Created by Lorenzo Pellegrino on 15/11/2020.
//

#include <stdio.h>
#define VERO 1
#define FALSO 0
#define DIM1 10 //cambiabile
#define DIM2 5  //cambiabile
int test_uguaglianza(int v1[], int v2[], int dimL1, int dimL2)
{
    int k=0;
    if(dimL1!=dimL2)
        return FALSO;
    else
        for(int i=0; i<dimL1; i++){
            if(&(v1[i])==&(v2[i]))
                k++;
        }
    if(k==dimL1)
        return VERO;
    else
        return FALSO;
}

int leggi(int v[], int dimF)
{
    int i=0, num;
    do{
        printf("inserire il valore di v[%d] : ", i);
        scanf("%d", &num);
        if(i<dimF){
            v[i]=num;
            i++;
        }
    }
    while(i<dimF);
    return i;
}

int main()
{
    int v1[DIM1], v2[DIM2], dimL1, dimL2, risposta;
    dimL1=leggi(v1, DIM1);
    dimL2=leggi(v2, DIM2);
    risposta=test_uguaglianza(v1, v2, dimL1, dimL2);
    if(risposta==VERO)
        printf("i due vettori sono uguali\n");
    else
        printf("i due vettori sono diversi\n");
    
}

/* VARIANTE ESERCIZIO 16
 
#include <stdio.h>
#define VERO 1
#define FALSO 0
#define DIM1 10 //cambiabile
#define DIM2 5  //cambiabile
int test_uguaglianza(int v1[], int v2[], int dimL1, int dimL2)
{
    int k=0;
    if(dimL1!=dimL2)
        return FALSO;
    else
        for(int i=0; i<dimL1; i++){
            if(&(v1[i])==&(v2[i]))
                k++;
        }
    if(k==dimL1)
        return VERO;
    else
        return FALSO;
}

int leggi(int v[], int dimF)
{
    int i=0, num;
    do{
        printf("inserire il valore di v[%d] : ", i);
        scanf("%d", &num);
        if(i<dimF){
            v[i]=num;
            i++;
        }
    }
    while(i<dimF);
    return i;
}

int main()
{
    int v1[DIM1], v2[DIM2], dimL1, dimL2, risposta;
    dimL1=leggi(v1, DIM1);
    dimL2=leggi(v2, DIM2);
    risposta=test_uguaglianza(v1, v2, dimL1, dimL2);
    if(risposta==VERO)
        printf("i due vettori sono uguali\n");
    else
        printf("i due vettori sono diversi\n");
    
}
 */


