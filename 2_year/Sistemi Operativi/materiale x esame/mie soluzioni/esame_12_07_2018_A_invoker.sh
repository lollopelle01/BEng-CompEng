#!/bin/bash

if [[ $# -ne 3 ]]; then echo Sintassi!; exit; fi

if [[ $1 != /* ]]; then echo "$1 non è un path assoluto"; exit; fi
if [[ $3 != /* ]]; then echo "$3 non è un path assoluto"; exit; fi

if [[ ! -d $1 ]]; then echo "$1 non è una directory"; exit; fi

if [[ $2 = *[!0-9]* ]]; then echo "$2 deve essere un'intero positivo"; exit; fi

if [[ ! -d $3 ]]; 
    then `mkdir "$3"` #se non esiste la directory la creo
elif [[ -d $3 ]]
    then `rm -r "$3"`; `mkdir "$3"` #se la directory esiste già la elimino e la ricreo per svuotarla
fi

case $0 in 
    /*) dirname="`dirname $0`/"
    ;;
    */*)dirname="`pwd`/`dirname $0`/"
    ;;
    *)  dirname=""
esac

comando_ricorsivo="$dirname"esame_12_07_2018_A.sh

echo "" >> $HOME/fileout.txt 

"$comando_ricorsivo" "$1" "$2" "$3"




