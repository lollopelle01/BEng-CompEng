//
//  main.c
//  es. 3 stringhe
//
//  Created by Lorenzo Pellegrino on 20/11/20.
//

#include <stdio.h>
#include <string.h>

int occorrenze(char a[], char b[]) {
    int result = 0;
    int trovato = 0;
    int i=0, j=0, temp;
    
       while (b[j] != '\0') {
             if (b[j] == a[i]) {
                 trovato = 1;
                 temp = j;
                 while (a[i] != '\0' && trovato) {
                        if (a[i] == b[j]) {
                            i++;
                            j++;
                        
                        }
                        else {
                            trovato = 0;
                    }
                     
                 }
                 if (trovato)
                            result++;
                 j = temp;
                 i=0;
                 
             }
            j++;
           
       }
       return result;
    
}

int main(){
    char A[]={"apelle figlio di apollo fece una palla di pelle di pollo, tutti i pesci vennero a galla per vedere la palla di pelle di pollo fatta da apelle figlio di apollo"};
    char B[]={"pelle"};
    int risultato;
    
    risultato=occorrenze(B, A);
    
    printf("numero di occorrenze: %d\n", risultato);
}
