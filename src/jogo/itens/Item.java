package jogo.itens;

import jogo.entidades.Jogador;

// Classe Abstrata
public abstract class Item implements Utilizavel {

    // Atributo Final - nome do item não muda
    private final String nome;
    private final String descricao;
    private final int valor;

    // Método Construtor
    public Item(String nome, String descricao, int valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    // Método Abstrato - cada item tem uso diferente
    @Override
    public abstract String usar(Jogador jogador);

    @Override
    public String getNome() { return nome; }

    @Override
    public String getDescricao() { return descricao; }

    public int getValor() { return valor; }

    @Override
    public String toString() {
        return String.format("%-20s | %s", nome, descricao);
    }
}
