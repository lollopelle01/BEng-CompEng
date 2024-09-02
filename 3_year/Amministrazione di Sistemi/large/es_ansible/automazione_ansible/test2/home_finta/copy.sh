#!/bin/bash

mkdir -p backup

for FILE in $(cat save.list)
do
	cp "$FILE" "./backup/$FILE"
done

NOME_ARCHIVIO="bck.$(date -u +%Y-%m-%dT%H:%M:%S.%3NZ).tgz"
tar -czvf $NOME_ARCHIVIO "./backup"


