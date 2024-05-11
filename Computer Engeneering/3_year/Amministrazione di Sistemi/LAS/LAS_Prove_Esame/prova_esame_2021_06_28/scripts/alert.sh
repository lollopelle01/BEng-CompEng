#!/bin/bash

# alert.sh che accetta come parametro un IP di un client e via SNMP
# ricava l'elenco degli utenti che sono stati presenti sul client negli ultimi 20 minuti. L'elenco ottenuto
# deve essere scritto in append sul file /root/active.users, dopo di che', se un utente compare in tale
# file più di 50 volte, il suo nome deve essere aggiunto al file /root/bad.users

snmpget -Oqv -v 1 -c public 10.111.111.160 NET-SNMP-EXTEND-MIB::nsExtendOutputFull.\"utenti\" >>  /root/active.users

# Controllo utenti
cat /root/active.users | sort | uniq -c |
while read NUM USR; do
    [[ $NUM -ge 50 ]] && echo "$USR" >> /root/bad.users
done