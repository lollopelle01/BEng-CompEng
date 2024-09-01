#!/bin/bash

# realizzare uno script che legga da un file ~/calendario una lista di scadenze espresse nel formato 
# YYYY-MM-DD HH:MM DESCRIZIONE
# e scriva sul file ~/promemoria due avvisi,1 giorno e 1 ora prima della scadenza.

CALENDARIO="~/calendario"
PROMEMORIA="~/promemoria"
FILETEMP=$(mktemp) # Crea autemoticamente un file temporaneo 

# Leggiamo dal calendario, in ogni riga troveremo il formato DATA(YYYY-MM-DD) ORA(HH:MM) e DESCRIZIONE
cat $CALENDARIO | while read DATA ORA DESCRIZIONE
do
	SECONDI=$(date -d "$DATA $ORA" +%s)

	GIORNO_PRIMA=(( $SECONDI - 24*60*60  ))
	ORA_PRIMA=(( $SECONDI - 60*60 ))

	echo "$GIORNO_PRIMA DATA ORA DESCRIZIONE" >> "$FILETEMP"
	echo "$ORA_PRIMA DATA ORA DESCRIZIONE" >> "$FILETEMP"
done

# Scriviamo sul promemoria
while sleep 1
do
	# Se il minuto non è cambiato allora torno a dormire
	if [[ $(date +%M) == $NOW ]] ; then continue ; fi 

	# Mi segno il minuto cambiato per il confronto
	NOW=$(date +%M)

	# Se è cambiato il minuto, controlliamo che in questo momento non ci siano avvenimenti da notificare
	# PS: i timestamp si basano su HH:MM mentre il date si basa su HH:MM:SS
	CURRENTSECONDS=(( $(date +%s) / 60 * 60 ))

	egrep "^$CURRENTSECONDS" "$FILETEMP" | while read TIMESTAMP DATA ORA DESCRIZIONE
	do
		echo "$DESCRIZIONE termina il $DATA alle $ORA" >> "$PROMEMORIA"
	done
done
