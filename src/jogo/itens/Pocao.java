package jogo.itens;

import jogo.entidades.Jogador;

// Herança - Pocao herda de Item
public class Pocao extends Item {

    private int cura;
    private String tipo;

    // Método Construtor
    public Pocao(String tipo, int cura) {
        super(
            tipo.equals("agua") ? "Água de Chuva" :
            tipo.equals("chá")  ? "Chá de Ervas Raras" : "Elixir Verde",
            tipo.equals("agua") ? "Água coletada de telhados. Cura " + cura + " HP." :
            tipo.equals("chá")  ? "Preparado com plantas quase extintas. Cura " + cura + " HP." :
                                   "Destilado misterioso. Cura " + cura + " HP e aumenta consciência.",
            tipo.equals("elixir") ? 40 : 15
        );
        this.cura = cura;
        this.tipo = tipo;
    }

    // Sobrescrita - implementação do método abstrato
    @Override
    public String usar(Jogador jogador) {
        jogador.curar(cura);
        if (tipo.equals("elixir")) {
            jogador.aumentarConsciencia(10);
            return String.format("Você bebeu o %s. +%d HP e +10 Consciência!", getNome(), cura);
        }
        return String.format("Você bebeu %s. +%d HP!", getNome(), cura);
    }
}

// Herança - Fragmento herda de Item
class Fragmento extends Item {

    private int bonusConsciencia;
    private int bonusXP;

    // Método Construtor - Sobrecarga (parametrização diferente)
    public Fragmento(String nome, String descricao, int bonusConsciencia, int bonusXP) {
        super(nome, descricao, 0);
        this.bonusConsciencia = bonusConsciencia;
        this.bonusXP = bonusXP;
    }

    @Override
    public String usar(Jogador jogador) {
        jogador.aumentarConsciencia(bonusConsciencia);
        jogador.ganharXP(bonusXP);
        return String.format(
            "Você contempla %s.\nUma memória antiga surge: havia verde por toda parte.\n+%d Consciência | +%d XP",
            getNome(), bonusConsciencia, bonusXP
        );
    }
}

// Classe Final - não pode ser estendida
final class SementeTruffula extends Item {

    // Atributo Final
    public static final String NOME_SEMENTE = "Última Semente Truffula";

    public SementeTruffula() {
        super(NOME_SEMENTE,
              "A última esperança do mundo. Uma semente das árvores Truffula extintas.",
              999);
    }

    @Override
    public String usar(Jogador jogador) {
        // A semente não é consumida, ela é plantada no final
        return "Esta semente não pode ser simplesmente \"usada\". " +
               "Ela precisa ser plantada no lugar certo, no momento certo.";
    }
}

// Fábrica de itens - Polimorfismo de classe
class FabricaItens {

    // Sobrecarga - múltiplas versões do método criar
    public static Item criar(String tipo) {
        return criar(tipo, 1);
    }

    public static Item criar(String tipo, int nivel) {
        switch (tipo.toLowerCase()) {
            case "agua":    return new Pocao("agua", 20 + nivel * 5);
            case "cha":     return new Pocao("chá", 35 + nivel * 5);
            case "elixir":  return new Pocao("elixir", 40 + nivel * 10);
            case "semente": return new SementeTruffula();
            case "foto":    return new Fragmento(
                "Fotografia Amarelada",
                "Uma foto de quando ainda havia parques na cidade.",
                15, 20
            );
            case "diario":  return new Fragmento(
                "Diário do Último Jardineiro",
                "Registros de alguém que lutou para preservar a natureza.",
                25, 35
            );
            default:        return new Pocao("agua", 20);
        }
    }
}
