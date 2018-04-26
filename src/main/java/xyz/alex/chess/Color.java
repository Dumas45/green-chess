package xyz.alex.chess;

/**
 * @author dumas45
 */
enum Color {
    WHITE('w') {
        @Override
        public int getPromotionH() {
            return 8;
        }
    },
    BLACK('b') {
        @Override
        public int getPromotionH() {
            return 1;
        }
    },
    NONE('-') {

    };

    private final char letter;

    Color(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

    public Color getOpposite() {
        if (this == WHITE) {
            return BLACK;
        } else if (this == BLACK) {
            return WHITE;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getPromotionH() {
        throw new UnsupportedOperationException("Unsupported for " + this.name());
    }
}
