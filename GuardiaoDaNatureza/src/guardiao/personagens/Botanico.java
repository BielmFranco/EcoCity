package guardiao.personagens;

// ============================================================
// HERANCA - Botanico herda de Personagem (nivel 3).
// ============================================================
public class Botanico extends Personagem {

    // CONSTRUTOR - fixa o nivel 3
    public Botanico(String nome) {
        super(nome, 3);
    }

    // CONSTRUTOR (SOBRECARGA) - mantem pontos ao evoluir de nivel
    public Botanico(String nome, int pontos) {
        super(nome, 3, pontos);
    }

    // SOBRESCRITA - implementa o metodo abstrato getTitulo
    @Override
    public String getTitulo() {
        return "Intermediario - Botanico";
    }

    // SOBRESCRITA - tipo de missao ideal desta classe
    @Override
    public String getClasseIdeal() {
        return "Botanica";
    }
}
