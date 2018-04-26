package xyz.alex.chess;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import static xyz.alex.chess.Piece.BLACK_KING;
import static xyz.alex.chess.Piece.WHITE_KING;
import static xyz.alex.chess.Square.square;

/**
 * @author dumas45
 */
public class BoardTest {
    @BeforeTest
    public void init() {
        square(1, 1);
    }

    @DataProvider(name = "correctFEN")
    public Object[][] getCorrectFEN() {
        return new Object[][] {
                {"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"},
                {"rn2k2r/ppp1qppp/5n2/2b3N1/2B1pPb1/8/PPPPQ1PP/RNB1K2R w KQkq - 2 9"},
                {"r2q1rk1/2p1b1pp/p2pbp2/6B1/1p1QP3/5N1P/PPP2PP1/2KRR3 w - - 0 17"},
                {"r2q1r2/1pp3kQ/p1n2p2/2bp1BB1/3p4/8/PPP2PPP/RN2R1K1 b - - 0 16"},
                {"8/8/8/8/8/5p2/4k1p1/6K1 w - - 2 61"},
                {"6r1/p4R2/2p5/7p/5Pk1/P2r4/1BR1Kb2/8 b - - 7 48"},
                {"3r4/1p5p/p4kp1/5p2/1PB2P2/P1K2n2/7P/8 w - - 0 37"},
                {"rnbqkb1r/pp3ppp/4pn2/2pp4/3P1B2/2N1P3/PPP2PPP/R2QKBNR w KQkq d6 0 5"},
                {"6k1/4n1p1/4b2p/p3N3/4P3/P5P1/1r4qP/R6K w - - 0 26"},
                {"5rk1/4n1p1/1pnqb2p/p3pp2/4P3/P2PBNP1/1P3PBP/R4RK1 b - - 1 17"},
                {"rnb1k2r/pp1pnppp/2p5/4P3/3P1P2/1RBB4/P1PQK1qP/7R w kq - 0 17"},
                {"4Qbr1/p1pPk3/1p2p3/4Pp2/3P3p/3P3P/P4P1K/RN3R2 b - - 1 22"},
                {"r3kb1r/p1pp1p2/1pn1p3/4P1pp/QP1P4/3P3P/P4PP1/RN3RK1 w kq - 0 15"},
                {"r3r1k1/pp5p/6pP/3q1p1b/5Q2/1BP2N2/P4PK1/6R1 b - - 1 26"},
                {"8/3n2pk/3qp2p/3p1p2/bp1P1N2/2r1P2P/rN1Q1PP1/1R1R2K1 w - - 6 34"},
                {"r1b1k1nr/ppppqpp1/2n4p/8/1bB1P3/1QN2N2/PP3PPP/R1B1K2R w KQkq - 3 8"},
                {"r4k1r/ppN1nppp/2n2bq1/8/5B2/1P2PNPb/P1P1BP1P/R2QK2R w KQ - 3 14"},
                {"rnbq1rk1/4ppbp/p1pp1np1/1pP5/3PP3/2N1BP2/PP2N1PP/1R1QKB1R b K - 1 9"},
                {"rnbq1rk1/4ppbp/p1pp1np1/1pP5/3PP3/2N1BP2/PP2N1PP/R2QKBR1 b Q - 1 9"},
                {"1rbqk2r/1p1nbppp/p2p4/2pPp3/P3P3/2N5/1PPN1PPP/R1BQ1RK1 w k - 3 11"},
                {"r1bqk1r1/1p1nbppp/p2p4/2pPp3/P3P3/2N5/1PPN1PPP/R1BQ1RK1 w q - 3 11"}
        };
    }

    @DataProvider(name = "incorrectFEN")
    public Object[][] getIncorrectFEN() {
        return new Object[][] {
                {""},
                {"lasdlkasfaakfksa"},
                {"r1bqk2r/pppp1ppp/2n2n2/2a5/2BpP3/5N2/PPP2PPP/RNBQR1K1 b kq - 5 6"},
                {"r2q1r2/1pp3kQ/p1n2p2/2bp1BB1/3p4/8/PPP9PPP/RN2R1K1 b - - 0 16"},
                {"rn1qkbnr/ppp2pp1/4b2p/3pp3/2P3P1/PP2PPB1/RNBQK1NR b KQkq c3 0 5"},
                {"rnbq1b1r/ppp1kppp/8/3p4/3Nn3/2PB4/PP2QPPP/RNB1K2R b KQ - a 8"},
                {"8//8/8/8/8/5p2/4k1p1/6K1 w - - 2 61"},
                {"6r1/p4R2/2p5/7p/5Pk1/P2r4/1BR1Kb2/8 b - - 7"},
                {"3r4/1p5p/p4kp1/5p2/1PB2P2/P1K2n2/7P/8 w  - 0 37"},
                {"rnbqkb1r/pp3ppp/4pn2/2pp4/3P1B2/2N1P3/PPP2PPP/R2QKBNR w KQkq d9 0 5"},
                {"rn2k2r/ppp1qppp/5n2/2b3N1/2B1pPb1/8/PPPPQ1PP/RNB1K2R w KQk- - 2 9"},
                {"rn2k2r/ppp1qppp/5n2/2b3N1/2B1pPb1/8/PPPPQ1PP/RNB1K2R w -- - 2 9"},
                {"rnbqkb1r/pp3ppp/4pn2/2pp4/3P1B2/2N1P3/PPP2PPP/R2QKBNR w KQkq d 0 5"},
                {"rnbqkb1r/pp3ppp/4pn2/2pp4/3P1B2/2N1P3/PPP2PPP/R2QKBNR w KQkq 6 0 5"},
                {"rnbqkb1r/pp3ppp/4pn2/2pp4/3P1B2/2N1P3/PPP2PPP/R2QKBNR w KQkq 22 0 5"},
                {"rnbqkb1r/pp3ppp/4pn2/2pp4/3P1B2/2N1P3/PPP2PPP/R2QKBNR w KQkq ab 0 5"},
                {"rnbqkb1r/pp3ppp/4pn2/2pp4/3P1B2/2N1P3/PPP2PPP/R2QKBNR w qkQK d6- 0 5"},
                {"7k/8/8/8/8/8/8/2K4K w - - 0 1"},
                {"1bn4k/p7/8/8/8/8/P7/1NB5 w - - 0 1"}
        };
    }

    @Test(dataProvider = "correctFEN")
    public void getFEN(String fen) {
        Board board = new Board(fen);
        assertEquals(board.getFEN(), fen);
    }

    @Test(dataProvider = "correctFEN")
    public void copyFrom(String fen) {
        Board board = new Board(fen);
        Board copy = new Board();
        copy.copyFrom(board);
        assertEquals(copy, board);
        assertEquals(copy.getFEN(), fen);
    }

    @Test(dataProvider = "correctFEN")
    public void copyConstructor(String fen) {
        Board board = new Board(fen);
        Board copy = new Board(board);
        assertEquals(copy, board);
        assertEquals(copy.getFEN(), fen);
    }

    @Test
    public void halfMoveClock() {
        Board board = new Board();
        for (int halfMoveClock = 0; halfMoveClock <= 255; ++halfMoveClock) {
            board.setHalfMoveClock(halfMoveClock);
            assertEquals(board.getHalfMoveClock(), halfMoveClock);
        }
    }

    @Test
    public void enPassantTarget() {
        Board board = new Board();
        board.setEnPassantTarget(square(1, 1));
        assertEquals(board.getEnPassantTarget(), square(1, 1));
        for (int v = 1; v <= 8; ++v) {
            for (int h = 1; h <= 8; ++h) {
                board.setEnPassantTarget(square(v, h));
                assertEquals(board.getEnPassantTarget(), square(v, h));
            }
        }
    }

    @Test(dataProvider = "incorrectFEN", expectedExceptions = IllegalArgumentException.class)
    public void constructor_fail(String fen) {
        new Board(fen);
        fail();
    }

    @DataProvider(name = "correctMoves")
    public Object[][] getDataFor_correctMoves() {
        return new Object[][] {
                {
                        "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
                        new String[][] {
                                {"e2", "e4", null, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"},
                                {"e7", "e5", null, "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2"},
                                {"f2", "f4", null, "rnbqkbnr/pppp1ppp/8/4p3/4PP2/8/PPPP2PP/RNBQKBNR b KQkq f3 0 2"},
                                {"e5", "f4", null, "rnbqkbnr/pppp1ppp/8/8/4Pp2/8/PPPP2PP/RNBQKBNR w KQkq - 0 3"},
                                {"f1", "c4", null, "rnbqkbnr/pppp1ppp/8/8/2B1Pp2/8/PPPP2PP/RNBQK1NR b KQkq - 1 3"},
                                {"d8", "h4", null, "rnb1kbnr/pppp1ppp/8/8/2B1Pp1q/8/PPPP2PP/RNBQK1NR w KQkq - 2 4"},
                                {"e1", "f1", null, "rnb1kbnr/pppp1ppp/8/8/2B1Pp1q/8/PPPP2PP/RNBQ1KNR b kq - 3 4"},
                                {"b7", "b5", null, "rnb1kbnr/p1pp1ppp/8/1p6/2B1Pp1q/8/PPPP2PP/RNBQ1KNR w kq b6 0 5"},
                                {"c4", "b5", null, "rnb1kbnr/p1pp1ppp/8/1B6/4Pp1q/8/PPPP2PP/RNBQ1KNR b kq - 0 5"},
                                {"g8", "f6", null, "rnb1kb1r/p1pp1ppp/5n2/1B6/4Pp1q/8/PPPP2PP/RNBQ1KNR w kq - 1 6"},
                                {"g1", "f3", null, "rnb1kb1r/p1pp1ppp/5n2/1B6/4Pp1q/5N2/PPPP2PP/RNBQ1K1R b kq - 2 6"},
                                {"h4", "h6", null, "rnb1kb1r/p1pp1ppp/5n1q/1B6/4Pp2/5N2/PPPP2PP/RNBQ1K1R w kq - 3 7"},
                                {"d2", "d3", null, "rnb1kb1r/p1pp1ppp/5n1q/1B6/4Pp2/3P1N2/PPP3PP/RNBQ1K1R b kq - 0 7"},
                                {"f6", "h5", null, "rnb1kb1r/p1pp1ppp/7q/1B5n/4Pp2/3P1N2/PPP3PP/RNBQ1K1R w kq - 1 8"},
                                {"f3", "h4", null, "rnb1kb1r/p1pp1ppp/7q/1B5n/4Pp1N/3P4/PPP3PP/RNBQ1K1R b kq - 2 8"},
                                {"h6", "g5", null, "rnb1kb1r/p1pp1ppp/8/1B4qn/4Pp1N/3P4/PPP3PP/RNBQ1K1R w kq - 3 9"},
                                {"h4", "f5", null, "rnb1kb1r/p1pp1ppp/8/1B3Nqn/4Pp2/3P4/PPP3PP/RNBQ1K1R b kq - 4 9"},
                                {"c7", "c6", null, "rnb1kb1r/p2p1ppp/2p5/1B3Nqn/4Pp2/3P4/PPP3PP/RNBQ1K1R w kq - 0 10"},
                                {"g2", "g4", null, "rnb1kb1r/p2p1ppp/2p5/1B3Nqn/4PpP1/3P4/PPP4P/RNBQ1K1R b kq g3 0 10"},
                                {"h5", "f6", null, "rnb1kb1r/p2p1ppp/2p2n2/1B3Nq1/4PpP1/3P4/PPP4P/RNBQ1K1R w kq - 1 11"},
                                {"h1", "g1", null, "rnb1kb1r/p2p1ppp/2p2n2/1B3Nq1/4PpP1/3P4/PPP4P/RNBQ1KR1 b kq - 2 11"},
                                {"c6", "b5", null, "rnb1kb1r/p2p1ppp/5n2/1p3Nq1/4PpP1/3P4/PPP4P/RNBQ1KR1 w kq - 0 12"},
                                {"h2", "h4", null, "rnb1kb1r/p2p1ppp/5n2/1p3Nq1/4PpPP/3P4/PPP5/RNBQ1KR1 b kq h3 0 12"},
                                {"g5", "g6", null, "rnb1kb1r/p2p1ppp/5nq1/1p3N2/4PpPP/3P4/PPP5/RNBQ1KR1 w kq - 1 13"},
                                {"h4", "h5", null, "rnb1kb1r/p2p1ppp/5nq1/1p3N1P/4PpP1/3P4/PPP5/RNBQ1KR1 b kq - 0 13"},
                                {"g6", "g5", null, "rnb1kb1r/p2p1ppp/5n2/1p3NqP/4PpP1/3P4/PPP5/RNBQ1KR1 w kq - 1 14"},
                                {"d1", "f3", null, "rnb1kb1r/p2p1ppp/5n2/1p3NqP/4PpP1/3P1Q2/PPP5/RNB2KR1 b kq - 2 14"},
                                {"f6", "g8", null, "rnb1kbnr/p2p1ppp/8/1p3NqP/4PpP1/3P1Q2/PPP5/RNB2KR1 w kq - 3 15"},
                                {"c1", "f4", null, "rnb1kbnr/p2p1ppp/8/1p3NqP/4PBP1/3P1Q2/PPP5/RN3KR1 b kq - 0 15"},
                                {"g5", "f6", null, "rnb1kbnr/p2p1ppp/5q2/1p3N1P/4PBP1/3P1Q2/PPP5/RN3KR1 w kq - 1 16"},
                                {"b1", "c3", null, "rnb1kbnr/p2p1ppp/5q2/1p3N1P/4PBP1/2NP1Q2/PPP5/R4KR1 b kq - 2 16"},
                                {"f8", "c5", null, "rnb1k1nr/p2p1ppp/5q2/1pb2N1P/4PBP1/2NP1Q2/PPP5/R4KR1 w kq - 3 17"},
                                {"c3", "d5", null, "rnb1k1nr/p2p1ppp/5q2/1pbN1N1P/4PBP1/3P1Q2/PPP5/R4KR1 b kq - 4 17"},
                                {"f6", "b2", null, "rnb1k1nr/p2p1ppp/8/1pbN1N1P/4PBP1/3P1Q2/PqP5/R4KR1 w kq - 0 18"},
                                {"f4", "d6", null, "rnb1k1nr/p2p1ppp/3B4/1pbN1N1P/4P1P1/3P1Q2/PqP5/R4KR1 b kq - 1 18"},
                                {"c5", "g1", null, "rnb1k1nr/p2p1ppp/3B4/1p1N1N1P/4P1P1/3P1Q2/PqP5/R4Kb1 w kq - 0 19"},
                                {"e4", "e5", null, "rnb1k1nr/p2p1ppp/3B4/1p1NPN1P/6P1/3P1Q2/PqP5/R4Kb1 b kq - 0 19"},
                                {"b2", "a1", null, "rnb1k1nr/p2p1ppp/3B4/1p1NPN1P/6P1/3P1Q2/P1P5/q4Kb1 w kq - 0 20"},
                                {"f1", "e2", null, "rnb1k1nr/p2p1ppp/3B4/1p1NPN1P/6P1/3P1Q2/P1P1K3/q5b1 b kq - 1 20"},
                                {"b8", "a6", null, "r1b1k1nr/p2p1ppp/n2B4/1p1NPN1P/6P1/3P1Q2/P1P1K3/q5b1 w kq - 2 21"},
                                {"f5", "g7", null, "r1b1k1nr/p2p1pNp/n2B4/1p1NP2P/6P1/3P1Q2/P1P1K3/q5b1 b kq - 0 21"},
                                {"e8", "d8", null, "r1bk2nr/p2p1pNp/n2B4/1p1NP2P/6P1/3P1Q2/P1P1K3/q5b1 w - - 1 22"},
                                {"f3", "f6", null, "r1bk2nr/p2p1pNp/n2B1Q2/1p1NP2P/6P1/3P4/P1P1K3/q5b1 b - - 2 22"},
                                {"g8", "f6", null, "r1bk3r/p2p1pNp/n2B1n2/1p1NP2P/6P1/3P4/P1P1K3/q5b1 w - - 0 23"},
                                {"d6", "e7", null, "r1bk3r/p2pBpNp/n4n2/1p1NP2P/6P1/3P4/P1P1K3/q5b1 b - - 1 23"}
                        }
                },
                {
                        "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
                        new String[][] {
                                {"e2", "e4", null, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"},
                                {"e7", "e5", null, "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2"},
                                {"g1", "f3", null, "rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2"},
                                {"b8", "c6", null, "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3"},
                                {"f1", "b5", null, "r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3"},
                                {"g8", "f6", null, "r1bqkb1r/pppp1ppp/2n2n2/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 4 4"},
                                {"e1", "g1", null, "r1bqkb1r/pppp1ppp/2n2n2/1B2p3/4P3/5N2/PPPP1PPP/RNBQ1RK1 b kq - 5 4"},
                                {"f6", "e4", null, "r1bqkb1r/pppp1ppp/2n5/1B2p3/4n3/5N2/PPPP1PPP/RNBQ1RK1 w kq - 0 5"},
                                {"f1", "e1", null, "r1bqkb1r/pppp1ppp/2n5/1B2p3/4n3/5N2/PPPP1PPP/RNBQR1K1 b kq - 1 5"},
                                {"e4", "d6", null, "r1bqkb1r/pppp1ppp/2nn4/1B2p3/8/5N2/PPPP1PPP/RNBQR1K1 w kq - 2 6"},
                                {"f3", "e5", null, "r1bqkb1r/pppp1ppp/2nn4/1B2N3/8/8/PPPP1PPP/RNBQR1K1 b kq - 0 6"},
                                {"f8", "e7", null, "r1bqk2r/ppppbppp/2nn4/1B2N3/8/8/PPPP1PPP/RNBQR1K1 w kq - 1 7"},
                                {"b5", "f1", null, "r1bqk2r/ppppbppp/2nn4/4N3/8/8/PPPP1PPP/RNBQRBK1 b kq - 2 7"},
                                {"e8", "g8", null, "r1bq1rk1/ppppbppp/2nn4/4N3/8/8/PPPP1PPP/RNBQRBK1 w - - 3 8"},
                                {"b1", "c3", null, "r1bq1rk1/ppppbppp/2nn4/4N3/8/2N5/PPPP1PPP/R1BQRBK1 b - - 4 8"},
                                {"c6", "e5", null, "r1bq1rk1/ppppbppp/3n4/4n3/8/2N5/PPPP1PPP/R1BQRBK1 w - - 0 9"}
                        }
                },
                {
                        "8/8/1pp1k3/8/p5p1/2P1K3/8/8 w - - 0 45",
                        new String[][] {
                                {"e3", "f2", null, "8/8/1pp1k3/8/p5p1/2P5/5K2/8 b - - 1 45"},
                                {"a4", "a3", null, "8/8/1pp1k3/8/6p1/p1P5/5K2/8 w - - 0 46"},
                                {"f2", "g3", null, "8/8/1pp1k3/8/6p1/p1P3K1/8/8 b - - 1 46"},
                                {"a3", "a2", null, "8/8/1pp1k3/8/6p1/2P3K1/p7/8 w - - 0 47"},
                                {"g3", "g4", null, "8/8/1pp1k3/8/6K1/2P5/p7/8 b - - 0 47"},
                                {"a2", "a1", "q", "8/8/1pp1k3/8/6K1/2P5/8/q7 w - - 0 48"},
                                {"c3", "c4", null, "8/8/1pp1k3/8/2P3K1/8/8/q7 b - - 0 48"},
                                {"a1", "d4", null, "8/8/1pp1k3/8/2Pq2K1/8/8/8 w - - 1 49"},
                                {"g4", "f3", null, "8/8/1pp1k3/8/2Pq4/5K2/8/8 b - - 2 49"},
                                {"e6", "e5", null, "8/8/1pp5/4k3/2Pq4/5K2/8/8 w - - 3 50"}
                        }
                },
                {
                        "rnb1k1nr/1pq1Bp1p/p2pp1p1/8/4P1Q1/1NNB4/PPP2PPP/R3K2R b KQkq - 0 10",
                        new String[][] {
                                {"g8", "e7", null, "rnb1k2r/1pq1np1p/p2pp1p1/8/4P1Q1/1NNB4/PPP2PPP/R3K2R w KQkq - 0 11"},
                                {"e1", "c1", null, "rnb1k2r/1pq1np1p/p2pp1p1/8/4P1Q1/1NNB4/PPP2PPP/2KR3R b kq - 1 11"},
                                {"e6", "e5", null, "rnb1k2r/1pq1np1p/p2p2p1/4p3/4P1Q1/1NNB4/PPP2PPP/2KR3R w kq - 0 12"},
                                {"g4", "g5", null, "rnb1k2r/1pq1np1p/p2p2p1/4p1Q1/4P3/1NNB4/PPP2PPP/2KR3R b kq - 1 12"},
                                {"c8", "e6", null, "rn2k2r/1pq1np1p/p2pb1p1/4p1Q1/4P3/1NNB4/PPP2PPP/2KR3R w kq - 2 13"},
                                {"f2", "f4", null, "rn2k2r/1pq1np1p/p2pb1p1/4p1Q1/4PP2/1NNB4/PPP3PP/2KR3R b kq f3 0 13"},
                                {"b8", "d7", null, "r3k2r/1pqnnp1p/p2pb1p1/4p1Q1/4PP2/1NNB4/PPP3PP/2KR3R w kq - 1 14"},
                                {"f4", "f5", null, "r3k2r/1pqnnp1p/p2pb1p1/4pPQ1/4P3/1NNB4/PPP3PP/2KR3R b kq - 0 14"},
                                {"f7", "f6", null, "r3k2r/1pqnn2p/p2pbpp1/4pPQ1/4P3/1NNB4/PPP3PP/2KR3R w kq - 0 15"},
                                {"g5", "h6", null, "r3k2r/1pqnn2p/p2pbppQ/4pP2/4P3/1NNB4/PPP3PP/2KR3R b kq - 1 15"},
                                {"e6", "b3", null, "r3k2r/1pqnn2p/p2p1ppQ/4pP2/4P3/1bNB4/PPP3PP/2KR3R w kq - 0 16"},
                                {"c2", "b3", null, "r3k2r/1pqnn2p/p2p1ppQ/4pP2/4P3/1PNB4/PP4PP/2KR3R b kq - 0 16"},
                                {"e8", "c8", null, "2kr3r/1pqnn2p/p2p1ppQ/4pP2/4P3/1PNB4/PP4PP/2KR3R w - - 1 17"},
                                {"c1", "b1", null, "2kr3r/1pqnn2p/p2p1ppQ/4pP2/4P3/1PNB4/PP4PP/1K1R3R b - - 2 17"},
                                {"c8", "b8", null, "1k1r3r/1pqnn2p/p2p1ppQ/4pP2/4P3/1PNB4/PP4PP/1K1R3R w - - 3 18"},
                                {"d1", "c1", null, "1k1r3r/1pqnn2p/p2p1ppQ/4pP2/4P3/1PNB4/PP4PP/1KR4R b - - 4 18"},
                                {"d7", "c5", null, "1k1r3r/1pq1n2p/p2p1ppQ/2n1pP2/4P3/1PNB4/PP4PP/1KR4R w - - 5 19"}
                        }
                },
                {
                        "r1r3k1/3b1pp1/3bp2p/1p1p4/p2N1P2/P3q3/QPP2R2/1K3R2 w - - 0 30",
                        new String[][] {
                                {"d4", "e2", null, "r1r3k1/3b1pp1/3bp2p/1p1p4/p4P2/P3q3/QPP1NR2/1K3R2 b - - 1 30"},
                                {"c8", "c7", null, "r5k1/2rb1pp1/3bp2p/1p1p4/p4P2/P3q3/QPP1NR2/1K3R2 w - - 2 31"},
                                {"f2", "f3", null, "r5k1/2rb1pp1/3bp2p/1p1p4/p4P2/P3qR2/QPP1N3/1K3R2 b - - 3 31"},
                                {"e3", "e2", null, "r5k1/2rb1pp1/3bp2p/1p1p4/p4P2/P4R2/QPP1q3/1K3R2 w - - 0 32"},
                                {"b2", "b4", null, "r5k1/2rb1pp1/3bp2p/1p1p4/pP3P2/P4R2/Q1P1q3/1K3R2 b - b3 0 32"},
                                {"a4", "b3", null, "r5k1/2rb1pp1/3bp2p/1p1p4/5P2/Pp3R2/Q1P1q3/1K3R2 w - - 0 33"},
                                {"a2", "b3", null, "r5k1/2rb1pp1/3bp2p/1p1p4/5P2/PQ3R2/2P1q3/1K3R2 b - - 0 33"},
                                {"a8", "a3", null, "6k1/2rb1pp1/3bp2p/1p1p4/5P2/rQ3R2/2P1q3/1K3R2 w - - 0 34"},
                                {"b3", "b2", null, "6k1/2rb1pp1/3bp2p/1p1p4/5P2/r4R2/1QP1q3/1K3R2 b - - 1 34"},
                                {"a3", "f3", null, "6k1/2rb1pp1/3bp2p/1p1p4/5P2/5r2/1QP1q3/1K3R2 w - - 0 35"},
                                {"f1", "g1", null, "6k1/2rb1pp1/3bp2p/1p1p4/5P2/5r2/1QP1q3/1K4R1 b - - 1 35"},
                                {"f3", "f1", null, "6k1/2rb1pp1/3bp2p/1p1p4/5P2/8/1QP1q3/1K3rR1 w - - 2 36"},
                                {"g1", "f1", null, "6k1/2rb1pp1/3bp2p/1p1p4/5P2/8/1QP1q3/1K3R2 b - - 0 36"},
                                {"e2", "f1", null, "6k1/2rb1pp1/3bp2p/1p1p4/5P2/8/1QP5/1K3q2 w - - 0 37"},
                                {"b1", "a2", null, "6k1/2rb1pp1/3bp2p/1p1p4/5P2/8/KQP5/5q2 b - - 1 37"},
                                {"c7", "a7", null, "6k1/r2b1pp1/3bp2p/1p1p4/5P2/8/KQP5/5q2 w - - 2 38"},
                                {"a2", "b3", null, "6k1/r2b1pp1/3bp2p/1p1p4/5P2/1K6/1QP5/5q2 b - - 3 38"},
                                {"f1", "c4", null, "6k1/r2b1pp1/3bp2p/1p1p4/2q2P2/1K6/1QP5/8 w - - 4 39"}
                        }
                },
                {
                        "rnbqkbnr/pp2pppp/2p5/3p4/3PP3/8/PPP2PPP/RNBQKBNR w KQkq d6 0 3",
                        new String[][] {
                                {"e4", "e5", null, "rnbqkbnr/pp2pppp/2p5/3pP3/3P4/8/PPP2PPP/RNBQKBNR b KQkq - 0 3"},
                                {"f7", "f5", null, "rnbqkbnr/pp2p1pp/2p5/3pPp2/3P4/8/PPP2PPP/RNBQKBNR w KQkq f6 0 4"},
                                {"e5", "f6", null, "rnbqkbnr/pp2p1pp/2p2P2/3p4/3P4/8/PPP2PPP/RNBQKBNR b KQkq - 0 4"},
                                {"c8", "f5", null, "rn1qkbnr/pp2p1pp/2p2P2/3p1b2/3P4/8/PPP2PPP/RNBQKBNR w KQkq - 1 5"},
                                {"f6", "g7", null, "rn1qkbnr/pp2p1Pp/2p5/3p1b2/3P4/8/PPP2PPP/RNBQKBNR b KQkq - 0 5"},
                                {"b8", "d7", null, "r2qkbnr/pp1np1Pp/2p5/3p1b2/3P4/8/PPP2PPP/RNBQKBNR w KQkq - 1 6"},
                                {"g7", "h8", "N", "r2qkbnN/pp1np2p/2p5/3p1b2/3P4/8/PPP2PPP/RNBQKBNR b KQq - 0 6"},
                                {"g8", "h6", null, "r2qkb1N/pp1np2p/2p4n/3p1b2/3P4/8/PPP2PPP/RNBQKBNR w KQq - 1 7"},
                                {"d1", "h5", null, "r2qkb1N/pp1np2p/2p4n/3p1b1Q/3P4/8/PPP2PPP/RNB1KBNR b KQq - 2 7"},
                                {"h6", "f7", null, "r2qkb1N/pp1npn1p/2p5/3p1b1Q/3P4/8/PPP2PPP/RNB1KBNR w KQq - 3 8"},
                                {"h5", "f7", null, "r2qkb1N/pp1npQ1p/2p5/3p1b2/3P4/8/PPP2PPP/RNB1KBNR b KQq - 0 8"}
                        }
                },
                {
                        "rnb1kbnr/ppp2ppp/1N3q2/3p4/4P3/8/PPPP1PPP/RNBQKB1R b KQkq - 1 5",
                        new String[][] {
                                {"c8", "d7", null, "rn2kbnr/pppb1ppp/1N3q2/3p4/4P3/8/PPPP1PPP/RNBQKB1R w KQkq - 2 6"},
                                {"b6", "a8", null, "Nn2kbnr/pppb1ppp/5q2/3p4/4P3/8/PPPP1PPP/RNBQKB1R b KQk - 0 6"},
                                {"b8", "a6", null, "N3kbnr/pppb1ppp/n4q2/3p4/4P3/8/PPPP1PPP/RNBQKB1R w KQk - 1 7"}
                        }
                },
                {
                        "rnbqk1nr/ppppppbp/6p1/8/1PP5/8/P2PPPPP/RNBQKBNR w KQkq - 1 3",
                        new String[][] {
                                {"a2", "a4", null, "rnbqk1nr/ppppppbp/6p1/8/PPP5/8/3PPPPP/RNBQKBNR b KQkq a3 0 3"},
                                {"g7", "a1", null, "rnbqk1nr/pppppp1p/6p1/8/PPP5/8/3PPPPP/bNBQKBNR w Kkq - 0 4"},
                                {"b1", "c3", null, "rnbqk1nr/pppppp1p/6p1/8/PPP5/2N5/3PPPPP/b1BQKBNR b Kkq - 1 4"}
                        }
                },
                {
                        "rnb1kbnr/ppp1pppp/6P1/3q4/3p4/8/PPPPPP1P/RNBQKBNR w KQkq - 1 4",
                        new String[][] {
                                {"g6", "h7", null, "rnb1kbnr/ppp1pppP/8/3q4/3p4/8/PPPPPP1P/RNBQKBNR b KQkq - 0 4"},
                                {"d5", "h1", null, "rnb1kbnr/ppp1pppP/8/8/3p4/8/PPPPPP1P/RNBQKBNq w Qkq - 0 5"},
                                {"h7", "g8", "R", "rnb1kbRr/ppp1ppp1/8/8/3p4/8/PPPPPP1P/RNBQKBNq b Qkq - 0 5"},
                                {"h8", "g8", null, "rnb1kbr1/ppp1ppp1/8/8/3p4/8/PPPPPP1P/RNBQKBNq w Qq - 0 6"},
                                {"f1", "g2", null, "rnb1kbr1/ppp1ppp1/8/8/3p4/8/PPPPPPBP/RNBQK1Nq b Qq - 1 6"},
                                {"h1", "g1", null, "rnb1kbr1/ppp1ppp1/8/8/3p4/8/PPPPPPBP/RNBQK1q1 w Qq - 0 7"}
                        }
                }
        };
    }

    @Test(dataProvider = "correctMoves")
    public void move(String startPos, String[][] moves) {
        Board board = new Board(startPos);
        assertEquals(board.getFEN(), startPos);

        for (String[] moveData : moves) {
            Square from = square(moveData[0]);
            Square to = square(moveData[1]);
            String msg = toString(board.getFEN(), "from " + from + " to " + to, moveData[2]);
            if (moveData[2] == null) {
                board.move(from, to);
            } else {
                Piece promotion = Piece.get(moveData[2].charAt(0));
                board.move(from, to, promotion);
            }

            assertEquals(board.getFEN(), moveData[3], msg);
            assertEquals(board.get(board.getWhiteKing()), WHITE_KING);
            assertEquals(board.get(board.getBlackKing()), BLACK_KING);
        }
    }

    @DataProvider(name = "incorrectMoves")
    public Object[][] getDataFor_incorrectMoves() {
        return new Object[][] {
                {"rn1qkb1r/pp2p1pp/2p1P3/4Pp2/3P4/4B3/PP3PPP/RN1QKBNR w KQkq f6 0 8", sq("e6"), sq("f6"), null},
                {"3qb3/1p3pk1/4pR2/p2pP2Q/P2n1pP1/2r1bp1P/8/1B1R3K b - g3 0 35", sq("f3"), sq("g3"), null},
                {"6k1/3P4/1p6/8/8/8/8/7K w - - 0 1", sq("d7"), sq("d8"), null}
        };
    }

    @Test(dataProvider = "incorrectMoves", expectedExceptions = IllegalArgumentException.class)
    public void move_fail(String startPos, Square from, Square to, Piece promotion) {
        Board board = new Board(startPos);
        assertEquals(board.getFEN(), startPos);

        if (promotion == null) {
            board.move(from, to);
        } else {
            board.move(from, to, promotion);
        }
        fail();
    }

    private Square sq(String val) {
        return square(val);
    }

    private String toString(Object... objs) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objs) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(obj);
        }
        return sb.toString();
    }
}
