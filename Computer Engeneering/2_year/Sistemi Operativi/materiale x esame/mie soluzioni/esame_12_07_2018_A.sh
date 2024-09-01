#!/bin/bash

cd $1

for file in *
do
    if [[ -f $file ]]
        then cp $file $3
    fi
    
    if [[ -d $file ]]
        then
            `ls $file | sort | head -n $2` >> "$HOME/fileout.txt"
            "`pwd`/$file" : >> "$HOME/fileout.txt"
            "$0" "$file" "$2" "$3"
    fi
done
    
    
            
        
