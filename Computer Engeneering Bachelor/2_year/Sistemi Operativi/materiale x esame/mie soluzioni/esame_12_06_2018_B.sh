#!/bin/bash 

cd $1

minD=$2
maxD=$3

for file in *
do
    if [[ -f $file ]]
        then
            last_access=`stat --format=%X $file`
            if [[ ! last_access -lt $minD ]] && [[ ! last_access -gt $maxD ]]; then echo "`pwd`/$file $last_access"; fi
    elif [[ -d $file ]]
        then "$0" "$file" "$2" "$3"
    fi
done
