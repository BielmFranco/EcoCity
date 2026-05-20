package guardiao;

import guardiao.sistema.Jogo;

import java.util.Scanner;

// ============================================================
// OPCAO 3 - FLUXO DE JOGO
// Ponto de entrada. Loop principal com menu de opcoes.
// Usa TRY-CATCH para impedir que o jogo quebre se o usuario
// digitar letras no lugar de numeros.
//
// GUARDIAO DA NATUREZA - RPG Textual - APS UNIP
// ============================================================
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

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
                        System.out.println("\n  Encerrando o jogo. Ate logo!");
                        rodando = false;
                        break;
                    default:
                        System.out.println("\n  [ERRO] Opcao inexistente. Digite 1, 2 ou 3.");
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

    private static void exibirMenu() {
        System.out.println("\n  +==================================+");
        System.out.println("  |    GUARDIAO DA NATUREZA - RPG     |");
        System.out.println("  +==================================+");
        System.out.println("  |  [1] Iniciar Jogo                |");
        System.out.println("  |  [2] Instrucoes                  |");
        System.out.println("  |  [3] Sair                        |");
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
    }
}
