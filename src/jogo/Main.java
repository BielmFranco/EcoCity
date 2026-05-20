package jogo;

import jogo.entidades.Jogador;
import jogo.mundo.Mundo;
import jogo.ui.MenuUI;
import jogo.ui.Terminal;
import jogo.excecoes.EntradaInvalidaException;

/**
 * ECOCITY — RPG Textual
 *
 * Projeto APS — UNIP 2026/1
 * Disciplina: LPOO — Linguagem de Programação Orientada a Objetos
 *
 * Grupo:
 *   Alessandra Cristina da Silva Souza  — R799565
 *   Caetano de Paula Telles Ribeiro     — R688GC4
 *   Jhonnatan Pereira Santos            — R363JH0
 *   Guilherme Moraes Franco             — H386632
 *   Gabriel Moraes Franco               — H384338
 *
 * Conceitos de OO utilizados (marcados nos arquivos fonte):
 *   - Encapsulamento         (Entidade.java, Personagem.java)
 *   - Método Construtor      (todas as classes)
 *   - Herança                (Personagem -> Entidade, Jogador -> Personagem, NPC -> Entidade, Pocao -> Item)
 *   - Sobrecarga             (NPC.java, Pocao.java, Inventario.java)
 *   - Sobrescrita            (Jogador.java, NPC.java)
 *   - Polimorfismo           (ClassePersonagem.java enum + métodos abstratos)
 *   - Método Abstrato        (Entidade.java, Item.java, Personagem.java)
 *   - Classe Abstrata        (Entidade.java, Item.java, Personagem.java)
 *   - Classe Final           (SementeTruffula em Pocao.java)
 *   - Atributo Final         (ClassePersonagem.java, Item.java, Inventario.java)
 *   - Atributo Estático      (Entidade.java, MenuUI.java, Mundo.java, Terminal.java)
 *   - Interface              (Atacavel.java, Utilizavel.java)
 *   - Tratamento de Exceções (Terminal.java, MenuUI.java, SistemaCombate.java, Main.java)
 */
public class Main {

    public static void main(String[] args) {
        // Tratamento de Exceções - bloco principal do jogo
        try {
            MenuUI.exibirBoasVindas();

            boolean rodando = true;
            while (rodando) {
                MenuUI.exibirMenuPrincipal();

                // Tratamento de Exceções - leitura do menu
                int opcao;
                try {
                    opcao = Integer.parseInt(Terminal.lerLinha().trim());
                    if (opcao < 1 || opcao > 4) {
                        throw new EntradaInvalidaException("Opção inválida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\n  ⚠ Digite 1, 2, 3 ou 4.");
                    Terminal.pausa(1000);
                    continue;
                } catch (EntradaInvalidaException e) {
                    System.out.println("\n  ⚠ " + e.getMessage());
                    Terminal.pausa(1000);
                    continue;
                }

                switch (opcao) {
                    case 1:
                        Jogador jogador = MenuUI.criarPersonagem();
                        Mundo mundo = new Mundo(jogador);
                        mundo.iniciar();
                        break;
                    case 2:
                        MenuUI.exibirComoJogar();
                        break;
                    case 3:
                        MenuUI.exibirCreditos();
                        break;
                    case 4:
                        Terminal.limparTela();
                        System.out.println();
                        Terminal.digitando("  \"A não ser que alguém como você cuide o suficiente,", 20);
                        Terminal.digitando("   nada vai melhorar. Não vai, de jeito nenhum.\"", 20);
                        Terminal.digitando("                              — O Lorax", 20);
                        System.out.println();
                        Terminal.pausa(2000);
                        rodando = false;
                        break;
                }
            }

        } catch (Exception e) {
            // Tratamento de Exceções - captura de erros inesperados
            System.err.println("\n  ERRO INESPERADO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Tratamento de Exceções - bloco finally garante fechamento do scanner
            Terminal.fechar();
            System.out.println("\n  Até a próxima jornada.");
        }
    }
}
