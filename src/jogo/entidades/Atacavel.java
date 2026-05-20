package jogo.entidades;

// Interface - pilar de OO
public interface Atacavel {
    void receberDano(int dano);
    boolean estaVivo();
    int getHP();
    int getHPMax();
    String getNome();
}
