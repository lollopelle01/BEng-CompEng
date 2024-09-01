
# esame tree N flat

cd "$1"

for i in * ; do
  if [ -d "$i" ] ; then
     echo `pwd`/$i : >> "$HOME/outfile.txt"
	ls "$i" | sort | head -n $2 >> "$HOME/outfile.txt"
     $0 "$i" $2 $3 $4
  elif [ -f "$i" ] ; then
		cp "$i" $3
  fi

done
