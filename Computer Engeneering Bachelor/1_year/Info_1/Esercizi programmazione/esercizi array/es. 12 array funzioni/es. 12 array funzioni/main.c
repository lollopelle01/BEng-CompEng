//
//  main.c
//  es. 12 array funzioni
//
//  Created by Lorenzo Pellegrino on 18/11/20.
//

#include <stdio.h>
#define DIM 9

void print(int list[], int lenght) {
    if (lenght>0) {
    printf("%d\n", list[lenght-1]);
    print(&(list[0]), lenght - 1);
    }
}


int main() {
int v[DIM] = { 1,2,3,4,5,6,7,8,9};
print(v, DIM);
}
