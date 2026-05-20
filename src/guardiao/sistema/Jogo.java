package guardiao.sistema;

import guardiao.excecoes.EntradaInvalidaException;
import guardiao.missoes.Missao;
import guardiao.missoes.MissaoEcologica;
import guardiao.npc.NPC;
import guardiao.personagens.Botanico;
import guardiao.personagens.Construtor;
import guardiao.personagens.Descontaminador;
import guardiao.personagens.Explorador;
import guardiao.personagens.Guardiao;
import guardiao.personagens.Personagem;
import guardiao.ui.Terminal;

import java.util.Scanner;

// ============================================================
// Orquestra a progressao do jogo: niveis 1 a 5.
// Cada nivel avanca para uma TELA NOVA (limpa a tela e aguarda
// ENTER), exibindo o enredo da restauracao ambiental da cidade.
// Nivel N possui N NPCs e N missoes (1 missao por NPC).
// ============================================================
public class Jogo {

    // ENCAPSULAMENTO
    private Personagem jogador;
    private int pontuacaoTotal;
    private int missoesTotais;
    private final Scanner scanner;

    // Missoes tematicas de cada nivel (nivel N tem N missoes).
    private static final String[][] MISSOES = {
        { "Encontrar uma semente rara e planta-la num terreno baldio" },

        { "Despoluir as aguas do rio central",
          "Filtrar o ar toxico da praca principal" },

        { "Replantar a mata ciliar destruida",
          "Criar uma horta comunitaria",
          "Recuperar especies de plantas nativas" },

        { "Construir um parque urbano arborizado",
          "Instalar paineis solares nos predios",
          "Erguer um sistema de captacao de chuva",
          "Criar um corredor verde entre os bairros" },

        { "Restaurar a grande reserva central",
          "Reflorestar a antiga zona industrial",
          "Despoluir o lago da cidade",
          "Selar a ultima fonte de poluicao",
          "Inaugurar o santuario da natureza" }
    };

    // Texto narrativo exibido na tela de cada nivel.
    private static final String[] NARRATIVA = {
        "Voce chega a uma cidade sufocada pela fumaca. As ruas sao\n" +
        "  cinzas, sem uma unica folha verde. Como EXPLORADOR iniciante,\n" +
        "  sua missao e encontrar onde a vida ainda resiste.",

        "As primeiras areas respondem ao seu esforco. Agora, como\n" +
        "  DESCONTAMINADOR, voce ataca a origem do problema: limpar o\n" +
        "  solo e as aguas envenenadas pela poluicao.",

        "O solo limpo pede vida nova. Como BOTANICO, voce devolve a\n" +
        "  cidade o que o desmatamento levou: arvores, plantas e cor.",

        "A natureza retorna - mas a cidade precisa aprender a conviver\n" +
        "  com ela. Como CONSTRUTOR, voce ergue estruturas sustentaveis\n" +
        "  que unem tecnologia e meio ambiente.",

        "O equilibrio ambiental esta quase restaurado. Esta e a prova\n" +
        "  final: conclua as ultimas missoes e torne-se o\n" +
        "  GUARDIAO DA NATUREZA."
    };

    // CONSTRUTOR
    public Jogo(Scanner scanner) {
        this.scanner = scanner;
        this.pontuacaoTotal = 0;
        this.missoesTotais = 0;
    }

    public void iniciar() {
        // TELA 1 - enredo do jogo
        Terminal.limparTela();
        exibirEnredo();
        Terminal.aguardarEnter(scanner);

        // TELA 2 - criacao do personagem
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("              CRIACAO DE PERSONAGEM");
        Terminal.linha();
        System.out.print("\n  Digite o nome do seu personagem: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            nome = "Guardiao";
        }

        // Percorre os 5 niveis. Cada nivel = uma tela nova.
        for (int nivel = 1; nivel <= 5; nivel++) {
            jogador = evoluir(nome, nivel, pontuacaoTotal);

            // TELA do nivel - narrativa + ENTER para comecar
            Terminal.limparTela();
            exibirNarrativaNivel(nivel);
            Terminal.aguardarEnter(scanner);

            jogarNivel(nivel);

            // Tela de conclusao do nivel + ENTER para avancar
            exibirFimDeNivel(nivel);
            Terminal.aguardarEnter(scanner);
        }

        // TELA final
        Terminal.limparTela();
        exibirFinal();
    }

    // Enredo - apresentado no inicio do jogo.
    private void exibirEnredo() {
        Terminal.linha();
        System.out.println("              E C O C I T Y  -  E N R E D O");
        Terminal.linha();
        System.out.println("   A cidade foi devastada pela poluicao, pelo desmatamento");
        System.out.println("   e por conflitos ambientais. O ar esta cinza, os rios");
        System.out.println("   estao mortos e as arvores desapareceram.");
        System.out.println();
        System.out.println("   Voce decide mudar esse destino. Cumprindo missoes");
        System.out.println("   ecologicas, ira restaurar o equilibrio ambiental da");
        System.out.println("   cidade, evoluindo progressivamente ate alcancar o");
        System.out.println("   titulo de GUARDIAO DA NATUREZA.");
        Terminal.linha();
    }

    // HERANCA / SOBRESCRITA em uso - retorna a subclasse do nivel.
    private Personagem evoluir(String nome, int nivel, int pontos) {
        switch (nivel) {
            case 1:  return new Explorador(nome, pontos);
            case 2:  return new Descontaminador(nome, pontos);
            case 3:  return new Botanico(nome, pontos);
            case 4:  return new Construtor(nome, pontos);
            default: return new Guardiao(nome, pontos);
        }
    }

    // Tela de abertura de um nivel - parte do enredo.
    private void exibirNarrativaNivel(int nivel) {
        Terminal.linha();
        System.out.printf("   NIVEL %d - %s%n", nivel, jogador.getTitulo());
        Terminal.linha();
        System.out.println("  " + NARRATIVA[nivel - 1]);
        System.out.println();
        System.out.printf("   Missoes deste nivel: %d  |  NPCs: %d%n", nivel, nivel);
        Terminal.linha();
    }

    private void jogarNivel(int nivel) {
        // Nivel N: cria N NPCs, cada um com 1 missao.
        NPC[] npcs = criarNPCs(nivel);

        for (int i = 0; i < npcs.length; i++) {
            NPC npc = npcs[i];
            System.out.printf("%n  > %s tem uma missao para voce.%n", npc.getNome());

            Missao missao = npc.entregarMissao();
            System.out.println("    Missao : " + missao.getDescricao());
            System.out.println("    Limite : " + missao.getTurnosLimite() + " turnos (para bonus)");

            boolean classeIdeal = executarMissao(missao);

            // Calcula pontos com bonificacao.
            int pontos = Pontuacao.calcular(nivel, missao, classeIdeal);
            jogador.adicionarPontos(pontos);
            jogador.registrarMissao();
            pontuacaoTotal += pontos;
            missoesTotais++;

            System.out.printf("    [OK] Missao cumprida! +%d pontos | Total: %d%n",
                    pontos, pontuacaoTotal);
        }
    }

    // Cria N NPCs com N missoes - 1 missao tematica por NPC.
    private NPC[] criarNPCs(int nivel) {
        String[] tarefas = MISSOES[nivel - 1];
        NPC[] npcs = new NPC[nivel];
        for (int i = 0; i < nivel; i++) {
            Missao missao = new MissaoEcologica(
                    tarefas[i],
                    Pontuacao.calcular(nivel),
                    3 + nivel);
            npcs[i] = new NPC("NPC " + nivel + "." + (i + 1), missao);
        }
        return npcs;
    }

    // TRATAMENTO DE EXCECOES - le a abordagem do jogador com seguranca.
    private boolean executarMissao(Missao missao) {
        while (true) {
            try {
                System.out.println("    Como cumprir a missao?");
                System.out.println("     [1] Usar a classe ideal (eficiente)");
                System.out.println("     [2] Abordagem comum (mais turnos)");
                System.out.print("     > ");

                int opcao = Integer.parseInt(scanner.nextLine().trim());

                if (opcao != 1 && opcao != 2) {
                    throw new EntradaInvalidaException("Digite 1 ou 2.");
                }

                if (opcao == 1) {
                    missao.concluir(missao.getTurnosLimite());
                    return true;
                } else {
                    missao.concluir(missao.getTurnosLimite() + 3);
                    return false;
                }

            } catch (NumberFormatException e) {
                System.out.println("    [ERRO] Digite apenas numeros.");
            } catch (EntradaInvalidaException e) {
                System.out.println("    [ERRO] " + e.getMessage());
            }
        }
    }

    // Tela de conclusao de nivel - mostra o avanco da restauracao.
    private void exibirFimDeNivel(int nivel) {
        System.out.println();
        Terminal.linha();
        if (nivel < 5) {
            System.out.printf("   NIVEL %d CONCLUIDO!%n", nivel);
            System.out.println("   O equilibrio ambiental da cidade avanca um pouco mais.");
            System.out.println("   Voce evolui para o proximo nivel...");
        } else {
            System.out.println("   TODAS AS MISSOES FORAM CUMPRIDAS!");
            System.out.println("   O equilibrio ambiental foi restaurado.");
        }
        System.out.println("   Pontuacao acumulada: " + pontuacaoTotal);
        Terminal.linha();
    }

    // Tela final - jogador se torna o Guardiao da Natureza.
    private void exibirFinal() {
        Terminal.linha();
        System.out.println("                  F I M   D E   J O G O");
        Terminal.linha();
        System.out.println("   A cidade respira de novo. Os rios correm limpos, as");
        System.out.println("   arvores cobrem as ruas e o ar voltou a ser puro.");
        System.out.println();
        System.out.println("   " + jogador.getNome() + " restaurou o equilibrio ambiental");
        System.out.println("   e conquistou o titulo de GUARDIAO DA NATUREZA.");
        System.out.println();
        System.out.println("   Pontuacao total  : " + pontuacaoTotal);
        System.out.println("   Missoes cumpridas: " + missoesTotais + " (de 15)");
        Terminal.linha();
    }
}
