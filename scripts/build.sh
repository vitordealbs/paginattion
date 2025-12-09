#!/bin/bash
# Script de compilação do projeto
# Compila todos os arquivos Java e gera os .class em bin/

echo "=========================================="
echo "  COMPILANDO PROJETO"
echo "=========================================="
echo ""

# Limpa diretório bin
if [ -f "../bin/*.class" ]; then
    echo "Limpando arquivos antigos..."
    rm -f ../bin/*.class
fi

# Compila arquivos Java
echo "Compilando arquivos Java..."
javac -d ../bin ../src/*.java

if [ $? -ne 0 ]; then
    echo ""
    echo "[ERRO] Falha na compilação!"
    exit 1
fi

echo ""
echo "[OK] Compilação bem-sucedida!"
echo "Arquivos .class gerados em: bin/"
echo ""
