package jogo.combate;

import jogo.entidades.Jogador;
import jogo.entidades.NPC;
import jogo.itens.Item;
import jogo.excecoes.EntradaInvalidaException;
import jogo.ui.Terminal;

import java.util.Random;

// Classe que gerencia o sistema de combate em turnos
public class SistemaCombate {

    private static final Random rand = new Random();

    // Polimorfismo de método - resolve combate com NPC
    public static boolean resolverCombate(Jogador jogador, NPC inimigo) {
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  ⚔  CONFRONTO: " + jogador.getNome() + " VS " + inimigo.getNome());
        Terminal.linha();
        System.out.println("  " + inimigo.descricao());
        Terminal.pausa(1500);

        while (jogador.estaVivo() && inimigo.estaVivo()) {
            exibirStatusCombate(jogador, inimigo);
            int acao = escolherAcao(jogador);

            switch (acao) {
                case 1: atacarBasico(jogador, inimigo);       break;
                case 2: usarHabilidade(jogador, inimigo);     break;
                case 3: usarItem(jogador);                     break;
                case 4: tentarDialogo(jogador, inimigo);       break;
                case 5:
                    System.out.println("\n  Você recuou da batalha!");
                    return false; // fugiu
            }

            // Turno do inimigo
            if (inimigo.estaVivo()) {
                turnoInimigo(inimigo, jogador);
            }

            Terminal.pausa(1000);
        }

        if (!jogador.estaVivo()) {
            return false; // derrota
        }

        // Vitória
        int xpGanho = 20 + inimigo.getHPMax() / 5;
        jogador.ganharXP(xpGanho);
        System.out.println("\n  ✓ " + inimigo.getNome() + " foi neutralizado!");
        System.out.println("  + " + xpGanho + " XP ganhos.");
        return true;
    }

    private static void exibirStatusCombate(Jogador jogador, NPC inimigo) {
        System.out.println();
        Terminal.linha();
        System.out.printf("  %-20s HP: %s %d/%d%n",
            jogador.getNome(), jogador.barraHP(), jogador.getHP(), jogador.getHPMax());
        System.out.printf("  %-20s HP: %s %d/%d%n",
            inimigo.getNome(), inimigo.barraHP(), inimigo.getHP(), inimigo.getHPMax());
        Terminal.linha();
    }

    private static int escolherAcao(Jogador jogador) {
        // Tratamento de Exceções - loop até entrada válida
        while (true) {
            try {
                System.out.println("\n  O que você faz?");
                System.out.println("  [1] Atacar");
                System.out.println("  [2] Habilidade Especial: " + jogador.getClasse().getNome());
                System.out.println("  [3] Usar Item");
                System.out.println("  [4] Tentar Diálogo");
                System.out.println("  [5] Recuar");
                System.out.print("  > ");

                String input = Terminal.lerLinha();
                int opcao = Integer.parseInt(input.trim());

                if (opcao < 1 || opcao > 5) {
                    throw new EntradaInvalidaException("Opção deve ser entre 1 e 5.");
                }
                return opcao;

            } catch (NumberFormatException e) {
                // Tratamento de Exceções - entrada não numérica
                System.out.println("  ⚠ Digite um número válido!");
            } catch (EntradaInvalidaException e) {
                // Tratamento de Exceções - valor fora do intervalo
                System.out.println("  ⚠ " + e.getMessage());
            }
        }
    }

    private static void atacarBasico(Jogador jogador, NPC inimigo) {
        int dano = jogador.getAtaque() + rand.nextInt(6);
        boolean critico = rand.nextInt(10) == 0;
        if (critico) {
            dano *= 2;
            System.out.println("\n  ★ GOLPE CRÍTICO! ★");
        }
        inimigo.receberDano(dano);
        System.out.printf("  Você atacou %s! Dano: %d%n", inimigo.getNome(), dano);
    }

    private static void usarHabilidade(Jogador jogador, NPC inimigo) {
        int dano = jogador.habilidadeEspecial();
        inimigo.receberDano(dano);
        System.out.printf("%n  Você usou %s!%n", jogador.getClasse().getNome());
        System.out.printf("  Dano da habilidade: %d%n", dano);
    }

    private static void usarItem(Jogador jogador) {
        if (jogador.getInventario().estaVazio()) {
            System.out.println("\n  ⚠ Seu inventário está vazio!");
            return;
        }
        System.out.println("\n  Inventário:");
        jogador.getInventario().listar();
        System.out.print("  Escolha um item (0 para cancelar): ");

        // Tratamento de Exceções - leitura do item
        try {
            int escolha = Integer.parseInt(Terminal.lerLinha().trim());
            if (escolha == 0) return;
            Item item = jogador.getInventario().get(escolha - 1);
            if (item == null) throw new EntradaInvalidaException("Item não encontrado.");
            String resultado = item.usar(jogador);
            jogador.getInventario().remover(escolha - 1);
            System.out.println("  " + resultado);
        } catch (NumberFormatException e) {
            System.out.println("  ⚠ Entrada inválida.");
        } catch (EntradaInvalidaException e) {
            System.out.println("  ⚠ " + e.getMessage());
        }
    }

    private static void tentarDialogo(Jogador jogador, NPC inimigo) {
        System.out.println("\n  Você tenta conversar com " + inimigo.getNome() + "...");
        if (jogador.getConsciencia() >= 50) {
            System.out.println("  Sua aura de consciência ecológica ressoa no vazio deles...");
            inimigo.conscientizar();
            inimigo.setHostil(false);
            jogador.aumentarConsciencia(5);
            System.out.println("  " + inimigo.reagirAoJogador(jogador.getConsciencia()));
            System.out.println("\n  " + inimigo.getNome() + " baixou a guarda. Combate encerrado.");
            // Esvaziar HP do inimigo simbolicamente
            inimigo.receberDano(inimigo.getHPMax() * 10);
        } else {
            System.out.println("  " + inimigo.falar());
            System.out.println("  (Você precisa de mais Consciência para despertar alguém pelo diálogo.)");
            inimigo.receberDano(0);
        }
    }

    private static void turnoInimigo(NPC inimigo, Jogador jogador) {
        int dano = inimigo.getAtaque() + rand.nextInt(4);
        // Chance de usar habilidade especial
        if (rand.nextInt(5) == 0) {
            dano = inimigo.habilidadeEspecial();
            System.out.printf("%n  %s usou seu poder especial! Dano: %d%n", inimigo.getNome(), dano);
        } else {
            System.out.printf("%n  %s atacou você! Dano: %d%n", inimigo.getNome(), dano);
        }
        jogador.receberDano(dano);
    }
}
