#!/bin/bash

SCRIPT_DIR=$(dirname "$(realpath "$0")")
if [ -z "$2" ]; then
    echo "Error: IP address is required as the second parameter!"
    exit 1
fi
SERVER_IP="$2"

if [ "$1" == "--download" ]; then
    echo "Downloading files: $SERVER_IP"
    rsync -rvh --delete devs@"$SERVER_IP":/home/outsideworx /tmp;
elif [ "$1" == "--deploy" ]; then
    echo "Uploading project: $SERVER_IP"
    rsync -rvh --delete \
        "$SCRIPT_DIR/.env" \
        "$SCRIPT_DIR/compose.yaml" \
        "$SCRIPT_DIR/Dockerfile" \
        "$SCRIPT_DIR/pom.xml" \
        "$SCRIPT_DIR/prometheus.yaml" \
        "$SCRIPT_DIR/src" \
        root@"$SERVER_IP":/home/outsideworx/vault
    echo "Deployment starts: $SERVER_IP"
    ssh root@"$SERVER_IP" "
        cd /home/outsideworx/vault;
        docker compose up --build --force-recreate --no-deps -d;
        docker system prune -af;
        cd /root;
        rm -rf /home/outsideworx/vault;
        docker logs vault -f;"
else
    echo "Error: Only download & deploy modes are supported!"
    exit 1
fi
