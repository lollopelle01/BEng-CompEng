//
//  list.c
//  es. 1 liste
//
//  Created by Lorenzo Pellegrino on 17/12/20.
//

#include "list.h"

//FUNZIONI PRIMITIVE//-------------------------------------------------------- - - - - - - - - - - - - -
list emptyList(void){
    return NULL;
}

int empty(list l){
    if (l==NULL)
        return 1;
    else
        return 0;
}

int head(list l){
    if (empty(l))
        exit(1);
    else
        return  l->value;
}
list tail(list l){
    if (empty(l))
        exit(1);
    else
        return l->next;
}

list cons(int e, list l){
    list t;
    t = (list) malloc(sizeof(item));
    t->value=e;
    t->next=l;
    return t;
    
}

//FUNZIONI NON PRIMITIVE//-------------------------------------------------------- - - - - - - - - - - -
void showList(list l){
    printf("[");
    while (!empty(l)){
             printf("%d", head(l));
             l = tail(l);
        if (!empty(l))
            printf(", ");
    }
    printf("]\n");
}
             
int member(int el, list l){
    if (empty(l))
        return 0;
    else if (el == head(l))
        return 1;
    else
        return member(el, tail(l));
}

int lenghtlist(list l){
    if (empty(l))
        return 0;
    else
        return 1+lenghtlist(tail(l));
}

list append(list l1, list l2){
    if (empty(l1))
        return l2;
    else
        return cons(head(l1), append(tail(l1),l2));
}

list reverse(list l){
    if (empty(l))
        return emptyList();
    else
        return append(reverse(tail(l)),cons(head(l), emptyList()));
}

list copy(list l) {
    if (empty(l))
        return l;
    else
        return cons(head(l), copy(tail(l)));
}

list delete(int el, list l){
    if (empty(l))
        return emptyList();
    else if (el == head(l))
        return copy(tail(l)); //senza copy per non avere condivisione
    else return cons(head(l), delete(el, tail(l)));
}

void freelist(list l) {
    if (empty(l))
        return;
    else {
        freelist(tail(l));
        free(l);
    }
    return;
}

list insord_p(int el, list l) {
    list pprec = NULL, patt = l, paux;
    int trovato = 0;

    while (patt != NULL && !trovato) {
        if (el.costo > patt->value.costo)
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
}
