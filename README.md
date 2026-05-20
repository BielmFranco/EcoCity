# A Última Semente 🌱
### RPG Textual — APS UNIP 2026/1 | LPOO

---

## Como Jogar

**Pré-requisito:** Java 11 ou superior instalado.

**Windows:**
```
jogar.bat
```

**Linux / Mac:**
```bash
chmod +x jogar.sh && ./jogar.sh
```

**Direto pelo terminal:**
```bash
java -jar UltimaSemente.jar
```

---

## Sobre o Jogo

Em 2157, as árvores Truffula desapareceram. A humanidade sobreviveu, mas perdeu
sua alma — tornando-se "robôs sociais": funcionais, mas vazios.

Você encontrou a última semente Truffula. Sua missão: atravessar Nova Cinzópolis,
conscientizar seus habitantes e plantar a semente no Jardim Central.

### Personagens do Jogo

| Personagem       | Tipo        | Papel                                     |
|------------------|-------------|-------------------------------------------|
| Velho Aramis     | Cidadão     | Primeiro NPC. Guarda memórias do verde.   |
| Dra. Léa Verona  | Aliada      | Última botânica. Fornece itens e missão.  |
| Guarda Ferro MK-7| Inimigo     | Protetor do Jardim Central. Pode ser dialogado. |
| Diretor Orloff   | Antagonista | Chefe da CorpVerde. Boss final.           |

### Classes Disponíveis

| Classe           | HP  | ATK | DEF | Habilidade Especial        |
|------------------|-----|-----|-----|----------------------------|
| Catador de Lixo  | 90  | 12  | 8   | Reciclagem Rápida          |
| Botânico Rebelde | 70  | 8   | 6   | Sporos Tóxicos             |
| Hacker Ecológico | 65  | 10  | 4   | Sobrecarga Digital         |

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
