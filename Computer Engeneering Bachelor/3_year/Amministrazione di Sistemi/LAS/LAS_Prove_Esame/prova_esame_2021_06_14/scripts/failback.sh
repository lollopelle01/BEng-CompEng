#!/bin/bash 

# a)
# Interroga via SNMP il Router, che deve rispondere col contenuto del proprio file /tmp/server.attivo. 
# Il contenuto di tale file consiste nel nome di un server (Server1 o Server2) seguito eventualmente da uno spazio e dalla parola "new"

# b)
# Se il risultato ottenuto contiene il nome del server su cui è in esecuzione lo script (Server1 o Server2), lo script assegna all'interfaccia eth2 l'indirizzo
# aggiuntivo 10.20.20.20, altrimenti lo script si assicura che l'interfaccia eth2 non detenga l'indirizzo 10.20.20.20, deconfigurandolo se necessario.

# c)
# Lancia lo script ldap.sh, passando comLancia lo script ldap.sh, passando come parametro la parola "new" se è presente nella risposta SNMPe parametro la parola "new" se è presente nella risposta SNMP

# a)
OUTPUT=$(snmpget -v 1 -c public 10.20.20.254 NET-SNMP-EXTEND-MIB::nsExtendOutputFull.\"file_content\")
if [[ $OUTPUT =~ '^[^(server1|Server2)( new)]?$' ]]; then echo "errore di formato in /tmp/server.attivo --> $OUTPUT"; exit 1; fi

# b)
if [[ $(hostname) == $(echo $OUTPUT | cut -d' ' -f1) ]]; then
    # Aggiungo 10.20.20.20 se non presente
    if ! ip addr show dev eth2 | grep -q 10.20.20.20; then
        # Aggiungi l'indirizzo IP
        sudo ip addr add 10.20.20.20/24 dev eth2
    fi
else
    # Tolgo 10.20.20.20 se presente
     if ip addr show dev eth2 | grep -q 10.20.20.20; then
        # Aggiungi l'indirizzo IP
        sudo ip addr del 10.20.20.20/24 dev eth2
    fi
fi 

# c) 
~/ldap.sh $OUTPUT