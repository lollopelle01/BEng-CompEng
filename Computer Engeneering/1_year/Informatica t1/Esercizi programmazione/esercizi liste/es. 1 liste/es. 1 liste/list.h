//
//  list.h
//  es. 1 liste
//
//  Created by Lorenzo Pellegrino on 17/12/20.
//

#ifndef list_h
#define list_h

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct list_element{
    int value;
    struct list_element *next;
} item;

typedef item *list;

list emptyList(void);
int empty(list);
int head(list);
list tail(list);
list cons(int, list);
void showList(list);
int member(int, list);
int lenghtlist(list l);
list append(list l1, list l2);
list reverse(list l);
list copy(list l);
list delete(int el, list l);
void freelist(list l);
list insord_p(int el, list l);

#endif /* list_h */
