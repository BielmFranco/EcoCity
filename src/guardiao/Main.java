package guardiao;

import guardiao.sistema.Jogo;

import java.util.Scanner;

// ============================================================
// ECOCITY - RPG Textual - APS UNIP 2026/1 | LPOO
//
// Ponto de entrada do jogo. Loop principal com menu de opcoes.
// Usa TRY-CATCH para impedir que o jogo quebre se o usuario
// digitar letras no lugar de numeros.
//
// Grupo:
//   Alessandra Cristina da Silva Souza - R799565
//   Caetano de Paula Telles Ribeiro    - R688GC4
//   Jhonnatan Pereira Santos           - R363JH0
//   Guilherme Moraes Franco            - H386632
//   Gabriel Moraes Franco              - H384338
//
// Conceitos de O.O. aplicados (marcados nos arquivos fonte):
//   - Encapsulamento, Construtores, Heranca
//   - Sobrecarga, Sobrescrita
//   - Classe Abstrata, Metodo Abstrato
//   - Interface, Tratamento de Excecoes (try-catch)
// ============================================================
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

        exibirAbertura();

        // LOOP PRINCIPAL do jogo
        while (rodando) {
            exibirMenu();

            // TRATAMENTO DE EXCECOES - protege a leitura do menu
            try {
                int opcao = Integer.parseInt(scanner.nextLine().trim());

                switch (opcao) {
                    case 1:
                        new Jogo(scanner).iniciar();
                        break;
                    case 2:
                        exibirInstrucoes();
                        break;
                    case 3:
                        exibirCreditos();
                        break;
                    case 4:
                        System.out.println("\n  Encerrando o jogo. Ate logo!");
                        rodando = false;
                        break;
                    default:
                        System.out.println("\n  [ERRO] Opcao inexistente. Digite 1, 2, 3 ou 4.");
                }

            } catch (NumberFormatException e) {
                // captura quando o usuario digita letras em vez de numeros
                System.out.println("\n  [ERRO] Entrada invalida! Digite apenas numeros.");
            } catch (Exception e) {
                // rede de seguranca para qualquer outro erro inesperado
                System.out.println("\n  [ERRO] Falha inesperada: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void exibirAbertura() {
        System.out.println();
        System.out.println("  ============================================");
        System.out.println("              E C O C I T Y   -   R P G");
        System.out.println("       Inspirado na obra \"O Lorax\" - Dr. Seuss");
        System.out.println("  ============================================");
        System.out.println("   Uma cidade devastada pela poluicao e pelo");
        System.out.println("   desmatamento. Evolua do nivel 1 ao 5 e");
        System.out.println("   torne-se o GUARDIAO DA NATUREZA.");
        System.out.println("  ============================================");
    }

    private static void exibirMenu() {
        System.out.println("\n  +==================================+");
        System.out.println("  |          ECOCITY - RPG           |");
        System.out.println("  +==================================+");
        System.out.println("  |  [1] Iniciar Jogo                |");
        System.out.println("  |  [2] Instrucoes                  |");
        System.out.println("  |  [3] Creditos                    |");
        System.out.println("  |  [4] Sair                        |");
        System.out.println("  +==================================+");
        System.out.print("   Sua escolha: ");
    }

    private static void exibirInstrucoes() {
        System.out.println("\n  INSTRUCOES:");
        System.out.println("   - Evolua do nivel 1 ao 5.");
        System.out.println("   - Cada nivel N tem N NPCs e N missoes (1 por NPC).");
        System.out.println("   - Cumpra as missoes para ganhar pontos.");
        System.out.println("   - Pontos sobem com a dificuldade do nivel.");
        System.out.println("   - Use a classe ideal para ganhar bonus de eficiencia.");
        System.out.println("   - Torne-se o GUARDIAO DA NATUREZA.");
        System.out.println();
        System.out.println("  NIVEIS / CLASSES:");
        System.out.println("   Nivel 1 - Iniciante / Explorador");
        System.out.println("   Nivel 2 - Basico / Descontaminador");
        System.out.println("   Nivel 3 - Intermediario / Botanico");
        System.out.println("   Nivel 4 - Experiente / Construtor");
        System.out.println("   Nivel 5 - Final / Guardiao da Natureza");
    }

    private static void exibirCreditos() {
        System.out.println("\n  CREDITOS - APS UNIP 2026/1 | LPOO");
        System.out.println("  --------------------------------------------");
        System.out.println("   Grupo:");
        System.out.println("   - Alessandra Cristina da Silva Souza - R799565");
        System.out.println("   - Caetano de Paula Telles Ribeiro    - R688GC4");
        System.out.println("   - Jhonnatan Pereira Santos           - R363JH0");
        System.out.println("   - Guilherme Moraes Franco            - H386632");
        System.out.println("   - Gabriel Moraes Franco              - H384338");
        System.out.println("  --------------------------------------------");
        System.out.println("   Inspirado em \"O Lorax\" - Dr. Seuss");
    }
}
