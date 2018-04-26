package xyz.alex.chess;

import static xyz.alex.chess.Color.BLACK;
import static xyz.alex.chess.Color.WHITE;
import static xyz.alex.utils.Utils.checkArgument;

/**
 * @author dumas45
 */
enum Piece {
    NONE(0, Color.NONE, ' '),
    WHITE_PAWN(1, WHITE, 'P') {
        @Override
        public boolean isPawn() {
            return true;
        }
    },
    WHITE_BISHOP(2, WHITE, 'B') {
        @Override
        public boolean isBishop() {
            return true;
        }
    },
    WHITE_KNIGHT(3, WHITE, 'N') {
        @Override
        public boolean isKnight() {
            return true;
        }
    },
    WHITE_ROOK(4, WHITE, 'R') {
        @Override
        public boolean isRook() {
            return true;
        }
    },
    WHITE_QUEEN(5, WHITE, 'Q') {
        @Override
        public boolean isQueen() {
            return true;
        }
    },
    WHITE_KING(6, WHITE, 'K') {
        @Override
        public boolean isKing() {
            return true;
        }
    },
    BLACK_PAWN(7, BLACK, 'p') {
        @Override
        public boolean isPawn() {
            return true;
        }
    },
    BLACK_BISHOP(8, BLACK, 'b') {
        @Override
        public boolean isBishop() {
            return true;
        }
    },
    BLACK_KNIGHT(9, BLACK, 'n') {
        @Override
        public boolean isKnight() {
            return true;
        }
    },
    BLACK_ROOK(10, BLACK, 'r') {
        @Override
        public boolean isRook() {
            return true;
        }
    },
    BLACK_QUEEN(11, BLACK, 'q') {
        @Override
        public boolean isQueen() {
            return true;
        }
    },
    BLACK_KING(12, BLACK, 'k') {
        @Override
        public boolean isKing() {
            return true;
        }
    };

    /**
     * Size of piece.getCode() value in bits
     */
    public static final int SIZE = 4;

    public static Piece get(char c, Color color) {
        checkArgument(color == WHITE || color == BLACK);
        return get(color == BLACK ? Character.toLowerCase(c) : Character.toUpperCase(c));
    }

    public static Piece get(char c) {
        switch (c) {
            case 'P': return WHITE_PAWN;
            case 'B': return WHITE_BISHOP;
            case 'N': return WHITE_KNIGHT;
            case 'R': return WHITE_ROOK;
            case 'Q': return WHITE_QUEEN;
            case 'K': return WHITE_KING;
            case 'p': return BLACK_PAWN;
            case 'b': return BLACK_BISHOP;
            case 'n': return BLACK_KNIGHT;
            case 'r': return BLACK_ROOK;
            case 'q': return BLACK_QUEEN;
            case 'k': return BLACK_KING;
            case ' ': return NONE;
            default: throw new IllegalArgumentException(Character.toString(c));
        }
    }

    public static Piece get(int code) {
        switch (code) {
            case 0: return NONE;
            case 1: return WHITE_PAWN;
            case 2: return WHITE_BISHOP;
            case 3: return WHITE_KNIGHT;
            case 4: return WHITE_ROOK;
            case 5: return WHITE_QUEEN;
            case 6: return WHITE_KING;
            case 7: return BLACK_PAWN;
            case 8: return BLACK_BISHOP;
            case 9: return BLACK_KNIGHT;
            case 10: return BLACK_ROOK;
            case 11: return BLACK_QUEEN;
            case 12: return BLACK_KING;
            default: throw new IllegalArgumentException(Integer.toString(code));
        }
    }

    private final int code;
    private final Color color;
    private final char letter;

    Piece(int code, Color color, char letter) {
        this.code = code;
        this.color = color;
        this.letter = letter;
    }

    public int getCode() {
        return code;
    }

    public char getLetter() {
        return letter;
    }

    public boolean isWhite() {
        return color.equals(WHITE);
    }

    public boolean isBlack() {
        return color.equals(Color.BLACK);
    }

    public boolean isKing() {
        return false;
    }

    public boolean isQueen() {
        return false;
    }

    public boolean isRook() {
        return false;
    }

    public boolean isKnight() {
        return false;
    }

    public boolean isBishop() {
        return false;
    }

    public boolean isPawn() {
        return false;
    }

    public Color getColor() {
        return color;
    }
}
