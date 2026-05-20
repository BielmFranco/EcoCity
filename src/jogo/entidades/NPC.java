package jogo.entidades;

// Herança - NPC herda de Entidade
public class NPC extends Entidade {

    private String[] dialogos;
    private int indiceDialogo;
    private boolean conscientizado;
    private boolean hostil;
    private String tipo; // "cidadao", "guarda", "corporativo", "aliado"

    // Método Construtor - Sobrecarga (construtor completo)
    public NPC(String nome, String tipo, int hp, int ataque, int defesa,
               boolean hostil, String... dialogos) {
        super(nome, hp, ataque, defesa);
        this.tipo = tipo;
        this.hostil = hostil;
        this.dialogos = dialogos;
        this.indiceDialogo = 0;
        this.conscientizado = false;
    }

    // Sobrecarga - construtor NPC não hostil simples
    public NPC(String nome, String tipo, String... dialogos) {
        super(nome, 50, 5, 3);
        this.tipo = tipo;
        this.hostil = false;
        this.dialogos = dialogos;
        this.indiceDialogo = 0;
        this.conscientizado = false;
    }

    // Sobrescrita - descrição específica do NPC
    @Override
    public String descricao() {
        return String.format("[%s] %s%s",
            tipo.toUpperCase(),
            getNome(),
            conscientizado ? " ★ (Conscientizado)" : "");
    }

    // Método Abstrato implementado
    @Override
    public int habilidadeEspecial() {
        // NPCs hostis têm ataque dobrado na habilidade especial
        return hostil ? getAtaque() * 2 : 0;
    }

    public String falar() {
        if (dialogos == null || dialogos.length == 0) {
            return "...";
        }
        String fala = dialogos[indiceDialogo];
        indiceDialogo = Math.min(indiceDialogo + 1, dialogos.length - 1);
        return fala;
    }

    public void conscientizar() {
        this.conscientizado = true;
        this.hostil = false;
    }

    // Polimorfismo - comportamento varia conforme o tipo
    public String reagirAoJogador(int conscienciaJogador) {
        if (conscientizado) {
            return getNome() + " sorri levemente. \"Eu me lembro agora... havia árvores aqui.\"";
        }
        if (conscienciaJogador > 60) {
            return getNome() + " para por um instante, como se uma memória distante tocasse sua mente vazia.";
        }
        return getNome() + " te olha com olhos vazios e continua caminhando, como um robô.";
    }

    public boolean isHostil() { return hostil; }
    public boolean isConscientizado() { return conscientizado; }
    public String getTipo() { return tipo; }
    public void setHostil(boolean hostil) { this.hostil = hostil; }
}
