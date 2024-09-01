#!/bin/bash

# si realizzi uno script che lancia i comandi sleep 10  e sleep 20 in parallelo,
# e che ogni 5 secondi scriva su STDOUT il loro stato (in esecuzione / terminato)

# ----------------------------------------------------------------------------------------------------------------------------------------------------------------

# Lanciamo i comandi in parallelo
sleep 10 &
PID_1=$!

sleep 20 &
PID_2=$!

# Verifichiamo il loro stato gni 5 sec
while sleep 5
do
	STATO_1="$(ps hp $PID_1)"
	STATO_2="$(ps hp $PID_2)"
        if [[ ( "$STATO_1" == "" ) && ( "$STATO_2" == "" ) ]] ; then break ; fi


	echo "--------------------------------------------------- --> $(date +%T)"
	echo $STATO_1 -- $( ( test -z "$STATO_1" ) && echo $PID_1 terminato || echo 'in sesecuzione' )
	echo $STATO_2 -- $( ( test -z "$STATO_2" ) && echo $PID_2 terminato || echo 'in sesecuzione' )
done
