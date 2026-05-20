package jogo.entidades;

import jogo.itens.Inventario;
import jogo.itens.Item;

// Herança - Personagem herda de Entidade
public abstract class Personagem extends Entidade {

    private int nivel;
    private int experiencia;
    private int consciencia; // Medidor de "despertar ecológico" do personagem
    private Inventario inventario;

    // Atributo Final - pontos de experiência para subir de nível
    public static final int XP_POR_NIVEL = 100;

    // Método Construtor com Sobrecarga (construtor da subclasse)
    public Personagem(String nome, int hpMax, int ataque, int defesa) {
        super(nome, hpMax, ataque, defesa); // Método Construtor - chama super
        this.nivel = 1;
        this.experiencia = 0;
        this.consciencia = 0;
        this.inventario = new Inventario();
    }

    public void ganharXP(int xp) {
        this.experiencia += xp;
        while (this.experiencia >= XP_POR_NIVEL) {
            this.experiencia -= XP_POR_NIVEL;
            this.nivel++;
            subirNivel();
        }
    }

    // Método Abstrato - cada classe sobe de nível de forma diferente
    protected abstract void subirNivel();

    public void aumentarConsciencia(int pontos) {
        this.consciencia = Math.min(100, this.consciencia + pontos);
    }

    public void adicionarItem(Item item) {
        inventario.adicionar(item);
    }

    // Título do nível conforme progressão dos slides
    public String getTituloNivel() {
        if (nivel <= 5)  return "Aprendiz Ecológico";
        if (nivel <= 10) return "Agente Urbano";
        return "Guardião da Cidade";
    }

    public Inventario getInventario() { return inventario; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }
    public int getConsciencia() { return consciencia; }
}
