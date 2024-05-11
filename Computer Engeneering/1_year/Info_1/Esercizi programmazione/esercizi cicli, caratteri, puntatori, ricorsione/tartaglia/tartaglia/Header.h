//
//  Header.h
//  tartaglia
//
//  Created by Lorenzo Pellegrino on 06/11/2020.
//

#ifndef Header_h
#define Header_h
int fattoriale(int n)
{
    if (n==0 || n==1)
        return 1;
    else
        return n*fattoriale(n-1);
}

int coefficiente_binomiale(int n, int k)
{
        return (fattoriale(n))/(fattoriale(k)*fattoriale(n-k));
}


#endif
