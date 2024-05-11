#!/bin/bash

# Modificare l'esercizio delle estensioni realizzando uno script estparam.sh che accetti sulla riga di comando

# un nome di directory da esplorare un elenco di lunghezza arbitraria di stringhe
# Lo script deve contare quanti file esistono nel sottoalbero definito dalla directory passata come primo parametro,
# che abbiano estensione uguale a una delle stringhe specificate coi parametri successivi. La soluzione deve essere efficiente,
# non deve invocare comandi pesanti sul filesystem in modo ridondante.

# ------------------------------------------------------------------------------------------------------------------------------------

# Lanciando './estparam.sh cartella_prova txt miao bau' dovrebbe essere:
#	4 txt
#	3 bau
#	3 miao

# CONTROLLO PARAMETRI
if [[ $# < 2 ]] ; then echo "La sintssi è ./estparam.sh directory elenco_estensioni" ; exit 1 ; fi
if [[ ! -d "$1" ]] ; then echo "$1 deve essere una directory"; exit 2 ; fi
DIRECTORY="$1"
shift # rimettiamo a posto i parametri

FILE_LISTA=$(mktemp) # ci scriviamo i file inizialmente
FILE_LISTA_CORRETTI=$(mktemp) # ci scriveremo i file con le estensioni giuste
cd "$DIRECTORY"

# ESPLORIAMO LA DIRECTORY --> scrivo su un file temporaneo tutti i file nel sottoalbero
function esplorazione_ricorsiva(){
	cd "$1"
	for FILE in *
	do
        	if [[ -d "$FILE" ]] ; then esplorazione_ricorsiva "$FILE" ; cd .. ; fi

		# Stampo tutti i file --> filtrerò dopo
		if [[ -f "$FILE" && "$FILE" != "estparam.sh" ]] ; then echo "$FILE" >> "$FILE_LISTA" ; fi
	done
}
esplorazione_ricorsiva "$DIRECTORY"

# ELENCO DEI FILE --> prendo i file scritti sul file e li filtro per estensione
# Filtriamo solo i file corretti
for FILE in $(cat "$FILE_LISTA")
do
	for ESTENSIONE in $@
	do
		if [[ "$FILE" =~ ^.*\."$ESTENSIONE"$ ]] ; then echo "$FILE" >> "$FILE_LISTA_CORRETTI" ; fi
	done
done

# CLASSIFICA ESTENSIONI
RISULTATO=$( cat $FILE_LISTA_CORRETTI | rev | cut -d\. -f1 | rev | sort | uniq -c | sort -rn )
echo "Nel sotto albero di $DIRECTORY, il numero delle occorrenze delle estensioni richieste:"
echo "$RISULTATO"
