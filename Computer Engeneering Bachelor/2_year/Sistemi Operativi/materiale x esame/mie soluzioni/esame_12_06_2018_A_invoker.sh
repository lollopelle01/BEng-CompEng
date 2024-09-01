#!/bin/bash

if [[ $# -lt 3 ]]; then echo Sintassi!; exit; fi

stringa=$1
M=$2
shift; shift; #a ogni shift tolgo l'elemento più a sx, lascio così solo gli N direttori ($0 non viene toccato!!)

if [[ "$M" = *[!0-9]* ]]
    then echo "$M deve essere un intero positivo"; exit; fi 
    
for dir in $@
do
    if [[ ! -d $dir ]]; then echo "$dir non è un direttorio"; exit; fi
done

#processo figlio
top -b > processi.out &
pid1=`ps | grep top | cut -d ' ' -f1`
echo "pid1 = $pid1"

#assemblaggio comando ricorsivo
case $0 in 
/*) dirname=`dirname $0`/; echo "caso 1, dirname: $dirname"
;;
*/*) dirname=`pwd`/`dirname $0`/; echo "caso 2, dirname: $dirname"
;;
*) dirname=""; echo "caso 3, dirname: $dirname";;
esac

recursive_command="$dirname"esame_12_06_2018_A.sh
echo "comando ricorsivo finale: $recursive_command"
    
for dir in $@
do
    echo "effettuo la ricorsione su $dir"
    "$recursive_command" "$stringa" "$M" "$dir"
done

kill -9 $pid1 #lancia SIGKILL al processo figlio
