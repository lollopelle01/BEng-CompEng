//
//  main.c
//  integrale
//
//  Created by Lorenzo Pellegrino on 03/11/2020.
//

//con iterazione
#include <stdio.h>
#include <math.h>
#include <stdlib.h>

 double f(double x)
 {
 return x*x;
 }

 double rettangoli(double a, double b, double N)
 {
     double a1, B, A=0, Atot=0, intervallo;
     int i;
     intervallo = b-a;
     B = intervallo/N;
     for(i=1; i<=N; i++ )
     {
         a1=B*(i-1);
         A = B * f(a1);
         Atot = Atot + A;
     }
     return Atot;
 }

 int main()
 {
     double a, b, integrale=0, N;
     printf("scrivi un intervallo [a, b] e il numero di basi: ");
     scanf("%lf %lf %lf", &a, &b, &N);
     if (a < b)
         integrale = rettangoli(a, b, N);
        printf("il risultato dell'integrale e': %lf\n", integrale);

     if (a == b)
         integrale = 0;
        printf("il risultato dell'integrale e': %lf\n", integrale);

     if (a > b)
         integrale = -rettangoli(a, b, N);
        printf("il risultato dell'integrale e': %lf\n", integrale);

     
    

     system("pause");
     return 0;
     
 }

/*//con ricorsione
#include <stdio.h>
#include <math.h>
#include <stdlib.h>

double f(double x) {
    return x*x;
}

double rettangoli(double a, double b, int N) {
    double Area=0, base_ret= (b - a) / (double) N;
    int i;
    for (i = 0; i < N; i++) {
        Area += base_ret*f(a+i*base_ret);
    }
    return Area;
}

double rettangoliRic(double a, double b, int N) {
    double delta = (b - a) / N;
    if (N == 1)
        return f(delta);
    else
        return f(a)*(delta) + rettangoliRic(a+delta, b, N - 1);

}

int main()
{
    double a, b, integrale=0, N;
    printf("scrivi un intervallo [a, b] e il numero di basi: ");
    scanf("%lf %lf %lf", &a, &b, &N);
    if (a < b)
        integrale = rettangoli(a, b, N);
       printf("il risultato dell'integrale e': %lf\n", integrale);

    if (a == b)
        integrale = 0;
       printf("il risultato dell'integrale e': %lf\n", integrale);

    if (a > b)
        integrale = -rettangoli(a, b, N);
       printf("il risultato dell'integrale e': %lf\n", integrale);

    
   

    system("pause");
    return 0;
    
}*/

