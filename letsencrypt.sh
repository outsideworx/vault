#!/bin/bash

SCRIPT_DIR=$(dirname "$(realpath "$0")")

if [ "$1" == "--remote" ]; then
    if [ -z "$2" ]; then
        echo "Error: IP address is required as the second parameter!"
        exit 1
    fi

    SERVER_IP="$2"
    echo "Uploading script and .env file to $SERVER_IP"
    scp "$(realpath "$0")" "$SCRIPT_DIR/.env" root@"$SERVER_IP":/root/
    echo "Upload completed successfully."
    ssh root@"$SERVER_IP" "bash /root/$(basename "$0")"
    exit 0
fi

if [ -f "$SCRIPT_DIR/.env" ]; then
    source "$SCRIPT_DIR/.env"
else
    echo "Error: .env file not found!"
    exit 1
fi
if [ -z "$SERVER_SSL_KEY_STORE" ]; then
    echo "Error: SERVER_SSL_KEY_STORE is not set in the .env file!"
    exit 1
fi
if [ -z "$SERVER_SSL_KEY_STORE_PASSWORD" ]; then
    echo "Error: SERVER_SSL_KEY_STORE_PASSWORD is not set in the .env file!"
    exit 1
fi

apt update
apt install -y openssl certbot
certbot certonly --standalone --noninteractive --agree-tos --email info@outsideworx.net -d services.outsideworx.net
mkdir -p "$(dirname "$SERVER_SSL_KEY_STORE")"
openssl pkcs12 -export -in /etc/letsencrypt/live/services.outsideworx.net/fullchain.pem -inkey /etc/letsencrypt/live/services.outsideworx.net/privkey.pem -out "$SERVER_SSL_KEY_STORE" -password pass:"$SERVER_SSL_KEY_STORE_PASSWORD"
