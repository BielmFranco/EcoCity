package jogo.ui;

import java.util.Scanner;
import jogo.excecoes.EntradaInvalidaException;

// Classe utilitária para I/O no terminal
public class Terminal {

    // Atributo Estático - Scanner compartilhado
    private static final Scanner scanner = new Scanner(System.in);

    // Atributo Final
    public static final String LINHA = "═".repeat(60);
    public static final String LINHA_SIMPLES = "─".repeat(60);

    public static void linha() {
        System.out.println("  " + LINHA);
    }

    public static void linhaSplit() {
        System.out.println("  " + LINHA_SIMPLES);
    }

    public static String lerLinha() {
        return scanner.nextLine();
    }

    // Tratamento de Exceções - leitura de inteiro com validação
    public static int lerInt(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int valor = Integer.parseInt(input);
                if (valor < min || valor > max) {
                    throw new EntradaInvalidaException(
                        "Digite um número entre " + min + " e " + max + ".");
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Entrada inválida. Digite um número!");
                System.out.print("  > ");
            } catch (EntradaInvalidaException e) {
                System.out.println("  ⚠ " + e.getMessage());
                System.out.print("  > ");
            }
        }
    }

    public static void pausa(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void limparTela() {
        // Em terminais Unix/Mac usa escape ANSI; Windows apenas imprime linhas
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void digitando(String texto, int delayMs) {
        for (char c : texto.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.print(texto.substring(texto.indexOf(c)));
                return;
            }
        }
        System.out.println();
    }

    public static void aguardarEnter() {
        System.out.print("\n  [ Pressione ENTER para continuar... ]");
        scanner.nextLine();
    }

    public static void fechar() {
        scanner.close();
    }
}
