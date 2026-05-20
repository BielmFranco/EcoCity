package guardiao.personagens;

// ============================================================
// HERANCA - Guardiao herda de Personagem (nivel 5 - final).
// ============================================================
public class Guardiao extends Personagem {

    // CONSTRUTOR - fixa o nivel 5
    public Guardiao(String nome) {
        super(nome, 5);
    }

    // CONSTRUTOR (SOBRECARGA) - mantem pontos ao evoluir de nivel
    public Guardiao(String nome, int pontos) {
        super(nome, 5, pontos);
    }

    // SOBRESCRITA - implementa o metodo abstrato getTitulo
    @Override
    public String getTitulo() {
        return "Final - Guardiao da Natureza";
    }

    // SOBRESCRITA - tipo de missao ideal desta classe
    @Override
    public String getClasseIdeal() {
        return "Protecao";
    }
}
