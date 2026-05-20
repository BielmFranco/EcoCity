package guardiao.missoes;

// ============================================================
// INTERFACE - contrato de toda missao do jogo.
// Qualquer classe que represente uma missao DEVE implementar
// todos estes metodos.
// ============================================================
public interface Missao {

    String getDescricao();

    int getPontosBase();

    int getTurnosLimite();

    // conclui a missao registrando quantos turnos foram gastos
    void concluir(int turnosGastos);

    boolean isConcluida();

    int getTurnosGastos();
}
