//
//  main.c
//  es. 4 stringhe
//
//  Created by Lorenzo Pellegrino on 20/11/20.
//

#include <stdio.h>
#include <string.h>

void printchar(char stringa[]){
    if(stringa[0] == '\0');
    else{
        printf("%c\n", stringa[0]);
        printchar(&stringa[1]);
    }
}

int main(){
    char V[]={"asdfghjkl"};
    printchar(V);
}
