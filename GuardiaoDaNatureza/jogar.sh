#!/bin/bash
cd "$(dirname "$0")"

if ! command -v java &> /dev/null; then
    echo "[ERRO] Java nao encontrado. Instale Java 21 ou superior: https://adoptium.net"
    exit 1
fi

java -Dfile.encoding=UTF-8 -jar GuardiaoDaNatureza.jar
