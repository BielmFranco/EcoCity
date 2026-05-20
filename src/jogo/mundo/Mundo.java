package jogo.mundo;

import jogo.entidades.Jogador;
import jogo.entidades.NPC;
import jogo.itens.Inventario;
import jogo.itens.Item;
import jogo.combate.SistemaCombate;
import jogo.ui.Terminal;
import jogo.ui.MenuUI;
import jogo.excecoes.EntradaInvalidaException;

// Classe que controla o fluxo narrativo do jogo
public class Mundo {

    // Atributo Estático - NPCs conscientizados no mundo inteiro
    private static int npcsDespertos = 0;

    private Jogador jogador;
    private boolean jogoAtivo;
    private int cenaAtual;

    // Método Construtor
    public Mundo(Jogador jogador) {
        this.jogador = jogador;
        this.jogoAtivo = true;
        this.cenaAtual = 0;
        npcsDespertos = 0;
    }

    public void iniciar() {
        cena0_VestigiosDaNatureza();
    }

    // ════════════════════════════════════════════════════════
    // CENA 0 — PRÓLOGO: VESTÍGIOS DE NATUREZA ABANDONADA
    // ════════════════════════════════════════════════════════
    private void cena0_VestigiosDaNatureza() {
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  CAPÍTULO 1 — VESTÍGIOS DE NATUREZA ABANDONADA");
        Terminal.linha();
        System.out.println();
        Terminal.digitando("  MetroCinza. A grande metrópole que os habitantes chamam de lar perfeito.", 20);
        Terminal.digitando("  Concreto sobre concreto. Metal sobre metal. Fumaça sobre fumaça.", 20);
        Terminal.digitando("  Ninguém questiona. Ninguém sente falta. Para eles, isso é o ideal.", 20);
        System.out.println();
        Terminal.digitando("  Mas hoje, no meio do asfalto rachado, você vê algo impossível:", 20);
        Terminal.digitando("  uma pequena planta. Frágil. Verde. Viva.", 40);
        Terminal.pausa(800);
        Terminal.digitando("  Um vestígio de natureza abandonada há décadas.", 30);
        System.out.println();
        Terminal.aguardarEnter();

        System.out.println("  O que você faz?");
        System.out.println();
        System.out.println("  [1] Examinar com cuidado — isso não deveria existir aqui");
        System.out.println("  [2] Ignorar — provavelmente é só lixo orgânico");
        System.out.print("\n  > ");

        int escolha = Terminal.lerInt(1, 2);

        if (escolha == 2) {
            Terminal.digitando("\n  Você se afasta. Mas seus pés param.", 25);
            Terminal.digitando("  Algo dentro de você não deixa ir embora.", 25);
            Terminal.digitando("  Você volta.", 25);
        }

        Terminal.digitando("\n  A planta guarda uma semente. Pequena, cor-de-laranja.", 25);
        Terminal.digitando("  Ao segurá-la, uma sensação estranha percorre seu corpo.", 25);
        Terminal.digitando("  Quente. Verde. Como uma memória que nunca foi sua.", 25);
        System.out.println();

        jogador.pegarSemente();
        jogador.adicionarItem(Inventario.criarItem("semente"));
        jogador.adicionarItem(Inventario.criarItem("agua"));
        jogador.adicionarItem(Inventario.criarItem("agua"));
        jogador.aumentarConsciencia(15);

        System.out.println("  + Você obteve: A Última Semente Truffula!");
        System.out.println("  + Consciência aumentou para " + jogador.getConsciencia() + "%");
        System.out.println("  + Missão iniciada: Restaurar a natureza de MetroCinza.");

        Terminal.aguardarEnter();
        cena1_HabitantesAlienados();
    }

    // ════════════════════════════════════════════════════════
    // CENA 1 — OS HABITANTES ALIENADOS
    // ════════════════════════════════════════════════════════
    private void cena1_HabitantesAlienados() {
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  CAPÍTULO 1 — OS HABITANTES DE METROCINZA");
        Terminal.linha();
        System.out.println();
        Terminal.digitando("  Você tenta mostrar a semente para os passantes.", 20);
        Terminal.digitando("  A maioria te ignora. Olhos fixos, passos mecânicos.", 20);
        Terminal.digitando("  Então um senhor para e olha para você com estranheza.", 20);
        System.out.println();

        NPC npcVelho = new NPC("Cidadão Aramis", "cidadao",
            "Que é isso? Jogue fora. Material orgânico não autorizado perturba a ordem da cidade.",
            "Natureza? Que conceito obsoleto. MetroCinza é perfeita assim. Eficiente.",
            "Você está perturbado. Consulte o Módulo de Saúde Mental do Setor 7.",
            "...espera. Isso é... uma semente? Eu... lembro de algo assim. Era pequeno. Havia um jardim..."
        );

        System.out.println("  " + npcVelho.getNome() + ": \"" + npcVelho.falar() + "\"");
        Terminal.pausa(800);

        System.out.println("\n  Como você responde?");
        System.out.println("  [1] \"Isso é natureza. A cidade um dia teve isso.\"");
        System.out.println("  [2] \"Você realmente acha MetroCinza perfeita?\"");
        System.out.println("  [3] \"Ajude-me a restaurar a cidade.\"");
        System.out.print("\n  > ");

        int escolha = Terminal.lerInt(1, 3);

        switch (escolha) {
            case 1:
                System.out.println("  " + npcVelho.getNome() + ": \"" + npcVelho.falar() + "\"");
                jogador.aumentarConsciencia(5);
                break;
            case 2:
                npcVelho.falar();
                System.out.println("  " + npcVelho.getNome() + ": \"" + npcVelho.falar() + "\"");
                break;
            case 3:
                npcVelho.falar(); npcVelho.falar();
                System.out.println("  " + npcVelho.getNome() + ": \"" + npcVelho.falar() + "\"");
                Terminal.pausa(1000);
                Terminal.digitando("\n  Uma centelha. Pequena. Mas real.", 30);
                break;
        }

        npcVelho.conscientizar();
        npcsDespertos++;
        jogador.ganharXP(25);
        jogador.aumentarConsciencia(10);

        System.out.println("\n  ★ " + npcVelho.getNome() + " começou a questionar a cidade! (" + npcsDespertos + " conscientizados)");
        System.out.println("  + 25 XP | + 10 Consciência");
        System.out.println("  + Missão: encontre aliados no Setor D.");

        Terminal.aguardarEnter();
        cena2_BuscaDeAliados();
    }

    // ════════════════════════════════════════════════════════
    // CENA 2 — BUSCA DE ALIADOS: SETOR D
    // ════════════════════════════════════════════════════════
    private void cena2_BuscaDeAliados() {
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  CAPÍTULO 2 — SETOR D: A ÚLTIMA ALIADA");
        Terminal.linha();
        System.out.println();
        Terminal.digitando("  O Setor D é uma área esquecida de MetroCinza.", 20);
        Terminal.digitando("  Grafites cobrem as paredes — imagens proibidas de árvores e flores.", 20);
        Terminal.digitando("  Aqui vivem os que questionaram a cidade e foram marginalizados.", 20);
        System.out.println();
        Terminal.aguardarEnter();

        NPC dra = new NPC("Dra. Léa Verona", "aliado",
            "Você... você tem uma semente Truffula! Como conseguiu? Onde estava?",
            "Sou a última botânica registrada aqui. Dediquei minha vida a documentar o que foi destruído.",
            "A semente precisa ir ao Jardim Central — o único solo ainda fértil sob MetroCinza.",
            "Mas a CorpVerde bloqueou o acesso. O Guarda do setor obedece às ordens sem questionar.",
            "Tome estes recursos. Você vai precisar. Por favor — plante essa semente. Reconstrua isso."
        );

        System.out.println("  Uma mulher de jaleco verde surrado aparece da sombra.");
        System.out.println();
        System.out.println("  " + dra.getNome() + ": \"" + dra.falar() + "\"");
        Terminal.pausa(800);

        System.out.println("\n  O que você diz?");
        System.out.println("  [1] \"Encontrei no asfalto. A cidade esqueceu que isso existia.\"");
        System.out.println("  [2] \"Como você sobreviveu aqui marginalizada?\"");
        System.out.println("  [3] \"Me conte sobre o Jardim Central.\"");
        System.out.print("\n  > ");

        int escolha = Terminal.lerInt(1, 3);

        switch (escolha) {
            case 1:
                System.out.println("\n  Dra. Léa: \"" + dra.falar() + "\"");
                break;
            case 2:
                dra.falar();
                System.out.println("\n  Dra. Léa: \"" + dra.falar() + "\"");
                break;
            case 3:
                dra.falar(); dra.falar();
                System.out.println("\n  Dra. Léa: \"" + dra.falar() + "\"");
                break;
        }

        Terminal.pausa(500);
        System.out.println("\n  Ela vai até uma prateleira e entrega um conjunto de suprimentos.");
        System.out.println("  Dra. Léa: \"" + dra.falar() + "\"");

        jogador.adicionarItem(Inventario.criarItem("cha", jogador.getNivel()));
        jogador.adicionarItem(Inventario.criarItem("elixir", jogador.getNivel()));
        jogador.adicionarItem(Inventario.criarItem("diario"));

        dra.conscientizar();
        npcsDespertos++;
        jogador.ganharXP(40);
        jogador.aumentarConsciencia(15);

        System.out.println("\n  ★ Dra. Léa Verona se juntou à causa! (" + npcsDespertos + " conscientizados)");
        System.out.println("  + 40 XP | + 15 Consciência");
        System.out.println("  + Chá de Ervas Raras, Elixir Verde e Diário do Último Jardineiro!");
        System.out.println("  + Nova área desbloqueada: Jardim Central.");

        Terminal.aguardarEnter();
        cena3_GuardaDoSetor();
    }

    // ════════════════════════════════════════════════════════
    // CENA 3 — O GUARDA DO SETOR (RESISTÊNCIA SOCIAL)
    // ════════════════════════════════════════════════════════
    private void cena3_GuardaDoSetor() {
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  CAPÍTULO 3 — O GUARDA DO SETOR");
        Terminal.linha();
        System.out.println();
        Terminal.digitando("  Na entrada do Jardim Central, um guarda bloqueia a passagem.", 20);
        Terminal.digitando("  Dois metros de armadura cinza. Programado para obedecer.", 20);
        Terminal.digitando("  Programado para não questionar. Programado para não sentir.", 20);
        System.out.println();
        Terminal.digitando("  Ele é MetroCinza em forma de pessoa.", 30);
        System.out.println();

        NPC guardaFerro = new NPC("Guarda Ferro MK-7", "guarda",
            50, 15, 10, true,
            "ACESSO NEGADO. ÁREA RESTRITA. RETORNE IMEDIATAMENTE.",
            "A ordem da cidade é preservada. Elementos perturbadores serão removidos.",
            "RESISTÊNCIA DETECTADA. PROTOCOLO DE CONTENÇÃO ATIVADO.",
            "...o que é isso na sua mão...? ...verde... eu... lembro de algo... havia um parque aqui..."
        );

        System.out.println("  " + guardaFerro.getNome() + ": \"" + guardaFerro.falar() + "\"");
        System.out.println();
        System.out.println("  Como você responde?");
        System.out.println("  [1] Tentar passar pela força");
        System.out.println("  [2] Mostrar a semente e tentar conscientizá-lo");
        System.out.println("  [3] Ver status do personagem");
        System.out.print("\n  > ");

        int escolha = Terminal.lerInt(1, 3);

        if (escolha == 3) {
            MenuUI.exibirStatusJogador(jogador);
            System.out.print("  Agora, sua escolha (1-2): ");
            escolha = Terminal.lerInt(1, 2);
        }

        if (escolha == 2 && jogador.getConsciencia() >= 40) {
            Terminal.digitando("\n  Você ergue a semente. A luz verde ilumina o rosto do Guarda.", 20);
            Terminal.digitando("  Algo trava nos seus circuitos. Uma memória que o sistema não apagou.", 20);
            System.out.println("\n  Guarda Ferro: \"" + guardaFerro.falar() + "\"");
            Terminal.pausa(1000);
            Terminal.digitando("  Lentamente, o Guarda Ferro se afasta do caminho.", 30);
            guardaFerro.conscientizar();
            npcsDespertos++;
            jogador.ganharXP(60);
            jogador.aumentarConsciencia(20);
            System.out.println("\n  ★ Guarda Ferro MK-7 foi conscientizado! (" + npcsDespertos + " conscientizados)");
            System.out.println("  + 60 XP | + 20 Consciência");
        } else if (escolha == 2 && jogador.getConsciencia() < 40) {
            Terminal.digitando("\n  Você mostra a semente. O Guarda a examina com olhos vazios.", 20);
            System.out.println("  " + guardaFerro.getNome() + ": \"" + guardaFerro.falar() + "\"");
            Terminal.digitando("  Sua consciência ainda não é forte o suficiente para alcançá-lo.", 20);
            Terminal.digitando("  O confronto é inevitável...", 20);
            Terminal.aguardarEnter();

            boolean venceu = SistemaCombate.resolverCombate(jogador, guardaFerro);

            if (!venceu) {
                if (!jogador.estaVivo()) {
                    fimDeJogo(false);
                    return;
                }
                System.out.println("\n  Você recuou. Precisa conscientizar mais habitantes primeiro.");
                Terminal.aguardarEnter();
                cena3_GuardaDoSetor();
                return;
            }

            if (guardaFerro.isConscientizado()) {
                npcsDespertos++;
                Terminal.digitando("\n  Ao cair, o Guarda murmurou algo sobre um parque...", 20);
                System.out.println("  ★ Guarda Ferro foi conscientizado! (" + npcsDespertos + " conscientizados)");
            }
        } else {
            System.out.println("\n  " + guardaFerro.getNome() + ": \"" + guardaFerro.falar() + "\"");
            System.out.println("\n  O confronto é inevitável...");
            Terminal.aguardarEnter();

            boolean venceu = SistemaCombate.resolverCombate(jogador, guardaFerro);

            if (!venceu) {
                if (!jogador.estaVivo()) {
                    fimDeJogo(false);
                    return;
                }
                System.out.println("\n  Você recuou. Precisa ficar mais forte.");
                Terminal.aguardarEnter();
                cena3_GuardaDoSetor();
                return;
            }

            if (guardaFerro.isConscientizado()) {
                npcsDespertos++;
                Terminal.digitando("\n  Ao cair, o Guarda murmurou algo sobre um parque...", 20);
                System.out.println("  ★ Guarda Ferro foi conscientizado! (" + npcsDespertos + " conscientizados)");
            }
        }

        Terminal.aguardarEnter();
        cena4_JardimCentral();
    }

    // ════════════════════════════════════════════════════════
    // CENA 4 — JARDIM CENTRAL E O ANTAGONISTA
    // ════════════════════════════════════════════════════════
    private void cena4_JardimCentral() {
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  CAPÍTULO 4 — JARDIM CENTRAL");
        Terminal.linha();
        System.out.println();
        Terminal.digitando("  O Jardim Central. Deveria ser o pulmão da cidade.", 20);
        Terminal.digitando("  Hoje: chaminés, tubulações, smog — e uma plataforma de concreto", 20);
        Terminal.digitando("  que um dia foi terra fértil. A CorpVerde transformou em fábrica.", 20);
        System.out.println();
        Terminal.digitando("  Mas então ele aparece.", 30);
        System.out.println();
        Terminal.aguardarEnter();

        NPC diretor = new NPC("Diretor Orloff", "corporativo",
            80, 18, 5, true,
            "Que fascinante. Um habitante com emoções. Que desperdício de processamento.",
            "A natureza era ineficiente. MetroCinza é a evolução. Vocês deveriam ser gratos.",
            "Você está contaminado por sentimentalismo. Vou corrigi-lo.",
            "Não... aquele cheiro... era como... havia um jardim aqui quando eu era criança...",
            "Eu... me lembro... de grama sob meus pés. De árvores. Eu também esqueci..."
        );

        System.out.println("  Diretor Orloff: \"" + diretor.falar() + "\"");
        Terminal.pausa(1000);
        System.out.println("\n  Como você responde?");
        System.out.println("  [1] \"MetroCinza não é perfeita. É uma prisão que todos aceitaram.\"");
        System.out.println("  [2] \"Você realmente não sente falta de nada?\"");
        System.out.println("  [3] Preparar-se para o confronto final");
        System.out.print("\n  > ");

        int escolha = Terminal.lerInt(1, 3);

        switch(escolha) {
            case 1:
                System.out.println("\n  Orloff: \"" + diretor.falar() + "\"");
                System.out.println("\n  Sua resposta acendeu algo nele. Raiva. Ou reconhecimento.");
                break;
            case 2:
                diretor.falar();
                System.out.println("\n  Orloff: \"" + diretor.falar() + "\"");
                jogador.aumentarConsciencia(5);
                break;
            case 3:
                System.out.println("\n  Você se prepara. Não há mais palavras.");
                break;
        }

        Terminal.aguardarEnter();
        System.out.println("\n  O confronto final começa!");
        boolean venceu = SistemaCombate.resolverCombate(jogador, diretor);

        if (!venceu) {
            if (!jogador.estaVivo()) {
                fimDeJogo(false);
                return;
            }
            System.out.println("\n  Você precisa de mais força. Volte mais preparado.");
            Terminal.aguardarEnter();
            cena4_JardimCentral();
            return;
        }

        // Epílogo do antagonista
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  EPÍLOGO — O ANTAGONISTA SE LEMBRA");
        Terminal.linha();
        System.out.println();
        Terminal.digitando("  Diretor Orloff está caído. Mas algo acontece.", 25);
        Terminal.digitando("  Ao ver a semente em sua mão, ele para. E se lembra.", 25);
        System.out.println();
        System.out.println("  Orloff: \"" + diretor.falar() + "\"");
        Terminal.pausa(1000);
        System.out.println("  Orloff: \"" + diretor.falar() + "\"");
        Terminal.pausa(1000);

        npcsDespertos++;
        diretor.conscientizar();

        Terminal.digitando("\n  Todos os que resistiram agora se lembram.", 25);
        Terminal.digitando("  E se juntam a você.", 25);
        System.out.println();

        Terminal.aguardarEnter();
        cena5_RestauracaoDaCidade();
    }

    // ════════════════════════════════════════════════════════
    // CENA 5 — RESTAURAÇÃO DA CIDADE
    // ════════════════════════════════════════════════════════
    private void cena5_RestauracaoDaCidade() {
        Terminal.limparTela();
        Terminal.linha();
        System.out.println("  O MOMENTO FINAL — RECONSTRUÇÃO");
        Terminal.linha();
        System.out.println();
        Terminal.digitando("  Você se ajoelha no centro da plataforma.", 25);
        Terminal.digitando("  Com as mãos, afasta o concreto até encontrar a terra.", 25);
        Terminal.digitando("  Ela ainda está lá. Escura, quente, esperando.", 25);
        System.out.println();
        Terminal.digitando("  Ao redor, os habitantes — antes alienados, agora despertos —", 25);
        Terminal.digitando("  se juntam a você. Todos juntos para reconstruir a cidade.", 25);
        System.out.println();

        System.out.println("  Você planta a semente?");
        System.out.println("  [1] Sim. Com cuidado e esperança.");
        System.out.println("  [2] Hesitar por um momento...");
        System.out.print("\n  > ");

        int escolha = Terminal.lerInt(1, 2);

        if (escolha == 2) {
            Terminal.digitando("\n  Você hesita. Então olha para os que estão ao seu lado:", 25);
            Terminal.digitando("  Aramis. Dra. Léa. O Guarda. Até Orloff.", 25);
            Terminal.digitando("  Todos esperando pelo verde. Todos prontos para mudar.", 25);
            System.out.println();
        }

        Terminal.digitando("  Você deposita a semente na terra.", 30);
        Terminal.pausa(500);
        Terminal.digitando("  Cobre com as mãos.", 30);
        Terminal.pausa(500);
        Terminal.digitando("  E espera.", 30);
        Terminal.pausa(2000);
        Terminal.digitando("  ...", 300);
        Terminal.pausa(1000);
        Terminal.digitando("  ......", 200);
        Terminal.pausa(1500);
        System.out.println();
        Terminal.digitando("  Uma folha. Pequena. Cor-de-laranja.", 40);
        Terminal.pausa(500);
        Terminal.digitando("  Ela rompe o concreto.", 40);
        Terminal.pausa(1000);
        System.out.println();
        Terminal.digitando("  MetroCinza começa a se tornar EcoCity.", 60);
        System.out.println();
        Terminal.pausa(2000);

        fimDeJogo(true);
    }

    // ════════════════════════════════════════════════════════
    // TELA DE FIM DE JOGO — 2 FINAIS (slides)
    // ════════════════════════════════════════════════════════
    private void fimDeJogo(boolean vitoria) {
        Terminal.limparTela();
        if (vitoria) {
            // FINAL POSITIVO: restauração completa da cidade
            System.out.println();
            System.out.println("  ╔══════════════════════════════════════════════════════════╗");
            System.out.println("  ║                                                          ║");
            System.out.println("  ║              ★  FINAL POSITIVO  ★                       ║");
            System.out.println("  ║                                                          ║");
            System.out.println("  ║         Restauração completa da cidade.                  ║");
            System.out.println("  ║         MetroCinza se transforma em EcoCity.             ║");
            System.out.println("  ║         Os habitantes, antes alienados, agora            ║");
            System.out.println("  ║         reconstruíram juntos a realidade que             ║");
            System.out.println("  ║         estavam inseridos.                               ║");
            System.out.println("  ║                                                          ║");
            System.out.printf ("  ║   NPCs Conscientizados: %-3d                              ║%n", npcsDespertos);
            System.out.printf ("  ║   Consciência Final:    %-3d%%                            ║%n", jogador.getConsciencia());
            System.out.printf ("  ║   Nível Alcançado:      %-3d (%s)%n",
                jogador.getNivel(), jogador.getTituloNivel() + ")                    ║");
            System.out.println("  ║                                                          ║");
            System.out.println("  ║  \"A menos que alguém como você se importe bastante,     ║");
            System.out.println("  ║   nada vai melhorar. Não vai, de jeito nenhum.\"          ║");
            System.out.println("  ║                              — O Lorax, Dr. Seuss        ║");
            System.out.println("  ║                                                          ║");
            System.out.println("  ╚══════════════════════════════════════════════════════════╝");
        } else {
            // FINAL NEGATIVO: cidade não é restaurada, degradação continua
            System.out.println();
            System.out.println("  ╔══════════════════════════════════════════════════════════╗");
            System.out.println("  ║                                                          ║");
            System.out.println("  ║              ✖  FINAL NEGATIVO  ✖                       ║");
            System.out.println("  ║                                                          ║");
            System.out.println("  ║         A cidade não é restaurada.                       ║");
            System.out.println("  ║         Os NPCs não foram conscientizados.               ║");
            System.out.println("  ║         MetroCinza continua sua degradação.              ║");
            System.out.println("  ║         A semente não foi plantada.                      ║");
            System.out.println("  ║                                                          ║");
            System.out.printf ("  ║   NPCs Conscientizados: %-3d                              ║%n", npcsDespertos);
            System.out.printf ("  ║   Consciência Final:    %-3d%%                            ║%n", jogador.getConsciencia());
            System.out.println("  ║                                                          ║");
            System.out.println("  ║         Tente novamente. A cidade precisa de você.       ║");
            System.out.println("  ║                                                          ║");
            System.out.println("  ╚══════════════════════════════════════════════════════════╝");
        }
        System.out.println();
        jogoAtivo = false;
        Terminal.aguardarEnter();
    }

    public boolean isJogoAtivo() { return jogoAtivo; }

    public static int getNpcsDespertos() { return npcsDespertos; }
}
