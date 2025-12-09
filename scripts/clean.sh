#!/bin/bash
# Script para limpar arquivos compilados

echo "Limpando arquivos compilados..."
if [ -f "../bin/*.class" ]; then
    rm -f ../bin/*.class
    echo "Arquivos .class removidos de bin/"
else
    echo "Nenhum arquivo para limpar"
fi
echo ""
echo "Limpeza concluída!"
