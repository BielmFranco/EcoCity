# -*- coding: utf-8 -*-
"""
Gerador da apresentacao do EcoCity (APS UNIP 2026/1).
12 slides: 1-3 introducao, 4-9 projeto, 10-12 codigo.
"""
from pptx import Presentation
from pptx.util import Inches, Pt, Emu
from pptx.dml.color import RGBColor
from pptx.enum.shapes import MSO_SHAPE
from pptx.enum.text import PP_ALIGN, MSO_ANCHOR

# ==== PALETA ====
FOREST = RGBColor(0x2C, 0x5F, 0x2D)
DARK   = RGBColor(0x14, 0x2A, 0x16)
MOSS   = RGBColor(0x97, 0xBC, 0x62)
LIGHT  = RGBColor(0xF6, 0xF8, 0xF2)
WHITE  = RGBColor(0xFF, 0xFF, 0xFF)
TEXT   = RGBColor(0x1B, 0x2B, 0x1D)
MUTED  = RGBColor(0x6E, 0x7C, 0x71)
ACCENT = RGBColor(0x6A, 0xD1, 0x8A)
GOLD   = RGBColor(0xE0, 0xB2, 0x3A)
BORDER = RGBColor(0xDD, 0xE7, 0xDD)

# ==== HELPERS ====
def set_bg(slide, color):
    fill = slide.background.fill
    fill.solid()
    fill.fore_color.rgb = color

def add_rect(slide, x, y, w, h, fill=None, line=None, line_w=None, shape=MSO_SHAPE.RECTANGLE):
    sh = slide.shapes.add_shape(shape, x, y, w, h)
    sh.shadow.inherit = False
    if fill is None:
        sh.fill.background()
    else:
        sh.fill.solid()
        sh.fill.fore_color.rgb = fill
    if line is None:
        sh.line.fill.background()
    else:
        sh.line.color.rgb = line
        if line_w is not None:
            sh.line.width = line_w
    return sh

def add_text(slide, x, y, w, h, text, size=18, bold=False, color=TEXT,
             align=PP_ALIGN.LEFT, anchor=MSO_ANCHOR.TOP, font="Calibri"):
    tb = slide.shapes.add_textbox(x, y, w, h)
    tf = tb.text_frame
    tf.word_wrap = True
    tf.margin_left = Emu(0)
    tf.margin_right = Emu(0)
    tf.margin_top = Emu(0)
    tf.margin_bottom = Emu(0)
    tf.vertical_anchor = anchor
    lines = text.split("\n") if isinstance(text, str) else text
    for i, line in enumerate(lines):
        p = tf.paragraphs[0] if i == 0 else tf.add_paragraph()
        p.alignment = align
        r = p.add_run()
        r.text = line
        r.font.name = font
        r.font.size = Pt(size)
        r.font.bold = bold
        r.font.color.rgb = color
    return tb

def footer(slide, dark=False):
    color = MUTED if not dark else RGBColor(0x9B, 0xB0, 0xA0)
    add_text(slide, Inches(0.5), Inches(7.05), Inches(9.5), Inches(0.3),
             "EcoCity  -  RPG Textual  |  APS UNIP 2026/1  -  LPOO",
             size=9, color=color, font="Calibri")

def slide_number(slide, n, total, dark=False):
    color = MUTED if not dark else RGBColor(0x9B, 0xB0, 0xA0)
    add_text(slide, Inches(9.3), Inches(7.05), Inches(0.8), Inches(0.3),
             f"{n} / {total}",
             size=9, color=color, align=PP_ALIGN.RIGHT, font="Calibri")

def section_header(slide, title, kicker=None, dark=False):
    title_color = WHITE if dark else TEXT
    kicker_color = ACCENT if dark else FOREST
    add_rect(slide, Inches(0.5), Inches(0.55), Inches(0.18), Inches(0.55),
             fill=kicker_color)
    if kicker:
        add_text(slide, Inches(0.85), Inches(0.55), Inches(8), Inches(0.3),
                 kicker.upper(), size=11, bold=True, color=kicker_color,
                 font="Calibri")
    add_text(slide, Inches(0.85), Inches(0.85 if kicker else 0.55),
             Inches(9), Inches(0.7),
             title, size=32, bold=True, color=title_color, font="Calibri")

# ==== APRESENTACAO ====
prs = Presentation()
prs.slide_width = Inches(10)
prs.slide_height = Inches(7.5)

TOTAL = 12
BLANK = prs.slide_layouts[6]

# --------------------------------------------------------------------------- #
# SLIDE 1 - CAPA
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, DARK)
add_rect(s, Inches(7.5), Inches(-1.5), Inches(4.5), Inches(4.5),
         fill=FOREST, shape=MSO_SHAPE.OVAL)
add_rect(s, Inches(8.2), Inches(-0.8), Inches(3), Inches(3),
         fill=DARK, shape=MSO_SHAPE.OVAL)
add_rect(s, Inches(-1.5), Inches(5.5), Inches(4.5), Inches(4.5),
         fill=FOREST, shape=MSO_SHAPE.OVAL)
add_rect(s, Inches(-0.8), Inches(6.2), Inches(3), Inches(3),
         fill=DARK, shape=MSO_SHAPE.OVAL)

add_text(s, Inches(0.7), Inches(1.4), Inches(8), Inches(0.4),
         "APS UNIP 2026/1  -  LPOO", size=14, bold=True, color=MOSS,
         font="Calibri")
add_text(s, Inches(0.7), Inches(1.9), Inches(9), Inches(1.6),
         "EcoCity", size=88, bold=True, color=WHITE, font="Calibri")
add_text(s, Inches(0.75), Inches(3.5), Inches(9), Inches(0.6),
         "RPG Textual em Java  -  Conscientizacao Ambiental",
         size=20, color=ACCENT, font="Calibri")
add_rect(s, Inches(0.75), Inches(4.15), Inches(0.9), Inches(0.05), fill=ACCENT)

add_text(s, Inches(0.75), Inches(4.4), Inches(8), Inches(0.4),
         "Universidade Paulista  -  Ciencia da Computacao",
         size=13, color=RGBColor(0xC8, 0xDA, 0xC0), font="Calibri")
add_text(s, Inches(0.75), Inches(4.75), Inches(8), Inches(0.3),
         "Orientador: Prof. Carlos Baltazar",
         size=12, color=RGBColor(0x9B, 0xB0, 0xA0), font="Calibri")

add_text(s, Inches(0.75), Inches(5.5), Inches(2), Inches(0.3),
         "GRUPO", size=11, bold=True, color=ACCENT, font="Calibri")
add_text(s, Inches(0.75), Inches(5.85), Inches(9), Inches(1.5),
         "Alessandra Cristina da Silva Souza  -  R799565\n"
         "Caetano de Paula Telles Ribeiro     -  R688GC4\n"
         "Jhonnatan Pereira Santos            -  R363JH0\n"
         "Guilherme Moraes Franco             -  H386632\n"
         "Gabriel Moraes Franco               -  H384338",
         size=11.5, color=WHITE, font="Consolas")

# --------------------------------------------------------------------------- #
# SLIDE 2 - SUMARIO (atualizado pra 12 slides)
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Sumario", "navegacao")

itens = [
    ("3", "Objetivo do Projeto"),
    ("4", "Enredo do Jogo"),
    ("5", "Tecnologias Utilizadas"),
    ("6", "Niveis e Evolucao do Personagem"),
    ("7", "Missoes e NPCs"),
    ("8", "Sistema de Pontuacao"),
    ("9", "Demonstracao do Jogo"),
    ("10", "Estrutura do Codigo"),
    ("11", "Conceitos de O.O. e Hierarquia"),
    ("12", "Exemplo de Codigo e Entrega"),
]

def sum_item(slide, x, y, num, txt):
    add_rect(slide, x, y, Inches(0.5), Inches(0.5), fill=FOREST,
             shape=MSO_SHAPE.OVAL)
    add_text(slide, x, y, Inches(0.5), Inches(0.5), num, size=12, bold=True,
             color=WHITE, align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE,
             font="Calibri")
    add_text(slide, x + Inches(0.7), y + Inches(0.05), Inches(3.7), Inches(0.4),
             txt, size=15, bold=True, color=TEXT, font="Calibri")

# duas colunas: 5 + 5
for i in range(5):
    sum_item(s, Inches(0.85), Inches(2.0 + i * 0.85), itens[i][0], itens[i][1])
for i in range(5):
    sum_item(s, Inches(5.4), Inches(2.0 + i * 0.85), itens[i + 5][0], itens[i + 5][1])

footer(s); slide_number(s, 2, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 3 - OBJETIVO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Objetivo do Projeto", "03")

add_rect(s, Inches(0.85), Inches(2.0), Inches(4.3), Inches(4.5),
         fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1.05), Inches(2.3), Inches(4), Inches(0.6),
         "META PRINCIPAL", size=11, bold=True, color=MOSS, font="Calibri")
add_text(s, Inches(1.05), Inches(2.7), Inches(4), Inches(2.5),
         "Aplicar Programacao Orientada\na Objetos no desenvolvimento\nde um RPG textual em Java,",
         size=20, bold=True, color=WHITE, font="Calibri")
add_text(s, Inches(1.05), Inches(5.0), Inches(4), Inches(1.3),
         "promovendo a conscientizacao\nambiental atraves de\nmecanicas de jogo.",
         size=16, color=ACCENT, font="Calibri")

add_text(s, Inches(5.6), Inches(2.05), Inches(4), Inches(0.5),
         "ENTREGAS TECNICAS", size=12, bold=True, color=FOREST, font="Calibri")
bullets = [
    "RPG textual rodando no console",
    "5 niveis de evolucao do personagem",
    "15 missoes ecologicas tematicas",
    "Sistema de pontuacao com bonus",
    "9 conceitos de O.O. aplicados",
    "Tratamento de excecoes (try-catch)",
]
for i, b in enumerate(bullets):
    y = 2.55 + i * 0.6
    add_rect(s, Inches(5.6), Inches(y + 0.18), Inches(0.18), Inches(0.18),
             fill=FOREST, shape=MSO_SHAPE.OVAL)
    add_text(s, Inches(5.95), Inches(y), Inches(4), Inches(0.4),
             b, size=14, color=TEXT, font="Calibri")
footer(s); slide_number(s, 3, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 4 - ENREDO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, DARK)
section_header(s, "Enredo do Jogo", "04", dark=True)

add_text(s, Inches(0.85), Inches(2.0), Inches(8.5), Inches(2.5),
         "A historia se passa em uma cidade devastada pela\n"
         "poluicao, pelo desmatamento e por conflitos ambientais.",
         size=20, bold=True, color=WHITE, font="Calibri")

add_text(s, Inches(0.85), Inches(3.4), Inches(8.5), Inches(2),
         "O jogador deve cumprir missoes ecologicas para restaurar o\n"
         "equilibrio ambiental, evoluindo progressivamente do nivel\n"
         "1 (Explorador) ate alcancar o titulo de Guardiao da Natureza.",
         size=15, color=RGBColor(0xC8, 0xDA, 0xC0), font="Calibri")

pill_data = [
    ("1", "Explorador"),
    ("2", "Descontaminador"),
    ("3", "Botanico"),
    ("4", "Construtor"),
    ("5", "Guardiao"),
]
px = 0.85
for i, (n, t) in enumerate(pill_data):
    add_rect(s, Inches(px), Inches(5.7), Inches(1.6), Inches(0.9),
             fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(px), Inches(5.78), Inches(1.6), Inches(0.3),
             "Nivel " + n, size=10, color=MOSS,
             align=PP_ALIGN.CENTER, font="Calibri")
    add_text(s, Inches(px), Inches(6.05), Inches(1.6), Inches(0.5),
             t, size=12, bold=True, color=WHITE,
             align=PP_ALIGN.CENTER, font="Calibri")
    if i < 4:
        add_text(s, Inches(px + 1.6), Inches(5.95), Inches(0.2), Inches(0.4),
                 ">", size=18, bold=True, color=ACCENT,
                 align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE,
                 font="Calibri")
    px += 1.8
footer(s, dark=True); slide_number(s, 4, TOTAL, dark=True)

# --------------------------------------------------------------------------- #
# SLIDE 5 - TECNOLOGIAS
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Tecnologias Utilizadas", "05")

techs = [
    ("Java 21", "Linguagem orientada a objetos. JDK Microsoft OpenJDK 21."),
    ("Console / Terminal", "Interface textual. Encoding UTF-8 e escape ANSI."),
    ("Git + GitHub", "Versionamento e hospedagem publica do projeto."),
    ("Markdown", "Documentacao tecnica: README e TUTORIAL."),
]
xs = [0.85, 5.0]
ys = [2.0, 4.5]
for i, (t, b) in enumerate(techs):
    x = Inches(xs[i % 2]); y = Inches(ys[i // 2])
    add_rect(s, x, y, Inches(4.2), Inches(2.2),
             fill=WHITE, line=BORDER, line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_rect(s, x + Inches(0.3), y + Inches(0.3), Inches(0.6), Inches(0.6),
             fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, x + Inches(0.3), y + Inches(0.3), Inches(0.6), Inches(0.6),
             str(i + 1), size=20, bold=True, color=WHITE,
             align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Calibri")
    add_text(s, x + Inches(1.05), y + Inches(0.4), Inches(3), Inches(0.5),
             t, size=17, bold=True, color=FOREST, font="Calibri")
    add_text(s, x + Inches(0.3), y + Inches(1.15), Inches(3.7), Inches(1),
             b, size=12, color=TEXT, font="Calibri")
footer(s); slide_number(s, 5, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 6 - NIVEIS E EVOLUCAO DO PERSONAGEM
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Niveis e Evolucao do Personagem", "06")

add_text(s, Inches(0.85), Inches(1.95), Inches(8), Inches(0.4),
         "5 niveis progressivos - o jogador evolui ao completar todas as missoes",
         size=12, color=MUTED, font="Calibri")

niveis = [
    ("1", "Explorador",       "Iniciante. Descobre o que restou da natureza."),
    ("2", "Descontaminador",  "Limpa solo, agua e ar da cidade."),
    ("3", "Botanico",         "Replanta a vegetacao perdida."),
    ("4", "Construtor",       "Constroi estruturas sustentaveis."),
    ("5", "Guardiao",         "Titulo final. Restaura o equilibrio ambiental da cidade."),
]
y = 2.55
for n, classe, desc in niveis:
    add_rect(s, Inches(0.85), Inches(y), Inches(8.3), Inches(0.78),
             fill=WHITE, line=BORDER, line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_rect(s, Inches(0.85), Inches(y), Inches(0.7), Inches(0.78),
             fill=FOREST, shape=MSO_SHAPE.RECTANGLE)
    add_text(s, Inches(0.85), Inches(y), Inches(0.7), Inches(0.78),
             n, size=24, bold=True, color=WHITE,
             align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Calibri")
    add_text(s, Inches(1.75), Inches(y + 0.13), Inches(3), Inches(0.5),
             classe, size=15, bold=True, color=FOREST, font="Calibri")
    add_text(s, Inches(1.75), Inches(y + 0.42), Inches(7.2), Inches(0.4),
             desc, size=11, color=TEXT, font="Calibri")
    y += 0.88
footer(s); slide_number(s, 6, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 7 - MISSOES E NPCs
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Missoes e NPCs", "07")

add_text(s, Inches(0.85), Inches(1.95), Inches(8.5), Inches(0.4),
         "Nivel N possui N NPCs e N missoes - 1 missao por NPC (total: 15)",
         size=13, color=MUTED, font="Calibri")

# left: como funciona NPC
add_rect(s, Inches(0.85), Inches(2.5), Inches(4.0), Inches(2.4),
         fill=WHITE, line=BORDER, line_w=Pt(1),
         shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1.1), Inches(2.65), Inches(3.5), Inches(0.4),
         "COMO FUNCIONA O NPC", size=11, bold=True, color=FOREST, font="Calibri")
add_text(s, Inches(1.1), Inches(3.0), Inches(3.6), Inches(1.9),
         "- Entrega 1 missao ecologica\n"
         "- Interacao direta e mecanica\n"
         "- Sem dialogos longos\n"
         "- Registra a conclusao\n"
         "- Libera o avanco do jogador",
         size=12.5, color=TEXT, font="Calibri")

# right: tipos de missao
add_rect(s, Inches(5.15), Inches(2.5), Inches(4.0), Inches(2.4),
         fill=WHITE, line=BORDER, line_w=Pt(1),
         shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(5.4), Inches(2.65), Inches(3.5), Inches(0.4),
         "TIPOS DE MISSAO ECOLOGICA", size=11, bold=True, color=FOREST,
         font="Calibri")
add_text(s, Inches(5.4), Inches(3.0), Inches(3.6), Inches(1.9),
         "- Plantar sementes / replantar\n"
         "- Despoluir rios e lagos\n"
         "- Filtrar o ar\n"
         "- Construir parques e captacao\n"
         "- Inaugurar reservas naturais",
         size=12.5, color=TEXT, font="Calibri")

# bottom: stats row
stats = [("15", "missoes"), ("15", "NPCs"), ("5", "niveis"),
         ("1", "missao por NPC")]
x = 0.85
for n, lbl in stats:
    add_rect(s, Inches(x), Inches(5.1), Inches(2.0), Inches(1.7),
             fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(x), Inches(5.2), Inches(2.0), Inches(1),
             n, size=46, bold=True, color=WHITE,
             align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Calibri")
    add_text(s, Inches(x), Inches(6.25), Inches(2.0), Inches(0.4),
             lbl, size=12, color=MOSS,
             align=PP_ALIGN.CENTER, font="Calibri")
    x += 2.1
footer(s); slide_number(s, 7, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 8 - SISTEMA DE PONTUACAO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Sistema de Pontuacao", "08")

items = [
    ("Pontuacao Base", "nivel x 100",
     "Sobe com a dificuldade. Nivel 1 = 100 / Nivel 5 = 500."),
    ("Bonus Eficiencia", "+ 50 pontos",
     "Cumprir a missao dentro do limite de turnos."),
    ("Bonus Classe Ideal", "nivel x 25",
     "Usar a classe adequada para o tipo de missao."),
]
x = 0.85
for t, formula, body in items:
    add_rect(s, Inches(x), Inches(2.0), Inches(2.85), Inches(3.5),
             fill=WHITE, line=BORDER, line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(x), Inches(2.2), Inches(2.85), Inches(0.5),
             t, size=14, bold=True, color=FOREST,
             align=PP_ALIGN.CENTER, font="Calibri")
    add_rect(s, Inches(x + 0.25), Inches(2.85), Inches(2.35), Inches(0.85),
             fill=DARK, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(x + 0.25), Inches(2.85), Inches(2.35), Inches(0.85),
             formula, size=20, bold=True, color=ACCENT,
             align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Consolas")
    add_text(s, Inches(x + 0.25), Inches(3.9), Inches(2.4), Inches(1.5),
             body, size=12, color=TEXT,
             align=PP_ALIGN.CENTER, font="Calibri")
    x += 3.05

add_rect(s, Inches(0.85), Inches(5.85), Inches(8.3), Inches(0.95),
         fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1), Inches(5.85), Inches(8), Inches(0.95),
         "Total = (nivel x 100) + bonus eficiencia + bonus classe",
         size=14, bold=True, color=WHITE,
         align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Consolas")
footer(s); slide_number(s, 8, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 9 - DEMONSTRACAO DO JOGO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, DARK)
section_header(s, "Demonstracao do Jogo", "09", dark=True)

mocks = [
    ("MENU PRINCIPAL",
     "+==================================+\n"
     "|          ECOCITY - RPG           |\n"
     "+==================================+\n"
     "|  [1] Iniciar Jogo                |\n"
     "|  [2] Instrucoes                  |\n"
     "|  [3] Creditos                    |\n"
     "|  [4] Sair                        |\n"
     "+==================================+\n"
     "   Sua escolha:"),
    ("ENREDO",
     "============================================\n"
     "      E C O C I T Y  -  E N R E D O\n"
     "============================================\n"
     "A cidade foi devastada pela poluicao,\n"
     "pelo desmatamento e por conflitos\n"
     "ambientais.\n\n"
     "Voce decide mudar esse destino..."),
    ("MISSAO",
     " > NPC 2.1 tem uma missao para voce.\n"
     "   Missao: Despoluir as aguas do rio\n"
     "   Limite: 5 turnos (para bonus)\n"
     "   Como cumprir a missao?\n"
     "    [1] Classe ideal (eficiente)\n"
     "    [2] Abordagem comum\n"
     "    >"),
    ("FIM DE JOGO",
     "F I M   D E   J O G O\n"
     "============================================\n"
     "A cidade respira de novo.\n"
     "Os rios correm limpos.\n\n"
     "Heroi conquistou o titulo de\n"
     "GUARDIAO DA NATUREZA.\n"
     "  Pontos: 7625  |  Missoes: 15"),
]
positions = [(0.85, 2.0), (5.15, 2.0), (0.85, 4.7), (5.15, 4.7)]
for (label, body), (x, y) in zip(mocks, positions):
    add_rect(s, Inches(x), Inches(y), Inches(4.0), Inches(2.55),
             fill=RGBColor(0x0A, 0x14, 0x0B),
             line=FOREST, line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(x + 0.15), Inches(y + 0.1), Inches(3.7), Inches(0.3),
             "$ " + label, size=10, bold=True, color=ACCENT, font="Consolas")
    add_text(s, Inches(x + 0.15), Inches(y + 0.45), Inches(3.7), Inches(2.0),
             body, size=8.5, color=RGBColor(0xC8, 0xDA, 0xC0), font="Consolas")
footer(s, dark=True); slide_number(s, 9, TOTAL, dark=True)

# --------------------------------------------------------------------------- #
# SLIDE 10 - ESTRUTURA DO CODIGO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Estrutura do Codigo", "10")

add_text(s, Inches(0.85), Inches(1.95), Inches(8), Inches(0.4),
         "14 arquivos .java organizados em 7 pacotes - cada um com 1 responsabilidade",
         size=12, color=MUTED, font="Calibri")

pacotes = [
    ("Main.java",     "ponto de entrada do jogo (menu)"),
    ("ui/",           "Terminal: limpa tela + aguarda ENTER"),
    ("personagens/",  "Personagem (abstrata) + 5 niveis"),
    ("missoes/",      "Interface Missao + MissaoEcologica"),
    ("npc/",          "NPC entrega missoes ao jogador"),
    ("sistema/",      "Jogo (orquestra) + Pontuacao (calcula)"),
    ("excecoes/",     "EntradaInvalidaException (customizada)"),
]
y = 2.6
for nome, desc in pacotes:
    add_rect(s, Inches(0.85), Inches(y), Inches(8.3), Inches(0.55),
             fill=WHITE, line=BORDER, line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_rect(s, Inches(0.85), Inches(y), Inches(0.18), Inches(0.55),
             fill=FOREST)
    add_text(s, Inches(1.15), Inches(y), Inches(3), Inches(0.55),
             nome, size=12.5, bold=True, color=FOREST,
             anchor=MSO_ANCHOR.MIDDLE, font="Consolas")
    add_text(s, Inches(4.3), Inches(y), Inches(4.8), Inches(0.55),
             desc, size=12, color=TEXT,
             anchor=MSO_ANCHOR.MIDDLE, font="Calibri")
    y += 0.6
footer(s); slide_number(s, 10, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 11 - CONCEITOS OO + HIERARQUIA
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Conceitos de O.O. e Hierarquia", "11")

# left: lista de conceitos
add_text(s, Inches(0.85), Inches(1.95), Inches(4), Inches(0.4),
         "9 CONCEITOS APLICADOS", size=12, bold=True, color=FOREST,
         font="Calibri")
conc = [
    "Encapsulamento",
    "Construtores",
    "Heranca",
    "Sobrecarga (overload)",
    "Sobrescrita (override)",
    "Classe Abstrata",
    "Metodo Abstrato",
    "Interface",
    "Tratamento de Excecoes",
]
for i, c in enumerate(conc):
    y = 2.45 + i * 0.42
    add_rect(s, Inches(0.85), Inches(y + 0.13), Inches(0.16), Inches(0.16),
             fill=FOREST, shape=MSO_SHAPE.OVAL)
    add_text(s, Inches(1.15), Inches(y), Inches(3.5), Inches(0.4),
             c, size=13, color=TEXT, font="Calibri")

# right: hierarquia (caixas)
add_text(s, Inches(5.15), Inches(1.95), Inches(4), Inches(0.4),
         "HIERARQUIA DE CLASSES", size=12, bold=True, color=FOREST,
         font="Calibri")

# Personagem box (top)
add_rect(s, Inches(6.4), Inches(2.5), Inches(2.5), Inches(0.7),
         fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(6.4), Inches(2.55), Inches(2.5), Inches(0.25),
         "abstract class", size=9, color=MOSS,
         align=PP_ALIGN.CENTER, font="Calibri")
add_text(s, Inches(6.4), Inches(2.78), Inches(2.5), Inches(0.4),
         "Personagem", size=14, bold=True, color=WHITE,
         align=PP_ALIGN.CENTER, font="Calibri")

# 5 children
subs = ["Explorador", "Descontamin.", "Botanico", "Construtor", "Guardiao"]
for i, sub in enumerate(subs):
    sy = 3.55 + i * 0.55
    add_rect(s, Inches(6.4), Inches(sy), Inches(2.5), Inches(0.45),
             fill=WHITE, line=FOREST, line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(6.5), Inches(sy + 0.07), Inches(0.6), Inches(0.3),
             f"N{i+1}", size=10, bold=True, color=MOSS, font="Calibri")
    add_text(s, Inches(7.0), Inches(sy + 0.07), Inches(2), Inches(0.3),
             sub, size=12, bold=True, color=TEXT, font="Calibri")

# small note
add_text(s, Inches(5.15), Inches(6.35), Inches(4.2), Inches(0.4),
         "5 subclasses herdam de Personagem (abstrata).",
         size=10, color=MUTED, font="Calibri", align=PP_ALIGN.CENTER)
footer(s); slide_number(s, 11, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 12 - EXEMPLO DE CODIGO + GITHUB + ENCERRAMENTO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, DARK)
section_header(s, "Em Codigo  -  Entrega e Encerramento", "12", dark=True)

# code box
add_text(s, Inches(0.85), Inches(2.0), Inches(8), Inches(0.4),
         "EXEMPLO: TRATAMENTO DE EXCECAO NO MENU", size=11, bold=True,
         color=ACCENT, font="Calibri")
code = ("try {\n"
        "    int opcao = Integer.parseInt(scanner.nextLine().trim());\n"
        "    if (opcao < 1 || opcao > 4)\n"
        "        throw new EntradaInvalidaException(\"Opcao invalida.\");\n"
        "    // ... executa a opcao escolhida ...\n"
        "} catch (NumberFormatException e) {\n"
        "    System.out.println(\"[ERRO] Digite apenas numeros.\");\n"
        "} catch (EntradaInvalidaException e) {\n"
        "    System.out.println(\"[ERRO] \" + e.getMessage());\n"
        "}")
add_rect(s, Inches(0.85), Inches(2.45), Inches(8.3), Inches(2.5),
         fill=RGBColor(0x0A, 0x14, 0x0B),
         line=FOREST, line_w=Pt(1),
         shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1.1), Inches(2.6), Inches(8), Inches(2.35),
         code, size=11.5, color=ACCENT, font="Consolas")

# github + obrigado
add_rect(s, Inches(0.85), Inches(5.2), Inches(8.3), Inches(1.5),
         fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1.1), Inches(5.3), Inches(8), Inches(0.4),
         "REPOSITORIO PUBLICO", size=11, bold=True, color=MOSS, font="Calibri")
add_text(s, Inches(1.1), Inches(5.6), Inches(8), Inches(0.6),
         "github.com/BielmFranco/EcoCity", size=22, bold=True, color=WHITE,
         font="Consolas")
add_text(s, Inches(1.1), Inches(6.15), Inches(8), Inches(0.4),
         "Obrigado!  -  EcoCity, APS UNIP 2026/1.",
         size=14, color=ACCENT, font="Calibri")
footer(s, dark=True); slide_number(s, 12, TOTAL, dark=True)

# salva
out_path = r"C:\Users\gabrielf\Desktop\ProjetoAPSEcoCity\relatorio\ApresentacaoEcoCity.pptx"
prs.save(out_path)
print(f"OK: {out_path}")
print(f"slides: {len(prs.slides)}")
