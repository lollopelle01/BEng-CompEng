#!/bin/bash

function print_args {
  echo "Numero di argomenti: $#"
  
  echo "Variabile \$*:"
  for arg in $*; do
    echo " - $arg"
  done
  
  echo "Variabile \$@:"
  for arg in $@; do
    echo " - $arg"
  done
}

args="arg1 arg2 arg3 con spazi"
print_args "$args"
