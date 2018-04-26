package xyz.alex.chess;

import java.util.regex.Pattern;

import static xyz.alex.utils.Utils.checkNotNull;

/**
 * @author dumas45
 */
public interface Position {
    String STARTING_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    Pattern FEN_PATTERN = Pattern.compile("[KQRNBkqrnb1-8]+/[KQRNBPkqrnbp1-8]+/[KQRNBPkqrnbp1-8]+" +
                    "/[KQRNBPkqrnbp1-8]+/[KQRNBPkqrnbp1-8]+/[KQRNBPkqrnbp1-8]+/[KQRNBPkqrnbp1-8]+" +
                    "/[KQRNBkqrnb1-8]+\\s+[wb]\\s+([KQkq]+|-)\\s+([a-h][36]|-)\\s+\\d+\\s+\\d+");

    static boolean validateFEN(String fen) {
        checkNotNull(fen);
        return FEN_PATTERN.matcher(fen).matches();
    }

    static Position create() {
        return new PositionImpl();
    }

    static Position create(String fen) {
        checkNotNull(fen);
        return new PositionImpl(fen);
    }

    String getFEN();

    boolean isCheck();

    boolean isCheckmate();

    boolean isStalemate();

    Position move(String move, MoveFormat format);

    String formatMove(String move, MoveFormat from, MoveFormat to);
}
