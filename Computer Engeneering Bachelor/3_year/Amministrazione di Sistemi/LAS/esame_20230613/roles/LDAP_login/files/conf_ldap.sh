#!/bin/bash -x
cd /home/vagrant
tar -xf /home/vagrant/config_files.tar
rm /home/vagrant/config_files.tar
cat /home/vagrant/file_location | { while read SRC DEST; do cp $SRC $DEST; rm $SRC ; done; }

