#!/bin/bash

# Monitorare con tcpdump il traffico ssh tra la VM Client e la VM Server, 
# sulla VM Router, loggando attraverso syslog sul file /var/log/newconn l'inizio e 
# la fine di ogni  connessione diretta da Client a Server

# ---------------------------------------------------------------------------------------
T=$(mktemp)

# Loggiamo sul file /var/log/newconn quando vediamo i messaggi
echo -e "local7.=info /var/log/newconn" > $T
sudo cp $T /etc/rsyslog.d/netmon.conf
sudo systemctl restart rsyslog.service

# Con tcpdump leggo eventi e mando messaggi
sudo tcpdump -l -i eth1 host 10.1.1.9 and host 10.2.2.200 and port 22 | grep --line-buffered -E 'Flags \[(S|F\.)\]' | cut -d' ' -f7 | ( 
    while read U; do
        # echo "$(date) $U" >> "~/prova.txt"
        if [[ $U == "[S]" ]]; then message="Connessione iniziata"; else message="Connessione terminata"; fi 
        logger -p "local7.info" "$message";
    done
)