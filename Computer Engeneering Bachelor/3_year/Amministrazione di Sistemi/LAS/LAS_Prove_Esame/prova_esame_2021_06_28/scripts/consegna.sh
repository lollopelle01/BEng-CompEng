#!/bin/bash -x

# Controllo argomenti
if [[ $# != 1 ]]; then echo "Usage: ./consegna.sh nome_file_esercizi_da_scaricare"; exit 1; fi

# Lo script viene eseguito da dentro prova_esame/script --> torniamo nella cartella radice
cd .. ;

# creiamo cartella dove inseriremo i file 
mkdir esame
cd esame

########################################
## Il file è in un formato:           ##
## ESERCIZIO HOST FILE(path assoluto) ##
########################################

echo "Intorno a noi abbiamo:" ; echo ; ls -1

# Andiamo a leggere e processare il file
cat "$1" | (
    while read -r ESERCIZIO HOST FILE; do
        if [[ ! -f "ssh.$HOST" ]]; then vagrant ssh-config $HOST > "ssh.$HOST"; fi
        scp -rF "ssh.$HOST" $HOST:$FILE ./$ESERCIZIO/$HOST$FILE
    done
)

# Creiamo archivio
cd ..
tar czf ammsis.tar.gz ./esame

# Ripuliamo cartella radice --> lasciamo solo il tar
rm -r esame
