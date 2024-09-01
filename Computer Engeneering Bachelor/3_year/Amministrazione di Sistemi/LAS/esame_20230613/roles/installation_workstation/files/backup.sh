#!/bin/bash

logger -n 172.16.0.1 -p "local1.notice" " _EXIT_$USER_$(hostname -I | cut -d' ' -f2)_"