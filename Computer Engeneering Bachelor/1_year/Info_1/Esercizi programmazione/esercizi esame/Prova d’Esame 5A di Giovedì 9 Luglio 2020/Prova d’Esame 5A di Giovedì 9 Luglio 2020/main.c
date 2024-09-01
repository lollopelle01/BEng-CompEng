//
//  main.c
//  Prova d’Esame 5A di Giovedì 9 Luglio 2020
//
//  Created by Lorenzo Pellegrino on 30/01/21.
//

#include "bar.h"

int main() {
    { // Es. 1
        Ingrediente * elenco; Ingrediente temp; int dim;
        elenco = leggiIng("ingredienti.txt", &dim);
        stampa(elenco, dim);
        temp = trova(elenco, dim, "prosciutto");
        printf("Cercavo prosciutto, ho trovato %s\n", temp.nome);
        temp = trova(elenco, dim, "capperi");
        printf("Cercavo capperi, ho trovato %s\n", temp.nome);
        free(elenco);
    }
    
    { // Es. 2
        Ingrediente * elenco; int dim;
        list panini;
        elenco = leggiIng("ingredienti.txt", &dim);
        ordina(elenco, dim);
        printf("\n\n\nElenco ingredienti ordinato:\n");
        stampa(elenco, dim);
        panini = leggiPanini("panini.txt", elenco, dim);
        printf("\nelenco panini:\n");
        showlist(panini);
        free(elenco);
        freelist(panini);
    }
    
    { // Es. 3 && 4
        Ingrediente * elenco;
        int dim;
        list panini;
        list paniniSenzaRip;
        elenco = leggiIng("ingredienti.txt", &dim);
        panini = leggiPanini("panini.txt", elenco, dim);
        paniniSenzaRip = eliminaDup(panini);
        printf("elenco panini senza ripetizioni:\n");
        showlist(paniniSenzaRip);
        free(elenco);
        freelist(panini);
        freelist(paniniSenzaRip);
    }
}
