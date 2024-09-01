//
//  list.c
//  
//
//  Created by Lorenzo Pellegrino on 11/02/21.
//  matricola: 0000971455

#include "list.h"

/* OPERAZIONI PRIMITIVE */
list  emptylist(void)        /* costruttore lista vuota */
{
    return NULL;
}

boolean  empty(list l)    /* verifica se lista vuota */
{
    return (l == NULL);
}

list  cons(element e, list l) 
{
    list t;       /* costruttore che aggiunge in testa alla lista */
    t = (list)malloc(sizeof(item));
    t->value = e;
    t->next = l;
    return(t);
}

element head(list l) /* selettore testa lista */
{
    if (empty(l)) exit(-2);
    else return (l->value);
}

list  tail(list l)         /* selettore coda lista */
{
    if (empty(l)) exit(-1);
    else return (l->next);
}

/*void showlist(list l) {
    element temp;
    if (!empty(l)) {
        temp = head(l);
        printf("%s %c\n", temp.negozio, temp.tipo);
        showlist(tail(l));
        return;
    }
    else {
        printf("fine lista\n\n");
        return;
    }
}
 */
 
 
int member(element el, list l) {
    int result = 0;
    while (!empty(l) && !result) {
        result = (strcmp(el.titolo,head(l).titolo)==0);
        if (!result)
            l = tail(l);
    }
    return result;
}

list copy(list l) {
    if (empty(l))
        return l;
    else
        return cons(head(l), copy(tail(l)));
}

/*list delete(element el, list l) {
    if (empty(l))
        return emptylist();
    else if (el.idC==head(l).idC)
        return copy(tail(l));
    else
        return cons(head(l), delete(el, tail(l)));
}*/
 

void freelist(list l) {
    if (empty(l))
        return;
    else {
        freelist(tail(l));
        free(l);
    }
    return;
}

/*list insord_p(element el, list l) {
    list pprec = NULL, patt = l, paux;
    int trovato = 0;

    while (patt != NULL && !trovato) {
        if (compare2(el, patt->value)>0) 
            trovato = 1;
        else {
            pprec = patt;
            patt = patt->next;
        }
    }
    paux = (list)malloc(sizeof(item));
    paux->value = el;
    paux->next = patt;
    if (patt == l)
        return paux;
    else {
        pprec->next = paux;
        return l;
    }
}*/


int length(list l) {
   if (empty(l))
       return 0;
    else
        return 1 + length(tail(l));
}

