package guardiao.personagens;

// ============================================================
// HERANCA - Descontaminador herda de Personagem (nivel 2).
// ============================================================
public class Descontaminador extends Personagem {

    // CONSTRUTOR - fixa o nivel 2
    public Descontaminador(String nome) {
        super(nome, 2);
    }

    // CONSTRUTOR (SOBRECARGA) - mantem pontos ao evoluir de nivel
    public Descontaminador(String nome, int pontos) {
        super(nome, 2, pontos);
    }

    // SOBRESCRITA - implementa o metodo abstrato getTitulo
    @Override
    public String getTitulo() {
        return "Basico - Descontaminador";
    }

    // SOBRESCRITA - tipo de missao ideal desta classe
    @Override
    public String getClasseIdeal() {
        return "Descontaminacao";
    }
}
