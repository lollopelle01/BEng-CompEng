#!/bin/bash

# 1)    Lanciare due processi in parallelo che contino le occorrenze della parola 'sherlock' case insensitive, alla fine restituisca il risultato totale sul terminale.

# 2)    Eseguire lo stesso esercizio proposto sopra ma quando uno dei due processi termina prima dell'altro questo deve segnalare prontamente la
#       cosa al processo che stà ancora lavorando il quale deve gestire l'interruzione interrompendo il conteggio e restituendo il risultato parziale in un file temporaneo scelto in precedenza;
#       dopo di che deve proseguire il conteggio; lo script deve restituire la somma dei due risultati parziali stampata a terminale

exec 2>/dev/null # Eliminiamo i warning (fare domanda sulla loro origine)

FILE="The_Adventures_Of_Sherlock_Holmes.txt"

FILE_SYNC_1=$(mktemp) ; echo "0" > $FILE_SYNC_1
FILE_SYNC_2=$(mktemp) ; echo "0" > $FILE_SYNC_2

declare -a PIDS

function figlio_terminato () {
	echo "Il padre ($$) ha ricevuto la terminazione di uno dei figli"
	for PID in "${!PIDS[@]}"
	do
		echo "Il processo ($PID) ne ha contati: $(cat ${PIDS[$PID]})"
		test -n "$(ps -p $PID)" && kill $PID
	done 	#--> se termina uno allora termina anche l'altro

	RISULTATO=$(( $(cat $FILE_SYNC_1) + $(cat $FILE_SYNC_2) ))
	echo "La somma parziale dei due conteggi è $RISULTATO"
}
trap figlio_terminato SIGCHLD #--> il figlio che termina lancerà SIGUSR1

# ES. 2

# primo conteggio ------------
( for PAROLA in $(cat $FILE | sed 's/[^[:alnum:]\n -]//g' | sed 's/ /\n/g')
do
        if [[ "${PAROLA,,}" == "sherlock" ]] ; then echo "$(( $(cat $FILE_SYNC_1) + 1 ))" > $FILE_SYNC_1 ; fi
done ) &
PIDS[$!]=$FILE_SYNC_1

# secondo conteggio -----------------
( for PAROLA in $(cat $FILE | sed 's/[^[:alnum:]\n -]//g' | sed 's/ /\n/g')
do
	echo "Perdiamo un po' di tempo" > /dev/null # Per rallentare -> vedo meglio la differenza di conteggio
        if [[ "${PAROLA,,}" == "sherlock" ]] ; then  echo "$(( $(cat $FILE_SYNC_2) + 1 ))" > $FILE_SYNC_2 ; fi
done ) &
PIDS[$!]=$FILE_SYNC_2

# -------------------
wait ; echo "Il conteggio dei figli è terminato"

