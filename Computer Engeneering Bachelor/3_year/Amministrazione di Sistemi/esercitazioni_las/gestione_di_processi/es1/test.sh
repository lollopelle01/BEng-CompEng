#!/bin/bash

# Metto un processo in background e gli faccio fare una sleep 1000
sleep 1000 &

# Salvo in A il PID dell'ultimo processo, ovvero di quello di sleep
A=$!

# Stampo il valore del PID
echo $A

# Recupero informazioni su quel processo
ps $A

# Elenco tutti i job (processi in pausa o in background) e il loro stato
jobs

# Elenca anche gli ID dei job
jobs -l

# Stoppa il processo
kill -STOP $A
ps $A
jobs

# Fa riprendere il processo
kill -CONT $A
ps $A
jobs

# Termina il processo
kill $A
ps $A
jobs
