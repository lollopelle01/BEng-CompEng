//
//  list.h
// 
//
//  Created by Lorenzo Pellegrino on 11/02/21.
//  matricola: 0000971455

#ifndef list_h
#define list_h

#include "element.h"
typedef struct list_element {
    element value; 
    struct list_element  *next;
} item;

typedef  item* list;

typedef enum { falso, vero } boolean;

/* PRIMITIVE  */
list emptylist(void);
boolean empty(list);
list cons(element, list);
element head(list);
list tail(list);

/* NON PRIMITIVE  */
list copy(list l);
list delete(element el, list l);
void showlist(list l);
void freelist(list l);
int member(element el, list l);
list insord_p(element el, list l);
int length(list l);

#endif /* list_h */
