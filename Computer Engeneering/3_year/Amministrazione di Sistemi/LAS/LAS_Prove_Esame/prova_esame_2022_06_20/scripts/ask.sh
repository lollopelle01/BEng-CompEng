#!/bin/bash

# DEVE ESSERE ESEGUIBILE SOLO COME UTENTE temp --> ?
if [[ $USER != "temp" ]]; then echo "ask.sh può essere eseguito solo dall'utente \"temp\""; exit 1; fi

# Chiede interattivamente all'utente quale username vuole scegliere, ripetendo la domanda finché l'utente non 
# risponde correttamente: lo username deve essere una stringa composta di soli caratteri minuscoli e 
# che non corrisponda a uno già presente in LDAP
USERNAME=''
while   (  
            read -p "Username: " USERNAME && 
            [ $USERNAME =~ '^[^[a-z]+]$' ] && 
            [ $(ldapsearch -LLL -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H ldap://10.100.1.254/ -b "uid=$USERNAME,ou=Peolple,dc=labammsis" -s base | egrep '^dn:') == "" ]
        );
do
    # Ricava dalla directory i gruppi disponibili e li mostra all'utente
    echo "Gruppi disponibili:"
    echo $(ldapsearch -LLL -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H ldap://10.100.1.254/ -b "ou=Groups,dc=labammsis" -s one )
done

# Chiede interattivamente all'utente quale gruppo vuole scegliere, ripetendo la domanda finché
# l'utente non risponde con una delle opzioni valide
while   (   
            read -p "Group: " GROUP &&  
            [ $(ldapsearch -LLL -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H ldap://10.100.1.254/ -b "cn=$GROUP,ou=Groups,dc=labammsis" -s one ) == "" ]
        );
do
    # Crea l'account sulla directory, impostando come home /tmp e come shell /bin/bash
    
    # Prima devo trovare il primo UID libero
    UID=$(ldapsearch -LLL -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H ldap://10.100.1.254/ -b "ou=People,dc=labammsis" -s one "uidNumber" | sort | head -1)
    (( UID++ ))
    
    # Prima devo trovare il primo GID libero
    GID=$(ldapsearch -LLL -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H ldap://10.100.1.254/ -b "ou=People,dc=labammsis" -s base "gidNumber" | sort | head -1)

    ldapadd -x -D "cn=admin,dc=labammsis" -w "gennaio.marzo" -H ldap://10.100.1.254/ <<EOF
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
homeDirectory: /tmp
loginShell: /bin/bash
userPassword: {crypt}x
EOF
    # Genera una password casuale, numerica, lunga almeno 6 caratteri, la imposta sull'account, e
    # la stampa in modo che l'utente sul Client la visualizzi a terminale
    PASSWORD=$(tr -dc '0-9' < /dev/urandom | head -c 6)
    ldappasswd -D "cn=admin,dc=labammsis" -w "gennaio.marzo" "uid=$USERNAME,ou=People,dc=labammsis" -s "$PASSWORD"
    echo "Per l'utente $USER (uid: $UID) nel gruppo $GROUP (gid: $GID) è stata impostata la password: $PASSWORD"
done