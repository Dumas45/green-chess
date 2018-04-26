package xyz.alex.chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static xyz.alex.chess.Color.BLACK;
import static xyz.alex.chess.Color.WHITE;
import static xyz.alex.chess.MoveFormat.ALGEBRAIC;
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
import static xyz.alex.chess.Square.isValid;
import static xyz.alex.chess.Square.square;
import static xyz.alex.utils.Utils.checkArgument;
import static xyz.alex.utils.Utils.checkNotNull;

/**
 * @author dumas45
 */
class PositionImpl implements Position {
    private static class Move {
        final int v;
        final int h;

        public Move(int v, int h) {
            this.v = v;
            this.h = h;
        }

        @Override
        public String toString() {
            return "[" + v + ", " + h + "]";
        }
    }

    private static final List<Move> KNIGHT_MOVES = Collections.unmodifiableList(Arrays.asList(
            new Move( 2,  1),
            new Move( 1,  2),
            new Move(-1,  2),
            new Move(-2,  1),
            new Move(-2, -1),
            new Move(-1, -2),
            new Move( 1, -2),
            new Move( 2, -1)
    ));
    private static final List<Move> KING_MOVES = Collections.unmodifiableList(Arrays.asList(
            new Move( 1,  1),
            new Move( 0,  1),
            new Move(-1,  1),
            new Move(-1,  0),
            new Move(-1, -1),
            new Move( 0, -1),
            new Move( 1, -1),
            new Move( 1,  0)
    ));
    private static final List<Move> WHITE_PAWN_THREATS = Collections.unmodifiableList(Arrays.asList(
            new Move(-1, -1),
            new Move( 1, -1)
    ));
    private static final List<Move> BLACK_PAWN_THREATS = Collections.unmodifiableList(Arrays.asList(
            new Move(-1,  1),
            new Move( 1,  1)
    ));
    private static final List<Move> ROOK_VECTORS = Collections.unmodifiableList(Arrays.asList(
            new Move( 0,  1),
            new Move(-1,  0),
            new Move( 0, -1),
            new Move( 1,  0)
    ));
    private static final List<Move> BISHOP_VECTORS = Collections.unmodifiableList(Arrays.asList(
            new Move( 1,  1),
            new Move(-1,  1),
            new Move(-1, -1),
            new Move( 1, -1)
    ));

    private final Board board;

    public PositionImpl() {
        this(STARTING_POSITION);
    }

    /**
     * @throws IllegalArgumentException If FEN is incorrect
     */
    PositionImpl(String fen) {
        board = new Board(fen).lock();
    }

    private PositionImpl(Board board) {
        this.board = new Board(board).lock();
    }

    @Override
    public String getFEN() {
        return board.getFEN();
    }

    @Override
    public boolean isCheck() {
        return isCheck(board);
    }

    static boolean isCheck(Board board) {
        return !isCheckmate(board) && isThreatened(board, getActiveKing(board));
    }

    @Override
    public boolean isCheckmate() {
        return isCheckmate(board);
    }

    static boolean isCheckmate(Board board) {
        Square activeKing = getActiveKing(board);
        List<Square> threats = getAllThreats(board, activeKing);
        if (threats.isEmpty()) {
            return false;
        }

        Board testBoard = new Board();

        // King moves or captures
        for (Move move : KING_MOVES) {
            Square moveSquare = square(activeKing.v + move.v, activeKing.h + move.h);
            if (moveSquare.isValid()) {
                Piece piece = board.get(moveSquare);
                if (piece == NONE || piece.getColor() == board.getActiveColor().getOpposite()) {
                    testBoard.copyFrom(board);
                    testBoard.move(activeKing, moveSquare);
                    if (!isThreatened(testBoard, moveSquare)) {
                        return false;
                    }
                }
            }
        }
        if (threats.size() > 1) {
            return true;
        }
        Square threat = threats.get(0);

        // Capture the checking piece
        for (Square defender : getAllThreats(board, threat, board.getActiveColor())) {
            if (!defender.equals(activeKing)) {
                testBoard.copyFrom(board);
                testBoard.move(defender, threat);
                if (!isThreatened(testBoard, activeKing)) {
                    return false;
                }
            }
        }

        // In case of distant checks, interposing a piece between the threatening sliding piece and the king
        for (Square throughSquare : getLine(activeKing, threat)) {
            for (Square defender : getAllMoves(board, throughSquare, board.getActiveColor())) {
                if (!defender.equals(activeKing)) {
                    testBoard.copyFrom(board);
                    testBoard.move(defender, throughSquare);
                    if (!isThreatened(testBoard, activeKing)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean isStalemate() {
        return isStalemate(board);
    }

    static boolean isStalemate(Board board) {
        Square activeKing = board.getKing(board.getActiveColor());
        if (isThreatened(board, activeKing)) {
            return false;
        }

        Board testBoard = new Board();
        for (Move move : KING_MOVES) {
            int v = activeKing.v + move.v;
            int h = activeKing.h + move.h;
            Piece piece;
            if (isValid(v, h)
                    && ((piece = board.get(v, h)) == NONE || piece.getColor() == board.getActiveColor().getOpposite())) {
                testBoard.copyFrom(board);
                testBoard.move(activeKing, square(v, h));
                if (!isThreatened(testBoard, testBoard.getKing(board.getActiveColor()))) {
                    return false;
                }
            }
        }

        for (int testV = 1; testV <= 8; ++testV) {
            for (int testH = 1; testH <= 8; ++testH) {
                Piece testPiece = board.get(testV, testH);
                if (testPiece != NONE && testPiece.getColor() == board.getActiveColor()) {

                    if (testPiece.isRook() || testPiece.isQueen()) {
                        for (Move move : ROOK_VECTORS) {
                            int v = testV;
                            int h = testH;
                            while (isValid(v = v + move.v, h = h + move.h)) {
                                Piece piece = board.get(v, h);
                                if (piece == NONE || piece.getColor() == board.getActiveColor().getOpposite()) {
                                    testBoard.copyFrom(board);
                                    testBoard.move(square(testV, testH), square(v, h));
                                    if (!isThreatened(testBoard, testBoard.getKing(board.getActiveColor()))) {
                                        return false;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }

                    if (testPiece.isBishop() || testPiece.isQueen()) {
                        for (Move move : BISHOP_VECTORS) {
                            int v = testV;
                            int h = testH;
                            while (isValid(v = v + move.v, h = h + move.h)) {
                                Piece piece = board.get(v, h);
                                if (piece == NONE || piece.getColor() == board.getActiveColor().getOpposite()) {
                                    testBoard.copyFrom(board);
                                    testBoard.move(square(testV, testH), square(v, h));
                                    if (!isThreatened(testBoard, testBoard.getKing(board.getActiveColor()))) {
                                        return false;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }

                    if (testPiece.isKnight()) {
                        for (Move move : KNIGHT_MOVES) {
                            int v = testV;
                            int h = testH;
                            while (isValid(v = v + move.v, h = h + move.h)) {
                                Piece piece = board.get(v, h);
                                if (piece == NONE || piece.getColor() == board.getActiveColor().getOpposite()) {
                                    testBoard.copyFrom(board);
                                    testBoard.move(square(testV, testH), square(v, h));
                                    if (!isThreatened(testBoard, testBoard.getKing(board.getActiveColor()))) {
                                        return false;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }

                    if (testPiece == WHITE_PAWN) {
                        if (isValid(testV, testH + 1) && board.get(testV, testH + 1) == NONE) {
                            testBoard.copyFrom(board);
                            testBoard.move(square(testV, testH), square(testV, testH + 1));
                            if (!isThreatened(testBoard, testBoard.getKing(board.getActiveColor()))) {
                                return false;
                            }
                        }
                        for (Move move : BLACK_PAWN_THREATS) {
                            int v = testV;
                            int h = testH;
                            while (isValid(v = v + move.v, h = h + move.h)) {
                                Piece piece = board.get(v, h);
                                if (piece != NONE && piece.getColor() == board.getActiveColor().getOpposite()
                                        || board.getEnPassantTarget().equals(square(v, h))) {
                                    testBoard.copyFrom(board);
                                    testBoard.move(square(testV, testH), square(v, h));
                                    if (!isThreatened(testBoard, testBoard.getKing(board.getActiveColor()))) {
                                        return false;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    if (testPiece == BLACK_PAWN) {
                        if (isValid(testV, testH - 1) && board.get(testV, testH - 1) == NONE) {
                            testBoard.copyFrom(board);
                            testBoard.move(square(testV, testH), square(testV, testH - 1));
                            if (!isThreatened(testBoard, testBoard.getKing(board.getActiveColor()))) {
                                return false;
                            }
                        }
                        for (Move move : WHITE_PAWN_THREATS) {
                            int v = testV;
                            int h = testH;
                            while (isValid(v = v + move.v, h = h + move.h)) {
                                Piece piece = board.get(v, h);
                                if (piece != NONE && piece.getColor() == board.getActiveColor().getOpposite()
                                        || board.getEnPassantTarget().equals(square(v, h))) {
                                    testBoard.copyFrom(board);
                                    testBoard.move(square(testV, testH), square(v, h));
                                    if (!isThreatened(testBoard, testBoard.getKing(board.getActiveColor()))) {
                                        return false;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public Position move(String move, MoveFormat format) {
        Board board = new Board(this.board);
        board.move(format.formatToAlgebraic(move, this.board));
        return new PositionImpl(board);
    }

    @Override
    public String formatMove(String move, MoveFormat from, MoveFormat to) {
        checkNotNull(move);
        checkNotNull(from);
        checkNotNull(to);

        if (from == to) {
            return move;
        } else if (from == ALGEBRAIC) {
            return to.formatFromAlgebraic(move, board);
        } else if (to == ALGEBRAIC) {
            return from.formatToAlgebraic(move, board);
        } else {
            return to.formatFromAlgebraic(from.formatToAlgebraic(move, board), board);
        }
    }

    boolean isCorrect() {
        return isCorrect(board);
    }

    private static boolean isCorrect(Board board) {
        Square whiteKing = Square.NONE;
        Square blackKing = Square.NONE;
        if (!board.getWhiteKing().isValid()) return false;
        if (!board.getBlackKing().isValid()) return false;

        int whitePawns = 0;
        int blackPawns = 0;

        for (int v = 1; v <= 8; ++v) {
            for (int h = 1; h <= 8; ++h) {
                Square square = square(v, h);
                Piece piece = board.get(square);
                if (piece == WHITE_KING) {
                    if (!square.equals(board.getWhiteKing())) {
                        return false;
                    }
                    whiteKing = square;
                } else if (piece == BLACK_KING) {
                    if (!square.equals(board.getBlackKing())) {
                        return false;
                    }
                    blackKing = square;
                } else if (piece.isPawn()) {
                    if (h <= 1 || h >= 8) {
                        return false;
                    }
                    if (piece == BLACK_PAWN) {
                        ++blackPawns;
                    } else {
                        ++whitePawns;
                    }
                }
            }
        }

        if (!whiteKing.isValid() || !whiteKing.equals(board.getWhiteKing())
                || !blackKing.isValid() || !blackKing.equals(board.getBlackKing())) {
            return false;
        }

        Square activeKing = board.getActiveColor() == BLACK ? blackKing : whiteKing;
        Square nonActiveKing = board.getActiveColor() == BLACK ? whiteKing : blackKing;

        // There are no more than 8 pawns from each color
        if (whitePawns > 8) return false;
        if (blackPawns > 8) return false;

        // In case of en passant square; see if it was legally created (e.g it must be on the x3 or x6 row,
        // there must be a pawn (from the correct color) in front of it, and the en passant square and the
        // one behind it are empty)
        Square enPassantTarget = board.getEnPassantTarget();
        if (!enPassantTarget.equals(Square.NONE)) {
            if (board.get(enPassantTarget) != NONE) {
                return false;
            }
            if (board.getActiveColor() == WHITE && (enPassantTarget.h != 6
                    || board.get(enPassantTarget.v, 5) != BLACK_PAWN || board.get(enPassantTarget.v, 7) != NONE)) {
                return false;
            }
            if (board.getActiveColor() == BLACK && (enPassantTarget.h != 3
                    || board.get(enPassantTarget.v, 4) != WHITE_PAWN || board.get(enPassantTarget.v, 2) != NONE)) {
                return false;
            }
            if (board.getHalfMoveClock() != 0) {
                return false;
            }
        }

        // Non-active color is not in check
        if (isThreatened(board, nonActiveKing)) {
            return false;
        }

        // Active color is checked less than 3 times;
        // in case of 2 that it is never pawn+(pawn, bishop, knight), bishop+bishop, knight+knight
        List<Square> threats = getAllThreats(board, activeKing);
        if (threats.size() > 2) {
            return false;
        } else if (threats.size() == 2) {
            Square square1 = threats.get(0);
            Piece piece1 = board.get(square1);
            Square square2 = threats.get(1);
            Piece piece2 = board.get(square2);
            if (piece1.isBishop() && piece2.isBishop()) {
                return false;
            }
            if (piece1.isKnight() && piece2.isKnight()) {
                return false;
            }
            if (piece1.isPawn()) {
                if (piece2.isPawn()) {
                    return false;
                }
                if (!piece2.isRook() && !piece2.isQueen()) {
                    return false;
                }
                if (activeKing.v != square2.v) {
                    return false;
                }
            }
            if (piece2.isPawn()) {
                if (piece1.isPawn()) {
                    return false;
                }
                if (!piece1.isRook() && !piece1.isQueen()) {
                    return false;
                }
                if (activeKing.v != square1.v) {
                    return false;
                }
            }
            if ((piece1.isRook() && piece2.isRook()) || (piece1.isQueen() && piece2.isQueen())) {
                int promotionH = board.getActiveColor().getOpposite().getPromotionH();
                if (activeKing.h != promotionH || square1.h != promotionH && square2.h != promotionH) {
                    return false;
                }
            }
        }

        // Castling: If the king or rooks are not in their starting position; the castling ability for that
        // side is lost (in the case of king, both are lost)
        if (!whiteKing.equals(square("e1"))) {
            if (board.isAbleCastleWK() || board.isAbleCastleWQ()) {
                return false;
            }
        }
        if (!blackKing.equals(square("e8"))) {
            if (board.isAbleCastleBK() || board.isAbleCastleBQ()) {
                return false;
            }
        }
        if (board.isAbleCastleWK() && !board.get(8, 1).equals(WHITE_ROOK)) return false;
        if (board.isAbleCastleWQ() && !board.get(1, 1).equals(WHITE_ROOK)) return false;
        if (board.isAbleCastleBK() && !board.get(8, 8).equals(BLACK_ROOK)) return false;
        if (board.isAbleCastleBQ() && !board.get(1, 8).equals(BLACK_ROOK)) return false;

        return true;
    }

    boolean isThreatened(Square square) {
        return isThreatened(board, square);
    }

    static boolean isThreatened(Board board, Square square) {
        Piece piece = board.get(square);
        checkArgument(piece != NONE);
        Color threatСolor = piece.getColor().getOpposite();
        return isThreatened(board, square, threatСolor);
    }

    boolean isThreatened(Square square, Color threatСolor) {
        return isThreatened(board, square, threatСolor);
    }

    static boolean isThreatened(Board board, Square square, Color threatСolor) {
        return detectThreatsOrMoves(board, square, threatСolor, null, null, true);
    }

    List<Square> getAllThreats(Square square) {
        return getAllThreats(board, square);
    }

    static List<Square> getAllThreats(Board board, Square square) {
        Piece piece = board.get(square);
        checkArgument(piece != NONE);
        Color threatСolor = piece.getColor().getOpposite();
        return getAllThreats(board, square, threatСolor);
    }

    static List<Square> getAllThreats(Board board, Square square, Piece threatPiece) {
        checkArgument(threatPiece != NONE);
        Piece piece = board.get(square);
        checkArgument(piece != NONE);
        return getAllThreats(board, square, threatPiece.getColor(), threatPiece);
    }

    List<Square> getAllThreats(Square square, Color threatСolor) {
        return getAllThreats(board, square, threatСolor);
    }

    static List<Square> getAllThreats(Board board, Square square, Color threatСolor) {
        List<Square> listToFill = new ArrayList<>();
        detectThreatsOrMoves(board, square, threatСolor, null, listToFill, true);
        return listToFill;
    }

    static List<Square> getAllThreats(Board board, Square square, Color threatСolor, Piece threatPiece) {
        List<Square> listToFill = new ArrayList<>();
        detectThreatsOrMoves(board, square, threatСolor, threatPiece, listToFill, true);
        return listToFill;
    }

    static List<Square> getAllMoves(Board board, Square square, Color threatСolor) {
        List<Square> listToFill = new ArrayList<>();
        detectThreatsOrMoves(board, square, threatСolor, null, listToFill, false);
        return listToFill;
    }

    static List<Square> getAllMoves(Board board, Square square, Piece piece) {
        checkArgument(piece != NONE);
        List<Square> listToFill = new ArrayList<>();
        detectThreatsOrMoves(board, square, piece.getColor(), piece, listToFill, false);
        return listToFill;
    }

    private static boolean detectThreatsOrMoves(Board board, Square square, Color threatСolor, Piece threatPiece,
                                                List<Square> listToFill, boolean threats) {
        checkArgument(listToFill == null || listToFill.isEmpty());
        checkArgument(square.isValid(), "square");
        checkArgument(threatСolor == WHITE || threatСolor == BLACK, "threatСolor");
        checkArgument(threats || listToFill != null, "listToFill is null when threats = false");
        Piece targetPiece = board.get(square);
        if (!threats && targetPiece != NONE) {
            threats = true;
        }

        // knights
        for (Move move : KNIGHT_MOVES) {
            int v = square.v + move.v;
            int h = square.h + move.h;
            if (isValid(v, h)) {
                Piece piece = board.get(v, h);
                if ((piece == WHITE_KNIGHT && threatСolor == WHITE || piece == BLACK_KNIGHT && threatСolor == BLACK)
                        && (threatPiece == null || piece == threatPiece)) {

                    if (listToFill == null) {
                        return true;
                    } else {
                        listToFill.add(square(v, h));
                    }
                }
            }
        }

        // kings
        for (Move move : KING_MOVES) {
            int v = square.v + move.v;
            int h = square.h + move.h;
            if (isValid(v, h)) {
                Piece piece = board.get(v, h);
                if ((piece == WHITE_KING && threatСolor == WHITE || piece == BLACK_KING && threatСolor == BLACK)
                        && (threatPiece == null || piece == threatPiece)) {

                    if (listToFill == null) {
                        return true;
                    } else {
                        listToFill.add(square(v, h));
                    }
                }
            }
        }

        // pawns
        if (threatСolor == WHITE && (threatPiece == null || threatPiece == WHITE_PAWN)) {
            if (threats || board.getEnPassantTarget().equals(square) && board.getActiveColor() == threatСolor) {
                for (Move move : WHITE_PAWN_THREATS) {
                    int v = square.v + move.v;
                    int h = square.h + move.h;
                    if (isValid(v, h) && h >= 2 && h <= 7) {
                        if (board.get(v, h) == WHITE_PAWN) {
                            if (listToFill == null) {
                                return true;
                            } else {
                                listToFill.add(square(v, h));
                            }
                        }
                    }
                }
            } else if (square.h > 2 && board.get(square) == NONE) {
                if (square.h == 4 &&
                        board.get(square.v, square.h - 1) == NONE && board.get(square.v, square.h - 2) == WHITE_PAWN) {
                    listToFill.add(square(square.v, square.h - 2));
                } else if (board.get(square.v, square.h - 1) == WHITE_PAWN) {
                    listToFill.add(square(square.v, square.h - 1));
                }
            }
        }
        if (threatСolor == BLACK && (threatPiece == null || threatPiece == BLACK_PAWN)) {
            if (threats || board.getEnPassantTarget().equals(square) && board.getActiveColor() == threatСolor) {
                for (Move move : BLACK_PAWN_THREATS) {
                    int v = square.v + move.v;
                    int h = square.h + move.h;
                    if (isValid(v, h) && h >= 2 && h <= 7) {
                        if (board.get(v, h) == BLACK_PAWN) {
                            if (listToFill == null) {
                                return true;
                            } else {
                                listToFill.add(square(v, h));
                            }
                        }
                    }
                }
            } else if (square.h < 7 && board.get(square) == NONE) {
                if (square.h == 5 &&
                        board.get(square.v, square.h + 1) == NONE && board.get(square.v, square.h + 2) == BLACK_PAWN) {
                    listToFill.add(square(square.v, square.h + 2));
                } else if (board.get(square.v, square.h + 1) == BLACK_PAWN) {
                    listToFill.add(square(square.v, square.h + 1));
                }
            }
        }

        // rooks and queens
        for (Move move : ROOK_VECTORS) {
            int v = square.v;
            int h = square.h;
            while (isValid(v = v + move.v, h = h + move.h)) {
                Piece piece = board.get(v, h);
                if (piece != NONE) {
                    if ((threatСolor == WHITE && (piece == WHITE_ROOK || piece == WHITE_QUEEN)
                            || threatСolor == BLACK && (piece == BLACK_ROOK || piece == BLACK_QUEEN))
                            && (threatPiece == null || piece == threatPiece)) {

                        if (listToFill == null) {
                            return true;
                        } else {
                            listToFill.add(square(v, h));
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        // bishops and queens
        for (Move move : BISHOP_VECTORS) {
            int v = square.v;
            int h = square.h;
            while (isValid(v = v + move.v, h = h + move.h)) {
                Piece piece = board.get(v, h);
                if (piece != NONE) {
                    if ((threatСolor == WHITE && (piece == WHITE_BISHOP || piece == WHITE_QUEEN)
                            || threatСolor == BLACK && (piece == BLACK_BISHOP || piece == BLACK_QUEEN))
                            && (threatPiece == null || piece == threatPiece)) {

                        if (listToFill == null) {
                            return true;
                        } else {
                            listToFill.add(square(v, h));
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return listToFill != null && !listToFill.isEmpty();
    }

    private static List<Square> getLine(Square from, Square to) {
        checkArgument(from.isValid() && to.isValid(), () -> "from " + from + " to " + to);
        int dv = Math.abs(to.v - from.v);
        int dh = Math.abs(to.h - from.h);
        if (dv > 0 && dh > 0 && dv != dh || dv < 2 && dh < 2) return emptyList();
        int count = Math.max(dv, dh) - 1;
        List<Square> list = new ArrayList<>(6);
        int iv = Integer.signum(to.v - from.v);
        int ih = Integer.signum(to.h - from.h);
        int v = from.v;
        int h = from.h;
        for (int i = 0; i < count; ++i) {
            v += iv;
            h += ih;
            list.add(square(v, h));
        }
        return list;
    }

    static Square getActiveKing(Board board) {
        return board.getActiveColor() == BLACK ? board.getBlackKing() : board.getWhiteKing();
    }
}
