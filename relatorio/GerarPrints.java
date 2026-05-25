import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// Gerador de imagens (prints) explicativas do projeto EcoCity.
public class GerarPrints {

    static final Color FUNDO   = new Color(0x12, 0x1a, 0x14);
    static final Color PAINEL  = new Color(0x1c, 0x2a, 0x20);
    static final Color BORDA   = new Color(0x3f, 0x6f, 0x4e);
    static final Color VERDE   = new Color(0x6a, 0xd1, 0x8a);
    static final Color BRANCO  = new Color(0xe6, 0xf0, 0xe8);
    static final Color CINZA   = new Color(0x9a, 0xa8, 0x9e);
    static final Color AMARELO = new Color(0xe8, 0xc8, 0x6a);

    public static void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "true");
        new File("saida").mkdirs();
        estrutura();
        hierarquia();
        fluxo();
        conceitos();
        System.out.println("4 prints gerados em relatorio/saida/");
    }

    static Graphics2D base(BufferedImage img, String titulo) {
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(FUNDO);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.setColor(VERDE);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.drawString(titulo, 40, 56);
        g.setColor(BORDA);
        g.drawLine(40, 72, img.getWidth() - 40, 72);
        g.setColor(CINZA);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.drawString("EcoCity - RPG Textual - APS UNIP 2026/1 | LPOO", 40, img.getHeight() - 28);
        return g;
    }

    static void salvar(BufferedImage img, String nome) throws Exception {
        ImageIO.write(img, "png", new File("saida/" + nome));
    }

    // PRINT 1 - estrutura de pastas e arquivos
    static void estrutura() throws Exception {
        BufferedImage img = new BufferedImage(1100, 760, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = base(img, "1. Estrutura do Codigo");
        String[][] linhas = {
            {"src/guardiao/", ""},
            {"  Main.java", "ponto de entrada: menu + loop try-catch"},
            {"  ui/Terminal.java", "limpa a tela e aguarda ENTER (telas)"},
            {"  personagens/", ""},
            {"    Personagem.java", "CLASSE ABSTRATA - base do jogador"},
            {"    Explorador.java", "nivel 1"},
            {"    Descontaminador.java", "nivel 2"},
            {"    Botanico.java", "nivel 3"},
            {"    Construtor.java", "nivel 4"},
            {"    Guardiao.java", "nivel 5"},
            {"  missoes/", ""},
            {"    Missao.java", "INTERFACE - contrato da missao"},
            {"    MissaoEcologica.java", "implementacao da missao"},
            {"  npc/NPC.java", "entrega missao de forma mecanica"},
            {"  sistema/", ""},
            {"    Jogo.java", "orquestra os 5 niveis e o enredo"},
            {"    Pontuacao.java", "calcula pontos base + bonus"},
            {"  excecoes/", ""},
            {"    EntradaInvalidaException.java", "excecao customizada"},
        };
        Font mono = new Font("Monospaced", Font.BOLD, 17);
        Font monoP = new Font("Monospaced", Font.PLAIN, 15);
        int y = 120;
        for (String[] l : linhas) {
            g.setFont(mono);
            g.setColor(l[1].isEmpty() ? AMARELO : BRANCO);
            g.drawString(l[0], 60, y);
            if (!l[1].isEmpty()) {
                g.setFont(monoP);
                g.setColor(CINZA);
                g.drawString("- " + l[1], 470, y);
            }
            y += 33;
        }
        g.dispose();
        salvar(img, "1_estrutura.png");
    }

    // desenha uma caixa com texto centralizado
    static void caixa(Graphics2D g, int x, int y, int w, int h, String txt, Color cor) {
        g.setColor(PAINEL);
        g.fillRoundRect(x, y, w, h, 14, 14);
        g.setColor(cor);
        g.setStroke(new BasicStroke(2f));
        g.drawRoundRect(x, y, w, h, 14, 14);
        g.setColor(BRANCO);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        g.drawString(txt, x + (w - fm.stringWidth(txt)) / 2, y + h / 2 + 6);
    }

    static void seta(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.setColor(BORDA);
        g.setStroke(new BasicStroke(2f));
        g.drawLine(x1, y1, x2, y2);
    }

    // PRINT 2 - hierarquia de classes (heranca + interface)
    static void hierarquia() throws Exception {
        BufferedImage img = new BufferedImage(1100, 760, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = base(img, "2. Hierarquia de Classes (Heranca)");

        caixa(g, 410, 110, 280, 56, "Personagem  (abstrata)", AMARELO);
        String[] subs = {"Explorador", "Descontaminador", "Botanico", "Construtor", "Guardiao"};
        int x = 70;
        for (int i = 0; i < subs.length; i++) {
            caixa(g, x, 270, 185, 52, subs[i], VERDE);
            seta(g, 550, 166, x + 92, 270);
            x += 195;
        }
        g.setColor(CINZA);
        g.setFont(new Font("SansSerif", Font.PLAIN, 15));
        g.drawString("5 subclasses herdam de Personagem - cada uma fixa seu nivel (1 a 5)", 70, 365);
        g.drawString("e SOBRESCREVE getTitulo() e getClasseIdeal().", 70, 390);

        caixa(g, 230, 470, 240, 54, "Missao  (interface)", AMARELO);
        caixa(g, 600, 470, 270, 54, "MissaoEcologica", VERDE);
        seta(g, 470, 497, 600, 497);
        g.setColor(CINZA);
        g.drawString("MissaoEcologica IMPLEMENTA a interface Missao.", 230, 575);

        g.setColor(BRANCO);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("Conceitos: Heranca | Classe Abstrata | Metodo Abstrato | Interface | Sobrescrita",
                70, 660);
        g.dispose();
        salvar(img, "2_hierarquia.png");
    }

    // PRINT 3 - fluxo de telas do jogo
    static void fluxo() throws Exception {
        BufferedImage img = new BufferedImage(1100, 760, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = base(img, "3. Fluxo de Telas (avanca com ENTER)");
        String[] telas = {"Enredo", "Criacao", "Nivel 1", "Nivel 2",
                           "Nivel 3", "Nivel 4", "Nivel 5", "Final"};
        int[] xs = {90, 340, 590, 840};
        int[] ys = {130, 290, 450, 610};
        int idx = 0;
        for (int row = 0; row < 4 && idx < telas.length; row++) {
            for (int col = 0; col < 4 && idx < telas.length; col++) {
                int cx = (row % 2 == 0) ? col : 3 - col;
                caixa(g, xs[cx], ys[row], 170, 56, telas[idx], VERDE);
                idx++;
            }
        }
        g.setColor(AMARELO);
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        g.drawString("Cada caixa = uma tela. Terminal.limparTela() + ENTER para avancar.", 90, 720);
        g.dispose();
        salvar(img, "3_fluxo.png");
    }

    // PRINT 4 - conceitos de O.O.
    static void conceitos() throws Exception {
        BufferedImage img = new BufferedImage(1100, 760, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = base(img, "4. Conceitos de O.O. no Codigo");
        String[][] c = {
            {"Encapsulamento", "atributos private + getters/setters"},
            {"Construtores", "todas as classes"},
            {"Heranca", "5 subclasses extends Personagem"},
            {"Sobrecarga", "2 construtores; Pontuacao.calcular()"},
            {"Sobrescrita", "getTitulo(); metodos da interface Missao"},
            {"Classe Abstrata", "Personagem"},
            {"Metodo Abstrato", "getTitulo(); getClasseIdeal()"},
            {"Interface", "Missao"},
            {"Tratamento Excecao", "EntradaInvalidaException + try-catch"},
        };
        int y = 130;
        Font fc = new Font("SansSerif", Font.BOLD, 18);
        Font fd = new Font("Monospaced", Font.PLAIN, 15);
        for (String[] l : c) {
            g.setColor(PAINEL);
            g.fillRoundRect(60, y - 24, 980, 44, 10, 10);
            g.setColor(VERDE);
            g.setFont(fc);
            g.drawString(l[0], 80, y + 4);
            g.setColor(BRANCO);
            g.setFont(fd);
            g.drawString(l[1], 380, y + 4);
            y += 58;
        }
        g.dispose();
        salvar(img, "4_conceitos.png");
    }
}
