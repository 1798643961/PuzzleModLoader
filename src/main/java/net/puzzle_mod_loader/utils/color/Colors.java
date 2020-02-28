package net.puzzle_mod_loader.utils.color;

public enum Colors {

    BLACK(0xFF010101),
    BLUE(0xFF00D2C6),
    DARKBLUE(0xFF1077AA),
    GREEN(0xFF6DCE27),
    DARKGREEN(0xFF549F1E),
    WHITE(0xFFFEFEFE),
    AQUA(0xFF27E2FB),
    DARKAQUA(0xFF037c8c),
    GREY(0xFF999999),
    DARKGREY(0xFF444444),
    RED(0xFFFF5555),
    DARKRED(0xFF880000),
    ORANGE(0xFFFFAA55),
    DARKORANGE(0xFF884400),
    YELLOW(0xFFFFFF55),
    DARKYELLOW(0xFF888800),
    MAGENTA(0xFFFF55FF),
    DARKMAGENTA(0xFF880088);

    public int c;

    Colors(int co) {
        c = co;
    }
}