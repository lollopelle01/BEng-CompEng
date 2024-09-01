#!/bin/bash

# VERSIONE ORIGINALE
# report () {
# 	echo $(date) osservate $TOT nuove righe
# 	TOT=0
# }
# echo $BASHPID > /tmp/logwatch.pid
# TOT=0
# trap report USR1
# tail -n +0 -f "$1" | while read R ; do
# 	TOT=$(( $TOT + 1 ))
# done ;

# -- Cosa dovrebbe fare questo script? ---------------------------------------------------------------------------------------------
# Lo script stampa il PID del processo corrente su '/tmp/logwatch.pid'.
# Inizializza il numero di righe lette a 0 e poi setta report() come handler per il segnale USR1.
# Stampa 'live' le righe del file passato come primo parametro, lo legge e autenta TOT

# -- Perchè non funziona e come si risolve? ----------------------------------------------------------------------------------------
# Non legge correttamente il numero di righe, rimanendo sempre a 0.
# Questo è dovuto al fatto che la pipe crea una subshell e quindi il valore di TOT si divide in quel momento tar figlio e padre.
# Per risolverlo dobbiamo sincronizzare il valore TOT tra padre e figlio

# VERSIONE MODIFICATA --> usiamo un file per far comunicare padre e figli sul valore di TOT
FILE_SYNC=$(mktemp)

function report () {
	TOT=$(cat $FILE_SYNC)
        echo $(date) osservate $TOT nuove righe
        echo 0 > $FILE_SYNC
}

echo 0 > $FILE_SYNC
echo $BASHPID > /tmp/logwatch.pid
trap report USR1
echo "Per vedere quante righe nuove sono state lette, eseguire: kill -USR1 $$"

( tail -n +0 -f "$1"  | while read R ; do
        TOT=$(( $(cat $FILE_SYNC) + 1 ))
	echo $TOT > $FILE_SYNC
done ) &

while true; do # --> così il padre non termina
    sleep 1
done
