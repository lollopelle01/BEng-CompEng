#!/bin/bash

#       Scrivere uno script bash che dato il file allegato conti le occorrenze:
#       1) della lettera 'a'
#       2) della parola 'sherlock' case insensitive
#       3) per ogni parola distinta conti le sue occorrenze e mostri a terminale le cinque parole con frequenza maggiore

# TESTI x conteggi
TESTO_PULITO=$( cat The_Adventures_Of_Sherlock_Holmes.txt | sed 's/[^[:alnum:]\n -]//g' )	# -> togliamo tutti i caratteri alfanumerici non necessari 
TESTO_CHAR_BY_CHAR=$( grep -o . <<< $( echo "$TESTO_PULITO" ) )					# -> separo ogni carattere per linea
TESTO_WORD_BY_WORD=$( echo "$TESTO_PULITO" | sed 's/ /\n/g' )					# -> separo ogni parola per linea

funzione_conteggio_occorrenze(){
	# CONTATORI
	COUNT_A=0
	COUNT_SHERLOCK=0
	CLASSIFICA=""
	COUNT_WORD=0

	# CONTEGGI
	for CHAR in $( echo "$TESTO_CHAR_BY_CHAR" )
	do
		if [[ $CHAR == "a" || $CHAR == "A" ]] ; then (( COUNT_A++ )) ; fi
	done

	for WORD in $( echo "$TESTO_PULITO" )
	do
		(( COUNT_WORD++ ))
		if [[ "${WORD,,}" == "sherlock" ]] ; then (( COUNT_SHERLOCK++ )) ; fi
	done

	CLASSIFICA=$( echo "$TESTO_WORD_BY_WORD" | sed '/^$/d' | sort | uniq -c | sort -rn | head -n 5 )

	# STAMPE
	echo "Sono state trovate $COUNT_WORD parole"
	echo "1) Il carattere'a' si è ripetuto $COUNT_A volte"
	echo "2) La parola 'sherlock' si è ripetuta $COUNT_SHERLOCK (case insensitive)"
	echo "3) Le 5 parole che si sono ripetute di più sono:"
		echo "$CLASSIFICA"
}

# ESECUZIONE FUNZIONE
funzione_conteggio_occorrenze
