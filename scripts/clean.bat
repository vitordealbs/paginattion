@echo off
REM Script para limpar arquivos compilados

echo Limpando arquivos compilados...
if exist "..\bin\*.class" (
    del /Q "..\bin\*.class"
    echo Arquivos .class removidos de bin\
) else (
    echo Nenhum arquivo para limpar
)
echo.
echo Limpeza concluida!
