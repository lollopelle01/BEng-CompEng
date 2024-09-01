//
//  main.c
//  problemaBuffon
//
//  Created by Lorenzo Pellegrino on 09/11/21.
//

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
double getRand(double f, double l){
    return f + (rand() / ( RAND_MAX / (l-f) ) ) ;
}

double calcolaPI(double a, double b, double freq){
    return (2*a)/(b*freq);
}

int calcolaIntersezioni(double a, double b, int numEsperimenti){
    int intersezioni=0;
    for(int i=0; i<=numEsperimenti; i++){
        double distanza;
    }
    
    return intersezioni;
}

int main(int argc, const char * argv[]) {
    printf("%lf\n", getRand(0, 1));
    return 0;
}
