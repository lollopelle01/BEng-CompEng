#!/bin/bash

# 1)	Lanciare due processi in parallelo che contino le occorrenze della parola 'sherlock' case insensitive, alla fine restituisca il risultato totale sul terminale.

# 2)	Eseguire lo stesso esercizio proposto sopra ma quando uno dei due processi termina prima dell'altro questo deve segnalare prontamente la
# 	cosa al processo che stà ancora lavorando il quale deve gestire l'interruzione interrompendo il conteggio e restituendo il risultato parziale in un file temporaneo scelto in precedenza;
# 	dopo di che deve proseguire il conteggio; lo script deve restituire la somma dei due risultati parziali stampata a terminale

FILE="The_Adventures_Of_Sherlock_Holmes.txt"
COUNT_SHERLOCK=0 # ; export COUNT_SHERLOCK_1
# COUNT_SHERLOCK_2=0 ; export COUNT_SHERLOCK_2

# ES. 1

# primo conteggio
( for PAROLA in $(cat $FILE | sed 's/[^[:alnum:]\n -]//g' | sed 's/ /\n/g')
do
	if [[ "${PAROLA,,}" == "sherlock" ]] ; then (( COUNT_SHERLOCK++ )) ; fi
done ; echo "$$ : ho trovato $COUNT_SHERLOCK occorrenze di 'sherlock'") &

# secondo conteggio
( for PAROLA in $(cat $FILE | sed 's/[^[:alnum:]\n -]//g' | sed 's/ /\n/g')
do
        if [[ "${PAROLA,,}" == "sherlock" ]] ; then (( COUNT_SHERLOCK++ )) ; fi
done ; echo "$$ : ho trovato $COUNT_SHERLOCK occorrenze di 'sherlock'") &

wait ; echo "Il conteggio dei figli è terminato"

exit 0
