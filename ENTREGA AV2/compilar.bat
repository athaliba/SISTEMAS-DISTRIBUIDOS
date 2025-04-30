@echo off
echo Compilando o projeto...
javac -cp ".;gson-2.10.jar" *.java
if %errorlevel% neq 0 (
    echo Erro na compilação!
) else (
    echo Compilado com sucesso!
)
pause
