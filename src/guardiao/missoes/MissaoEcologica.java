package guardiao.missoes;

// ============================================================
// Classe concreta que IMPLEMENTA a INTERFACE Missao.
// Representa uma missao ecologica entregue por um NPC.
// ============================================================
public class MissaoEcologica implements Missao {

    // ENCAPSULAMENTO - atributos privados.
    // ATRIBUTO FINAL - estes valores nao mudam apos a criacao.
    private final String descricao;
    private final int pontosBase;
    private final int turnosLimite;

    private boolean concluida;
    private int turnosGastos;

    // CONSTRUTOR
    public MissaoEcologica(String descricao, int pontosBase, int turnosLimite) {
        this.descricao = descricao;
        this.pontosBase = pontosBase;
        this.turnosLimite = turnosLimite;
        this.concluida = false;
        this.turnosGastos = 0;
    }

    // SOBRESCRITA - implementacao dos metodos da interface Missao
    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public int getPontosBase() {
        return pontosBase;
    }

    @Override
    public int getTurnosLimite() {
        return turnosLimite;
    }

    @Override
    public void concluir(int turnosGastos) {
        this.turnosGastos = turnosGastos;
        this.concluida = true;
    }

    @Override
    public boolean isConcluida() {
        return concluida;
    }

    @Override
    public int getTurnosGastos() {
        return turnosGastos;
    }
}
