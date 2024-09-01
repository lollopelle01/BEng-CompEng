#!/bin/bash
cat /etc/passwd | ( 
    # apro una subshell per poter inizializzare variabili 
    # che saranno aggiornate nel ciclo, 
    # e poi stampate prima che il processo venga distrutto
        HIGHEST=0
        SECOND=0
        while IFS=: read NAME X U RESTO ; do
    # con read posso impostare un valore di IFS valido solo localmente
    # per parsare la riga letta e assegnare i campi a diverse variabili
        if test $U -gt $HIGHEST ; then
		echo "-- $U > $HIGHEST --"
                SECOND=$HIGHEST ; echo "SECOND=$HIGHEST"
                SECONDNAME=$HIGHESTNAME ; echo "SECONDNAME=$HIGHESTNAME"
                HIGHEST=$U ; echo "HIGHEST=$U"
                HIGHESTNAME=$NAME ; echo "HIGHESTNAME=$NAME"
		echo ""
        elif test $U -gt $SECOND ; then
		echo "-- $U > $SECOND --"
                SECOND=$U ; echo "SECOND=$U"
                SECONDNAME=$NAME ; echo "SECONDNAME=$NAME"
		echo ""
        fi
done 
echo $HIGHESTNAME
echo $SECONDNAME
)
