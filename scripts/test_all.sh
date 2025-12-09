#!/bin/bash
# Script para testar todos os algoritmos (Linux/Mac)
# Execute: chmod +x test_all.sh && ./test_all.sh

echo "=========================================="
echo "  SIMULADOR DE SUBSTITUIÇÃO DE PÁGINAS"
echo "=========================================="
echo ""

# Compila o projeto
echo "Compilando..."
./build.sh
if [ $? -ne 0 ]; then
    echo "Erro na compilação!"
    exit 1
fi

echo "=========================================="
echo "  TESTES COM 3 FRAMES"
echo "=========================================="
echo ""

for algo in fifo lru otimo segundachance clock nru lfu mfu; do
    echo "--- $algo (3 frames) ---"
    java -cp ../bin Pager --algo $algo --frames 3 --trace ../traces/silberschatz2001.trace
    echo ""
done

echo "=========================================="
echo "  TESTES COM 4 FRAMES"
echo "=========================================="
echo ""

for algo in fifo lru otimo; do
    echo "--- $algo (4 frames) ---"
    java -cp ../bin Pager --algo $algo --frames 4 --trace ../traces/silberschatz2001.trace
    echo ""
done

echo "=========================================="
echo "  TESTES CONCLUÍDOS"
echo "=========================================="
echo ""
echo "Resultados esperados:"
echo "  3 frames: FIFO=15, LRU=12, Ótimo=9"
echo "  4 frames: FIFO=10, LRU=8, Ótimo=8"
echo ""
