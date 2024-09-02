//
//  main.c
//  es. 4 strutture
//
//  Created by Lorenzo Pellegrino on 28/11/20.
//

#include <stdio.h>
#include <stdlib.h>
#define DIM 300

struct time{
    int hour, minute, second;
};

typedef struct time  Time;

Time leggitime(){
    Time result;
    
    printf("scrivi due minutaggi di cui verrà restituita la differenza:  \n");
    
    printf("inserisci l'ora:");
    scanf("%d", &result.hour);
    printf("inserisci i minuti:");
    scanf("%d", &result.minute);
    printf("inserisci i secondi:");
    scanf("%d", &result.second);
    
    printf("\n");
    
    return result;
}

int leggimoreTimes(Time v[], int dim){
    int i=0;
    Time struttura;
    
    do{
        struttura = leggitime();
        if(struttura.hour>=0 && i<dim){
            v[i]=struttura;
            i++;
        }
    }
    while(struttura.hour>=0 && i<dim);
    return i;
}

Time subtract(Time t1, Time t2){
    Time result;
    int s1, s2, st=0, resto=0;
    
    s1= (t1.second)+60*(t1.minute)+3600*(t1.hour);
    s2= (t2.second)+60*(t2.minute)+3600*(t2.hour);
    
    if(s1>s2)
        st=s1-s2;
    if(s2>s1)
        st=s2-s1;
    
    result.hour=st/3600;
    resto=st%3600;
    
    result.minute=resto/60;
    resto=resto%60;
    
    result.second=resto;

    return result;
}

int main(){
    Time lista[DIM], result;
    int dimL;
    dimL=leggimoreTimes(lista, DIM);
    
    for(int i=0; i<dimL; i++){
        if(i+1<dimL){
        result=subtract(lista[i], lista[i+1]);
        printf("la %d differenza e' %d : %d : %d\n", i+1, result.hour, result.minute, result.second);
        }
    }
}
