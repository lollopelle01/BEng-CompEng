#!/bin/bash

# realizzare uno script che legga da un file ~/calendario una lista di scadenze espresse nel formato 
# YYYY-MM-DD HH:MM DESCRIZIONE
# e scriva sul file ~/promemoria due avvisi,1 giorno e 1 ora prima della scadenza.

# ESTENSIONE: i file sono passati come argomenti, esamina il calendario e accoda al file delle scadenze solo quelle che non sono già presenti. 
# ESTENSIONE: usare cron

# CONTROLLO PARAMETRI
if [[ $# != 2 ]] ; then echo "Devi scrivere ./es5.sh CALENDARIO PROMEMORIA" ; exit 1 ; fi
if [[ ! -f "$1" ]] ; then echo "$1 non esiste" ; exit 1 ; fi
CALENDARIO="$1"
PROMEMORIA="$2"

FILETEMP=$(mktemp)

# Leggiamo dal calendario, in ogni riga troveremo il formato DATA(YYYY-MM-DD) ORA(HH:MM) e DESCRIZIONE
cat $CALENDARIO | while read DATA ORA DESCRIZIONE
do
        SECONDI=$(date -d "$DATA $ORA" +%s)

        GIORNO_PRIMA=(( $SECONDI - 24*60*60  ))
        ORA_PRIMA=(( $SECONDI - 60*60 ))

        echo "$GIORNO_PRIMA DATA ORA DESCRIZIONE" >> "$FILETEMP"
        echo "$ORA_PRIMA DATA ORA DESCRIZIONE" >> "$FILETEMP"
done

# Usiamo cron per scrivere gli avvisi su promemoria
cat "$FILETEMP" | while read MOMENTO_AVVISO DATA ORA DESCRIZIONE
do
        # Se il promemoria non è già presente procedo
        if [[ $( cat $PROMEMORIA | egrep "$DATA $ORA $DESCRIZIONE") == "" ]]
        then
                # Imposterò il 'echo $DATA $ORA $DESCRIZIONE' tra $MOMENTO_AVVISO secondi --> bisogna trasformarlo in MINUTI ORA GIORNO MESE SETTIMANA
                QUANDO=$(date -d MOMENTO_AVVISO +"%M %H %d %m %u") # ATTENZIONE !!!! --> CRON DICE OGNI QUANTO FARE IL COMANDO E NON QUANDO !!!!!!!!!!!!!!!!
                MEMO="$DATA $ORA $DESCRIZIONE"

		# Accediamo alla crontable seguendo il protocollo di append
		TMPTAB=$(mktemp)
		crontab -l | grep -vF "/bin/echo $MEMO" > $TMPTAB
		echo "$QUANDO /bin/echo $MEMO" >> $TMPTAB
		crontab $TMPTAB
		rm -f $TMPTAB
        fi
done
