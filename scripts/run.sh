#!/bin/bash
# Script para executar o simulador
# Uso: ./run.sh <algoritmo> <frames> [verbose]
# Exemplo: ./run.sh lru 3
# Exemplo: ./run.sh lru 3 verbose

if [ $# -lt 2 ]; then
    echo "Uso: ./run.sh <algoritmo> <frames> [verbose]"
    echo ""
    echo "Exemplos:"
    echo "  ./run.sh lru 3"
    echo "  ./run.sh fifo 4 verbose"
    echo ""
    echo "Algoritmos disponíveis:"
    echo "  fifo, lru, otimo, segundachance, clock, nru, lfu, mfu"
    exit 1
fi

ALGO=$1
FRAMES=$2
VERBOSE=""

if [ "$3" == "verbose" ]; then
    VERBOSE="--verbose"
fi

# Verifica se foi compilado
if [ ! -f "../bin/Pager.class" ]; then
    echo "Projeto não compilado. Execute: ./build.sh"
    exit 1
fi

# Executa o simulador
java -cp ../bin Pager --algo $ALGO --frames $FRAMES --trace ../traces/silberschatz2001.trace $VERBOSE
