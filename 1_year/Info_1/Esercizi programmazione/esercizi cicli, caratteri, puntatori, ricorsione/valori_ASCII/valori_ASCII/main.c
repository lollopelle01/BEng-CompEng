//
//  main.c
//  valori_ASCII
//
//  Created by Lorenzo Pellegrino on 27/10/2020.
//

#include <stdio.h>

int main()
{
    
    char h;
    do
    {
        printf("inserisci un carattere e ti dirò il suo valore in ASCII:\n");
        scanf("*%c", &h);
        int n=(char)h;
        printf("il valore di *%c in ASCII e': %d\n", n, n);
    }
    while(h!='\0');
    return 0;
}
