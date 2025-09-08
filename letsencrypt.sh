#!/bin/bash

if [ "$1" == "--remote" ]; then
    if [ -z "$2" ]; then
        echo "Error: IP address is required as the second parameter!"
        exit 1
    fi

    SERVER_IP="$2"
    echo "Uploading script to $SERVER_IP"
    scp "$(realpath "$0")" root@"$SERVER_IP":/home/outsideworx/vault
    echo "Upload completed successfully."
    ssh root@"$SERVER_IP" "bash /home/outsideworx/vault/$(basename "$0")"
    exit 0
fi

# WARNING: For this section to work, port 80 has to be open and accessible via the below mentioned address.
apt update
apt install -y certbot
certbot certonly --standalone --noninteractive --agree-tos --email info@outsideworx.net -d services.outsideworx.net