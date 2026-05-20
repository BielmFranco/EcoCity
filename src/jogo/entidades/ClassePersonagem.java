package jogo.entidades;

// Classe Final - enum não pode ser estendido
public enum ClassePersonagem {

    BIOLOGO("Biólogo",
            "Especialista em plantação e regeneração de áreas degradadas.",
            80, 8, 7) {
        @Override
        public int calcularHabilidade(int ataque, int consciencia) {
            // Habilidade: Regeneração de Área - usa conhecimento botânico e consciência ecológica
            return ataque + (consciencia / 3) + 4;
        }
        @Override public int getBonusHPNivel() { return 6; }
        @Override public int getBonusAtaqueNivel() { return 1; }
        @Override public int getBonusDefesaNivel() { return 2; }
    },

    ENGENHEIRO("Engenheiro",
               "Mestre em construção e criação de soluções de tecnologia verde.",
               85, 12, 10) {
        @Override
        public int calcularHabilidade(int ataque, int consciencia) {
            // Habilidade: Construção Sustentável - combina força física e inovação
            return ataque + (consciencia / 5) + 6;
        }
        @Override public int getBonusHPNivel() { return 7; }
        @Override public int getBonusAtaqueNivel() { return 2; }
        @Override public int getBonusDefesaNivel() { return 3; }
    },

    ATIVISTA("Ativista",
             "Convence cidadãos e reduz a resistência social à mudança.",
             70, 6, 5) {
        @Override
        public int calcularHabilidade(int ataque, int consciencia) {
            // Habilidade: Conscientização em Massa - poder baseado na consciência acumulada
            return ataque + (consciencia / 2) + 2;
        }
        @Override public int getBonusHPNivel() { return 5; }
        @Override public int getBonusAtaqueNivel() { return 1; }
        @Override public int getBonusDefesaNivel() { return 1; }
    },

    EXPLORADOR("Explorador",
               "Acessa áreas restritas e recolhe recursos escondidos na cidade.",
               75, 10, 6) {
        @Override
        public int calcularHabilidade(int ataque, int consciencia) {
            // Habilidade: Exploração Furtiva - imprevisível, bônus aleatório de terreno
            return ataque + (consciencia / 4) + (int)(Math.random() * 10);
        }
        @Override public int getBonusHPNivel() { return 5; }
        @Override public int getBonusAtaqueNivel() { return 3; }
        @Override public int getBonusDefesaNivel() { return 2; }
    };

    // Atributo Final - nome da classe não pode ser alterado
    private final String nome;
    private final String descricao;
    private final int hpBase;
    private final int ataqueBase;
    private final int defesaBase;

    ClassePersonagem(String nome, String descricao, int hpBase, int ataqueBase, int defesaBase) {
        this.nome = nome;
        this.descricao = descricao;
        this.hpBase = hpBase;
        this.ataqueBase = ataqueBase;
        this.defesaBase = defesaBase;
    }

    // Método Abstrato no enum - Polimorfismo de método
    public abstract int calcularHabilidade(int ataque, int consciencia);
    public abstract int getBonusHPNivel();
    public abstract int getBonusAtaqueNivel();
    public abstract int getBonusDefesaNivel();

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getHpBase() { return hpBase; }
    public int getAtaqueBase() { return ataqueBase; }
    public int getDefesaBase() { return defesaBase; }
}
