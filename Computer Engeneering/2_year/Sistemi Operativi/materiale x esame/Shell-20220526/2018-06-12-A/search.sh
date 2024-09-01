#!/bin/bash

#Controllo argomenti
if [[ $# -lt 3 ]]; then
    echo "Errore: numero di argomenti non corretto" 1>&2
    echo -e "Usage:\n\t$0 Stringa M dir1 ... dirN" 1>&2
    exit 1
fi

if [[ $2 = *[!0-9]* ]] ; then
    echo "$2 non è un intero positivo" 1>&2
    exit 1
fi


stringa=$1
M=$2

shift 2

for dir in "$@"; do
    if ! [[ -d "$dir" ]]; then
        echo "Errore: $dir non esiste o non è una cartella" 1>&2
        exit 1
    fi
done

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

for dir in "$@"; do
    echo "Launching recursion for $dir"
    "$recursive_command" "$stringa" "$M" "$dir"
done


kill -9 $pidP1

