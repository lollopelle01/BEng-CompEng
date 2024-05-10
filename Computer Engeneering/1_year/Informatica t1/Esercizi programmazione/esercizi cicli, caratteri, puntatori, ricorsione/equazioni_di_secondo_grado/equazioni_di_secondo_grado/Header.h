//
//  Header.h
//  equazioni_di_secondo_grado
//
//  Created by Lorenzo Pellegrino on 08/11/2020.
//

#ifndef Header_h
#define Header_h
#include <math.h>
void soluzione(float a, float b, float c, float *x1, float *x2)
{
    float delta;
    delta=b*b-4*a*c;
    if(delta>0)
    {
        *x1=(-b+sqrt(delta))/(2*a);
        *x2=(-b-sqrt(delta))/(2*a);
    }
    
    if (delta==0)
    {
        
        *x1=(-b+sqrt(delta))/(2*a);
        *x2=*x1;
    }
    
    if(delta<0)
    {
        printf("non ci sono soluzioni\n");
        return;
    }
}

#endif /* Header_h */
