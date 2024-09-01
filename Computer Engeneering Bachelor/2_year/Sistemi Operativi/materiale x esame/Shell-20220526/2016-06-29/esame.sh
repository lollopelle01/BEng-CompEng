#!/bin/bash

#Controllo argomenti
if [[ $# -ne 4 ]]; then
    echo "Errore: numero di argomenti non corretto" 
    echo -e "Usage:\n\t$0 dirin word N dirout" 
    exit 1
fi

if ! [[ -d "$1" ]] ; then
    echo -e "Il file $1 non è una directory" 
    exit 1
fi 
if ! [[ -d "$4" ]] ; then
    echo -e "Il file $4 non è una directory"
    exit 1
fi 

if [[ $3 = *[!0-9]* ]] ; then
    echo "$3 non è un intero positivo" 
    exit 1
fi

dirin=$1
word=$2
N=$3
dirout=$4

#outfile=$dirout/summary.out   

case "$0" in
    # La directory inizia per / Path assoluto.
    /*) 
    dir_name=`dirname $0`
    recursive_command="$dir_name"/rec_search.sh
    ;;
    */*)
    # La directory non inizia per slash, ma ha uno slash al suo interno.
    # Path relativo.
    dir_name=`dirname $0`
    recursive_command="`pwd`/$dir_name/rec_search.sh"
    ;;
    *)
    #Path né assoluto nP relativo, il comando deve essere nel $PATH
    #comando nel path
    recursive_command=rec_search.sh
    ;;
esac


#creo il file di output
echo "" > $outfile
echo "Innesco la ricorsione su $dirin"\n
$recursive_command $dirin $word $N $dirout

