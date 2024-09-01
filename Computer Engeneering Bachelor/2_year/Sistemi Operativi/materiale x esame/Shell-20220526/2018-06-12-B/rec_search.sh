# Recursively search through a directory's subtree
# rec_search.sh dir minD maxD

cd "$1"
for file in * ; do
    if [[ -f "$file" ]] ; then
        T=`stat --format=%X $file`

        if [[ $T -ge $2 && $T -le $3 ]]; then
            echo "`pwd`/$file: $T" 
        fi
    elif [[ -d "$file" ]] ; then
        echo "Starting recursion on dir `pwd`/$file"
        "$0" "$file" "$2" "$3" 
    fi
done

