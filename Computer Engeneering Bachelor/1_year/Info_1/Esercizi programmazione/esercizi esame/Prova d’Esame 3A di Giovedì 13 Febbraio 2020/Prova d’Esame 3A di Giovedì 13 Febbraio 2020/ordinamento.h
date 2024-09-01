//
//  ordinamento.h
//  Prova d’Esame 1A di Giovedì 9 Gennaio 2020
//
//  Created by Lorenzo Pellegrino on 27/01/21.
//

#ifndef ordinamento_h
#define ordinamento_h

#include <stdio.h>
/*__________________________________________________________________________________________________________
NAIVE SORT*/
void scambia(int *a, int *b);
int trovaPosMax(int v[], int n);
void naiveSort(int v[], int n);

/*__________________________________________________________________________________________________________
BUBBLE SORT*/
void bubbleSort(int v[], int n);

/*__________________________________________________________________________________________________________
INSERT SORT*/
void insOrd(int v[], int pos);
void insertSort(int v[], int n);

/*__________________________________________________________________________________________________________
MERGE SORT*/
void  merge(int v[], int i1, int i2, int fine, int vout[]);
void mergeSort(int v[], int first, int last, int vout[]);

/*__________________________________________________________________________________________________________
QUICK SORT*/
void quickSortR(int a[], int iniz, int fine);
void quickSort(int a[], int dim);

/*__________________________________________________________________________________________________________*/


#endif /* ordinamento_h */
