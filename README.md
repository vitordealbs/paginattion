# Simulador de Algoritmos de Substituição de Páginas

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-Educational-blue.svg)]()

**Disciplina:** Sistemas Operacionais
**Universidade Federal do Ceará**

---

## 📋 Sobre o Projeto

Simulador completo de algoritmos clássicos de substituição de páginas para gerência de memória virtual. Implementa 8 algoritmos diferentes, permitindo comparação de desempenho e análise de métricas como faltas de página, taxa de faltas e evicções.

### Algoritmos Implementados

- ✅ **FIFO** (First-In, First-Out) - Remove a página mais antiga
- ✅ **LRU** (Least Recently Used) - Remove a página menos recentemente usada
- ✅ **Ótimo** - Remove a página que será usada mais tarde no futuro
- ✅ **Segunda Chance** - FIFO com bit de referência
- ✅ **Clock** (Relógio) - Versão circular do Segunda Chance
- ✅ **NRU** (Not Recently Used) - Usa bits R e M para classificar páginas
- ✅ **LFU** (Least Frequently Used) - Remove a página menos frequentemente usada
- ✅ **MFU** (Most Frequently Used) - Remove a página mais frequentemente usada

---

## 📁 Estrutura do Projeto (Clean Code)

```
pagination/
│
├── src/                          # Código-fonte Java
│   ├── Pager.java               # Classe principal (CLI)
│   ├── PageReplacementSimulator.java  # Classe abstrata base
│   ├── Frame.java               # Estrutura de dados de frame
│   ├── FIFOSimulator.java       # Implementação FIFO
│   ├── LRUSimulator.java        # Implementação LRU
│   ├── OptimalSimulator.java   # Implementação Ótimo
│   ├── SecondChanceSimulator.java  # Implementação Segunda Chance
│   ├── ClockSimulator.java     # Implementação Clock
│   ├── NRUSimulator.java       # Implementação NRU
│   ├── LFUSimulator.java       # Implementação LFU
│   └── MFUSimulator.java       # Implementação MFU
│
├── bin/                         # Arquivos compilados (.class)
│   └── (gerados automaticamente)
│
├── docs/                        # Documentação completa
│   ├── IMPLEMENTACAO.md        # Decisões técnicas e arquitetura
│   ├── EXEMPLOS.md             # Exemplos práticos de uso
│   └── QUICK_START.md          # Guia rápido de início
│
├── traces/                      # Arquivos de trace
│   └── silberschatz2001.trace  # Trace oficial (20 referências)
│
├── scripts/                     # Scripts de build e execução
│   ├── build.bat               # Compilação (Windows)
│   ├── build.sh                # Compilação (Linux/Mac)
│   ├── run.bat                 # Execução rápida (Windows)
│   ├── run.sh                  # Execução rápida (Linux/Mac)
│   ├── test_all.bat            # Testes completos (Windows)
│   ├── test_all.sh             # Testes completos (Linux/Mac)
│   ├── clean.bat               # Limpeza (Windows)
│   └── clean.sh                # Limpeza (Linux/Mac)
│
└── README.md                    # Este arquivo
```

---

## 🚀 Início Rápido (Quick Start)

### Pré-requisitos

- **Java JDK 21** ou superior instalado
- Terminal/Prompt de comando

### Verificar Instalação do Java

```bash
java -version
```

Deve mostrar Java 21 ou superior.

---

### Windows

```batch
# 1. Navegue até a pasta do projeto
cd C:\Users\Vitodea\Desktop\pagination

# 2. Compile o projeto
cd scripts
build.bat

# 3. Execute um teste simples
run.bat lru 3

# 4. Execute todos os testes
test_all.bat
```

### Linux / Mac

```bash
# 1. Navegue até a pasta do projeto
cd ~/Desktop/pagination

# 2. Compile o projeto
cd scripts
chmod +x *.sh
./build.sh

# 3. Execute um teste simples
./run.sh lru 3

# 4. Execute todos os testes
./test_all.sh
```

---

## 💻 Uso Detalhado

### Compilação

**Windows:**
```batch
cd scripts
build.bat
```

**Linux/Mac:**
```bash
cd scripts
./build.sh
```

O script compila todos os arquivos `.java` e gera os `.class` na pasta `bin/`.

---

### Execução Manual (Forma Completa)

**Sintaxe:**
```bash
java -cp bin Pager --algo <ALGORITMO> --frames <NÚMERO> --trace traces/<ARQUIVO> [--verbose]
```

**Parâmetros:**
- `--algo` : Algoritmo a usar (`fifo`, `lru`, `otimo`, `segundachance`, `clock`, `nru`, `lfu`, `mfu`)
- `--frames` : Número de molduras de página (inteiro positivo)
- `--trace` : Caminho para o arquivo de trace
- `--verbose` : (Opcional) Mostra detalhes passo a passo

**Exemplo:**
```bash
# Windows
java -cp bin Pager --algo lru --frames 3 --trace traces\silberschatz2001.trace

# Linux/Mac
java -cp bin Pager --algo lru --frames 3 --trace traces/silberschatz2001.trace
```

---

### Execução Simplificada (com scripts)

**Windows:**
```batch
cd scripts
run.bat <algoritmo> <frames> [verbose]
```

**Exemplos:**
```batch
run.bat lru 3
run.bat fifo 4
run.bat otimo 3 verbose
```

**Linux/Mac:**
```bash
cd scripts
./run.sh <algoritmo> <frames> [verbose]
```

**Exemplos:**
```bash
./run.sh lru 3
./run.sh fifo 4
./run.sh otimo 3 verbose
```

---

## 📊 Exemplos de Saída

### Exemplo 1: LRU com 3 frames

```bash
cd scripts
run.bat lru 3
```

**Saída:**
```
Algoritmo: LRU
Frames: 3
Referências: 20
Faltas de página: 12
Taxa de faltas: 60.00%
Evicções: 9
Conjunto residente final:
frame_ids: 0 1 2
page_ids:  1 0 7
```

### Exemplo 2: FIFO com 3 frames (modo verbose)

```bash
cd scripts
run.bat fifo 3 verbose
```

**Saída (parcial):**
```
Ref 1: Page fault - Carregou página 7 no frame 0
Ref 2: Page fault - Carregou página 0 no frame 1
Ref 3: Page fault - Carregou página 1 no frame 2
Ref 4: Page fault - Evictou página 7, carregou 2 no frame 0
Ref 5: Hit - Página 0 já está no frame 1
...
```

---

## 📈 Resultados Esperados (Validação)

### Com arquivo `silberschatz2001.trace` (20 referências)

| Molduras | FIFO | LRU | Ótimo |
|----------|------|-----|-------|
| **3**    | 15   | 12  | 9     |
| **4**    | 10   | 8   | 8     |

### Comparação Completa (3 frames)

| Algoritmo      | Page Faults | Taxa Faltas | Evicções |
|----------------|-------------|-------------|----------|
| FIFO           | 15          | 75.00%      | 12       |
| LRU            | 12          | 60.00%      | 9        |
| Ótimo          | 9           | 45.00%      | 6        |
| Segunda Chance | 11          | 55.00%      | 8        |
| Clock          | 11          | 55.00%      | 8        |
| NRU            | 12          | 60.00%      | 9        |

---

## 🧪 Executar Todos os Testes

### Windows

```batch
cd scripts
test_all.bat
```

### Linux / Mac

```bash
cd scripts
./test_all.sh
```

Este script:
1. Compila o projeto automaticamente
2. Executa todos os 8 algoritmos com 3 frames
3. Executa FIFO, LRU e Ótimo com 4 frames
4. Mostra resultados esperados para validação

---

## 🗂️ Criar Arquivo de Trace Customizado

Crie um arquivo de texto com uma referência de página por linha:

**Exemplo: `traces/custom.trace`**
```
1
2
3
1
2
3
4
```

**Execute:**
```bash
# Windows
java -cp bin Pager --algo lru --frames 2 --trace traces\custom.trace

# Linux/Mac
java -cp bin Pager --algo lru --frames 2 --trace traces/custom.trace
```

---

## 🧹 Limpeza de Arquivos Compilados

### Windows

```batch
cd scripts
clean.bat
```

### Linux / Mac

```bash
cd scripts
./clean.sh
```

Remove todos os arquivos `.class` da pasta `bin/`.

---

## 📚 Documentação Completa

- **[README.md](README.md)** - Este arquivo (visão geral e guia de uso)
- **[docs/IMPLEMENTACAO.md](docs/IMPLEMENTACAO.md)** - Decisões técnicas, estruturas de dados e padrões de projeto
- **[docs/EXEMPLOS.md](docs/EXEMPLOS.md)** - Exemplos práticos, casos de teste e exercícios
- **[docs/QUICK_START.md](docs/QUICK_START.md)** - Guia rápido de 5 passos

---

## 🏗️ Arquitetura do Código

### Padrão de Projeto: Template Method

```
PageReplacementSimulator (Abstract)
    ├── simulate() [abstract]
    ├── findPageInMemory() [protected]
    ├── findFreeFrame() [protected]
    └── printResults() [public]

Concrete Implementations:
    ├── FIFOSimulator
    ├── LRUSimulator
    ├── OptimalSimulator
    ├── SecondChanceSimulator
    ├── ClockSimulator
    ├── NRUSimulator
    ├── LFUSimulator
    └── MFUSimulator
```

### Classe Frame (Estrutura de Dados)

```java
Frame:
    - frameId: int          // ID único do frame
    - pageId: Integer       // ID da página (null = vazio)
    - referenced: boolean   // Bit R (Segunda Chance, Clock, NRU)
    - modified: boolean     // Bit M (NRU)
    - frequency: int        // Contador (LFU/MFU)
    - lastUsed: int         // Timestamp (LRU)
```

---

## 🔧 Características Técnicas

- **Linguagem:** Java 21
- **Paradigma:** Orientação a Objetos
- **Padrões:** Template Method, Factory
- **Características Java 21:**
  - Enhanced Switch Expressions
  - Text Blocks
  - String Formatting
  - Try-with-Resources

---

## 🐛 Solução de Problemas

### ❌ Erro: "javac não é reconhecido"

**Solução:**
1. Instale o [Java JDK 21](https://www.oracle.com/java/technologies/downloads/)
2. Configure a variável de ambiente `JAVA_HOME`
3. Adicione `%JAVA_HOME%\bin` ao PATH

### ❌ Erro: "Could not find or load main class Pager"

**Solução:**
```bash
# Certifique-se de estar usando -cp corretamente
cd C:\Users\Vitodea\Desktop\pagination
cd scripts
build.bat
run.bat lru 3
```

### ❌ Erro: "class file has wrong version"

**Solução:**
- Verifique a versão do Java: `java -version`
- Deve ser Java 21 ou superior
- Recompile o projeto: `build.bat`

### ⚠️ Caracteres especiais não aparecem no Windows

**Nota:** Isso é normal devido à codificação do console. Os resultados numéricos não são afetados.

**Alternativa:** Redirecione para arquivo
```batch
run.bat lru 3 > resultado.txt
```

---

## 📖 Referências

1. **Silberschatz, A., Galvin, P. B., & Gagne, G.** (2018). *Operating System Concepts* (10th ed.)
2. **Tanenbaum, A. S., & Bos, H.** (2014). *Modern Operating Systems* (4th ed.)
3. [Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/) - Oracle

---

## 👥 Contribuições

Este projeto foi desenvolvido para fins educacionais na disciplina de Sistemas Operacionais da UFC.

### Estrutura Desenvolvida

- ✅ 8 algoritmos completos e funcionais
- ✅ Interface CLI robusta
- ✅ Scripts automatizados de build e teste
- ✅ Documentação completa em português
- ✅ Estrutura de pastas organizada (Clean Code)
- ✅ Validação com casos de teste oficiais

---

## 📄 Licença

Este projeto é de código aberto e está disponível para fins educacionais.

---

## 🎯 Objetivos de Aprendizado

Ao utilizar este simulador, você será capaz de:

1. ✅ Compreender o funcionamento dos algoritmos de substituição de páginas
2. ✅ Entender o impacto do número de molduras no desempenho
3. ✅ Calcular métricas (page faults, taxa de faltas, evicções)
4. ✅ Analisar a anomalia de Belady
5. ✅ Observar o efeito da localidade de referência
6. ✅ Comparar desempenho entre diferentes algoritmos

---

## 📞 Suporte

Para dúvidas sobre o projeto:
1. Consulte a documentação em `docs/`
2. Verifique os exemplos em `docs/EXEMPLOS.md`
3. Revise a seção de solução de problemas acima

---

**Desenvolvido com ☕ para a disciplina de Sistemas Operacionais - UFC**

*Última atualização: 2025*
