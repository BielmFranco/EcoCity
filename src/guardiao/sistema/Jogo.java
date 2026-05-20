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

import java.util.Scanner;

// ============================================================
// Orquestra a progressao do jogo: niveis 1 a 5.
// Nivel N possui N NPCs e N missoes (1 missao por NPC).
// ============================================================
public class Jogo {

    // ENCAPSULAMENTO
    private Personagem jogador;
    private int pontuacaoTotal;
    private int missoesTotais;
    private final Scanner scanner;

    // CONSTRUTOR
    public Jogo(Scanner scanner) {
        this.scanner = scanner;
        this.pontuacaoTotal = 0;
        this.missoesTotais = 0;
    }

    public void iniciar() {
        System.out.print("\n  Digite o nome do seu personagem: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            nome = "Guardiao";
        }

        // Percorre os 5 niveis. A cada nivel o personagem EVOLUI
        // para a subclasse correspondente, mantendo a pontuacao.
        for (int nivel = 1; nivel <= 5; nivel++) {
            jogador = evoluir(nome, nivel, pontuacaoTotal);
            jogarNivel(nivel);
        }

        exibirFinal();
    }

    // HERANCA / SOBRESCRITA em uso - retorna a subclasse do nivel.
    // Cada subclasse tem seu proprio getTitulo() e getClasseIdeal().
    private Personagem evoluir(String nome, int nivel, int pontos) {
        switch (nivel) {
            case 1:  return new Explorador(nome, pontos);
            case 2:  return new Descontaminador(nome, pontos);
            case 3:  return new Botanico(nome, pontos);
            case 4:  return new Construtor(nome, pontos);
            default: return new Guardiao(nome, pontos);
        }
    }

    private void jogarNivel(int nivel) {
        System.out.println("\n  ============================================");
        System.out.printf("   NIVEL %d - %s%n", nivel, jogador.getTitulo());
        System.out.printf("   Missoes: %d  |  NPCs: %d%n", nivel, nivel);
        System.out.println("  ============================================");

        // Nivel N: cria N NPCs, cada um com 1 missao (distribuicao igual).
        NPC[] npcs = criarNPCs(nivel);

        for (int i = 0; i < npcs.length; i++) {
            NPC npc = npcs[i];
            System.out.printf("%n  > %s tem uma missao para voce.%n", npc.getNome());

            Missao missao = npc.entregarMissao();
            System.out.println("    Missao : " + missao.getDescricao());
            System.out.println("    Limite : " + missao.getTurnosLimite() + " turnos (para bonus)");

            boolean classeIdeal = executarMissao(missao);

            // OPCAO 2 em uso - calcula pontos com bonificacao
            int pontos = Pontuacao.calcular(nivel, missao, classeIdeal);
            jogador.adicionarPontos(pontos);
            jogador.registrarMissao();
            pontuacaoTotal += pontos;
            missoesTotais++;

            System.out.printf("    [OK] Missao cumprida! +%d pontos | Total: %d%n",
                    pontos, pontuacaoTotal);
        }
    }

    // Cria N NPCs com N missoes - 1 missao por NPC.
    private NPC[] criarNPCs(int nivel) {
        NPC[] npcs = new NPC[nivel];
        for (int i = 0; i < nivel; i++) {
            Missao missao = new MissaoEcologica(
                    "Tarefa ecologica #" + (i + 1) + " do nivel " + nivel,
                    Pontuacao.calcular(nivel),
                    3 + nivel);
            npcs[i] = new NPC("NPC " + nivel + "." + (i + 1), missao);
        }
        return npcs;
    }

    // TRATAMENTO DE EXCECOES - le a abordagem do jogador com seguranca.
    // Retorna true se o jogador usou a classe ideal.
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
                    // dentro do limite -> ganha bonus de eficiencia
                    missao.concluir(missao.getTurnosLimite());
                    return true;
                } else {
                    // estoura o limite -> sem bonus de eficiencia
                    missao.concluir(missao.getTurnosLimite() + 3);
                    return false;
                }

            } catch (NumberFormatException e) {
                // captura letras digitadas no lugar de numeros
                System.out.println("    [ERRO] Digite apenas numeros.");
            } catch (EntradaInvalidaException e) {
                System.out.println("    [ERRO] " + e.getMessage());
            }
        }
    }

    private void exibirFinal() {
        System.out.println("\n  ============================================");
        System.out.println("   FIM DE JOGO");
        System.out.println("   " + jogador.getNome() + " agora e: " + jogador.getTitulo());
        System.out.println("   Pontuacao total : " + pontuacaoTotal);
        System.out.println("   Missoes cumpridas: " + missoesTotais + " (de 15)");
        System.out.println("  ============================================");
    }
}
