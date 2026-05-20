package guardiao.personagens;

// ============================================================
// CLASSE ABSTRATA - molde base de todo personagem jogavel.
// Nao pode ser instanciada diretamente (new Personagem() e proibido).
// ============================================================
public abstract class Personagem {

    // ENCAPSULAMENTO - atributos privados, acessados so por getters/setters.
    private String nome;
    private int nivel;
    private int pontos;
    private int missoesCumpridas;

    // CONSTRUTOR (versao simples) - SOBRECARGA: chama o construtor completo.
    public Personagem(String nome, int nivel) {
        this(nome, nivel, 0);
    }

    // CONSTRUTOR (versao completa) - SOBRECARGA: recebe pontos iniciais,
    // usado quando o personagem evolui de nivel mantendo a pontuacao.
    public Personagem(String nome, int nivel, int pontosIniciais) {
        this.nome = nome;
        this.nivel = nivel;
        this.pontos = pontosIniciais;
        this.missoesCumpridas = 0;
    }

    // METODO ABSTRATO - cada subclasse de nivel define seu proprio titulo.
    public abstract String getTitulo();

    // METODO ABSTRATO - cada subclasse define qual tipo de missao
    // e ideal para ela (usado no bonus de pontuacao).
    public abstract String getClasseIdeal();

    public void adicionarPontos(int p) {
        this.pontos += p;
    }

    public void registrarMissao() {
        this.missoesCumpridas++;
    }

    // ENCAPSULAMENTO - getters e setters
    public String getNome() {
        return nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPontos() {
        return pontos;
    }

    public int getMissoesCumpridas() {
        return missoesCumpridas;
    }
}
