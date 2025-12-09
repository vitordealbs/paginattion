@echo off
REM Script para testar todos os algoritmos
REM Execute: test_all.bat

echo ==========================================
echo   SIMULADOR DE SUBSTITUICAO DE PAGINAS
echo ==========================================
echo.

REM Compila o projeto
echo Compilando...
call build.bat
if errorlevel 1 (
    echo Erro na compilacao!
    exit /b 1
)

echo ==========================================
echo   TESTES COM 3 FRAMES
echo ==========================================
echo.

echo --- FIFO (3 frames) ---
java -cp ..\bin Pager --algo fifo --frames 3 --trace ..\traces\silberschatz2001.trace
echo.

echo --- LRU (3 frames) ---
java -cp ..\bin Pager --algo lru --frames 3 --trace ..\traces\silberschatz2001.trace
echo.

echo --- Otimo (3 frames) ---
java -cp ..\bin Pager --algo otimo --frames 3 --trace ..\traces\silberschatz2001.trace
echo.

echo --- Segunda Chance (3 frames) ---
java -cp ..\bin Pager --algo segundachance --frames 3 --trace ..\traces\silberschatz2001.trace
echo.

echo --- Clock (3 frames) ---
java -cp ..\bin Pager --algo clock --frames 3 --trace ..\traces\silberschatz2001.trace
echo.

echo --- NRU (3 frames) ---
java -cp ..\bin Pager --algo nru --frames 3 --trace ..\traces\silberschatz2001.trace
echo.

echo --- LFU (3 frames) ---
java -cp ..\bin Pager --algo lfu --frames 3 --trace ..\traces\silberschatz2001.trace
echo.

echo --- MFU (3 frames) ---
java -cp ..\bin Pager --algo mfu --frames 3 --trace ..\traces\silberschatz2001.trace
echo.

echo ==========================================
echo   TESTES COM 4 FRAMES
echo ==========================================
echo.

echo --- FIFO (4 frames) ---
java -cp ..\bin Pager --algo fifo --frames 4 --trace ..\traces\silberschatz2001.trace
echo.

echo --- LRU (4 frames) ---
java -cp ..\bin Pager --algo lru --frames 4 --trace ..\traces\silberschatz2001.trace
echo.

echo --- Otimo (4 frames) ---
java -cp ..\bin Pager --algo otimo --frames 4 --trace ..\traces\silberschatz2001.trace
echo.

echo ==========================================
echo   TESTES CONCLUIDOS
echo ==========================================
echo.
echo Resultados esperados:
echo   3 frames: FIFO=15, LRU=12, Otimo=9
echo   4 frames: FIFO=10, LRU=8, Otimo=8
echo.
pause
