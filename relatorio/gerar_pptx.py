# -*- coding: utf-8 -*-
"""
Gerador da apresentacao do EcoCity (APS UNIP 2026/1).
Usa python-pptx. Tema Forest & Moss (ecologico).
"""
from pptx import Presentation
from pptx.util import Inches, Pt, Emu
from pptx.dml.color import RGBColor
from pptx.enum.shapes import MSO_SHAPE
from pptx.enum.text import PP_ALIGN, MSO_ANCHOR
from pptx.oxml.ns import qn
from copy import deepcopy
from lxml import etree

# ==== PALETA ====
FOREST    = RGBColor(0x2C, 0x5F, 0x2D)
DARK      = RGBColor(0x14, 0x2A, 0x16)
MOSS      = RGBColor(0x97, 0xBC, 0x62)
LIGHT     = RGBColor(0xF6, 0xF8, 0xF2)
WHITE     = RGBColor(0xFF, 0xFF, 0xFF)
TEXT      = RGBColor(0x1B, 0x2B, 0x1D)
MUTED     = RGBColor(0x6E, 0x7C, 0x71)
ACCENT    = RGBColor(0x6A, 0xD1, 0x8A)
GOLD      = RGBColor(0xE0, 0xB2, 0x3A)

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

def add_runs(slide, x, y, w, h, runs, align=PP_ALIGN.LEFT, anchor=MSO_ANCHOR.TOP):
    """runs = list of (text, size, bold, color, font)"""
    tb = slide.shapes.add_textbox(x, y, w, h)
    tf = tb.text_frame
    tf.word_wrap = True
    tf.margin_left = Emu(0); tf.margin_right = Emu(0)
    tf.margin_top = Emu(0); tf.margin_bottom = Emu(0)
    tf.vertical_anchor = anchor
    p = tf.paragraphs[0]
    p.alignment = align
    for i, (txt, size, bold, color, font) in enumerate(runs):
        r = p.add_run()
        r.text = txt
        r.font.size = Pt(size)
        r.font.bold = bold
        r.font.color.rgb = color
        r.font.name = font
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

def section_header(slide, title, kicker=None):
    # small accent block + title (no full-width bars - guidance)
    add_rect(slide, Inches(0.5), Inches(0.55), Inches(0.18), Inches(0.55), fill=FOREST)
    if kicker:
        add_text(slide, Inches(0.85), Inches(0.55), Inches(8), Inches(0.3),
                 kicker.upper(), size=11, bold=True, color=FOREST,
                 font="Calibri")
    add_text(slide, Inches(0.85), Inches(0.85 if kicker else 0.55), Inches(9), Inches(0.7),
             title, size=32, bold=True, color=TEXT, font="Calibri")

def card(slide, x, y, w, h, title, body, num=None):
    add_rect(slide, x, y, w, h, fill=WHITE, line=RGBColor(0xDD, 0xE7, 0xDD), line_w=Pt(1))
    cx = x + Inches(0.25)
    if num:
        # numbered circle
        circle = add_rect(slide, x + Inches(0.25), y + Inches(0.25),
                          Inches(0.45), Inches(0.45),
                          fill=FOREST, shape=MSO_SHAPE.OVAL)
        add_text(slide, x + Inches(0.25), y + Inches(0.25), Inches(0.45), Inches(0.45),
                 num, size=14, bold=True, color=WHITE,
                 align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Calibri")
        cx = x + Inches(0.85)
    add_text(slide, cx, y + Inches(0.27), w - (cx - x) - Inches(0.2), Inches(0.4),
             title, size=15, bold=True, color=FOREST, font="Calibri")
    add_text(slide, x + Inches(0.25), y + Inches(0.85), w - Inches(0.5), h - Inches(1),
             body, size=11, color=TEXT, font="Calibri")

def pill(slide, x, y, w, h, text, fill=FOREST, fg=WHITE, size=12, bold=True):
    add_rect(slide, x, y, w, h, fill=fill, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(slide, x, y, w, h, text, size=size, bold=bold, color=fg,
             align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Calibri")

# ==== APRESENTACAO ====
prs = Presentation()
prs.slide_width = Inches(10)
prs.slide_height = Inches(7.5)

TOTAL = 19
BLANK = prs.slide_layouts[6]

# --------------------------------------------------------------------------- #
# SLIDE 1 - CAPA
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, DARK)
# decorative concentric arcs (motif: leaves) - subtle
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

# small underline accent (NOT full-width)
add_rect(s, Inches(0.75), Inches(4.15), Inches(0.9), Inches(0.05), fill=ACCENT)

add_text(s, Inches(0.75), Inches(4.4), Inches(8), Inches(0.4),
         "Universidade Paulista  -  Ciencia da Computacao",
         size=13, color=RGBColor(0xC8, 0xDA, 0xC0), font="Calibri")
add_text(s, Inches(0.75), Inches(4.75), Inches(8), Inches(0.3),
         "Orientador: Prof. Carlos Baltazar",
         size=12, color=RGBColor(0x9B, 0xB0, 0xA0), font="Calibri")

# grupo
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
# SLIDE 2 - SUMARIO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Sumario", "navegacao")

items_left = [
    ("01", "Objetivo"),
    ("02", "Introducao e Tema"),
    ("03", "Enredo do Jogo"),
    ("04", "Tecnologias Utilizadas"),
    ("05", "Conceitos de O.O. Aplicados"),
]
items_right = [
    ("06", "Arquitetura do Codigo"),
    ("07", "Hierarquia de Classes"),
    ("08", "Niveis, Missoes e Pontuacao"),
    ("09", "Demonstracao do Jogo"),
    ("10", "Entrega e Conclusao"),
]

def sum_item(slide, x, y, num, txt):
    add_rect(slide, x, y, Inches(0.5), Inches(0.5), fill=FOREST,
             shape=MSO_SHAPE.OVAL)
    add_text(slide, x, y, Inches(0.5), Inches(0.5), num, size=12, bold=True,
             color=WHITE, align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE,
             font="Calibri")
    add_text(slide, x + Inches(0.75), y + Inches(0.05), Inches(3.7), Inches(0.4),
             txt, size=16, bold=True, color=TEXT, font="Calibri")

y0 = 2.0
for i, (n, t) in enumerate(items_left):
    sum_item(s, Inches(0.85), Inches(y0 + i * 0.8), n, t)
for i, (n, t) in enumerate(items_right):
    sum_item(s, Inches(5.5), Inches(y0 + i * 0.8), n, t)
footer(s); slide_number(s, 2, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 3 - OBJETIVO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Objetivo do Projeto", "01")

# big stat callout
add_rect(s, Inches(0.85), Inches(2.0), Inches(4.3), Inches(4.5),
         fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1.05), Inches(2.3), Inches(4), Inches(0.6),
         "META PRINCIPAL", size=11, bold=True, color=MOSS, font="Calibri")
add_text(s, Inches(1.05), Inches(2.7), Inches(4), Inches(2.5),
         "Aplicar Programacao Orientada a Objetos no\n"
         "desenvolvimento de um RPG\ntextual em Java,",
         size=20, bold=True, color=WHITE, font="Calibri")
add_text(s, Inches(1.05), Inches(5.0), Inches(4), Inches(1.3),
         "promovendo a conscientizacao\nambiental atraves de\nmecanicas de jogo (gamificacao).",
         size=16, color=ACCENT, font="Calibri")

# right column - bullets
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
# SLIDE 4 - INTRODUCAO E TEMA
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Introducao e Tema", "02")

add_text(s, Inches(0.85), Inches(1.95), Inches(8.5), Inches(0.5),
         "Tecnologia como ferramenta de educacao ambiental",
         size=18, bold=True, color=FOREST, font="Calibri")

add_text(s, Inches(0.85), Inches(2.55), Inches(8.5), Inches(2.2),
         "Os jogos digitais deixaram de ser apenas entretenimento e passaram a "
         "desempenhar papel relevante na educacao e na sociedade.\n\n"
         "Segundo Piaget (1978), os jogos sao fundamentais no desenvolvimento "
         "cognitivo - a aprendizagem ocorre por meio da experimentacao e da "
         "construcao ativa do conhecimento.",
         size=14, color=TEXT, font="Calibri")

# quote card
add_rect(s, Inches(0.85), Inches(5.1), Inches(8.5), Inches(1.6),
         fill=WHITE, line=FOREST, line_w=Pt(1.5),
         shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1.1), Inches(5.25), Inches(0.5), Inches(0.5),
         '"', size=46, bold=True, color=MOSS, font="Georgia")
add_text(s, Inches(1.7), Inches(5.35), Inches(7.5), Inches(1.3),
         "O EcoCity nasce dessa premissa: um RPG em Java que une\n"
         "POO, narrativa ambiental e gamificacao em uma unica experiencia.",
         size=14, bold=True, color=TEXT, font="Calibri")
footer(s); slide_number(s, 4, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 5 - ENREDO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, DARK)
add_rect(s, Inches(0.5), Inches(0.55), Inches(0.18), Inches(0.55), fill=ACCENT)
add_text(s, Inches(0.85), Inches(0.55), Inches(8), Inches(0.3),
         "03", size=11, bold=True, color=ACCENT, font="Calibri")
add_text(s, Inches(0.85), Inches(0.85), Inches(9), Inches(0.7),
         "Enredo do Jogo", size=32, bold=True, color=WHITE, font="Calibri")

add_text(s, Inches(0.85), Inches(2.0), Inches(8.5), Inches(2.5),
         "A historia se passa em uma cidade devastada pela\n"
         "poluicao, pelo desmatamento e por conflitos ambientais.",
         size=20, bold=True, color=WHITE, font="Calibri")

add_text(s, Inches(0.85), Inches(3.4), Inches(8.5), Inches(2),
         "O jogador deve cumprir missoes ecologicas para restaurar o\n"
         "equilibrio ambiental, evoluindo progressivamente do nivel\n"
         "1 (Explorador) ate alcancar o titulo de Guardiao da Natureza.",
         size=15, color=RGBColor(0xC8, 0xDA, 0xC0), font="Calibri")

# evolution pills
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

footer(s, dark=True); slide_number(s, 5, TOTAL, dark=True)

# --------------------------------------------------------------------------- #
# SLIDE 6 - TECNOLOGIAS
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Tecnologias Utilizadas", "04")

techs = [
    ("Java 21", "Linguagem orientada a objetos. JDK Microsoft OpenJDK 21."),
    ("Console / Terminal", "Interface textual. Encoding UTF-8, escape ANSI para limpar tela."),
    ("Git + GitHub", "Versionamento e hospedagem do codigo-fonte do projeto."),
    ("Markdown", "Documentacao tecnica: README, TUTORIAL e relatorio."),
]
xs = [0.85, 5.0]
ys = [2.0, 4.5]
for i, (t, b) in enumerate(techs):
    x = Inches(xs[i % 2]); y = Inches(ys[i // 2])
    add_rect(s, x, y, Inches(4.2), Inches(2.2),
             fill=WHITE, line=RGBColor(0xDD, 0xE7, 0xDD),
             line_w=Pt(1), shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    # accent square
    add_rect(s, x + Inches(0.3), y + Inches(0.3), Inches(0.6), Inches(0.6),
             fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, x + Inches(0.3), y + Inches(0.3), Inches(0.6), Inches(0.6),
             str(i + 1), size=20, bold=True, color=WHITE,
             align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Calibri")
    add_text(s, x + Inches(1.05), y + Inches(0.4), Inches(3), Inches(0.5),
             t, size=17, bold=True, color=FOREST, font="Calibri")
    add_text(s, x + Inches(0.3), y + Inches(1.15), Inches(3.7), Inches(1),
             b, size=12, color=TEXT, font="Calibri")
footer(s); slide_number(s, 6, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 7 - CONCEITOS POO (1/2 - 9 cards grid)
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Conceitos de O.O. Aplicados", "05")
add_text(s, Inches(0.85), Inches(1.45), Inches(8), Inches(0.4),
         "Os 9 conceitos exigidos pela disciplina, marcados com comentarios no codigo",
         size=12, color=MUTED, font="Calibri")

conc = [
    ("Encapsulamento", "Atributos private + getters/setters em todas as classes."),
    ("Construtores", "Em todas as classes; com sobrecarga para pontos iniciais."),
    ("Heranca", "5 subclasses extends Personagem (abstrata)."),
    ("Sobrecarga", "Personagem(2 forms); Pontuacao.calcular() em 2 versoes."),
    ("Sobrescrita", "@Override em getTitulo() e getClasseIdeal() das subclasses."),
    ("Classe Abstrata", "Personagem - molde dos 5 niveis."),
    ("Metodo Abstrato", "getTitulo() e getClasseIdeal() implementados pelas subs."),
    ("Interface", "Missao - contrato implementado por MissaoEcologica."),
    ("Tratamento Excecao", "try-catch + EntradaInvalidaException customizada."),
]
gx = [0.85, 4.05, 7.25]
gy = [1.95, 3.65, 5.35]
W = Inches(3.0); H = Inches(1.55)
for i, (t, b) in enumerate(conc):
    x = Inches(gx[i % 3]); y = Inches(gy[i // 3])
    add_rect(s, x, y, W, H, fill=WHITE,
             line=RGBColor(0xDD, 0xE7, 0xDD), line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    # small green tab
    add_rect(s, x, y, Inches(0.18), H, fill=FOREST,
             shape=MSO_SHAPE.RECTANGLE)
    add_text(s, x + Inches(0.32), y + Inches(0.18), W - Inches(0.5), Inches(0.4),
             t, size=13, bold=True, color=FOREST, font="Calibri")
    add_text(s, x + Inches(0.32), y + Inches(0.6), W - Inches(0.4), Inches(0.9),
             b, size=10, color=TEXT, font="Calibri")
footer(s); slide_number(s, 7, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 8 - ARQUITETURA / ESTRUTURA
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Arquitetura do Codigo", "06")

add_text(s, Inches(0.85), Inches(1.95), Inches(5), Inches(0.4),
         "Pacote principal: guardiao", size=15, bold=True,
         color=FOREST, font="Calibri")

tree = (
    "src/guardiao/\n"
    "  Main.java                            entrada + menu\n"
    "  ui/Terminal.java                     limpa tela + ENTER\n"
    "  personagens/\n"
    "    Personagem.java                    CLASSE ABSTRATA\n"
    "    Explorador.java                    nivel 1\n"
    "    Descontaminador.java               nivel 2\n"
    "    Botanico.java                      nivel 3\n"
    "    Construtor.java                    nivel 4\n"
    "    Guardiao.java                      nivel 5\n"
    "  missoes/\n"
    "    Missao.java                        INTERFACE\n"
    "    MissaoEcologica.java               implementacao\n"
    "  npc/NPC.java                         entrega missao\n"
    "  sistema/\n"
    "    Jogo.java                          orquestra 5 niveis\n"
    "    Pontuacao.java                     calcula pontos\n"
    "  excecoes/EntradaInvalidaException.java"
)
add_rect(s, Inches(0.85), Inches(2.5), Inches(8.2), Inches(4.4),
         fill=WHITE, line=RGBColor(0xDD, 0xE7, 0xDD), line_w=Pt(1),
         shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1.1), Inches(2.7), Inches(7.8), Inches(4.0),
         tree, size=11, color=TEXT, font="Consolas")

footer(s); slide_number(s, 8, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 9 - HIERARQUIA DE CLASSES
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Hierarquia de Classes", "07")

# top box - Personagem
add_rect(s, Inches(3.6), Inches(1.9), Inches(2.8), Inches(0.8),
         fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(3.6), Inches(1.95), Inches(2.8), Inches(0.3),
         "abstract class", size=10, color=MOSS,
         align=PP_ALIGN.CENTER, font="Calibri")
add_text(s, Inches(3.6), Inches(2.2), Inches(2.8), Inches(0.5),
         "Personagem", size=18, bold=True, color=WHITE,
         align=PP_ALIGN.CENTER, font="Calibri")

# connector lines + boxes
subs = ["Explorador", "Descontamina-\ndor", "Botanico", "Construtor", "Guardiao"]
x = 0.5
for i, sub in enumerate(subs):
    cx = Inches(x)
    add_rect(s, cx, Inches(3.7), Inches(1.7), Inches(0.85),
             fill=WHITE, line=FOREST, line_w=Pt(1.5),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, cx, Inches(3.78), Inches(1.7), Inches(0.3),
             f"nivel {i + 1}", size=9, color=MOSS,
             align=PP_ALIGN.CENTER, font="Calibri")
    add_text(s, cx, Inches(4.0), Inches(1.7), Inches(0.5),
             sub, size=11, bold=True, color=TEXT,
             align=PP_ALIGN.CENTER, font="Calibri")
    # connector
    c = s.shapes.add_connector(1, Inches(5.0), Inches(2.7),
                               Inches(x + 0.85), Inches(3.7))
    c.line.color.rgb = MOSS
    c.line.width = Pt(1.2)
    x += 1.85

# interface block at bottom
add_rect(s, Inches(0.85), Inches(5.4), Inches(4), Inches(1),
         fill=WHITE, line=GOLD, line_w=Pt(1.5),
         shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(0.85), Inches(5.5), Inches(4), Inches(0.3),
         "interface", size=10, color=GOLD, align=PP_ALIGN.CENTER, font="Calibri")
add_text(s, Inches(0.85), Inches(5.75), Inches(4), Inches(0.5),
         "Missao", size=17, bold=True, color=TEXT,
         align=PP_ALIGN.CENTER, font="Calibri")

c2 = s.shapes.add_connector(1, Inches(4.85), Inches(5.9),
                            Inches(5.15), Inches(5.9))
c2.line.color.rgb = GOLD
c2.line.width = Pt(1.5)

add_rect(s, Inches(5.15), Inches(5.4), Inches(4), Inches(1),
         fill=WHITE, line=FOREST, line_w=Pt(1.5),
         shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(5.15), Inches(5.5), Inches(4), Inches(0.3),
         "implements", size=10, color=MOSS, align=PP_ALIGN.CENTER, font="Calibri")
add_text(s, Inches(5.15), Inches(5.75), Inches(4), Inches(0.5),
         "MissaoEcologica", size=16, bold=True, color=TEXT,
         align=PP_ALIGN.CENTER, font="Calibri")

footer(s); slide_number(s, 9, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 10 - NIVEIS, NPCS E MISSOES (tabela)
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Niveis, NPCs e Missoes", "08")

add_text(s, Inches(0.85), Inches(1.95), Inches(8), Inches(0.5),
         "Nivel N tem N NPCs e N missoes - 1 missao por NPC", size=14,
         bold=True, color=FOREST, font="Calibri")

# table
hdr = ["Nivel", "Classe", "NPCs", "Missoes", "Pontos Max"]
rows = [
    ("1", "Explorador",       "1", "1", "175"),
    ("2", "Descontaminador",  "2", "2", "300"),
    ("3", "Botanico",         "3", "3", "425"),
    ("4", "Construtor",       "4", "4", "550"),
    ("5", "Guardiao",         "5", "5", "675"),
]
col_w = [1.0, 2.6, 1.0, 1.2, 1.6]
col_x = []
acc = 0.85
for w in col_w:
    col_x.append(acc); acc += w

# header
for i, h in enumerate(hdr):
    add_rect(s, Inches(col_x[i]), Inches(2.7), Inches(col_w[i]), Inches(0.55),
             fill=FOREST, shape=MSO_SHAPE.RECTANGLE)
    add_text(s, Inches(col_x[i]), Inches(2.7), Inches(col_w[i]), Inches(0.55),
             h, size=12, bold=True, color=WHITE,
             align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Calibri")
# rows
for r_i, row in enumerate(rows):
    bg = WHITE if r_i % 2 == 0 else RGBColor(0xEF, 0xF4, 0xEC)
    for c_i, cell in enumerate(row):
        add_rect(s, Inches(col_x[c_i]), Inches(3.25 + r_i * 0.5),
                 Inches(col_w[c_i]), Inches(0.5),
                 fill=bg, line=RGBColor(0xDD, 0xE7, 0xDD), line_w=Pt(0.5))
        add_text(s, Inches(col_x[c_i]), Inches(3.25 + r_i * 0.5),
                 Inches(col_w[c_i]), Inches(0.5),
                 cell, size=12, bold=(c_i == 0),
                 color=FOREST if c_i == 0 else TEXT,
                 align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Calibri")

# totals callout
add_rect(s, Inches(0.85), Inches(6.0), Inches(8.3), Inches(0.9),
         fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1.1), Inches(6.0), Inches(8), Inches(0.9),
         "TOTAL: 15 missoes  |  15 NPCs  |  Pontuacao maxima: 7.625 pontos",
         size=15, bold=True, color=WHITE,
         align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Calibri")

footer(s); slide_number(s, 10, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 11 - SISTEMA DE PONTUACAO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Sistema de Pontuacao", "08.1")

# three cards
items = [
    ("Pontuacao Base", "nivel x 100", "Cresce com a dificuldade. Nivel 1 = 100 / Nivel 5 = 500."),
    ("Bonus Eficiencia", "+ 50 pontos", "Concedido quando a missao e cumprida dentro do limite de turnos."),
    ("Bonus Classe Ideal", "nivel x 25", "Quando o jogador usa a classe adequada para a missao."),
]
x = 0.85
for t, formula, body in items:
    add_rect(s, Inches(x), Inches(2.0), Inches(2.85), Inches(3.5),
             fill=WHITE, line=RGBColor(0xDD, 0xE7, 0xDD), line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(x), Inches(2.2), Inches(2.85), Inches(0.5),
             t, size=14, bold=True, color=FOREST,
             align=PP_ALIGN.CENTER, font="Calibri")
    # formula
    add_rect(s, Inches(x + 0.25), Inches(2.85), Inches(2.35), Inches(0.85),
             fill=DARK, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(x + 0.25), Inches(2.85), Inches(2.35), Inches(0.85),
             formula, size=20, bold=True, color=ACCENT,
             align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Consolas")
    add_text(s, Inches(x + 0.25), Inches(3.9), Inches(2.4), Inches(1.5),
             body, size=12, color=TEXT,
             align=PP_ALIGN.CENTER, font="Calibri")
    x += 3.05

# total formula
add_rect(s, Inches(0.85), Inches(5.85), Inches(8.3), Inches(0.95),
         fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1), Inches(5.85), Inches(8), Inches(0.95),
         "Total da missao  =  (nivel x 100)  +  bonus eficiencia  +  bonus classe ideal",
         size=15, bold=True, color=WHITE,
         align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Consolas")

footer(s); slide_number(s, 11, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 12 - NPCs e MISSOES TEMATICAS
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "NPCs e Missoes Tematicas", "08.2")

add_text(s, Inches(0.85), Inches(1.95), Inches(8), Inches(0.4),
         "NPCs entregam missoes de forma direta e mecanica (sem dialogos longos)",
         size=12, color=MUTED, font="Calibri")

missoes_por_nivel = [
    ("Nivel 1 - Explorador",       "Encontrar uma semente rara e planta-la num terreno baldio"),
    ("Nivel 2 - Descontaminador",  "Despoluir o rio central  |  Filtrar o ar toxico da praca"),
    ("Nivel 3 - Botanico",         "Replantar mata ciliar  |  Horta comunitaria  |  Recuperar especies"),
    ("Nivel 4 - Construtor",       "Parque urbano  |  Paineis solares  |  Captacao de chuva  |  Corredor verde"),
    ("Nivel 5 - Guardiao",         "Reserva central  |  Reflorestar  |  Despoluir lago  |  Selar poluicao  |  Santuario"),
]
y = 2.55
for t, b in missoes_por_nivel:
    add_rect(s, Inches(0.85), Inches(y), Inches(8.3), Inches(0.75),
             fill=WHITE, line=RGBColor(0xDD, 0xE7, 0xDD), line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    # small left tab
    add_rect(s, Inches(0.85), Inches(y), Inches(0.18), Inches(0.75), fill=FOREST)
    add_text(s, Inches(1.15), Inches(y + 0.05), Inches(3), Inches(0.3),
             t, size=12, bold=True, color=FOREST, font="Calibri")
    add_text(s, Inches(1.15), Inches(y + 0.35), Inches(7.8), Inches(0.4),
             b, size=11, color=TEXT, font="Calibri")
    y += 0.85
footer(s); slide_number(s, 12, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 13 - FLUXO DE TELAS
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Fluxo de Telas (avanca com ENTER)", "09")

add_text(s, Inches(0.85), Inches(1.95), Inches(8), Inches(0.4),
         "Cada tela e limpa antes da proxima - imersao tipo console game",
         size=12, color=MUTED, font="Calibri")

telas = ["Enredo", "Criacao", "Nivel 1", "Nivel 2",
         "Nivel 3", "Nivel 4", "Nivel 5", "Final"]
positions = [(0.85, 2.7), (3.0, 2.7), (5.15, 2.7), (7.3, 2.7),
             (7.3, 4.4), (5.15, 4.4), (3.0, 4.4), (0.85, 4.4)]
for i, (t, (x, y)) in enumerate(zip(telas, positions)):
    is_special = i in (0, 7)
    fill = FOREST if is_special else WHITE
    fg = WHITE if is_special else TEXT
    add_rect(s, Inches(x), Inches(y), Inches(1.9), Inches(0.9),
             fill=fill, line=FOREST if not is_special else None,
             line_w=Pt(1.5) if not is_special else None,
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(x), Inches(y + 0.1), Inches(1.9), Inches(0.3),
             f"{i + 1}", size=10, color=MOSS if is_special else MUTED,
             align=PP_ALIGN.CENTER, font="Calibri")
    add_text(s, Inches(x), Inches(y + 0.35), Inches(1.9), Inches(0.5),
             t, size=14, bold=True, color=fg,
             align=PP_ALIGN.CENTER, font="Calibri")

# arrows (top row left->right; bottom row right->left)
def arrow(slide, x1, y1, x2, y2):
    c = slide.shapes.add_connector(2, Inches(x1), Inches(y1),
                                   Inches(x2), Inches(y2))
    c.line.color.rgb = FOREST
    c.line.width = Pt(2)

for i in range(3):
    arrow(s, 0.85 + 1.9 + 2.15 * i + 0.05, 3.15, 0.85 + 2.15 * (i + 1) - 0.05, 3.15)
# down arrow (right)
arrow(s, 9.2 - 0.2, 3.6, 9.2 - 0.2, 4.4)
# bottom row right->left
for i in range(3):
    arrow(s, 7.3 - 0.05 - 2.15 * i, 4.85, 7.3 + 1.9 - 2.15 * (i + 1) + 0.05, 4.85)

add_text(s, Inches(0.85), Inches(5.8), Inches(8), Inches(0.4),
         "Implementado por Terminal.limparTela() + Terminal.aguardarEnter()",
         size=12, bold=True, color=FOREST, font="Calibri")
footer(s); slide_number(s, 13, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 14 - DEMONSTRACAO (PRINTS)
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, DARK)
add_rect(s, Inches(0.5), Inches(0.55), Inches(0.18), Inches(0.55), fill=ACCENT)
add_text(s, Inches(0.85), Inches(0.55), Inches(8), Inches(0.3),
         "10", size=11, bold=True, color=ACCENT, font="Calibri")
add_text(s, Inches(0.85), Inches(0.85), Inches(9), Inches(0.7),
         "Demonstracao do Jogo", size=32, bold=True, color=WHITE, font="Calibri")

# 2x2 mock prints
mocks = [
    ("MENU PRINCIPAL", "+==================================+\n"
                       "|          ECOCITY - RPG           |\n"
                       "+==================================+\n"
                       "|  [1] Iniciar Jogo                |\n"
                       "|  [2] Instrucoes                  |\n"
                       "|  [3] Creditos                    |\n"
                       "|  [4] Sair                        |\n"
                       "+==================================+\n"
                       "   Sua escolha:"),
    ("ENREDO", "============================================\n"
               "      E C O C I T Y  -  E N R E D O\n"
               "============================================\n"
               "A cidade foi devastada pela poluicao,\n"
               "pelo desmatamento e por conflitos\n"
               "ambientais.\n\n"
               "Voce decide mudar esse destino..."),
    ("MISSAO", " > NPC 2.1 tem uma missao para voce.\n"
               "   Missao: Despoluir as aguas do rio\n"
               "   Limite: 5 turnos (para bonus)\n"
               "   Como cumprir a missao?\n"
               "    [1] Classe ideal (eficiente)\n"
               "    [2] Abordagem comum\n"
               "    >"),
    ("FIM DE JOGO", "F I M   D E   J O G O\n"
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

footer(s, dark=True); slide_number(s, 14, TOTAL, dark=True)

# --------------------------------------------------------------------------- #
# SLIDE 15 - TRATAMENTO DE EXCECOES
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Tratamento de Excecoes", "11")

add_text(s, Inches(0.85), Inches(1.95), Inches(8.5), Inches(0.5),
         "Estabilidade: o jogo nao quebra com entradas invalidas",
         size=15, bold=True, color=FOREST, font="Calibri")

# code box
code = ("try {\n"
        "    int opcao = Integer.parseInt(scanner.nextLine().trim());\n"
        "    if (opcao < 1 || opcao > 4)\n"
        "        throw new EntradaInvalidaException(\"Opcao invalida.\");\n"
        "    // ... executar acao do menu ...\n"
        "} catch (NumberFormatException e) {\n"
        "    System.out.println(\"[ERRO] Digite apenas numeros.\");\n"
        "} catch (EntradaInvalidaException e) {\n"
        "    System.out.println(\"[ERRO] \" + e.getMessage());\n"
        "}")
add_rect(s, Inches(0.85), Inches(2.65), Inches(8.3), Inches(2.7),
         fill=DARK, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1.1), Inches(2.8), Inches(8), Inches(2.55),
         code, size=12.5, color=ACCENT, font="Consolas")

# bottom callouts
calls = [
    ("Letra no menu", "Capturado por NumberFormatException."),
    ("Numero fora", "Lancada EntradaInvalidaException."),
    ("Sistema segue", "Loop continua, jogador tenta de novo."),
]
x = 0.85
for t, b in calls:
    add_rect(s, Inches(x), Inches(5.65), Inches(2.7), Inches(1.4),
             fill=WHITE, line=RGBColor(0xDD, 0xE7, 0xDD), line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(x + 0.2), Inches(5.75), Inches(2.4), Inches(0.4),
             t, size=12.5, bold=True, color=FOREST, font="Calibri")
    add_text(s, Inches(x + 0.2), Inches(6.15), Inches(2.4), Inches(0.85),
             b, size=10.5, color=TEXT, font="Calibri")
    x += 2.85
footer(s); slide_number(s, 15, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 16 - ESTATISTICAS DO PROJETO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "O Projeto em Numeros", "12")

stats = [
    ("14", "arquivos .java"),
    ("7", "pacotes Java"),
    ("9", "conceitos O.O."),
    ("5", "niveis"),
    ("15", "missoes"),
    ("8", "telas distintas"),
]
positions = [(0.85, 2.1), (4.0, 2.1), (7.15, 2.1),
             (0.85, 4.5), (4.0, 4.5), (7.15, 4.5)]
for (n, label), (x, y) in zip(stats, positions):
    add_rect(s, Inches(x), Inches(y), Inches(2.85), Inches(2.15),
             fill=WHITE, line=RGBColor(0xDD, 0xE7, 0xDD), line_w=Pt(1),
             shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(x), Inches(y + 0.25), Inches(2.85), Inches(1.3),
             n, size=72, bold=True, color=FOREST,
             align=PP_ALIGN.CENTER, font="Calibri")
    add_text(s, Inches(x), Inches(y + 1.55), Inches(2.85), Inches(0.4),
             label, size=13, color=MUTED,
             align=PP_ALIGN.CENTER, font="Calibri")
footer(s); slide_number(s, 16, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 17 - GITHUB / ENTREGA
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Entrega e Repositorio", "13")

add_text(s, Inches(0.85), Inches(1.95), Inches(8), Inches(0.5),
         "Codigo-fonte publico no GitHub", size=18, bold=True,
         color=FOREST, font="Calibri")

# big link box
add_rect(s, Inches(0.85), Inches(2.65), Inches(8.3), Inches(1.4),
         fill=DARK, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
add_text(s, Inches(1.1), Inches(2.75), Inches(7), Inches(0.4),
         "REPOSITORIO PUBLICO", size=11, bold=True, color=MOSS, font="Calibri")
add_text(s, Inches(1.1), Inches(3.1), Inches(8), Inches(0.6),
         "github.com/BielmFranco/EcoCity", size=24, bold=True, color=ACCENT,
         font="Consolas")
add_text(s, Inches(1.1), Inches(3.6), Inches(8), Inches(0.4),
         "Clone, fork ou download ZIP - pronto pra rodar.",
         size=12, color=RGBColor(0xC8, 0xDA, 0xC0), font="Calibri")

# entregaveis
add_text(s, Inches(0.85), Inches(4.4), Inches(8), Inches(0.4),
         "ENTREGAVEIS", size=12, bold=True, color=FOREST, font="Calibri")

entregas = [
    ("Codigo-fonte", "14 arquivos .java organizados em 7 pacotes."),
    ("EcoCity.jar", "Executavel compilado, pronto para rodar."),
    ("jogar.bat / jogar.sh", "Scripts portateis Windows / Linux / Mac."),
    ("README + TUTORIAL", "Documentacao tecnica e guia do usuario."),
]
y = 4.85
for t, b in entregas:
    add_rect(s, Inches(0.85), Inches(y + 0.1), Inches(0.18), Inches(0.18),
             fill=FOREST, shape=MSO_SHAPE.OVAL)
    add_text(s, Inches(1.15), Inches(y), Inches(2.5), Inches(0.4),
             t, size=12.5, bold=True, color=TEXT, font="Calibri")
    add_text(s, Inches(3.7), Inches(y), Inches(5.5), Inches(0.4),
             b, size=11.5, color=MUTED, font="Calibri")
    y += 0.5
footer(s); slide_number(s, 17, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 18 - CONCLUSAO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, LIGHT)
section_header(s, "Conclusao", "14")

add_text(s, Inches(0.85), Inches(1.95), Inches(8.5), Inches(0.5),
         "O EcoCity uniu tecnica e mensagem", size=20, bold=True,
         color=FOREST, font="Calibri")

points = [
    ("Tecnica", "POO aplicada na pratica: heranca, polimorfismo, interfaces, "
                "abstracao e excecoes - todos demonstrados num sistema coeso."),
    ("Pedagogica", "Gamificacao transforma conceitos sustentaveis em experiencia "
                   "interativa - aprendizado ativo, nao passivo."),
    ("Social", "Mensagem ambiental: poluicao, desmatamento e a importancia da "
               "restauracao chegam ao jogador atraves da jogabilidade."),
]
y = 2.7
for t, b in points:
    add_rect(s, Inches(0.85), Inches(y), Inches(1.5), Inches(1.1),
             fill=FOREST, shape=MSO_SHAPE.ROUNDED_RECTANGLE)
    add_text(s, Inches(0.85), Inches(y), Inches(1.5), Inches(1.1),
             t, size=14, bold=True, color=WHITE,
             align=PP_ALIGN.CENTER, anchor=MSO_ANCHOR.MIDDLE, font="Calibri")
    add_text(s, Inches(2.55), Inches(y + 0.15), Inches(6.6), Inches(1),
             b, size=12.5, color=TEXT,
             anchor=MSO_ANCHOR.MIDDLE, font="Calibri")
    y += 1.3

footer(s); slide_number(s, 18, TOTAL)

# --------------------------------------------------------------------------- #
# SLIDE 19 - OBRIGADO
# --------------------------------------------------------------------------- #
s = prs.slides.add_slide(BLANK)
set_bg(s, DARK)
# decoration
add_rect(s, Inches(-1.5), Inches(-1.5), Inches(4.5), Inches(4.5),
         fill=FOREST, shape=MSO_SHAPE.OVAL)
add_rect(s, Inches(-0.8), Inches(-0.8), Inches(3), Inches(3),
         fill=DARK, shape=MSO_SHAPE.OVAL)
add_rect(s, Inches(7.5), Inches(5.5), Inches(4.5), Inches(4.5),
         fill=FOREST, shape=MSO_SHAPE.OVAL)
add_rect(s, Inches(8.2), Inches(6.2), Inches(3), Inches(3),
         fill=DARK, shape=MSO_SHAPE.OVAL)

add_text(s, Inches(1), Inches(2.0), Inches(9), Inches(2),
         "Obrigado!", size=92, bold=True, color=WHITE, font="Calibri")
add_rect(s, Inches(1.05), Inches(3.5), Inches(0.9), Inches(0.05), fill=ACCENT)
add_text(s, Inches(1.05), Inches(3.7), Inches(8.5), Inches(0.5),
         "Perguntas, comentarios e ideias sao bem-vindos.",
         size=18, color=ACCENT, font="Calibri")

add_text(s, Inches(1.05), Inches(5.5), Inches(8), Inches(0.4),
         "github.com/BielmFranco/EcoCity",
         size=16, bold=True, color=MOSS, font="Consolas")
add_text(s, Inches(1.05), Inches(6.0), Inches(8), Inches(0.4),
         "EcoCity  -  APS UNIP 2026/1  -  LPOO",
         size=13, color=RGBColor(0xC8, 0xDA, 0xC0), font="Calibri")

# salva
out_path = r"C:\Users\gabrielf\Desktop\ProjetoAPSEcoCity\relatorio\ApresentacaoEcoCity.pptx"
prs.save(out_path)
print(f"OK: {out_path}")
print(f"slides: {len(prs.slides)}")
