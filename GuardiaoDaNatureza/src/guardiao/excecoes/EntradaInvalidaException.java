package guardiao.excecoes;

// ============================================================
// TRATAMENTO DE EXCECOES - excecao customizada do jogo
// Lancada quando o jogador digita uma entrada fora do esperado.
// ============================================================
public class EntradaInvalidaException extends Exception {

    // CONSTRUTOR - recebe a mensagem de erro e repassa para a superclasse
    public EntradaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
