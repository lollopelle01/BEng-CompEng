#!/bin/bash

read -p "Username: " USERNAME
OUTPUT=$(ldapsearch -LLL -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H ldap://172.16.0.1/ -b "uid=$USERNAME,ou=Peolple,dc=labammsis" -s base | grep ^dn:)
if [ -z $OUTPUT ]; then
    # CASO A
    if ! [[ $USERNAME =~ '^[a-z]+$' ]]; then echo "errore"; exit 1; fi
    PASSWORD=$(tr -dc '0-9a-zA-Z' < /dev/urandom | head -c 8)

    GID_NUMBER=$(ldapsearch -x -LLL -H "ldap://172.16.0.1/" -b "ou=Groups,dc=labammsis" -s base "gidNumber" | awk -F 'gidNumber: ' '{ print $2 }' | sort -nr | head -1)
    (( GID_NUMBER++ ))

    # creazione gruppo
     ldapadd -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H "ldap://10.100.1.254" <<EOF
dn: cn=$USERNAME,ou=Groups,dc=labammsis
objectClass: top
objectClass: posixGroup
cn: $USERNAME
gidNumber: $GID_NUMBER
EOF

    UID_NUMBER=$(ldapsearch -x -LLL -H "ldap://172.16.0.1/" -b "ou=People,dc=labammsis" -s one "uidNumber" | awk -F 'uidNumber: ' '{ print $2 }' | sort -nr | head -1)
    (( UID_NUMBER++ ))

    # creazione utente
    ldapadd -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H "ldap://10.100.1.254" <<EOF
dn: uid=$USERNAME,ou=People,dc=labammsis
objectClass: top
objectClass: posixAccount
objectClass: shadowAccount
objectClass: inetOrgPerson
givenName: $USERNAME
cn: $USERNAME
sn: $USERNAME
uid: $USERNAME
uidNumber: $UID_NUMBER
gidNumber: $GID_NUMBER
homeDirectory: /home/$USERNAME
loginShell: /bin/bash
userPassword: {crypt}x
EOF

OUTPUT='operazione riuscita' # così se andato a buin fine ricade nel caso B
elif [ -z $OUTPUT ]; then
    # CASO B
    OUTPUT=$(ldapsearch -LLL -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H ldap://172.16.0.1/ -b "uid=$USERNAME,ou=Peolple,dc=labammsis" -s base (homeDirectory=/home/$USERNAME) )
    if [ -z $OUTPUT ]; then logger -n "172.16.0.1" -p local1.notice "_NEW_USERNAME_$(hostname -I | cut -d' ' -f2)_"
    COUNTER=0
    while sleep 2; do
        OUTPUT=$(ldapsearch -LLL -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H ldap://172.16.0.1/ -b "uid=$USERNAME,ou=Peolple,dc=labammsis" -s base (homeDirectory=/home/$USERNAME) )
        if [[ -z $OUTPUT && $COUNTER == 30 ]]; then echo "Sono passati 30 secondi e la home non e' ancora presente"; exit 2; fi
        if ! [[ -z $OUTPUT ]]; then /usr/bin/su - $USERNAME; fi
        
        (( COUNTER++ ))

    done
fi

