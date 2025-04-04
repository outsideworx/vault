#!/bin/bash

if [ "$1" == "--download" ]; then
    if [ -z "$2" ]; then
        echo "Error: IP address is required as the second parameter!"
        exit 1
    fi

    SERVER_IP="$2"
    rm -rf /tmp/data;
    scp -r root@"$SERVER_IP":/root/data /tmp;
else
    echo "Error: Only download mode is supported!"
    exit 1
fi
