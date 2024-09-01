#!/bin/bash
DEPTH=0
function handler() {
        (( DEPTH++ ))
        echo "depth $DEPTH - $(date) - gestisco $1"
        for i in {1..3} ; do sleep 3 ; done
        echo "depth $DEPTH - $(date) - fine $1"
        (( DEPTH-- ))
}
trap "handler USR1" USR1
trap "handler USR2" USR2
date ; echo sono $$ mandami SIGUSR1 o SIGUSR2 o CTRL-C
while true ; do sleep 2 ; done
