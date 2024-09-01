#!/bin/bash

# Lo script deve:
# 1) lanci in parallelo tutti i comandi forniti come parametri
# 2) controlli ogni 5 secondi quali sono ancora in esecuzione, verificando che il PID corrisponda al nome del comando
# 3) scriva a ogni controllo sul file "log" lo stato dei processi
# 4) termini quando tutti i processi in background sono terminati
# 5) garantisca la terminazione di tutti i processi in background se viene terminato dall'esterno il processo parallenne

# -----------------------------------------------------------------------------------------------------------------------------------------------------

# Controllo sui parametri --> deve esserci almeno un comando
if [[ $# < 1 ]] ; then echo "La sintassi deve essere ./parallenne.sh \'nomi_comandi\' " ; exit 1 ; fi

declare -a PIDS # uso un array per mantenere associazione PID-nomeProcesso

# 1) Ogni parametro contiene tutto il comando
for COMANDO in "$@"
do
	$( $COMANDO ) &
	PIDS[$!]=$COMANDO
done

# 5) Garantiamo la terminazione
function garanzia (){
	echo "SIGTERM inviato a ./parallenne.sh :"
	for PID in ${!PIDS[@]}
	do
		if [[ $(ps -a | grep $PID) != "" ]] # prendo solo quelli ancora in esecuzione
			then $( kill $PID ) ; echo "Ho killato $PID --> ${PIDS[$PID]}"
		fi
	done

	echo "Padre ($$) è terminato" >> "log"

	exit 0
}
trap garanzia SIGTERM # --> è il massimo che posso gestire, SIGKILL non è gestibile

# 2) Controllo quali comandi sono ancora in esecuzione
while sleep 5
do
	echo "/---------------------------------------------------------------------------/ $(date +%T)" > "log"	# --> Per avere un'idea del tempo
	for PID in ${!PIDS[@]}
	do
		test -z $(ps -p $PID -o state=) && STATO="Terminato" || STATO=$(ps -p $PID -o state=)
		echo "Lo stato di \"${PIDS[$PID]}\" è $STATO" >> "log"  # --> stampa lo stato come singola lettera R (running) , S (sleeping) , D (disk sleep) , Z (zombie) , T (stopped)
	done

	# 4) Se sono terminati tutti i processi, il padre termina
	if [[ $(cat "log" | grep "Terminato" | wc -l) == ${#PIDS[@]} ]] ; then echo "Sono terminati tutti i miei figli, termina anche il padre" >> "log" ; exit 0 ; fi 
done
