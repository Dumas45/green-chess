package xyz.alex.chess;

import xyz.alex.utils.Utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static xyz.alex.chess.Color.BLACK;
import static xyz.alex.chess.Color.WHITE;
import static xyz.alex.chess.Piece.BLACK_BISHOP;
import static xyz.alex.chess.Piece.BLACK_KING;
import static xyz.alex.chess.Piece.BLACK_KNIGHT;
import static xyz.alex.chess.Piece.BLACK_PAWN;
import static xyz.alex.chess.Piece.BLACK_QUEEN;
import static xyz.alex.chess.Piece.BLACK_ROOK;
import static xyz.alex.chess.Piece.NONE;
import static xyz.alex.chess.Piece.WHITE_BISHOP;
import static xyz.alex.chess.Piece.WHITE_KING;
import static xyz.alex.chess.Piece.WHITE_KNIGHT;
import static xyz.alex.chess.Piece.WHITE_PAWN;
import static xyz.alex.chess.Piece.WHITE_QUEEN;
import static xyz.alex.chess.Piece.WHITE_ROOK;
import static xyz.alex.chess.Square.square;
import static xyz.alex.utils.Utils.checkArgument;
import static xyz.alex.utils.Utils.checkNotNull;
import static xyz.alex.utils.Utils.getUnsignedNumberFromBytes;
import static xyz.alex.utils.Utils.isNotEmpty;
import static xyz.alex.utils.Utils.storeUnsignedNumberIntoBytes;

/**
 * Chess board.
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a <tt>Board</tt> instance concurrently,
 * and at least one of the threads modifies the instance, it
 * <i>must</i> be synchronized externally.
 *
 * @author dumas45
 */
class Board {
    private static final Pattern FEN_SPLIT_PATTERN = Pattern.compile("[^/\\s]+");
    private static final int BYTE_MASK = ~0 >>> Integer.SIZE - Byte.SIZE;

    private final byte[] boardBytes;
    private boolean activeColorIsBlack;
    private boolean ableCastleWK;
    private boolean ableCastleWQ;
    private boolean ableCastleBK;
    private boolean ableCastleBQ;
    private byte enPassant;
    private byte halfMoveClock;
    private short fullMoveNumber;
    private byte whiteKing;
    private byte blackKing;

    private void init() {
        checkUnmodifiableState();

        Arrays.fill(boardBytes, (byte) 0);
        activeColorIsBlack = false;
        ableCastleWK = false;
        ableCastleWQ = false;
        ableCastleBK = false;
        ableCastleBQ = false;
        enPassant = 0;
        setHalfMoveClock(0);
        fullMoveNumber = 1;
        whiteKing = 0;
        blackKing = 0;
    }

    private boolean isUnmodifiable = false;

    Board() {
        int bits = 64 * Piece.SIZE;
        int size = bits / Byte.SIZE + (bits % Byte.SIZE == 0 ? 0 : 1);
        boardBytes = new byte[size];

        init();
    }

    Board(Board other) {
        this();

        copyFrom(other);
    }

    /**
     * @throws IllegalArgumentException If FEN is incorrect
     */
    Board(String fen) {
        this();

        parseFEN(this, fen);
    }

    /**
     * @throws IllegalArgumentException If FEN is incorrect
     * @throws UnsupportedOperationException If board is in unmodifiable state
     */
    static void parseFEN(Board board, String fen) {
        checkArgument(Position.validateFEN(fen), fen);

        board.init();

        Color activeColor = null;

        Matcher matcher = FEN_SPLIT_PATTERN.matcher(fen);
        int n = 0;
        while (matcher.find()) {
            String group = matcher.group();
            if (!Utils.isBlank(group)) {
                ++n;
                if (n <= 8) {
                    // fill board
                    int h = -(n - 9);
                    checkArgument(h >= 1 && h <= 8, fen);
                    int v = 1;
                    for (int i = 0; i < group.length(); ++i) {
                        checkArgument(v >= 1 && v <= 8, fen);
                        char c = group.charAt(i);
                        if (Character.isDigit(c)) {
                            v += Character.digit(c, 10);
                        } else {
                            switch (c) {
                                case 'K':
                                    checkArgument(board.getWhiteKing().equals(Square.NONE), fen);
                                    board.put(v, h, WHITE_KING);
                                    break;
                                case 'k':
                                    checkArgument(board.getBlackKing().equals(Square.NONE), fen);
                                    board.put(v, h, BLACK_KING);
                                    break;
                                case 'Q':
                                    board.put(v, h, WHITE_QUEEN);
                                    break;
                                case 'q':
                                    board.put(v, h, BLACK_QUEEN);
                                    break;
                                case 'R':
                                    board.put(v, h, WHITE_ROOK);
                                    break;
                                case 'r':
                                    board.put(v, h, BLACK_ROOK);
                                    break;
                                case 'B':
                                    board.put(v, h, WHITE_BISHOP);
                                    break;
                                case 'b':
                                    board.put(v, h, BLACK_BISHOP);
                                    break;
                                case 'N':
                                    board.put(v, h, WHITE_KNIGHT);
                                    break;
                                case 'n':
                                    board.put(v, h, BLACK_KNIGHT);
                                    break;
                                case 'P':
                                    checkArgument(h >= 2 && h <= 7, fen);
                                    board.put(v, h, WHITE_PAWN);
                                    break;
                                case 'p':
                                    checkArgument(h >= 2 && h <= 7, fen);
                                    board.put(v, h, BLACK_PAWN);
                                    break;
                                default:
                                    throw new IllegalArgumentException(fen);
                            }
                            ++v;
                        }
                    }
                } else if (n == 9) {
                    // Active color
                    checkArgument(group.length() == 1, fen);
                    switch (group.charAt(0)) {
                        case 'w': activeColor = WHITE; break;
                        case 'b': activeColor = BLACK; break;
                        default: throw new IllegalArgumentException(fen);
                    }
                } else if (n == 10) {
                    // Castling availability
                    int[] counts = {0, 0, 0, 0, 0};
                    for (int i = 0; i < group.length(); ++i) {
                        switch (group.charAt(i)) {
                            case 'q':
                                checkArgument(++counts[0] == 1, fen);
                                board.setAbilityCastleBQ(true);
                                break;
                            case 'k':
                                checkArgument(++counts[1] == 1, fen);
                                board.setAbilityCastleBK(true);
                                break;
                            case 'Q':
                                checkArgument(++counts[2] == 1, fen);
                                board.setAbilityCastleWQ(true);
                                break;
                            case 'K':
                                checkArgument(++counts[3] == 1, fen);
                                board.setAbilityCastleWK(true);
                                break;
                            case '-':
                                checkArgument(++counts[4] == 1, fen);
                                break;
                            default:
                                throw new IllegalArgumentException(fen);
                        }
                    }
                } else if (n == 11) {
                    // En passant target square
                    if (!group.equals("-")) {
                        checkArgument(group.length() == 2, fen);
                        Square enPassant = square(group.charAt(0) - 'a' + 1, Character.digit(group.charAt(1), 10));
                        checkArgument(enPassant.v >= 1 && enPassant.v <= 8 && enPassant.h >= 1 && enPassant.h <= 8, fen);
                        board.setEnPassantTarget(enPassant);
                    }
                } else if (n == 12) {
                    // Halfmove clock
                    int hmc = Integer.parseInt(group);
                    checkArgument(hmc >= 0);
                    board.setHalfMoveClock(hmc);
                } else if (n == 13) {
                    // Fullmove number
                    int moveNumber = Integer.parseInt(group);
                    checkArgument(moveNumber > 0);
                    board.setFullMoveNumber(moveNumber);
                } else {
                    throw new IllegalArgumentException(fen);
                }
            }
        }

        checkArgument(n > 12, fen);

        checkArgument(activeColor == WHITE || activeColor == BLACK, fen);
        board.setActiveColor(activeColor);

        checkArgument(!board.getWhiteKing().equals(Square.NONE), fen);
        checkArgument(!board.getBlackKing().equals(Square.NONE), fen);
    }

    void copyFrom(Board other) {
            checkUnmodifiableState();
            System.arraycopy(other.boardBytes, 0, boardBytes, 0, boardBytes.length);
            activeColorIsBlack = other.activeColorIsBlack;
            ableCastleWK = other.ableCastleWK;
            ableCastleWQ = other.ableCastleWQ;
            ableCastleBK = other.ableCastleBK;
            ableCastleBQ = other.ableCastleBQ;
            enPassant = other.enPassant;
            halfMoveClock = other.halfMoveClock;
            fullMoveNumber = other.fullMoveNumber;
            whiteKing = other.whiteKing;
            blackKing = other.blackKing;
    }

    Piece get(Square square) {
        return get(square.v, square.h);
    }

    /**
     * @param v Vertical index from1 to 8
     * @param h Horizontal index from 1 to 8
     * @return Piece in specified position, or {@link Piece#NONE} in there is no piece in specified position
     */
    Piece get(int v, int h) {
        checkArgument(Square.isValid(v, h));

        int index = (v - 1) * 8 + h - 1;
        int code = getUnsignedNumberFromBytes(index, Piece.SIZE, boardBytes);
        return Piece.get(code);
    }

    void put(Square square, Piece piece) {
        put(square.v, square.h, piece);
    }

    void put(int v, int h, Piece piece) {
        checkUnmodifiableState();
        checkArgument(Square.isValid(v, h));
        int index = (v - 1) * 8 + h - 1;
        storeUnsignedNumberIntoBytes(piece.getCode(), index, Piece.SIZE, boardBytes);

        if (piece == WHITE_KING) {
            whiteKing = (byte) ((v << 4) | (h));
        } else if (piece == BLACK_KING) {
            blackKing = (byte) ((v << 4) | (h));
        }
    }

    void move(String move) {
        checkNotNull(move);
        Matcher matcher = MoveFormat.ALGEBRAIC_MOVE_PATTERN.matcher(move);
        if (matcher.matches()) {
            String group;
            Square from = square(matcher.group(1));
            Square to = square(matcher.group(2));
            Piece promotion = null;
            if (isNotEmpty(group = matcher.group(3))) {
                promotion = Piece.get(group.charAt(0), getActiveColor());
            }

            move(from, to, promotion);
        } else {
            throw new IllegalArgumentException(move);
        }
    }

    void move(Square from, Square to) {
        move(from, to, null);
    }

    void move(Square from, Square to, Piece promotion) {
        checkUnmodifiableState();
        checkArgument(from.isValid() && to.isValid() && !from.equals(to));
        Piece fromPiece = get(from);
        checkArgument(fromPiece != NONE && fromPiece.getColor() == getActiveColor());
        Piece toPiece = get(to);
        checkArgument(toPiece == NONE || toPiece.getColor() != fromPiece.getColor());

        boolean isEnPassantCapture = false;
        Square enPassantTarget = getEnPassantTarget();
        if (to.equals(enPassantTarget) && fromPiece.isPawn()) {
            if (getActiveColor() == BLACK) {
                checkArgument(from.equals(square(enPassantTarget.v + 1, enPassantTarget.h + 1))
                        || from.equals(square(enPassantTarget.v - 1, enPassantTarget.h + 1)));
                checkArgument(toPiece == NONE);
                checkArgument(get(enPassantTarget.v, enPassantTarget.h + 1) == WHITE_PAWN);
                isEnPassantCapture = true;
            } else {
                checkArgument(from.equals(square(enPassantTarget.v + 1, enPassantTarget.h - 1))
                        || from.equals(square(enPassantTarget.v - 1, enPassantTarget.h - 1)));
                checkArgument(toPiece == NONE);
                checkArgument(get(enPassantTarget.v, enPassantTarget.h - 1) == BLACK_PAWN);
                isEnPassantCapture = true;
            }
        }

        checkArgument(fromPiece != WHITE_PAWN || to.h > 1);
        checkArgument(fromPiece != BLACK_PAWN || to.h < 8);

        if (promotion != null) {
            checkArgument(fromPiece.isPawn() && (to.h == 1 || to.h == 8) && Math.abs(to.h - from.h) == 1);
            checkArgument(promotion.isBishop() || promotion.isKnight() || promotion.isRook() || promotion.isQueen());
            checkArgument(to.h == 1 && promotion.getColor() == BLACK || to.h == 8 && promotion.getColor() == WHITE);
        } else {
            checkArgument(fromPiece != WHITE_PAWN || to.h < 8);
            checkArgument(fromPiece != BLACK_PAWN || to.h > 1);
        }

        boolean castleWK = false;
        boolean castleWQ = false;
        boolean castleBK = false;
        boolean castleBQ = false;

        if (fromPiece == WHITE_KING && from.equals(square("e1")) && to.equals(square("g1"))) {
            checkArgument(isAbleCastleWK());
            checkArgument(get(square("h1")) == WHITE_ROOK);
            checkArgument(toPiece == NONE);
            checkArgument(get(square("f1")) == NONE);
            castleWK = true;
        }
        if (fromPiece == WHITE_KING && from.equals(square("e1")) && to.equals(square("c1"))) {
            checkArgument(isAbleCastleWQ());
            checkArgument(get(square("a1")) == WHITE_ROOK);
            checkArgument(get(square("b1")) == NONE);
            checkArgument(toPiece == NONE);
            checkArgument(get(square("d1")) == NONE);
            castleWQ = true;
        }

        if (fromPiece == BLACK_KING && from.equals(square("e8")) && to.equals(square("g8"))) {
            checkArgument(isAbleCastleBK());
            checkArgument(get(square("h8")) == BLACK_ROOK);
            checkArgument(toPiece == NONE);
            checkArgument(get(square("f8")) == NONE);
            castleBK = true;
        }
        if (fromPiece == BLACK_KING && from.equals(square("e8")) && to.equals(square("c8"))) {
            checkArgument(isAbleCastleBQ());
            checkArgument(get(square("a8")) == BLACK_ROOK);
            checkArgument(get(square("b8")) == NONE);
            checkArgument(toPiece == NONE);
            checkArgument(get(square("d8")) == NONE);
            castleBQ = true;
        }

        put(from, NONE);
        if (promotion == null) {
            put(to, fromPiece);
            if (castleWK) {
                put(square("h1"), NONE);
                put(square("f1"), WHITE_ROOK);
            }
            if (castleWQ) {
                put(square("a1"), NONE);
                put(square("d1"), WHITE_ROOK);
            }
            if (castleBK) {
                put(square("h8"), NONE);
                put(square("f8"), BLACK_ROOK);
            }
            if (castleBQ) {
                put(square("a8"), NONE);
                put(square("d8"), BLACK_ROOK);
            }
            if (isEnPassantCapture) {
                if (getActiveColor() == BLACK) {
                    put(enPassantTarget.v, enPassantTarget.h + 1, NONE);
                } else {
                    put(enPassantTarget.v, enPassantTarget.h - 1, NONE);
                }
            }
        } else {
            put(to, promotion);
        }

        if (fromPiece == WHITE_KING) {
            setAbilityCastleWK(false);
            setAbilityCastleWQ(false);
        }
        if (from.equals(square("a1")) || to.equals(square("a1"))) {
            setAbilityCastleWQ(false);
        }
        if (from.equals(square("h1")) || to.equals(square("h1"))) {
            setAbilityCastleWK(false);
        }

        if (fromPiece == BLACK_KING) {
            setAbilityCastleBK(false);
            setAbilityCastleBQ(false);
        }
        if (from.equals(square("a8")) || to.equals(square("a8"))) {
            setAbilityCastleBQ(false);
        }
        if (from.equals(square("h8")) || to.equals(square("h8"))) {
            setAbilityCastleBK(false);
        }

        setEnPassantTarget(Square.NONE);
        if (from.h == 2 && fromPiece == WHITE_PAWN && to.h == 4 && from.v == to.v) {
            setEnPassantTarget(square(from.v, 3));
        }
        if (from.h == 7 && fromPiece == BLACK_PAWN && to.h == 5 && from.v == to.v) {
            setEnPassantTarget(square(from.v, 6));
        }

        if (fromPiece.isPawn() || toPiece != NONE) {
            setHalfMoveClock(0);
        } else {
            incrementHalfMoveClock();
        }

        setActiveColor(getActiveColor().getOpposite());

        if (getActiveColor() == WHITE) {
            incrementFullMoveNumber();
        }
    }

    Color getActiveColor() {
        return activeColorIsBlack ? BLACK : WHITE;
    }

    void setActiveColor(Color activeColor) {
        checkUnmodifiableState();
        checkNotNull(activeColor, "activeColor");
        checkArgument(activeColor != Color.NONE, "activeColor");

        this.activeColorIsBlack = activeColor == BLACK;
    }

    boolean isAbleCastleWK() {
        return ableCastleWK;
    }

    void setAbilityCastleWK(boolean abilityCastleWK) {
        checkUnmodifiableState();
        this.ableCastleWK = abilityCastleWK;
    }

    boolean isAbleCastleWQ() {
        return ableCastleWQ;
    }

    void setAbilityCastleWQ(boolean abilityCastleWQ) {
        checkUnmodifiableState();
        this.ableCastleWQ = abilityCastleWQ;
    }

    boolean isAbleCastleBK() {
        return ableCastleBK;
    }

    void setAbilityCastleBK(boolean abilityCastleBK) {
        checkUnmodifiableState();
        this.ableCastleBK = abilityCastleBK;
    }

    boolean isAbleCastleBQ() {
        return ableCastleBQ;
    }

    void setAbilityCastleBQ(boolean abilityCastleBQ) {
        checkUnmodifiableState();
        this.ableCastleBQ = abilityCastleBQ;
    }

    /**
     * @return Square for en passant target square, or {@link Square#NONE} if there is no en passant target.
     */
    Square getEnPassantTarget() {
        return square((enPassant & 0xF0) >>> 4, enPassant & 0xF);
    }

    void setEnPassantTarget(Square enPassantTarget) {
        checkUnmodifiableState();
        if (Square.NONE.equals(enPassantTarget)) {
            enPassant = 0;
        } else {
            checkArgument(enPassantTarget.isValid(), "enPassantTarget");
            enPassant = (byte) ((enPassantTarget.v << 4) | (enPassantTarget.h));
        }
    }

    int getHalfMoveClock() {
        return halfMoveClock & BYTE_MASK;
    }

    void setHalfMoveClock(int halfMoveClock) {
        checkUnmodifiableState();
        checkArgument(halfMoveClock >= 0, "halfMoveClock");
        int value = Math.min(Byte.MAX_VALUE - Byte.MIN_VALUE, halfMoveClock);
        this.halfMoveClock = (byte) (value & BYTE_MASK);
    }

    void incrementHalfMoveClock() {
        checkUnmodifiableState();
        setHalfMoveClock(getHalfMoveClock() + 1);
    }

    int getFullMoveNumber() {
        return fullMoveNumber;
    }

    void setFullMoveNumber(int fullMoveNumber) {
        checkUnmodifiableState();
        checkArgument(fullMoveNumber > 0);
        this.fullMoveNumber = (short) Math.min(Short.MAX_VALUE, fullMoveNumber);
    }

    void incrementFullMoveNumber() {
        checkUnmodifiableState();
        setFullMoveNumber(getFullMoveNumber() + 1);
    }

    /**
     * @return Square for white king, or {@link Square#NONE} if board is empty
     */
    Square getWhiteKing() {
        return square((whiteKing & 0xF0) >>> 4, whiteKing & 0xF);
    }

    /**
     * @return Square for black king, or {@link Square#NONE} if board is empty
     */
    Square getBlackKing() {
        return square((blackKing & 0xF0) >>> 4, blackKing & 0xF);
    }

    Square getKing(Color color) {
        if (color == WHITE) {
            return getWhiteKing();
        } else if (color == BLACK) {
            return getBlackKing();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return String in FEN format
     */
    String getFEN() {
        StringBuilder sb = new StringBuilder(100);
        for (int h = 8; h > 0; --h) {
            if (h < 8) {
                sb.append('/');
            }
            int empty = 0;
            for (int v = 1; v <= 8; ++v) {
                Piece piece = get(v, h);
                if (piece.equals(Piece.NONE)) {
                    ++empty;
                } else {
                    if (empty > 0) {
                        sb.append(empty);
                        empty = 0;
                    }
                    sb.append(piece.getLetter());
                }
            }
            if (empty > 0) sb.append(empty);
        }

        sb.append(' ');
        sb.append(getActiveColor().getLetter());

        sb.append(' ');
        int length = sb.length();
        if (isAbleCastleWK()) sb.append(WHITE_KING.getLetter());
        if (isAbleCastleWQ()) sb.append(WHITE_QUEEN.getLetter());
        if (isAbleCastleBK()) sb.append(BLACK_KING.getLetter());
        if (isAbleCastleBQ()) sb.append(BLACK_QUEEN.getLetter());
        if (length == sb.length()) sb.append('-');

        Square square;
        sb.append(' ');
        if ((square = getEnPassantTarget()).equals(Square.NONE)) {
            sb.append('-');
        } else {
            sb.append((char) ('a' + square.v - 1));
            sb.append(square.h);
        }

        sb.append(' ');
        sb.append(getHalfMoveClock());

        sb.append(' ');
        sb.append(getFullMoveNumber());

        return sb.toString();
    }

    private void checkUnmodifiableState() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("Object is unmodifiable");
        }
    }

    /**
     * Makes this object unmodifiable
     */
    Board lock() {
        isUnmodifiable = true;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return activeColorIsBlack == board.activeColorIsBlack &&
                ableCastleWK == board.ableCastleWK &&
                ableCastleWQ == board.ableCastleWQ &&
                ableCastleBK == board.ableCastleBK &&
                ableCastleBQ == board.ableCastleBQ &&
                enPassant == board.enPassant &&
                halfMoveClock == board.halfMoveClock &&
                fullMoveNumber == board.fullMoveNumber &&
                whiteKing == board.whiteKing &&
                blackKing == board.blackKing &&
                Arrays.equals(boardBytes, board.boardBytes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardBytes, activeColorIsBlack, ableCastleWK, ableCastleWQ,
                ableCastleBK, ableCastleBQ, enPassant, halfMoveClock, fullMoveNumber, whiteKing, blackKing);
    }

    @Override
    public String toString() {
        return getFEN();
    }
}
