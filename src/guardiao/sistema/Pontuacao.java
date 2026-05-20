package guardiao.sistema;

import guardiao.missoes.Missao;

// ============================================================
// OPCAO 2 - SISTEMA DE PONTOS
// Calcula a pontuacao base por nivel e aplica a bonificacao
// por missao cumprida com eficiencia.
// ============================================================
public class Pontuacao {

    // Pontos extras concedidos como bonus
    private static final int BONUS_EFICIENCIA = 50;
    private static final int BONUS_CLASSE_POR_NIVEL = 25;

    // SOBRECARGA (versao 1) - calculo simples: apenas a base por nivel.
    // A dificuldade sobe com o nivel: nivel 1 = 100, nivel 5 = 500.
    public static int calcular(int nivel) {
        return nivel * 100;
    }

    // SOBRECARGA (versao 2) - calculo completo: base + bonificacoes.
    //  - Bonus de eficiencia: missao cumprida dentro do limite de turnos.
    //  - Bonus de classe ideal: classe do jogador combina com a missao.
    public static int calcular(int nivel, Missao missao, boolean classeIdeal) {
        int base = calcular(nivel); // reaproveita a versao 1
        int bonus = 0;

        // BONUS por eficiencia (menos turnos que o limite)
        if (missao.getTurnosGastos() <= missao.getTurnosLimite()) {
            bonus += BONUS_EFICIENCIA;
        }

        // BONUS por usar a classe ideal para o tipo de missao
        if (classeIdeal) {
            bonus += nivel * BONUS_CLASSE_POR_NIVEL;
        }

        return base + bonus;
    }
}
