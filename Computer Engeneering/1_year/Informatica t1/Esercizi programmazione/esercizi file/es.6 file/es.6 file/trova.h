//
//  trova.h
//  es.6 file
//
//  Created by Lorenzo Pellegrino on 06/12/20.
//

#ifndef trova_h
#define trova_h

#include <stdio.h>
#include "azioni.h"

Azione trovaMin(Azione src[], int dim, float* val);
Azione trovaMax(Azione src[], int dim, float* val);
float media(Azione lista[], int dim);

#endif /* trova_h */
