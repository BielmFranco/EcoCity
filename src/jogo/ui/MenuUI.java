package jogo.ui;

import jogo.entidades.ClassePersonagem;
import jogo.entidades.Jogador;
import jogo.excecoes.EntradaInvalidaException;

// Classe responsável pela UI do menu e criação de personagem
public class MenuUI {

    // Atributo Estático - versão do jogo
    public static final String VERSAO = "1.0";

    public static void exibirBoasVindas() {
        Terminal.limparTela();
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════════════╗");
        System.out.println("  ║                                                          ║");
        System.out.println("  ║              ★  E C O C I T Y  ★                       ║");
        System.out.println("  ║                                                          ║");
        System.out.println("  ║      RPG educativo inspirado em \"O Lorax\" de Dr. Seuss  ║");
        System.out.println("  ║                                                          ║");
        System.out.println("  ║              Versão " + VERSAO + " — APS UNIP 2026/1              ║");
        System.out.println("  ║                                                          ║");
        System.out.println("  ╚══════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  Grupo:");
        System.out.println("  ├ Alessandra Cristina da Silva Souza  — R799565");
        System.out.println("  ├ Caetano de Paula Telles Ribeiro     — R688GC4");
        System.out.println("  ├ Jhonnatan Pereira Santos            — R363JH0");
        System.out.println("  ├ Guilherme Moraes Franco             — H386632");
        System.out.println("  └ Gabriel Moraes Franco               — H384338");
        System.out.println();
        Terminal.digitando("  Você habita MetroCinza — uma cidade artificial onde tudo", 25);
        Terminal.digitando("  é concreto, metal e fumaça. Os habitantes acreditam ser", 25);
        Terminal.digitando("  este o mundo ideal: funcional, controlado, perfeito.", 25);
        System.out.println();
        Terminal.digitando("  Mas hoje você encontrou algo que ninguém mais via há décadas:", 25);
        Terminal.digitando("  um vestígio de natureza abandonada.", 25);
        System.out.println();
        Terminal.aguardarEnter();
    }

    public static void exibirMenuPrincipal() {
        Terminal.limparTela();
        System.out.println();
        System.out.println("  ╔══════════════════════════════╗");
        System.out.println("  ║        E C O C I T Y        ║");
        System.out.println("  ╠══════════════════════════════╣");
        System.out.println("  ║  [1] Nova Jornada            ║");
        System.out.println("  ║  [2] Como Jogar              ║");
        System.out.println("  ║  [3] Créditos                ║");
        System.out.println("  ║  [4] Sair                    ║");
        System.out.println("  ╚══════════════════════════════╝");
        System.out.print("\n  Sua escolha: ");
    }

    public static void exibirComoJogar() {
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  COMO JOGAR");
        Terminal.linha();
        System.out.println();
        System.out.println("  • Explore MetroCinza fazendo escolhas numeradas.");
        System.out.println("  • Conclua missões ambientais: reciclagem, plantação de árvores, etc.");
        System.out.println("  • Interaja com os habitantes — inicialmente resistentes à mudança.");
        System.out.println("  • Conscientize os NPCs para conseguir apoio e reconstruir a cidade.");
        System.out.println("  • O progresso depende das missões e dos NPCs conscientizados.");
        System.out.println("  • Desbloqueie novas áreas conforme a restauração avança.");
        System.out.println();
        System.out.println("  ATRIBUTOS:");
        System.out.println("  ├ HP           — Pontos de vida. Zero = fim de jogo.");
        System.out.println("  ├ Ataque       — Dano base nos confrontos.");
        System.out.println("  ├ Defesa       — Reduz dano recebido.");
        System.out.println("  ├ Nível        — Sobe com XP. Melhora atributos.");
        System.out.println("  └ Consciência  — Permite despertar NPCs pelo diálogo.");
        System.out.println();
        System.out.println("  TÍTULOS DE NÍVEL:");
        System.out.println("  ├ Nível  1-5  — Aprendiz Ecológico");
        System.out.println("  ├ Nível  6-10 — Agente Urbano");
        System.out.println("  └ Nível 11+   — Guardião da Cidade");
        System.out.println();
        Terminal.aguardarEnter();
    }

    public static void exibirCreditos() {
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  ECOCITY — APS UNIP 2026/1 | LPOO");
        Terminal.linhaSplit();
        System.out.println();
        System.out.println("  Grupo:");
        System.out.println();
        System.out.println("  ├ Alessandra Cristina da Silva Souza  — R799565");
        System.out.println("  ├ Caetano de Paula Telles Ribeiro     — R688GC4");
        System.out.println("  ├ Jhonnatan Pereira Santos            — R363JH0");
        System.out.println("  ├ Guilherme Moraes Franco             — H386632");
        System.out.println("  └ Gabriel Moraes Franco               — H384338");
        System.out.println();
        Terminal.linhaSplit();
        System.out.println("  Inspirado em \"O Lorax\" — Dr. Seuss");
        Terminal.linha();
        System.out.println();
        Terminal.aguardarEnter();
    }

    // Tratamento de Exceções - criação de personagem com validação
    public static Jogador criarPersonagem() {
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  CRIAÇÃO DE PERSONAGEM");
        Terminal.linha();
        System.out.println();
        System.out.print("  Digite seu nome (ou pressione Enter para \"Sobrevivente\"): ");

        String nome;
        try {
            nome = Terminal.lerLinha().trim();
            if (nome.isEmpty()) nome = "Sobrevivente";
            if (nome.length() > 20) {
                throw new EntradaInvalidaException("Nome muito longo (máx 20 caracteres).");
            }
        } catch (EntradaInvalidaException e) {
            System.out.println("  ⚠ " + e.getMessage() + " Usando 'Sobrevivente'.");
            nome = "Sobrevivente";
        }

        System.out.println("\n  Escolha sua classe, " + nome + ":");
        System.out.println();

        ClassePersonagem[] classes = ClassePersonagem.values();
        for (int i = 0; i < classes.length; i++) {
            ClassePersonagem c = classes[i];
            System.out.printf("  [%d] %-20s%n", i + 1, c.getNome());
            System.out.printf("      \"%s\"%n", c.getDescricao());
            System.out.printf("      HP: %d | Ataque: %d | Defesa: %d%n%n",
                c.getHpBase(), c.getAtaqueBase(), c.getDefesaBase());
        }

        System.out.print("  Sua escolha (1-" + classes.length + "): ");
        int escolha = Terminal.lerInt(1, classes.length);
        ClassePersonagem classeSelecionada = classes[escolha - 1];

        Jogador jogador = new Jogador(nome, classeSelecionada);

        System.out.println();
        Terminal.linha();
        System.out.println("  Personagem criado!");
        System.out.println("  " + jogador.descricao());
        Terminal.linha();
        Terminal.aguardarEnter();

        return jogador;
    }

    public static void exibirStatusJogador(Jogador jogador) {
        System.out.println();
        Terminal.linha();
        System.out.println("  STATUS: " + jogador.getNome());
        Terminal.linhaSplit();
        System.out.printf("  Classe     : %s (Nível %d — %s)%n",
            jogador.getClasse().getNome(), jogador.getNivel(), jogador.getTituloNivel());
        System.out.printf("  HP         : %s %d/%d%n",
            jogador.barraHP(), jogador.getHP(), jogador.getHPMax());
        System.out.printf("  Consciência: %d%%%n", jogador.getConsciencia());
        System.out.printf("  XP         : %d/%d%n",
            jogador.getExperiencia(), jogador.XP_POR_NIVEL);
        System.out.printf("  Ataque     : %d | Defesa: %d%n",
            jogador.getAtaque(), jogador.getDefesa());
        Terminal.linha();
    }
}
