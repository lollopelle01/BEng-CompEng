#!/bin/bash

# Realizzare una funzione waitfile che accetti

# un primo parametro obbligatorio = nome di comando
# un secondo parametro obbligatorio = nome di file
# un terzo parametro facoltativo

# La funzione deve
# controllare che il valore di $1 sia uno di ls, rm, touch
# eseguire il comando $1 con parametro $2 (basta lanciare $1 $2) in modi diversi a seconda di $3 come spiegato di seguito

# Usando case, discriminare tre possibilità:
# $3 è "force" --> esecuzione immediata
# $3 è un numero "N" di una cifra decimale --> se $2 non esiste, aspetta che eventualmente compaia, riprovando al massimo N volte con un'attesa di 1 secondo tra un tentativo e il successivo (usare sleep 1)
# $3 è assente o altro valore --> come caso precedente, considerando un valore di default N=10
# La funzione deve ritornare exit code significativi e messaggi d'errore dove opportuno.

function waitfile(){
	# Controlliamo il numero di parametri
	if [[ $# < 2 ]] ; then echo "La sintassi deve essere 'waitfile nome_comando nome_file'" ; exit 1 ; fi

	# Controllo sul primo parametro
	if [[ ( "$1" != "rm" ) && ( "$1" != "ls" ) && ( "$1" != "touch" ) ]] ; then echo "Il comando deve essere uno tra rm, ls, touch" ; exit 2 ; fi
	COMANDO="$1"

	# Secondo parametro
	FILE="$2"

	# Controllo sul terzo parametro
	if [[ "$3" == 'force' ]] ; then $COMANDO $FILE ; exit 0 ; fi 	# Sarà il comando a gestire gli errori
	if [[ "$3" =~ ^.*[0-9]+$ ]] ; then N="$3" ; else N=10 ; fi		# Gestisco subito N che darò in pasto allo stesso algoritmo

	# Facciamo gli ultimi due casi insieme
	if [[ ! -f $FILE ]]
		then	COUNT=$N
			while $(sleep 1)
			do
				if [[ -f $FILE ]] ; then $( $COMANDO $FILE ) ; exit 0 ; fi
				if [[ ( ! -f $FILE ) && $COUNT == 1 ]] ; then echo "Sono passati $N secondi e il file non è stato ancora creato" ; exit 3 ; fi
				(( COUNT-- ))
			done
	else $( $COMANDO $FILE ) ; exit 0
	fi
}

waitfile $@
