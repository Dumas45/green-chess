package xyz.alex.chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static xyz.alex.chess.Color.BLACK;
import static xyz.alex.chess.Piece.BLACK_KING;
import static xyz.alex.chess.Piece.BLACK_PAWN;
import static xyz.alex.chess.Piece.WHITE_KING;
import static xyz.alex.chess.Piece.WHITE_PAWN;
import static xyz.alex.chess.Square.square;
import static xyz.alex.utils.Utils.checkArgument;
import static xyz.alex.utils.Utils.checkNotNull;
import static xyz.alex.utils.Utils.isEmpty;
import static xyz.alex.utils.Utils.isNotEmpty;
import static xyz.alex.utils.Utils.validState;

/**
 * @author dumas45
 */
public enum MoveFormat {
    ALGEBRAIC {
        @Override
        public boolean validate(String move) {
            checkNotNull(move);
            return ALGEBRAIC_VALIDATION_PATTERN.matcher(move).matches();
        }

        @Override
        String formatToAlgebraic(String move, Board board) {
            validate(move);
            return move;
        }

        @Override
        String formatFromAlgebraic(String move, Board board) {
            validate(move);
            return move;
        }
    },

    PGN {
        @Override
        public boolean validate(String move) {
            checkNotNull(move);
            return PGN_VALIDATION_PATTERN.matcher(move).matches();
        }

        @Override
        String formatToAlgebraic(String move, Board board) {
            checkArgument(validate(move), move);

            Matcher matcher;

            if ((matcher = PGN_CASTLE_PATTERN.matcher(move)).matches()) {
                boolean isShort = isEmpty(matcher.group(1));
                if (board.getActiveColor() == BLACK) {
                    checkArgument(isShort && board.isAbleCastleBK() || !isShort && board.isAbleCastleBQ(), move);
                    return isShort ? "e8g8" : "e8c8";
                } else {
                    checkArgument(isShort && board.isAbleCastleWK() || !isShort && board.isAbleCastleWQ(), move);
                    return isShort ? "e1g1" : "e1c1";
                }
            }

            Square from = null;
            Square to = null;
            Piece promotion = null;
            String group;

            if ((matcher = PGN_PIECE_MOVE_PATTERN.matcher(move)).matches()) {
                Piece movePiece = Piece.get(matcher.group(1).charAt(0), board.getActiveColor());
                int v = 0;
                int h = 0;
                if (isNotEmpty(group = matcher.group(2))) v = group.charAt(0) - 'a' + 1;
                if (isNotEmpty(group = matcher.group(3))) h = Character.digit(group.charAt(0), 10);
                boolean capture = isNotEmpty(matcher.group(4));
                to = square(matcher.group(5));
                List<Square> candidates;
                if (capture) {
                    Piece toPiece = board.get(to);
                    checkArgument(toPiece != Piece.NONE && toPiece.getColor() == board.getActiveColor().getOpposite(), move);
                    candidates = getAllThreats(board, to, movePiece, promotion);
                } else {
                    candidates = getAllMoves(board, to, movePiece, promotion);
                }
                for (Square candidate : candidates) {
                    validState(board.get(candidate) == movePiece);
                    if ((v == 0 || candidate.v == v) && (h == 0 || candidate.h == h)) {
                        checkArgument(from == null, move);
                        from = candidate;
                    }
                }
            } else if ((matcher = PGN_PAWN_MOVE_PATTERN.matcher(move)).matches()) {
                Piece movePiece = board.getActiveColor() == BLACK ? BLACK_PAWN : WHITE_PAWN;
                int v = 0;
                int h = 0;
                if (isNotEmpty(group = matcher.group(1))) v = group.charAt(0) - 'a' + 1;
                if (isNotEmpty(group = matcher.group(2))) h = Character.digit(group.charAt(0), 10);
                boolean capture = isNotEmpty(matcher.group(3));
                to = square(matcher.group(4));
                checkArgument(to.isValid(), move);
                if (isNotEmpty(group = matcher.group(6))) promotion = Piece.get(group.charAt(0), board.getActiveColor());
                List<Square> candidates;
                if (capture) {
                    Piece toPiece = board.get(to);
                    checkArgument(toPiece != Piece.NONE && toPiece.getColor() == board.getActiveColor().getOpposite()
                            || to.equals(board.getEnPassantTarget()), move);
                    candidates = getAllThreats(board, to, movePiece, promotion);
                } else {
                    candidates = getAllMoves(board, to, movePiece, promotion);
                }
                for (Square candidate : candidates) {
                    validState(board.get(candidate) == movePiece);
                    if ((v == 0 || candidate.v == v) && (h == 0 || candidate.h == h)) {
                        checkArgument(from == null, move);
                        from = candidate;
                    }
                }
            }

            checkArgument(from != null && to != null && from.isValid() && to.isValid(), move);

            StringBuilder sb = new StringBuilder(6);
            sb.append((char) ('a' + from.v - 1));
            sb.append(from.h);
            sb.append((char) ('a' + to.v - 1));
            sb.append(to.h);
            if (promotion != null) {
                sb.append(Character.toLowerCase(promotion.getLetter()));
            }

            return sb.toString();
        }

        @Override
        String formatFromAlgebraic(String move, Board board) {
            checkArgument(ALGEBRAIC.validate(move), move);
            Matcher matcher = ALGEBRAIC_MOVE_PATTERN.matcher(move);
            if (matcher.find()) {
                Square from = square(matcher.group(1));
                Square to = square(matcher.group(2));
                checkArgument(from.isValid() && to.isValid() && !from.equals(to), move);
                Piece movePiece = board.get(from);
                Piece toPiece = board.get(to);
                checkArgument(movePiece != Piece.NONE && movePiece.getColor() == board.getActiveColor(), move);
                Piece promotion = null;
                String group;
                if (isNotEmpty(group = matcher.group(3))) promotion = Piece.get(group.charAt(0), board.getActiveColor());

                if (from.equals(square("e1")) && to.equals(square("g1")) && movePiece == WHITE_KING
                        || from.equals(square("e8")) && to.equals(square("g8")) && movePiece == BLACK_KING) {
                    checkArgument(movePiece == WHITE_KING && board.isAbleCastleWK()
                            || movePiece == BLACK_KING && board.isAbleCastleBK(), move);
                    return "O-O";
                }
                if (from.equals(square("e1")) && to.equals(square("c1")) && movePiece == WHITE_KING
                        || from.equals(square("e8")) && to.equals(square("c8")) && movePiece == BLACK_KING) {
                    checkArgument(movePiece == WHITE_KING && board.isAbleCastleWQ()
                            || movePiece == BLACK_KING && board.isAbleCastleBQ(), move);
                    return "O-O-O";
                }

                boolean capture = toPiece != Piece.NONE;
                checkArgument(!capture || toPiece.getColor() == movePiece.getColor().getOpposite(), move);
                List<Square> candidates;
                if (capture) {
                    candidates = getAllThreats(board, to, movePiece, promotion);
                } else {
                    candidates = getAllMoves(board, to, movePiece, promotion);
                }
                boolean found = false;
                boolean useLetter = false;
                boolean useDigit = false;
                if (movePiece.isPawn() || candidates.size() == 1) {
                    found = candidates.contains(from);
                } else {
                    final int SIZE = 8;
                    int[] verticals = new int[SIZE];
                    Arrays.fill(verticals, 0);
                    int verticalsCount = 0;
                    int[] horizontals = new int[SIZE];
                    Arrays.fill(horizontals, 0);
                    int horizontalsCount = 0;

                    for (Square candidate : candidates) {
                        validState(board.get(candidate).equals(movePiece));
                        if (candidate.equals(from)) {
                            found = true;
                        }

                        boolean findVertical = false;
                        for (int i = 0; verticals[i] > 0 && i < SIZE; ++i) {
                            if (verticals[i] == candidate.v) {
                                findVertical = true;
                                break;
                            }
                        }
                        if (!findVertical) {
                            verticals[verticalsCount++] = candidate.v;
                        }

                        boolean findHorizontal = false;
                        for (int i = 0; horizontals[i] > 0 && i < SIZE; ++i) {
                            if (horizontals[i] == candidate.h) {
                                findHorizontal = true;
                                break;
                            }
                        }
                        if (!findHorizontal) {
                            horizontals[horizontalsCount++] = candidate.h;
                        }
                    }

                    if (verticalsCount == candidates.size()) {
                        useLetter = true;
                    } else if (horizontalsCount == candidates.size()) {
                        useDigit = true;
                    } else {
                        useLetter = true;
                        useDigit = true;
                    }
                }

                if (movePiece.isPawn() && capture) {
                    useLetter = true;
                }

                checkArgument(found, move);

                StringBuilder sb = new StringBuilder(8);

                if (!movePiece.isPawn()) sb.append(Character.toUpperCase(movePiece.getLetter()));
                if (useLetter) sb.append((char) ('a' + from.v - 1));
                if (useDigit) sb.append(from.h);
                if (capture) sb.append('x');
                sb.append((char) ('a' + to.v - 1));
                sb.append(to.h);

                if (promotion != null) {
                    sb.append('=');
                    sb.append(Character.toUpperCase(promotion.getLetter()));
                }

                Board testBoard = new Board(board);
                testBoard.move(from, to, promotion);

                if (PositionImpl.isCheckmate(testBoard)) {
                    sb.append('#');
                } else if (PositionImpl.isThreatened(testBoard, PositionImpl.getActiveKing(testBoard))) {
                    sb.append('+');
                }

                return sb.toString();
            } else {
                throw new IllegalArgumentException(move);
            }
        }
    };

    private static final Pattern PGN_PIECE_MOVE_PATTERN = Pattern.compile("([KQRNB])([a-h]?)([1-8]?)(x?)([a-h][1-8])[+#]?");
    private static final Pattern PGN_PAWN_MOVE_PATTERN = Pattern.compile("([a-h]?)([1-8]?)(x?)([a-h][1-8])(=([QRNB]))?[+#]?");
    private static final Pattern PGN_CASTLE_PATTERN = Pattern.compile("[0O]-[0O](-[0O])?[+#]?");
    static final Pattern ALGEBRAIC_MOVE_PATTERN = Pattern.compile("([a-h][1-8])([a-h][1-8])([qrnb])?");
    private static final Pattern ALGEBRAIC_VALIDATION_PATTERN = Pattern.compile(
            "([a-h][1-8][a-h][1-8])|([a-h]7[a-h]8[qrnb])|([a-h]2[a-h]1[qrnb])");
    private static final Pattern PGN_VALIDATION_PATTERN = Pattern.compile(
            "(([KQRNB][a-h]?[1-8]?x?[a-h][1-8])|((([a-h][1-8]?x)|([a-h][1-8]))?[a-h][1-8](=[QRNB])?)|([0O]-[0O](-[0O])?))[+#]?");

    private static final Pattern PGN_LINE_PATTERN = Pattern.compile("\\d+\\.\\s*(\\S+)(\\s+(\\S+))?");

    public static List<String> getMovesFromPgnLine(String pgnLine) {
        List<String> list = new ArrayList<>(100);

        Matcher matcher = PGN_LINE_PATTERN.matcher(pgnLine);
        while (matcher.find()) {
            String group;
            if (isNotEmpty(group = matcher.group(1)) && PGN_VALIDATION_PATTERN.matcher(group).matches())
                list.add(group);
            if (isNotEmpty(group = matcher.group(3))) list.add(group);
        }

        return list;
    }

    private static List<Square> getAllMoves(Board board, Square square, Piece piece, Piece promotion) {
        List<Square> list = new ArrayList<>();
        Board testBoard = new Board();
        for (Square candidate : PositionImpl.getAllMoves(board, square, piece)) {
            testBoard.copyFrom(board);
            testBoard.move(candidate, square, promotion);
            if (!PositionImpl.isThreatened(testBoard, testBoard.getKing(board.getActiveColor()))) {
                list.add(candidate);
            }
        }
        return list;
    }

    private static List<Square> getAllThreats(Board board, Square square, Piece piece, Piece promotion) {
        List<Square> list = new ArrayList<>();
        Board testBoard = new Board();
        for (Square candidate : PositionImpl.getAllThreats(board, square, piece)) {
            testBoard.copyFrom(board);
            testBoard.move(candidate, square, promotion);
            if (!PositionImpl.isThreatened(testBoard, testBoard.getKing(board.getActiveColor()))) {
                list.add(candidate);
            }
        }
        return list;
    }

    abstract public boolean validate(String move);

    abstract String formatToAlgebraic(String move, Board board);

    abstract String formatFromAlgebraic(String move, Board board);
}
