#!/bin/bash

#Controllo argomenti
if [[ $# -ne 3 ]]; then
    echo "Errore: numero di argomenti non corretto" 1>&2
    echo -e "Usage:\n\t$0 dir minD maxD" 1>&2
    exit 1
fi

if [[ $2 = *[!0-9]* || $3 = *[!0-9]* ]] ; then
    echo "$2 e $3 devono essere interi positivi" 1>&2
    exit 1
fi


if ! [[ -d "$1" ]]; then
    echo "Errore: $dir non esiste o non è una cartella" 1>&2
    exit 1
fi

#creao un figlio per eseguire top:
top -b > processi.out &
pidP1=`ps| grep top| cut -d' ' -f2`
echo "pidP1 = $pidP1"

case "$0" in
    /*) 
    dir_name=`dirname $0`
    recursive_command="$dir_name"/rec_search.sh
    ;;
    */*)
    dir_name=`dirname $0`
    recursive_command="`pwd`/$dir_name/rec_search.sh"
    ;;
    *)
    recursive_command=rec_search.sh
    ;;
esac

echo "Launching recursion for $1"
"$recursive_command" "$1" $2 $3



kill -9 $pidP1

