package guardiao.ui;

import java.util.Scanner;

// ============================================================
// Classe utilitaria de interface no terminal.
// Controla a limpeza de tela e a pausa entre telas, permitindo
// que cada nivel avance para uma "tela" nova ao apertar ENTER.
// ============================================================
public class Terminal {

    // ATRIBUTO FINAL - linha decorativa fixa
    public static final String LINHA =
            "  ============================================";

    // Limpa a tela (escape ANSI) - usado para avancar de tela.
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Aguarda o jogador apertar ENTER para avancar para a proxima tela.
    public static void aguardarEnter(Scanner scanner) {
        System.out.print("\n  [ Pressione ENTER para continuar... ]");
        scanner.nextLine();
    }

    public static void linha() {
        System.out.println(LINHA);
    }

    // Pequena pausa em milissegundos.
    public static void pausa(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
