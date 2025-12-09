@echo off
REM Script para executar o simulador
REM Uso: run.bat <algoritmo> <frames> [verbose]
REM Exemplo: run.bat lru 3
REM Exemplo: run.bat lru 3 verbose

if "%1"=="" (
    echo Uso: run.bat ^<algoritmo^> ^<frames^> [verbose]
    echo.
    echo Exemplos:
    echo   run.bat lru 3
    echo   run.bat fifo 4 verbose
    echo.
    echo Algoritmos disponiveis:
    echo   fifo, lru, otimo, segundachance, clock, nru, lfu, mfu
    exit /b 1
)

if "%2"=="" (
    echo Erro: Numero de frames nao especificado
    echo Uso: run.bat ^<algoritmo^> ^<frames^> [verbose]
    exit /b 1
)

set ALGO=%1
set FRAMES=%2
set VERBOSE=

if "%3"=="verbose" (
    set VERBOSE=--verbose
)

REM Verifica se foi compilado
if not exist "..\bin\Pager.class" (
    echo Projeto nao compilado. Execute: build.bat
    exit /b 1
)

REM Executa o simulador
java -cp ..\bin Pager --algo %ALGO% --frames %FRAMES% --trace ..\traces\silberschatz2001.trace %VERBOSE%
