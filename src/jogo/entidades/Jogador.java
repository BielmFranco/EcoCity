package jogo.entidades;

// Herança - Jogador herda de Personagem
public class Jogador extends Personagem {

    private ClassePersonagem classePersonagem;
    private boolean temSemente; // A semente Truffula

    // Método Construtor - Sobrecarga
    public Jogador(String nome, ClassePersonagem classe) {
        super(nome,
              classe.getHpBase(),
              classe.getAtaqueBase(),
              classe.getDefesaBase());
        this.classePersonagem = classe;
        this.temSemente = false;
    }

    // Sobrecarga - descrição sem parâmetro
    @Override
    public String descricao() {
        return String.format(
            "[ %s | Classe: %s | Nível: %d (%s) | Consciência: %d%% ]",
            getNome(), classePersonagem.getNome(), getNivel(), getTituloNivel(), getConsciencia()
        );
    }

    // Método Abstrato implementado - habilidade especial do jogador
    @Override
    public int habilidadeEspecial() {
        // Habilidade varia conforme a classe escolhida
        return classePersonagem.calcularHabilidade(getAtaque(), getConsciencia());
    }

    // Sobrescrita - sobe de nível baseado na classe
    @Override
    protected void subirNivel() {
        int bonusHP = classePersonagem.getBonusHPNivel();
        int bonusAtk = classePersonagem.getBonusAtaqueNivel();
        int bonusDef = classePersonagem.getBonusDefesaNivel();

        setHpMax(getHPMax() + bonusHP);
        curar(bonusHP);
        setAtaque(getAtaque() + bonusAtk);
        setDefesa(getDefesa() + bonusDef);

        System.out.println("\n✨ VOCÊ SUBIU PARA O NÍVEL " + getNivel() + "! ✨");
        System.out.println("   HP Max +"+bonusHP+" | Ataque +"+bonusAtk+" | Defesa +"+bonusDef);
    }

    public void pegarSemente() { this.temSemente = true; }
    public boolean temSemente() { return temSemente; }
    public ClassePersonagem getClasse() { return classePersonagem; }
}
