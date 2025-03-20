# Verificação de Números Primos com Programação Paralela

## 📌 Descrição
Este projeto tem como objetivo a implementação de um programa para verificar números primos a partir de um arquivo de entrada. Foram desenvolvidas três versões diferentes do programa:

1. *Implementação Sequencial* (1 thread)
2. *Implementação Paralela com 5 Threads*
3. *Implementação Paralela com 10 Threads*

Os números primos identificados são gravados em um arquivo de saída, mantendo a mesma ordem do arquivo de entrada.

---

## 📁 Estrutura do Projeto

- PrimosSequencial.java → Implementação sequencial (1 thread)
- Primos5t.java → Implementação paralela com 5 threads
- Primos10t.java → Implementação paralela com 10 threads
- Entrada01.txt → Arquivo de entrada com os números a serem analisados
- SaidaSequencial.txt → Resultado da execução sequencial
- SaidaParalela_5Threads.txt → Resultado da execução com 5 threads
- SaidaParalela_10Threads.txt → Resultado da execução com 10 threads

---

## 🚀 Como Executar o Projeto

### ✅ *Pré-requisitos*
- Java 8 ou superior instalado
- Um terminal ou ambiente de desenvolvimento compatível
- Git instalado

### 📌 *Clonar o Repositório*
Para obter o código do projeto, abra o terminal e execute:

    sh
    git clone https://github.com/athaliba/SISTEMAS-DISTRIBUIDOS
    cd SEU_REPOSITORIO

### 📌 **Compilação e Execução**

1. **Compilar os arquivos Java**  
   No terminal, dentro da pasta do projeto, execute:

   sh
   javac PrimosSequencial.java 
   javac Primos5t.java 
   javac Primos10t.java

2. *Rodar a versão desejada*  
   No terminal, dentro da pasta do projeto, execute:

   ```sh
   java PrimosSequencial
   java Primos5t
   java Primos10t