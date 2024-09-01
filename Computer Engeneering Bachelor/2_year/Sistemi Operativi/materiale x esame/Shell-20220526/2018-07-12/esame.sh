#!/bin/bash

# esame tree N flat



# Controllo argomenti
if [[ $2 = *[!0-9]* ]] ; then
    echo "$2 non è un intero positivo" 1>&2
    exit 1
fi


if [ -d "$3" ] ; then
	rm -r "$3"
fi
mkdir "$3"

> "$HOME/outfile.txt"

# In questo caso, se avessi utilizzato test oppure [, 
# che sono comandi esterni, non avrei avuto l'espansione dell'*,
# in quanto l'espansione dei metacaratteri avviene dopo l'esecuzione
# di comandi in bash.
if [[ "$0" = /* ]] ; then
    #Iniziando con /, si tratta di un path assoluto
    #(eg /home/andrea/recurse_dir.sh)

    #Estrazione di parti di path: man dirname oppure man basename
    dir_name=`dirname "$0"`
    recursive_command="$dir_name/do_recurse_dir.sh"
elif [[ "$0" = */* ]] ; then
    # C'è uno slash nel comando, ma non inizia con /. Path relativo
    dir_name=`dirname "$0"`
    recursive_command="`pwd`/$dir_name/do_recurse_dir.sh"
else 
    # Non si tratta ne di un path relativo, ne di uno assoluto.
    # E' un path "secco": il comando sarà dunque cercato
    # nelle cartelle indicate dalla variabile d'ambiente $PATH.
    recursive_command=do_recurse_dir.sh
fi

#Invoco il comando ricorsivo
"$recursive_command" "$1" "$2" "$3"

