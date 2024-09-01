#!/bin/bash

cd $3
for file in *
do
    if [[ -f $file ]]
        then
            comando_conta=`grep -o $1 $file | wc -l`
            if [[ $comando_conta -gt $2 ]]; then echo "`pwd`/$file $comando_conta"; fi
    elif [[ -d $file ]]
        then 
            $0 $1 $2 $file;
    fi
done
