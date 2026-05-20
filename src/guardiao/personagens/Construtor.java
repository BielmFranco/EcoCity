package guardiao.personagens;

// ============================================================
// HERANCA - Construtor herda de Personagem (nivel 4).
// ============================================================
public class Construtor extends Personagem {

    // CONSTRUTOR - fixa o nivel 4
    public Construtor(String nome) {
        super(nome, 4);
    }

    // CONSTRUTOR (SOBRECARGA) - mantem pontos ao evoluir de nivel
    public Construtor(String nome, int pontos) {
        super(nome, 4, pontos);
    }

    // SOBRESCRITA - implementa o metodo abstrato getTitulo
    @Override
    public String getTitulo() {
        return "Experiente - Construtor";
    }

    // SOBRESCRITA - tipo de missao ideal desta classe
    @Override
    public String getClasseIdeal() {
        return "Construcao";
    }
}
