//
//  main.c
//  min_sec_ore
//
//  Created by Lorenzo Pellegrino on 28/10/2020.
//

#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int min_to_sec (int a)
{
    int s;
    s=60*a;
    return s;
}

int ore_to_sec (int a)
{
    int h;
    h=a*3600;
    return h;
}

int main()
{
    int sm, sh, m, h, s;
    printf("scrivi il minutaggio di un video: ");
    scanf("%d %d %d", &h, &m, &s);
    sm=min_to_sec(m);
    sh=ore_to_sec(h);
    int st=sm+sh+s;
    printf("il valore totale di secondi e': %d ", st);
   
}

