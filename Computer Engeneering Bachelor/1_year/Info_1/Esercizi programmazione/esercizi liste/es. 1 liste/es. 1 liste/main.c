//
//  main.c
//  es. 1 liste
//
//  Created by Lorenzo Pellegrino on 17/12/20.
//

#include <stdio.h>
#include <stdlib.h>
#include "list.h"

int main(){
    FILE * fp;
    list startList, lowList, highList, temp;
    int voto, soglia;
   
    if ( (fp=fopen("voti.txt", "r")) == NULL){
        perror("The file does not exist!");
        exit(1);
    }
    startList=emptyList();
    lowList=emptyList();
    highList=emptyList();
    
    while(fscanf(fp, "%d", &voto)>0){
        startList=cons(voto, startList);
    }
    fclose(fp);
    showList(startList);
    printf("inserire soglia: ");
    scanf("%d", &soglia);
    while(startList!=NULL){
        if(startList->value<soglia){
            temp=(item*)malloc(sizeof(item));
            temp->value=startList->value;
            temp->next=lowList;
            temp=lowList;
        }
        else{
            temp=(item*)malloc(sizeof(item));
            temp->value=startList->value;
            temp->next=highList;
            temp=highList;
        }
        startList=startList->next;
    }
    
    while(!empty(startList)){
        int value=head(startList);
        if(value<soglia)
            lowList=cons(value, lowList);
        else
            highList=cons(value, highList);
    }
    
    showList(lowList);
    showList(lowList);
    
    while(!empty(lowList)){
        temp=lowList;
        lowList=tail(lowList);
        free(temp);
    }
    
}
