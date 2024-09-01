#!/bin/bash

tail -f -n 0 "/var/log/homes.log" | egrep --line-buffered -w '(NEW|EXIT)' | 
while IFS=_ read HEAD KEYWORD USERNAME IP; do
    if [[ KEYWORD == "NEW" ]]; then
        CMD="mkdir -m '0600' /home/$USERNAME && chmod 0700 /home/$USERNAME"
        ssh -n -i ~/.ssh/id_rsa_ansible -o StrictHostKeyChecking=no $IP "$CMD"

        CMD1="cat > ~/.bash_logout"
        cat /home/$USERNAME | ssh -i ~/.ssh/id_rsa_ansible -o StrictHostKeyChecking=no $IP "$CMD1"

        CMD2="tar -C ~/backup -xvpf /backups/$USERNAME.tgz"
        [ -f "/backups/$USERNAME.tgz" ] && scp /backups/$USERNAME.tgz $IP:/home/$USERNAME/backup.tgz && ssh -n -i ~/.ssh/id_rsa_ansible -o StrictHostKeyChecking=no $IP "$CMD2"
    elif [[ KEYWORD == "EXIT" ]]; then
        CMD3="tar cvpf /backups/$USERNAME.tgz /home/$USERNAME && cat /backups/$USERNAME.tgz"
        ssh -n -i ~/.ssh/id_rsa_ansible -o StrictHostKeyChecking=no $IP "$CMD3" > /backups/$USERNAME.tgz
    else echo "Errore nel formato del messaggio: $LINE"
    fi
done
