@echo off
REM Script de compilacao do projeto
REM Compila todos os arquivos Java e gera os .class em bin/

echo ==========================================
echo   COMPILANDO PROJETO
echo ==========================================
echo.

REM Limpa diretorio bin
if exist "..\bin\*.class" (
    echo Limpando arquivos antigos...
    del /Q "..\bin\*.class" 2>nul
)

REM Compila arquivos Java
echo Compilando arquivos Java...
javac -d ..\bin ..\src\*.java

if errorlevel 1 (
    echo.
    echo [ERRO] Falha na compilacao!
    exit /b 1
)

echo.
echo [OK] Compilacao bem-sucedida!
echo Arquivos .class gerados em: bin\
echo.
