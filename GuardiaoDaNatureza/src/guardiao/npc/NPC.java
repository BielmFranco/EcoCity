package guardiao.npc;

import guardiao.missoes.Missao;

// ============================================================
// Classe NPC - habitante que entrega missoes ao jogador.
// A interacao e DIRETA E MECANICA: o NPC apenas entrega a
// missao e registra a conclusao. Nao tenta conscientizar.
// ============================================================
public class NPC {

    // ENCAPSULAMENTO - atributos privados.
    // ATRIBUTO FINAL - nome e a missao nao mudam apos criados.
    private final String nome;
    private final Missao missao;

    private boolean missaoEntregue;

    // CONSTRUTOR
    public NPC(String nome, Missao missao) {
        this.nome = nome;
        this.missao = missao;
        this.missaoEntregue = false;
    }

    // Entrega a missao de forma mecanica e retorna o objeto Missao.
    public Missao entregarMissao() {
        this.missaoEntregue = true;
        return missao;
    }

    // ENCAPSULAMENTO - getters
    public String getNome() {
        return nome;
    }

    public Missao getMissao() {
        return missao;
    }

    public boolean isMissaoEntregue() {
        return missaoEntregue;
    }
}
