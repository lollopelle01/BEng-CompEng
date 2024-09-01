#!/bin/bash

if [[ $# != 3 ]]; then echo Sintassi; exit; fi

if [[ $1 != /* ]]; then echo "$1 non è un path assoluto"; exit; fi
if [[ ! -d $1 ]]; then echo "$1 non è una directory"; exit; fi

if [[ $2 = *[!0-9]* ]]; then echo "$2 deve essere un intero positivo"; exit; fi

if [[ $3 = *[!0-9]* ]]; then echo "$3 deve essere un intero positivo"; exit; fi

#creazione processo figlio
top -b > processi.out &
pid1=`ps | grep top | cut -d ' ' -f1`
echo pid1 = $pid1

#assemblaggio comando ricorsivo
case $0 in 
    /*) command_recursive="`dirname $0`"/esame_12_06_2018_B.sh
    ;;
    */*) command_recursive="`pwd`"/"`dirname $0`"/esame_12_06_2018_B.sh
    ;;
    *)  command_recursive=esame_12_06_2018_B.sh
    ;;
esac

#eseguo comando ricorsivo
"$command_recursive" "$1" "$2" "$3"

kill -9 $pid1
