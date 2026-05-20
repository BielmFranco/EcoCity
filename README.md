# EcoCity 🌱
### RPG Textual — APS UNIP 2026/1 | LPOO

RPG educativo inspirado na obra **"O Lorax"** de Dr. Seuss. O jogador
reintroduz a natureza em uma metrópole dominada pela poluição e
conscientiza a população sobre sustentabilidade.

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

## Sobre o Jogo

Você habita **MetroCinza**, uma cidade artificial onde os habitantes
acreditam viver no mundo ideal: concreto, metal e fumaça. Ao encontrar
um vestígio de natureza abandonada — a última semente Truffula — você
decide restaurar a cidade.

No início, os moradores alienados resistem à mudança. Ao longo da
jornada, você conscientiza os cidadãos, que se juntam para reconstruir
a cidade e transformar MetroCinza em **EcoCity**.

### Personagens

| Personagem        | Tipo        | Papel                                          |
|-------------------|-------------|------------------------------------------------|
| Cidadão Aramis    | Cidadão     | Primeiro NPC. Resistente, depois conscientizado.|
| Dra. Léa Verona   | Aliada      | Última botânica. Fornece itens e missão.       |
| Guarda Ferro MK-7 | Guarda      | Bloqueia o Jardim Central. Pode ser dialogado. |
| Diretor Orloff    | Antagonista | Chefe da CorpVerde. Confronto final.           |

### Classes do Jogador

| Classe     | HP | ATK | DEF | Função                              |
|------------|----|-----|-----|-------------------------------------|
| Biólogo    | 80 | 8   | 7   | Plantação e regeneração de áreas    |
| Engenheiro | 85 | 12  | 10  | Construção e criação                |
| Ativista   | 70 | 6   | 5   | Convencer cidadãos, reduz resistência social |
| Explorador | 75 | 10  | 6   | Acessa áreas restritas, recolhe recursos |

### Níveis

| Nível    | Título               |
|----------|----------------------|
| 1 – 5    | Aprendiz Ecológico   |
| 6 – 10   | Agente Urbano        |
| 11+      | Guardião da Cidade   |

### Finais

- **Positivo:** restauração completa da cidade.
- **Negativo:** a cidade não é restaurada, os NPCs não são
  conscientizados e a degradação continua.

---

## Conceitos de OO Implementados

Todos marcados com comentários `// <conceito>` nos arquivos fonte:

- **Encapsulamento** — `Entidade.java`, `Personagem.java`
- **Método Construtor** — todas as classes
- **Herança** — `Jogador → Personagem → Entidade`, `NPC → Entidade`, `Pocao → Item`
- **Sobrecarga** — `NPC.java`, `Pocao.java`, `Inventario.java`
- **Sobrescrita** — `Jogador.java`, `NPC.java`
- **Polimorfismo** — `ClassePersonagem.java` (enum com métodos abstratos)
- **Método Abstrato** — `Entidade.java`, `Item.java`, `Personagem.java`
- **Classe Abstrata** — `Entidade.java`, `Item.java`, `Personagem.java`
- **Classe Final** — `SementeTruffula` em `Pocao.java`
- **Atributo Final** — `ClassePersonagem.java`, `Item.java`, `Inventario.java`
- **Atributo Estático** — `Entidade.java`, `Mundo.java`, `Terminal.java`
- **Interface** — `Atacavel.java`, `Utilizavel.java`
- **Tratamento de Exceções** — `Terminal.java`, `MenuUI.java`, `SistemaCombate.java`, `Main.java`

---

## Compilar do Código-Fonte

```bash
javac -encoding UTF-8 -d out/ src/jogo/**/*.java src/jogo/*.java
jar cfe EcoCity.jar jogo.Main -C out/ .
```
