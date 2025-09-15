#!/bin/bash

SCRIPT_DIR=$(dirname "$(realpath "$0")")
if [ -z "$2" ]; then
    echo "Error: IP address is required as the second parameter!"
    exit 1
fi
SERVER_IP="$2"

if [ "$1" == "--download" ]; then
    echo "Downloading starts from $SERVER_IP"
    rsync -rvh --delete root@"$SERVER_IP":/home/outsideworx /tmp;
elif [ "$1" == "--upload" ]; then
    echo "Uploading project to $SERVER_IP"
    rsync -rvh --delete \
        "$SCRIPT_DIR/src" \
        "$SCRIPT_DIR/pom.xml" \
        "$SCRIPT_DIR/.env" \
        "$SCRIPT_DIR/Dockerfile" \
        "$SCRIPT_DIR/compose.yaml" \
        root@"$SERVER_IP":/home/outsideworx/vault
else
    echo "Error: Only download & upload modes are supported!"
    exit 1
fi
