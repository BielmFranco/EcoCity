package jogo.itens;

import java.util.ArrayList;
import java.util.List;

public class Inventario {

    // Encapsulamento
    private List<Item> itens;

    // Atributo Final - capacidade máxima
    public static final int CAPACIDADE_MAX = 10;

    // Método Construtor
    public Inventario() {
        this.itens = new ArrayList<>();
    }

    public boolean adicionar(Item item) {
        if (itens.size() >= CAPACIDADE_MAX) {
            System.out.println("⚠ Inventário cheio! Máximo de " + CAPACIDADE_MAX + " itens.");
            return false;
        }
        itens.add(item);
        System.out.println("  + Item adicionado: " + item.getNome());
        return true;
    }

    public Item remover(int indice) {
        if (indice < 0 || indice >= itens.size()) return null;
        return itens.remove(indice);
    }

    public void listar() {
        if (itens.isEmpty()) {
            System.out.println("  [Inventário vazio]");
            return;
        }
        for (int i = 0; i < itens.size(); i++) {
            System.out.printf("  [%d] %s%n", i + 1, itens.get(i));
        }
    }

    public boolean estaVazio() { return itens.isEmpty(); }
    public int tamanho() { return itens.size(); }
    public Item get(int indice) {
        if (indice < 0 || indice >= itens.size()) return null;
        return itens.get(indice);
    }
    public List<Item> getItens() { return itens; }

    // Método estático de fábrica exposto
    public static Item criarItem(String tipo) {
        return FabricaItens.criar(tipo);
    }

    public static Item criarItem(String tipo, int nivel) {
        return FabricaItens.criar(tipo, nivel);
    }
}
