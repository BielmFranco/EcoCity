@echo off
chcp 65001 > nul
title Guardiao da Natureza

where java >nul 2>nul
if errorlevel 1 (
    echo.
    echo  [ERRO] Java nao encontrado no sistema.
    echo  Baixe e instale o Java 21 ou superior:
    echo  https://adoptium.net/temurin/releases/?version=21
    echo.
    pause
    exit /b 1
)

java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -jar "%~dp0GuardiaoDaNatureza.jar"

if errorlevel 1 (
    echo.
    echo  [AVISO] O jogo encerrou com erro.
    echo  Pode ser versao de Java incompativel. Necessario Java 21 ou superior.
    echo  Sua versao instalada:
    java -version
    echo.
)
pause
