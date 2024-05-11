#!/bin/bash

# implementare manualmente l'esplorazione ricorsiva di una cartella (equivalente all'opzione -R di ls)

CARTELLA_RADICE="$1"

# Prima versione con esplorazione delle cartelle e contemporaneamente stampa dei file
function esplora_cartella(){
	# Controlliamo il numero di argomenti
	if [ "$#" -ne 1 ] ; then echo  "Solo 1 parametro!" ; exit 1 ; fi

	# La cartella ci viene fornita da input -> trasformiamo in path assoluto
	FOLDER="$1"

	# Ci spostiamo nella cartella fornita
	cd "$FOLDER"

	# Andiamo a prendere tutti i file nella cartella
	LISTA_FILE_FOLDER=$(ls --color=auto .)

	# Stampiamo i file per la cartella
	echo "$CARTELLA_RADICE$(echo $PWD | sed "s/.*$CARTELLA_RADICE//g")"
	echo $( echo $LISTA_FILE_FOLDER | sed 's/\n/ /g' )
	echo ""

	# Differenziamo quello che facciamo in base al tipo di file
	for FILE in $LISTA_FILE_FOLDER
	do
		# Se è cartella allora invochiamo ricorsivamente il comando e poi torno indietro per analizzare altre cartelle
		if [[ -d "$FILE" ]] ; then esplora_cartella "$FILE" ; cd .. ; fi

	done

}

# Seconda verione con costruzione dei path e poi ls su tutte le cartelle
function ls_ricorsivo(){
        if [ "$#" -ne 1 ] ; then echo  "Solo 1 parametro!" ; exit 1 ; fi

	# Salviamo il contenuto della cartella
	CONTENUTO_CARTELLA="$(ls $1)"

	# Stampo per ogni cartella
	echo "$1:"
	echo $(ls "$1")
	echo ""

	# Invocazione ricorsiva del comando
	for FILE in $(ls "$1")
	do
		if [[ -d "$1/$FILE" ]] ; then ls_ricorsivo "$1/$FILE" ; fi
	done
}


## SCEGLIERE QUALE FUNZIONE USARE
# esplora_cartella "$CARTELLA_RADICE"
ls_ricorsivo "$CARTELLA_RADICE"
