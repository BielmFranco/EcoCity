# EcoCity 🌱
### RPG Textual — APS UNIP 2026/1 | LPOO

RPG textual educativo inspirado na obra **"O Lorax"** de Dr. Seuss.
Numa cidade devastada pela poluição e pelo desmatamento, o jogador
evolui do nível 1 ao 5, cumprindo missões ecológicas entregues por
NPCs, até se tornar o **Guardião da Natureza**.

---

## Enredo

A história se passa em uma cidade devastada pela poluição, pelo
desmatamento e por conflitos ambientais. O jogador cumpre missões
ecológicas para restaurar o equilíbrio ambiental da cidade, evoluindo
progressivamente até alcançar o título de **Guardião da Natureza**.

Cada nível é apresentado em uma tela própria, com um trecho da
narrativa da restauração — basta pressionar ENTER para avançar.

---

## Grupo

| Integrante                          | RA      |
|-------------------------------------|---------|
| Alessandra Cristina da Silva Souza  | R799565 |
| Caetano de Paula Telles Ribeiro     | R688GC4 |
| Jhonnatan Pereira Santos            | R363JH0 |
| Guilherme Moraes Franco             | H386632 |
| Gabriel Moraes Franco               | H384338 |

---

## Como Jogar

**Pré-requisito:** Java 21 ou superior instalado. Baixe em https://adoptium.net

**Windows:** dê duplo-clique em `jogar.bat`

**Linux / Mac:**
```bash
chmod +x jogar.sh && ./jogar.sh
```

**Direto pelo terminal:**
```bash
java -jar EcoCity.jar
```

Os scripts `jogar.bat` e `jogar.sh` verificam automaticamente se o Java
está instalado e avisam caso a versão seja incompatível.

---

## Mecânicas

### Níveis e Classes

O jogador evolui por 5 níveis. Cada nível tem um número de missões e
NPCs igual ao número do nível, com **1 missão por NPC**.

| Nível | Classe          | Título         | Missões | NPCs |
|-------|-----------------|----------------|---------|------|
| 1     | Explorador      | Iniciante      | 1       | 1    |
| 2     | Descontaminador | Básico         | 2       | 2    |
| 3     | Botânico        | Intermediário  | 3       | 3    |
| 4     | Construtor      | Experiente     | 4       | 4    |
| 5     | Guardião        | Final          | 5       | 5    |

Total: **15 missões** e **15 NPCs** ao longo do jogo.

### NPCs e Missões

Os NPCs têm interação **direta e mecânica**: entregam a missão e
registram a conclusão. Não há diálogo de conscientização.

### Pontuação e Bônus

- **Base:** sobe com a dificuldade do nível (`nível × 100`).
- **Bônus de eficiência:** +50 ao cumprir a missão dentro do limite de turnos.
- **Bônus de classe ideal:** +`nível × 25` ao usar a classe ideal para a missão.

---

## Conceitos de O.O. Implementados

Todos marcados com comentários explícitos nos arquivos fonte:

| Conceito | Onde |
|----------|------|
| Encapsulamento | `Personagem`, `MissaoEcologica`, `NPC` — atributos privados + getters |
| Construtores | Todas as classes |
| Herança | `Explorador/Descontaminador/Botanico/Construtor/Guardiao → Personagem` |
| Sobrecarga | Construtores de `Personagem` e subclasses; `Pontuacao.calcular()` |
| Sobrescrita | `getTitulo()`, `getClasseIdeal()` nas subclasses; métodos de `Missao` |
| Classe Abstrata | `Personagem` |
| Método Abstrato | `Personagem.getTitulo()`, `Personagem.getClasseIdeal()` |
| Interface | `Missao` |
| Tratamento de Exceções | `EntradaInvalidaException` em `Main` e `Jogo` (try-catch) |

---

## Estrutura do Projeto

```
src/guardiao/
├── Main.java                  ← ponto de entrada, menu, loop try-catch
├── personagens/
│   ├── Personagem.java         ← classe abstrata base
│   ├── Explorador.java         ← nível 1
│   ├── Descontaminador.java    ← nível 2
│   ├── Botanico.java           ← nível 3
│   ├── Construtor.java         ← nível 4
│   └── Guardiao.java           ← nível 5
├── missoes/
│   ├── Missao.java             ← interface
│   └── MissaoEcologica.java    ← implementação
├── npc/
│   └── NPC.java
├── sistema/
│   ├── Pontuacao.java          ← cálculo de pontos + bônus
│   └── Jogo.java               ← orquestra os 5 níveis
└── excecoes/
    └── EntradaInvalidaException.java
```

---

## Compilar do Código-Fonte

```bash
find src -name "*.java" | xargs javac -encoding UTF-8 -d out/
jar cfe EcoCity.jar guardiao.Main -C out/ .
java -jar EcoCity.jar
```
