package guardiao.personagens;

// ============================================================
// HERANCA - Explorador herda de Personagem (nivel 1).
// ============================================================
public class Explorador extends Personagem {

    // CONSTRUTOR - fixa o nivel 1
    public Explorador(String nome) {
        super(nome, 1);
    }

    // CONSTRUTOR (SOBRECARGA) - mantem pontos ao evoluir de nivel
    public Explorador(String nome, int pontos) {
        super(nome, 1, pontos);
    }

    // SOBRESCRITA - implementa o metodo abstrato getTitulo
    @Override
    public String getTitulo() {
        return "Iniciante - Explorador";
    }

    // SOBRESCRITA - tipo de missao ideal desta classe
    @Override
    public String getClasseIdeal() {
        return "Exploracao";
    }
}
