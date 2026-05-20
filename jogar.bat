@echo off
chcp 65001 > nul
"C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot\bin\java.exe" -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -jar "%~dp0UltimaSemente.jar"
pause
