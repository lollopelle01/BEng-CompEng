#!/bin/bash

# viene avviato automaticamente ogni minuto nella fascia oraria 8:00-18:00 dei giorni feriali
# Chron impostato con Ansible --> stabiliamo soglia 4.00, passata come parametro
CARICO=$1

function interrogazioneClient(){
    # se individua un Client con carico sopra la soglia stabilita, gli invia attraverso syslog un
    # messaggio col solo testo "STOP"
    if [[ $(snmpget -Oqv 1 -c public $1 NET-SNMP-EXTEND-MIB::nsExtendOutputFull.\"carico\") -gt $CARICO ]]; then
        logger -n $1 "local5.info" "STOP"
    fi
}

# interroga via SNMP in parallelo tutti i Client potenzialmente attivi --> da 10.100.2.1 a 10.100.7.254
PREFISSO='10.100.'
for IP_CLIENT in 2.{1..255} {3..6}.{0..255} 7.{0..254}; do
    interrogazioneClient "$PREFISSO$IP_CLIENT" &
done

