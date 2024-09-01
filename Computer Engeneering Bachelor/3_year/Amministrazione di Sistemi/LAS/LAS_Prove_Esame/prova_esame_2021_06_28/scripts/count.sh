#!/bin/bash

# VIENE LANCIATO COME ROOT DA CRON

# a)
# effettui uno spostamento corretto ed efficiente di quanto contenuto in /var/log/nfs.log in
# /var/log/nfs.log.last, in modo che contestualmente il log di nuovi messaggi riprenda nel file
# (nuovo) /var/log/nfs.log

# b)
# analizzi /var/log/nfs.log.last calcolando, per ogni IP della rete dei Client presente nel log:
# • la somma delle lunghezze dei pacchetti che lo riguardano (in entrambe le direzioni)
# • il numero totale di pacchetti che lo riguardano (in entrambe le direzioni)

# c)
# • per ogni macchina client che ha generato più di 20MB di traffico o scambiato più di 10.000
# pacchetti, invochi lo script alert.sh passandogli come parametro l'IP

# a)
mv /var/log/nfs.log /var/log/nfs.log.last
systemctl restart rsyslog

# Contenuto /var/log/nfs.log.last :
# Jun  9 10:48:02 Router2 vagrant: 10.222.222.1 10.222.222.139 0

# b)
declare -A IP_LENGTH_TOT
declare -A IP_PACK_TOT

cat /var/log/nfs.log.last | egrep "10.111.111." | 
while read M D H HOST USER IP_SRC IP_DEST LENGTH; do
    if [[ IP_SRC =~ "10.111.111." ]]
        then IP=$IP_SRC
        else IP=$IP_DEST
    fi
    (( IP_PACK_TOT[$IP]++ ))
    (( IP_LENGTH_TOT[$IP] += $LENGTH ))
done

for IP in ${IP_PACK_TOT[@]}; do
    if [[ IP_LENGTH_TOT[$IP] -gt 20000000 || PACK_TOT[$IP] -gt 10000 ]]; then /root/alert.sh $IP; fi
done
