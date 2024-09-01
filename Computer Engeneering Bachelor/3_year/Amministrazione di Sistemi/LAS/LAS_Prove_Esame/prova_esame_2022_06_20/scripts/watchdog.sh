#!/bin/bash

# viene avviato al boot e riavviato automaticamente se termina in modo anomalo
# Configuro systemd

# legge le righe che appaiono via via nel file /var/log/alerts.log
tail -f -n 0 /var/log/alerts.log | grep --line-buffered -w "STOP" | while read -r LINE; do
    echo $LINE
    # quando incontra una riga con testo "STOP", individua l'utente con più processi attivi, e ne
    # termina tutti i processi
    USR=$( ps -e -o user= | grep -v root | sort | uniq -c | sort -rn | head -1 | awk '{print $2}') 
    echo "Killiamo $USR "
    for PID in $(ps -u $USR -o pid=); do
        kill -9 $PID
    done
done