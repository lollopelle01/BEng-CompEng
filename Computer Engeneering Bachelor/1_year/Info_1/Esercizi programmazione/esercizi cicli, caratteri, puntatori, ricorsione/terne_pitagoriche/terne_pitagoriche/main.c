//
//  main.c
//  terne_pitagoriche
//
//  Created by Lorenzo Pellegrino on 29/10/2020.
//
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
int mioMin(int a, int b)
{
       if (a<b) return a;
       else return b;
}
int isTerna(int a, int b, int c)
{
       int cateto1, cateto2, ipotenusa;
       int prod;

cateto1 = mioMin(a,b);
cateto2 = mioMin(a+b-cateto1, c);
ipotenusa = a+b+c-cateto1-cateto2;
prod = cateto1*cateto1+cateto2*cateto2;

if (prod == ipotenusa*ipotenusa)
       return 1;
else
return 0;
}

int main()
{
    int a, b, c, i, j, k, N;
    printf("inserisci un numero limite: ");
    scanf("%d", &N);
    for(i=1; i<=N; i++)
    {
        for(j=1; j<=i; j++)
        {
            for(k=1; k<=j; k++)
            {
                if (isTerna(i, j, k)==1)
                    printf("la terna e': (%d %d %d)\n", j, i, k);
            }
        }
        
    }
}
