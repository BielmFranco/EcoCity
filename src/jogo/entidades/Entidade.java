package jogo.entidades;

// Classe Abstrata - pilar de OO
public abstract class Entidade implements Atacavel {

    // Encapsulamento - atributos privados com getters/setters
    private String nome;
    private int hp;
    private int hpMax;
    private int ataque;
    private int defesa;

    // Atributo Estático - conta o total de entidades criadas
    private static int totalEntidades = 0;

    // Método Construtor
    public Entidade(String nome, int hpMax, int ataque, int defesa) {
        this.nome = nome;
        this.hpMax = hpMax;
        this.hp = hpMax;
        this.ataque = ataque;
        this.defesa = defesa;
        totalEntidades++; // Atributo Estático
    }

    // Método Abstrato - deve ser implementado pelas subclasses
    public abstract String descricao();

    // Método Abstrato - ação especial de cada entidade
    public abstract int habilidadeEspecial();

    @Override
    public void receberDano(int dano) {
        int danoReal = Math.max(0, dano - this.defesa);
        this.hp = Math.max(0, this.hp - danoReal);
    }

    @Override
    public boolean estaVivo() {
        return this.hp > 0;
    }

    public void curar(int quantidade) {
        this.hp = Math.min(hpMax, this.hp + quantidade);
    }

    // Getters e Setters - Encapsulamento
    @Override
    public String getNome() { return nome; }

    @Override
    public int getHP() { return hp; }

    @Override
    public int getHPMax() { return hpMax; }

    public int getAtaque() { return ataque; }
    public int getDefesa() { return defesa; }

    public void setAtaque(int ataque) { this.ataque = ataque; }
    public void setDefesa(int defesa) { this.defesa = defesa; }
    public void setHpMax(int hpMax) { this.hpMax = hpMax; }

    // Atributo Estático - getter
    public static int getTotalEntidades() { return totalEntidades; }

    public String barraHP() {
        int cheios = (int) ((double) hp / hpMax * 20);
        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < 20; i++) {
            barra.append(i < cheios ? "█" : "░");
        }
        barra.append("]");
        return barra.toString();
    }
}
