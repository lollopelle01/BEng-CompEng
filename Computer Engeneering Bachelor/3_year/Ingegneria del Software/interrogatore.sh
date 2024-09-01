#!/bin/bash

# DA METTERE A POSTO PERCHÈ RISPOSTA IN OUTPUT NON È FPRMATTATA

# Controllo parametri
if [[ ! -f $1 ]]
then echo "$1 non è un file"; exit 1
fi

# Creazione database
declare -a domande_risposte
declare -a domande
declare -a risposte
T=$(mktemp)

# -- Estrazione di domande+risposte --------------------------------------------
i=0
while read -r linea ; do
	# echo "$linea"
	if [ "$linea" != "--------------------------------------------" ] ; then echo $linea >> $T ; fi
	if [ "$linea" == "--------------------------------------------" ] ; then domande_risposte[$i]=$(cat $T) ; (( i++ )) ; echo "" > $T ; fi
done < $1

# -- Inserimento domande e risposte --------------------------------------------
i=0
for QA in "${domande_risposte[@]}"; do
	domande[$i]=$(echo $QA | cut -d? -f1 | sed 's/£ //g') ; domande[$i]="${domande[$i]}?"
	risposte[$i]=$(echo $QA | cut -d? -f2 | sed 's/^ //g')

	(( i++ ))
done

# -- Generazione domande --------------------------------------------
echo "Generatore di domande avviato"
echo "--------------------------------------------"
echo ""
echo "Vuoi generare una domanda (SI/NO)? [Poi premi invio per avere risposta]"
read risp
while [ $risp == "SI" ] ; do
	# Generazione casuale domanda
	i=$(( RANDOM % ${#domande[@]} ))
	echo "${domande[$i]}"

	# Gestione risposta, solo su richiesta
	read flag
	echo "${risposte[$i]}"

	# Per il ciclo
	echo "--------------------------------------------"
	echo ""
	echo "Vuoi generare una domanda (SI/NO)? [Poi premi invio per avere risposta]"
	read risp
done
