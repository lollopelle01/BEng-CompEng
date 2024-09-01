# Recursively search through a directory's subtree
# rec_search.sh $dirin $word $N $dirout

cd "$1"
for file in *; do
    if [[ -f "$file" ]] ; then

	    Nocc=`grep -o $2 "$file" |wc -l`
        if [[ $Nocc -gt $3 ]] && [[ $file = $1* ]]; then
            cp "$file" $4
        fi

        echo "$file  $Nocc" >> "$4/summary.out"
        
    elif [[ -d "$file" ]] ; then
        echo "Ricorsione nella directory `pwd`/$file"
        "$0" "$file" "$2" "$3" "$4"
    fi
done
/Users/pelle/Desktop/Shell-20220526/2016-06-29/rec_search.sh