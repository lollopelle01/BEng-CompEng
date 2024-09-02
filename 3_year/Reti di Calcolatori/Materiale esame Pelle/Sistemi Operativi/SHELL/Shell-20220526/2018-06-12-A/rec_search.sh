# Recursively search through a directory's subtree
# rec_search.sh string M dir 

cd "$3"
for file in * ; do
    if [[ -f "$file" ]] ; then
        X=`grep -o "$1" $file | wc -l`

        #echo "size of $file is $size"
        if [[ $X -gt $2 ]]; then
            echo "`pwd`/$file: $X" 
        fi
    elif [[ -d "$file" ]] ; then
        echo "Starting recursion on dir `pwd`/$file"
        "$0" "$1" "$2" "$file"
    fi
done

