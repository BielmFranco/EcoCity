package jogo.excecoes;

// Tratamento de Exceções - exceções customizadas do jogo
public class EntradaInvalidaException extends Exception {
    public EntradaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
