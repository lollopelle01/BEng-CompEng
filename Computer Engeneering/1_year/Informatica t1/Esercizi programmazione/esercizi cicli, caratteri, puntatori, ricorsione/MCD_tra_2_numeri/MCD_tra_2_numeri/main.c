//
//  main.c
//  MCD_tra_2_numeri
//
//  Created by Lorenzo Pellegrino on 02/11/2020.
//

#include <stdio.h>

int MCD(int m, int n)
{
    if (m==n)
        return m;
    if(m>n)
        return MCD(m-n, n);
    if(m<n)
        return MCD(m, n-m);
}

int main()
{
    int m, n, M;
    printf("scrivi 2 valori di cui vuoi l'MCD: ");
    scanf("%d %d", &m, &n);
    
    M=MCD(m, n);
    
    printf("l'MCD tra %d e %d e': %d", m, n, M);

}
