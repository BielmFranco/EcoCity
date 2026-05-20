# 🎮 Tutorial — Instalar e Jogar EcoCity

Guia passo a passo para instalar e jogar o EcoCity em qualquer computador.

---

## Passo 1 — Instalar o Java 21

O jogo precisa de **Java 21 ou superior**.

1. Acesse: **https://adoptium.net**
2. Clique em **"Latest LTS Release"** (versão 21).
3. Baixe o instalador `.msi` (Windows) ou o pacote do seu sistema.
4. Execute o instalador. Avance em tudo (next → next → install).
5. **Importante:** na tela de opções, marque **"Add to PATH"** / **"Set JAVA_HOME"** se aparecer.

**Verificar a instalação:** abra o CMD ou PowerShell e digite:

```
java -version
```

Deve aparecer `version "21..."`. Se aparecer uma versão menor que 21,
desinstale a antiga e reinstale a 21.

---

## Passo 2 — Baixar o jogo

### Opção A — Download ZIP (mais fácil, sem programa extra)

1. Acesse: **https://github.com/BielmFranco/EcoCity**
2. Clique no botão verde **`< > Code`**.
3. Clique em **`Download ZIP`**.
4. Extraia o ZIP baixado (botão direito → Extrair tudo).

### Opção B — Git clone (se tiver Git instalado)

```
git clone https://github.com/BielmFranco/EcoCity.git
```

---

## Passo 3 — Jogar

### Windows

1. Abra a pasta `EcoCity` extraída.
2. Dê **duplo-clique** em **`jogar.bat`**.
3. Uma janela abre com o jogo.

### Linux / Mac

```bash
cd EcoCity
chmod +x jogar.sh
./jogar.sh
```

### Qualquer sistema (pelo terminal)

```bash
java -jar EcoCity.jar
```

---

## Passo 4 — Como se joga

- O jogo é textual. Você lê o texto e escolhe **opções numeradas**.
- Digite o **número** da escolha e aperte **Enter**.
- Ao criar o personagem: escolha um nome e uma das 4 classes
  (Biólogo, Engenheiro, Ativista, Explorador).
- Objetivo: conscientizar os NPCs, restaurar MetroCinza e plantar a
  semente para transformar a cidade em EcoCity.

---

## ⚠️ Problemas comuns

| Erro | Causa | Solução |
|------|-------|---------|
| `'java' não é reconhecido` | Java não instalado ou fora do PATH | Refazer o Passo 1 e marcar "Add to PATH" |
| `UnsupportedClassVersionError` | Versão do Java menor que 21 | Instalar Java 21 ou superior |
| Acentos quebrados (`Ã©`, `?`) | Encoding errado | Usar `jogar.bat` (já corrige). Não rodar pelo CMD direto |
| A janela fecha sozinha | Erro na execução | Abrir o CMD na pasta e rodar `jogar.bat` para ver a mensagem |

---

*EcoCity — APS UNIP 2026/1 | LPOO*
