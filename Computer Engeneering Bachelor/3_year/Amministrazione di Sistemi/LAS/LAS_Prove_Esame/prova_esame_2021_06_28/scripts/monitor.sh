#!/bin/bash

# Realizzare su Router2 uno script monitor.sh che sorvegli continuamente il traffico NFS (porta
# 2049 del Server, sia tcp sia udp) scrivendo via syslog sul file /var/log/nfs.log di Router1 , per ogni
# pacchetto: IP sorgente, IP destinazione e lunghezza (length)

# TCPDUMP OUTPUT:
# 10:03:48.196963 IP 10.222.222.1.2049 > 10.222.222.100.37304: Flags [R.], seq 0, ack 1692253991, win 0, length 0


sudo tcpdump -i eth2 -nlp '((dst net 10.222.222.0/24 and dst port 2049) or (src net 10.222.222.0/24 and src port 2049))' | 
while read DATA IP IP_SRC FRECCIA IP_DEST RESTO; do
    IP_SRC=$(echo $IP_SRC | cut -d. -f1-4)
    IP_DEST=$(echo $IP_DEST | cut -d. -f1-4)
    LENGHT=$(echo $RESTO | awk -F 'length ' '{print $2}')
    logger -p "local0.debug" "$IP_SRC $IP_DEST $LENGHT"
done