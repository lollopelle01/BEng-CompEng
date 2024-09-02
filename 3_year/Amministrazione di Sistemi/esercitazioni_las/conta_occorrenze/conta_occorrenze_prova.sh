#!/bin/bash

#	Scrivere uno script bash che dato il file allegato conti le occorrenze:
#	1) della lettera 'a'
#	2) della parola 'sherlock' case insensitive
#	3) per ogni parola distinta conti le sue occorrenze e mostri a terminale le cinque parole con frequenza maggiore

funzione_conteggio_occorrenze(){

	# TESTI
	#TESTO_LETTERA=$()
	#TESTO_PAROLA=$()
	echo "Sono state trovate $# parole"

	# CONTATORI
	COUNT_A=0
	COUNT_SHERLOCK=0
	declare -A tabella
	declare -A listaParole
	COUNT_LOADING=0

	# CONTEGGIO
	for PAROLA in $@ # ciclo di pulizia delle parole e per 1) e 2) e per inizializzare 3)
	do
		# Controllo se PAROLA è una parola --> contiene almeno una carattere alfanumerico
		if [[ "$PAROLA" =~ ^.*[a-zA-Z0-9]+.*$ ]]
		then

			# Pulizia di PAROLA
			PAROLA_MODIFICATA=${PAROLA//[^[:alnum:]\'\-]/} # --> sostituiamo col vuoto i caratteri non alfanumerici tranne apostrofo e -
			#PAROLA_MODIFICATA=`echo $PAROLA | tr -d .,\"`
			#echo "$PAROLA --> $PAROLA_MODIFICATA"

			# 1) e 2)
			if [[ "$PAROLA_MODIFICATA" == "a" ]] ; then (( COUNT_A++ )) ; fi
                	if [[ "${PAROLA_MODIFICATA,,}" == "sherlock" ]] ; then (( COUNT_SHERLOCK++ )) ; fi

			# Inizializzo 3)
			tabella["$PAROLA_MODIFICATA"]=0  # inizializzo tabella per dopo --> NON È NECESSARIO , VIENE FATTO DI DEFAULT !!!!!!!!!!!!!!!!!!!!!

			# Salvo parole 'pulite' per riempimento di 3)
			(( COUNT_LOADING++ ))
			listaParole["$COUNT_LOADING"]="$PAROLA_MODIFICATA"
		fi
	done

	COUNT_LOADING=0

	for A in ${listaParole[*]}
	do
		tabella["$A"]=$(( ${tabella["$A"]} + 1 ))  # compilo tabella
                (( COUNT_LOADING++ ))  ; # echo "$COUNT_LOADING / ${#listaParole[*]} "
	done

	# STAMPA
	echo "1) Occorrenze di 'a': $COUNT_A"
	echo "2) Occorrenze case insensitive di 'sherlock': $COUNT_SHERLOCK"
	echo "3) Le 5 parole con più occorrenze sono:"

		FILE_TEMP="top5.txt"
		`( echo ${tabella[@]} | sed 's/ /\n/g' | sort -nr | head -n 5 ) > $FILE_TEMP`
		A=`sed -n '1p' $FILE_TEMP`
		B=`sed -n '2p' $FILE_TEMP`
		C=`sed -n '3p' $FILE_TEMP`
		D=`sed -n '4p' $FILE_TEMP`
		E=`sed -n '5p' $FILE_TEMP`
		`rm $FILE_TEMP`

		for WORD in ${!tabella[@]}
		do
			if [[ ${tabella[$WORD]} == $A ]] ; then PRIMO=" -> 1) $WORD con ${tabella[$WORD]}" ; fi
			if [[ ${tabella[$WORD]} == $B ]] ; then SECONDO=" -> 2) $WORD con ${tabella[$WORD]}" ; fi
			if [[ ${tabella[$WORD]} == $C ]] ; then TERZO=" -> 3) $WORD con ${tabella[$WORD]}" ; fi
			if [[ ${tabella[$WORD]} == $D ]] ; then QUARTO=" -> 4) $WORD con ${tabella[$WORD]}" ; fi
			if [[ ${tabella[$WORD]} == $E ]] ; then QUINTO=" -> 5) $WORD con ${tabella[$WORD]}" ; fi
		done

		echo "$PRIMO"
		echo "$SECONDO"
		echo "$TERZO"
		echo "$QUARTO"
		echo "$QUINTO"
}

funzione_conteggio_occorrenze $( cat The_Adventures_Of_Sherlock_Holmes.txt )
