package xyz.alex.chess;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static xyz.alex.chess.Color.BLACK;
import static xyz.alex.chess.Color.WHITE;
import static xyz.alex.chess.MoveFormat.ALGEBRAIC;
import static xyz.alex.chess.MoveFormat.PGN;
import static xyz.alex.chess.Position.STARTING_POSITION;
import static xyz.alex.chess.Square.square;

/**
 * @author dumas45
 */
public class PositionImplTest {

    @DataProvider(name = "correctAlgebraicToPgnMoves")
    public Object[][] getCorrectAlgebraicToPgnMoves() {
        return new Object[][] {
                {"2kr2r1/ppp4p/4qbp1/3pRp2/5P2/1PP5/P5PP/RNBQ3K w - - 1 23", "e5e6", "Rxe6"},
                {"rnb1kbnr/pppp1ppp/5q2/4p3/3PP3/5N2/PPP2PPP/RNBQKB1R b KQkq d3 0 3", "e5d4", "exd4"},
                {"rnb1k1nr/pppp1ppp/8/2b5/3NP3/2N1B3/PqP2PPP/R2QKB1R w KQkq - 0 7", "d4b5", "Ndb5"},
                {"rn2k2r/ppp1bp1p/1q2b1pB/3pP3/3N4/1PP2P2/P5PP/RN1QK2R w KQkq - 2 14", "e1g1", "O-O"},
                {"r3k1r1/ppp4p/4q1p1/3pnp2/5P1b/1PP5/P3R1PP/RNBQ3K b q - 0 21", "e8c8", "O-O-O"},
                {"rnbqk2r/pppp1Npp/5n2/2b5/4P3/8/PPPP1PPP/RNBQKB1R b KQkq - 0 4", "e8g8", "O-O"},
                {"r3kb1r/ppp2ppp/2n1b3/4q3/4p3/P1N1B3/1PP1QPPP/R3KB1R w KQkq - 4 10", "e1c1", "O-O-O"},
                {"7k/8/1N6/3n4/1N3N2/8/8/7K w - - 0 1", "b4d5", "Nb4xd5"},
                {"r1bqk2r/p1ppbp1p/1p2pn1R/4n2p/P7/8/1PPPPPP1/1NBQKBNR w Kkq - 4 9", "h6h5", "R6xh5"},
                {"r1b1k2r/pp1qbppp/2np1n2/4p3/3pP3/2PB1N2/PP1B1PPP/RN1QK2R w KQkq - 1 9", "c3d4", "cxd4"},
                {"r1bq1rk1/pp3ppp/2n1pn2/2pp4/1bP5/2N2NP1/PPQPPPBP/R1B2RK1 w - d6 0 8", "c4d5", "cxd5"},
                {"r1b1k2r/pp1qbppp/2np1n2/4p3/3pP3/2PB1N2/PP1B1PPP/RN1QK2R w KQkq - 1 9", "a2a4", "a4"},
                {"r1b1k2r/pp1qbppp/2np1n2/4p3/3pP3/2PB1N2/PP1B1PPP/RN1QK2R w KQkq - 1 9", "b2b3", "b3"},
                {"r1b1k2r/pp1qbppp/2np1n2/4p3/3pP3/2PB1N2/PP1B1PPP/RN1QK2R w KQkq - 1 9", "b2b4", "b4"},
                {"r1bqkbnr/pppppppp/8/4n3/3P1P2/8/PPP1P1PP/RNBQKBNR w KQkq - 1 3", "f4e5", "fxe5"},
                {"rnbqkbnr/pp1p1ppp/8/2p1p3/3P4/4P3/PPPB1PPP/RN1QKBNR b KQkq - 0 3", "c5d4", "cxd4"},
                {"2r2k2/3r3p/3p1p2/p2BpP2/2PR3P/6Q1/pP3P1K/8 b - - 0 32", "a2a1r", "a1=R"},
                {"8/1p5p/2B5/4k1p1/8/P5P1/1P1RpP1P/1K6 b - - 0 41", "e2e1q", "e1=Q+"},
                {"8/3P4/2k5/8/8/8/8/7K w - - 0 1", "d7d8q", "d8=Q"},
                {"r4brk/p4ppp/4b3/1p5N/2q5/3pP2P/PB1P1PP1/1KR4R b - - 3 23", "c4a2", "Qxa2#"},
                {"r1bqkbnr/pp1npppp/3p4/1Bp5/4P3/5N2/PPPP1PPP/RNBQ1RK1 b kq - 3 4", "g8f6", "Nf6"}
        };
    }

    @Test(dataProvider = "correctAlgebraicToPgnMoves")
    public void testFormatMove(String fen, String algebraicMove, String pgnMove) {
        Position pos = Position.create(fen);
        assertEquals(pos.formatMove(algebraicMove, ALGEBRAIC, PGN), pgnMove);
        assertEquals(pos.formatMove(pgnMove, PGN, ALGEBRAIC), algebraicMove);
    }

    @DataProvider(name = "pgnMoves")
    public Object[][] getPgnMoves() {
        return new Object[][] {
                {
                        "8/1p3p2/p1p5/P2p2kp/1P1Prp2/2P2KP1/5P2/6R1 b - - 1 40",
                        "fxg3",
                        "8/1p3p2/p1p5/P2p2kp/1P1Pr3/2P2Kp1/5P2/6R1 w - - 0 41"
                },
                {
                        "2kr2r1/2p2p2/1p3p1p/pP1n4/P2P4/1Q4Pq/3N1P1P/2R1R1K1 w - - 0 23",
                        "Nc4",
                        "2kr2r1/2p2p2/1p3p1p/pP1n4/P1NP4/1Q4Pq/5P1P/2R1R1K1 b - - 1 23"
                },
                {
                        "2kr2r1/2p2p2/1p3p1p/pP1n4/P1NP4/1Q4Pq/5P1P/2R1R1K1 b - - 1 23",
                        "Qg4",
                        "2kr2r1/2p2p2/1p3p1p/pP1n4/P1NP2q1/1Q4P1/5P1P/2R1R1K1 w - - 2 24"
                },
                {
                        "2kr2r1/2p2p2/1p3p1p/pP1n4/P1NP2q1/1Q4P1/5P1P/2R1R1K1 w - - 2 24",
                        "Qc2",
                        "2kr2r1/2p2p2/1p3p1p/pP1n4/P1NP2q1/6P1/2Q2P1P/2R1R1K1 b - - 3 24"
                },
                {
                        "2kr2r1/2p2p2/1p3p1p/pP1n4/P1NP2q1/6P1/2Q2P1P/2R1R1K1 b - - 3 24",
                        "Qd7",
                        "2kr2r1/2pq1p2/1p3p1p/pP1n4/P1NP4/6P1/2Q2P1P/2R1R1K1 w - - 4 25"
                },
                {
                        "2kr2r1/2pq1p2/1p3p1p/pP1n4/P2P4/4N1P1/2Q2P1P/2R1R1K1 b - - 5 25",
                        "Rge8",
                        "2krr3/2pq1p2/1p3p1p/pP1n4/P2P4/4N1P1/2Q2P1P/2R1R1K1 w - - 6 26"
                }
        };
    }

    @Test(dataProvider = "pgnMoves")
    public void testMove_pgnMoves(String startFen, String pgnMove, String nextFen) throws Exception {
        Position pos = Position.create(startFen);
        Position nextPos = pos.move(pgnMove, PGN);
        assertEquals(nextPos.getFEN(), nextFen);
    }

    @DataProvider(name = "algebraicMoves")
    public Object[][] getAlgebraicMoves() {
        return new Object[][] {
                {
                        "8/1p3p2/p1p5/P2p2kp/1P1Prp2/2P2KP1/5P2/6R1 b - - 1 40",
                        "f4g3",
                        "8/1p3p2/p1p5/P2p2kp/1P1Pr3/2P2Kp1/5P2/6R1 w - - 0 41"
                },
                {
                        "2kr2r1/2pq1p2/1p3p1p/pP1n4/P1NP4/6P1/2Q2P1P/2R1R1K1 w - - 4 25",
                        "c4e3",
                        "2kr2r1/2pq1p2/1p3p1p/pP1n4/P2P4/4N1P1/2Q2P1P/2R1R1K1 b - - 5 25"
                },
                {
                        "2kr2r1/2pq1p2/1p3p1p/pP1n4/P2P4/4N1P1/2Q2P1P/2R1R1K1 b - - 5 25",
                        "g8e8",
                        "2krr3/2pq1p2/1p3p1p/pP1n4/P2P4/4N1P1/2Q2P1P/2R1R1K1 w - - 6 26"
                },
                {
                        "2krr3/2pq1p2/1p3p1p/pP1n4/P2P4/4N1P1/2Q2P1P/2R1R1K1 w - - 6 26",
                        "c2c6",
                        "2krr3/2pq1p2/1pQ2p1p/pP1n4/P2P4/4N1P1/5P1P/2R1R1K1 b - - 7 26"
                },
                {
                        "2krr3/2pq1p2/1pQ2p1p/pP1n4/P2P4/4N1P1/5P1P/2R1R1K1 b - - 7 26",
                        "d7c6",
                        "2krr3/2p2p2/1pq2p1p/pP1n4/P2P4/4N1P1/5P1P/2R1R1K1 w - - 0 27"
                }
        };
    }

    @Test(dataProvider = "algebraicMoves")
    public void testMove_algebraicMoves(String startFen, String algebraicMove, String nextFen) throws Exception {
        Position pos = Position.create(startFen);
        Position nextPos = pos.move(algebraicMove, ALGEBRAIC);
        assertEquals(nextPos.getFEN(), nextFen);
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
                {"r1bqk1r1/1p1nbppp/p2p4/2pPp3/P3P3/2N5/1PPN1PPP/R1BQ1RK1 w q - 3 11"},
                {"8/8/8/4k3/8/4K3/3P4/8 w - - 0 1"},
                {"8/8/3k4/2P5/8/8/8/3R3K b - - 0 1"},
                {"8/8/3k4/2P5/8/8/3Q4/7K b - - 0 1"},
                {"3Rk3/8/8/8/8/8/8/4R2K b - - 0 1"},
                {"4kQ2/8/8/8/8/4Q3/8/7K b - - 0 1"}
        };
    }

    @DataProvider(name = "incorrectFEN")
    public Object[][] getIncorrectFEN() {
        return new Object[][] {
                {"8/8/8/4k3/4K3/8/3P4/8 w - - 0 1"},
                {"8/8/8/5k2/4K3/8/P7/8 w - - 0 1"},
                {"8/8/8/8/4Kk2/8/P7/8 w - - 0 1"},
                {"8/8/8/8/4K3/5k2/P7/8 w - - 0 1"},
                {"8/8/8/8/4K3/4k3/P7/8 w - - 0 1"},
                {"8/8/8/8/4K3/3k4/P7/8 w - - 0 1"},
                {"8/8/8/8/3kK3/8/P7/8 w - - 0 1"},
                {"8/8/8/3k4/4K3/8/P7/8 w - - 0 1"},
                {"4r2r/p2n1kp1/2p2p2/5Q1q/3N4/3P4/PPP1KP2/R2bR3 w - - 2 24"},
                {"r4r2/ppq2pbp/2p1b1p1/3p1kn1/2KP3P/3Q1P2/PPP3P1/RN2R3 b - - 0 19"},
                {"r1bqkb1r/2pppppp/p3nn2/1pp3B1/8/P3QP2/1PP1P1PP/RN2KBNR w KQkq - 4 9"},
                {"r1bqk2r/2pp1ppp/p4n2/1pb3n1/3P3P/P2Q1P2/1PPNP1PP/R3KBNR b KQkq h3 0 11"},
                {"r1bqkb1r/2pp1ppp/pQn2n2/1p4B1/8/P4P2/1PP1P1PP/RN2KBNR w KQkq b6 0 7"},
                {"r1bqk2r/2pp1ppp/p4n2/1pb3nP/8/P2Q1P2/1PPNP1P1/R3KBNR b KQkq h3 0 11"},
                {"r1b1k2r/2pq1ppp/p3n3/1pbp4/4Q2P/P4P2/1PP1P1P1/R3KBNR w KQkq d6 0 14"},
                {"r1bqk2r/5ppp/p1p1n3/1p1p4/4P2P/P1b2PQ1/2P2KP1/3R1BNR b kq e3 1 18"},
                {"r1b4r/2k2ppp/pqp1n3/1p1p4/4P2P/P1b2PQ1/2P2KP1/3R1BNR w - - 1 19"},
                {"K7/8/8/8/8/4k3/8/2B5 w - - 0 1"},
                {"5B2/8/3k4/8/5B2/8/8/7K b - - 0 1"},
                {"8/5N2/3k4/8/4N3/8/8/7K b - - 0 1"},
                {"8/8/1R1k4/8/8/8/3R4/7K b - - 0 1"},
                {"8/8/3k4/8/5Q2/8/3Q4/7K b - - 0 1"},
                {"8/8/3k4/2P1P3/8/8/8/7K b - - 0 1"},
                {"8/5N2/3k4/2P5/8/8/8/7K b - - 0 1"},
                {"8/8/3k4/2P5/8/8/7B/7K b - - 0 1"},
                {"8/8/3k2R1/2P5/8/8/8/7K b - - 0 1"},
                {"8/8/Q2k4/2P5/8/8/8/7K b - - 0 1"},
                {"rnb2k1r/2qpbppp/2p2n2/1p4B1/p3P3/P2P1N2/BPP2PPP/R2QK2R b KQk - 1 11"},
                {"rnb2k1r/2qpbppp/2p2n2/1p4B1/p3P3/P2P1N2/BPP2PPP/R2QK2R b KQq - 1 11"},
                {"rnb2k1r/2qpbppp/2p2n2/1p4B1/p3P3/P2P1N2/BPP2PPP/R2QK2R b KQkq - 1 11"},
                {"rnb1k2r/2qpbppp/2p2n2/1p4B1/p3P3/P2P1N2/BPP2PPP/R2Q1K1R b Kkq - 1 11"},
                {"rnb1k2r/2qpbppp/2p2n2/1p4B1/p3P3/P2P1N2/BPP2PPP/R2Q1K1R b Qkq - 1 11"},
                {"rnb1k2r/2qpbppp/2p2n2/1p4B1/p3P3/P2P1N2/BPP2PPP/R2Q1K1R b KQkq - 1 11"},
                {"rnb1k2r/2qpbppp/2p2n2/1p4B1/p3P3/P2P1N2/BPP2PPP/R2QKR2 b KQkq - 1 11"},
                {"rnb1k2r/2qpbppp/2p2n2/1p4B1/p3P3/P2P1N2/BPP2PPP/1R1QK2R b KQkq - 1 11"},
                {"rnb1k1r1/2qpbppp/2p2n2/1p4B1/p3P3/P2P1N2/BPP2PPP/R2QK2R b KQkq - 1 11"},
                {"1nb1k2r/2qpbppp/2p2n2/rp4B1/p3P3/P2P1N2/BPP2PPP/R2QK2R b KQkq - 1 11"}
        };
    }

    @DataProvider(name = "invalidFEN")
    public Object[][] getInvalidFEN() {
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
                {"rnbqkb1r/pp3ppp/4pn2/2pp4/3P1B2/2N1P3/PPP2PPP/R2QKBNR w qkQK d6- 0 5"}
        };
    }

    @Test(dataProvider = "correctFEN")
    public void validateFEN_correct(String fen) throws Exception {
        assertTrue(Position.validateFEN(fen), fen);
    }

    @Test(dataProvider = "invalidFEN")
    public void validateFEN_invalid(String fen) throws Exception {
        assertFalse(Position.validateFEN(fen), fen);
    }

    @Test(dataProvider = "correctFEN")
    public void getFEN(String fen) {
        Position position = new PositionImpl(fen);
        assertEquals(position.getFEN(), fen);
    }

    @Test(dataProvider = "correctFEN")
    public void isCorrect_correct(String fen) {
        PositionImpl position = new PositionImpl(fen);
        assertTrue(position.isCorrect(), fen);
    }

    @Test(dataProvider = "incorrectFEN")
    public void isCorrect_incorrect(String fen) {
        PositionImpl position = new PositionImpl(fen);
        assertFalse(position.isCorrect(), fen);
    }

    @DataProvider(name = "for_getAllThreats_Square")
    public Object[][] getDataFor_getAllThreats_Square() {
        return new Object[][] {
                {"R7/p3ppkp/1bp3p1/PN6/4P2P/1r3P2/6P1/7K b - - 0 26", sq("f3"),  sql("b3")},
                {"R7/p3ppkp/1bp3p1/PN6/4P2P/1r3P2/6P1/7K b - - 0 26", sq("b5"),  sql("b3", "c6")},
                {"R7/p3ppkp/1bp3p1/PN6/4P2P/1r3P2/6P1/7K b - - 0 26", sq("a5"),  sql("b6")},
                {"R7/p3ppkp/1bp3p1/PN6/4P2P/1r3P2/6P1/7K b - - 0 26", sq("a7"),  sql("a8", "b5")},
                {"R7/p3ppkp/1bp3p1/PN6/4P2P/1r3P2/6P1/7K b - - 0 26", sq("b6"),  sql("a5")},
                {"r1b2rk1/4ppbp/2pp1np1/2q5/p7/1PB1PB2/P2N1PPP/2RQ1RK1 b - - 1 16", sq("b3"), sql("a4")},
                {"r1b2rk1/4ppbp/2pp1np1/2q5/p7/1PB1PB2/P2N1PPP/2RQ1RK1 b - - 1 16", sq("c3"), sql("c5")},
                {"r1b2rk1/4ppbp/2pp1np1/2q5/p7/1PB1PB2/P2N1PPP/2RQ1RK1 b - - 1 16", sq("e3"), sql("c5")},
                {"r1b2rk1/4ppbp/2pp1np1/2q5/p7/1PB1PB2/P2N1PPP/2RQ1RK1 b - - 1 16", sq("a4"), sql("b3")},
                {"r1b2rk1/4ppbp/2pp1np1/2q5/p7/1PB1PB2/P2N1PPP/2RQ1RK1 b - - 1 16", sq("c6"), sql("f3")},
                {"r1b2rk1/4ppbp/2pp1np1/2q5/p7/1PB1PB2/P2N1PPP/2RQ1RK1 b - - 1 16", sq("f6"), sql("c3")},
                {"8/5pkp/2n3p1/P3p3/R6P/1P2P2N/5PP1/3r2K1 w - - 2 33", sq("g1"), sql("d1")},
                {"8/5pkp/2n3p1/P3p3/R6P/1P2P2N/5PP1/3r2K1 w - - 2 33", sq("a5"), sql("c6")},
                {"r1b1k2r/pp1pbppp/5n2/2pPN1B1/1P1nP3/8/P4PPP/RN1QKB1R b KQkq - 0 9", sq("b4"), sql("c5")},
                {"r1b1k2r/pp1pbppp/5n2/2pPN1B1/1P1nP3/8/P4PPP/RN1QKB1R b KQkq - 0 9", sq("e4"), sql("f6")},
                {"r1b1k2r/pp1pbppp/5n2/2pPN1B1/1P1nP3/8/P4PPP/RN1QKB1R b KQkq - 0 9", sq("d5"), sql("f6")},
                {"r1b1k2r/pp1pbppp/5n2/2pPN1B1/1P1nP3/8/P4PPP/RN1QKB1R b KQkq - 0 9", sq("d4"), sql("d1")},
                {"r1b1k2r/pp1pbppp/5n2/2pPN1B1/1P1nP3/8/P4PPP/RN1QKB1R b KQkq - 0 9", sq("c5"), sql("b4")},
                {"r1b1k2r/pp1pbppp/5n2/2pPN1B1/1P1nP3/8/P4PPP/RN1QKB1R b KQkq - 0 9", sq("f6"), sql("g5")},
                {"r1b1k2r/pp1pbppp/5n2/2pPN1B1/1P1nP3/8/P4PPP/RN1QKB1R b KQkq - 0 9", sq("d7"), sql("e5")},
                {"r1b1k2r/pp1pbppp/5n2/2pPN1B1/1P1nP3/8/P4PPP/RN1QKB1R b KQkq - 0 9", sq("f7"), sql("e5")},
                {"4r1k1/2q1rppQ/2nb3p/p2p4/N1pP4/2P2bP1/1P3P1P/RB2R1K1 b - - 1 27", sq("g3"), sql("d6")},
                {"4r1k1/2q1rppQ/2nb3p/p2p4/N1pP4/2P2bP1/1P3P1P/RB2R1K1 b - - 1 27", sq("h7"), sql("g8")},
                {"4r1k1/2q1rppQ/2nb3p/p2p4/N1pP4/2P2bP1/1P3P1P/RB2R1K1 b - - 1 27", sq("e1"), sql("e7")},
                {"4r1k1/2q1rppQ/2nb3p/p2p4/N1pP4/2P2bP1/1P3P1P/RB2R1K1 b - - 1 27", sq("e7"), sql("e1")},
                {"4r1k1/2q1rppQ/2nb3p/p2p4/N1pP4/2P2bP1/1P3P1P/RB2R1K1 b - - 1 27", sq("h6"), sql("h7")},
                {"4r1k1/2q1rppQ/2nb3p/p2p4/N1pP4/2P2bP1/1P3P1P/RB2R1K1 b - - 1 27", sq("g7"), sql("h7")},
                {"4r1k1/2q1rppQ/2nb3p/p2p4/N1pP4/2P2bP1/1P3P1P/RB2R1K1 b - - 1 27", sq("g8"), sql("h7")},
                {"r2r4/1pk5/pBpbp2p/4p1p1/2N1Pn2/5P2/PPP3PP/R2R1K2 b - - 1 19", sq("g2"), sql("f4")},
                {"r2r4/1pk5/pBpbp2p/4p1p1/2N1Pn2/5P2/PPP3PP/R2R1K2 b - - 1 19", sq("b6"), sql("c7")},
                {"r2r4/1pk5/pBpbp2p/4p1p1/2N1Pn2/5P2/PPP3PP/R2R1K2 b - - 1 19", sq("e5"), sql("c4")},
                {"r2r4/1pk5/pBpbp2p/4p1p1/2N1Pn2/5P2/PPP3PP/R2R1K2 b - - 1 19", sq("d6"), sql("d1", "c4")},
                {"r2r4/1pk5/pBpbp2p/4p1p1/2N1Pn2/5P2/PPP3PP/R2R1K2 b - - 1 19", sq("c7"), sql("b6")},
                {"r2r4/1pk5/pBpbp2p/4p1p1/2N1Pn2/5P2/PPP3PP/R2R1K2 b - - 1 19", sq("d8"), sql()},
                {"1r4k1/3b1pp1/p1p2q1p/3p1Pn1/P6P/3BR3/2P2QP1/5N1K b - h3 0 29", sq("f5"), sql("f6", "d7")},
                {"1r4k1/3b1pp1/p1p2q1p/3p1Pn1/P6P/3BR3/2P2QP1/5N1K b - h3 0 29", sq("a6"), sql("d3")},
                {"1r4k1/3b1pp1/p1p2q1p/3p1Pn1/P6P/3BR3/2P2QP1/5N1K b - h3 0 29", sq("g5"), sql("h4")},
                {"2kr3r/2pp1p2/b1p4n/p3P2R/3P4/1NP2q1P/1RP5/2BK2Q1 w - - 4 25", sq("d1"), sql("f3")},
                {"2kr3r/2pp1p2/b1p4n/p3P2R/3P4/1NP2q1P/1RP5/2BK2Q1 w - - 4 25", sq("a5"), sql("b3")},
                {"2kr3r/2pp1p2/b1p4n/p3P2R/3P4/1NP2q1P/1RP5/2BK2Q1 w - - 4 25", sq("h6"), sql("h5", "c1")},
                {"2kr3r/2pp1p2/b1p4n/p3P2R/3P4/1NP2q1P/1RP5/2BK2Q1 w - - 4 25", sq("h3"), sql("f3")},
                {"2kr3r/2pp1p2/b1p4n/p3P2R/3P4/1NP2q1P/1RP5/2BK2Q1 w - - 4 25", sq("h5"), sql("f3")},
                {"3qr3/Q2b1p1k/3pp1pB/7B/4PP2/N1bK4/PrP2P2/R5R1 b - - 4 23", sq("a2"), sql("b2")},
                {"3qr3/Q2b1p1k/3pp1pB/7B/4PP2/N1bK4/PrP2P2/R5R1 b - - 4 23", sq("c2"), sql("b2")},
                {"3qr3/Q2b1p1k/3pp1pB/7B/4PP2/N1bK4/PrP2P2/R5R1 b - - 4 23", sq("a1"), sql()},
                {"3qr3/Q2b1p1k/3pp1pB/7B/4PP2/N1bK4/PrP2P2/R5R1 b - - 4 23", sq("h5"), sql("g6")},
                {"3qr3/Q2b1p1k/3pp1pB/7B/4PP2/N1bK4/PrP2P2/R5R1 b - - 4 23", sq("h6"), sql("h7")},
                {"3qr3/Q2b1p1k/3pp1pB/7B/4PP2/N1bK4/PrP2P2/R5R1 b - - 4 23", sq("c3"), sql("d3")},
                {"3qr3/Q2b1p1k/3pp1pB/7B/4PP2/N1bK4/PrP2P2/R5R1 b - - 4 23", sq("g6"), sql("h5", "g1")},
                {"3qr3/Q2b1p1k/3pp1pB/7B/4PP2/N1bK4/PrP2P2/R5R1 b - - 4 23", sq("f7"), sql()},
                {"3qr3/Q2b1p1k/3pp1pB/7B/4PP2/N1bK4/PrP2P2/R5R1 b - - 4 23", sq("d7"), sql("a7")}
        };
    }

    @Test(dataProvider = "for_getAllThreats_Square")
    public void getAllThreats_Square(String fen, Square square, List<Square> list) {
        String message = toString(fen, square, list);
        PositionImpl position = new PositionImpl(fen);
        assertTrue(position.isCorrect(), message);
        assertTrue(square.isValid(), message);
        List<Square> threats = position.getAllThreats(square);
        assertEquals(threats.size(), list.size(), message);
        for (Square expected : list) {
            assertTrue(threats.contains(expected), message);
        }
    }

    @DataProvider(name = "for_isThreatened_Square")
    public Object[][] getDataFor_isThreatened_Square() {
        return new Object[][] {
                {"r1bqkb1r/p2npp2/2pp1npp/1p6/3PPB2/2NB1N2/PPPQ1PPP/R3K2R b KQkq - 1 8", sq("b5"), true},
                {"r1bqkb1r/p2npp2/2pp1npp/1p6/3PPB2/2NB1N2/PPPQ1PPP/R3K2R b KQkq - 1 8", sq("c6"), false},
                {"r1bqkb1r/p2npp2/2pp1npp/1p6/3PPB2/2NB1N2/PPPQ1PPP/R3K2R b KQkq - 1 8", sq("e4"), true},
                {"r1bqkb1r/p2npp2/2pp1npp/1p6/3PPB2/2NB1N2/PPPQ1PPP/R3K2R b KQkq - 1 8", sq("d4"), false}
        };
    }

    @Test(dataProvider = "for_isThreatened_Square")
    public void isThreatened_Square(String fen, Square square, boolean expected) {
        String message = toString(fen, square, expected);
        PositionImpl position = new PositionImpl(fen);
        assertTrue(position.isCorrect(), message);
        assertTrue(square.isValid(), message);
        assertEquals(position.isThreatened(square), expected, message);
    }

    @DataProvider(name = "for_isThreatened_Square_Color")
    public Object[][] getDataFor_isThreatened_Square_Color() {
        return new Object[][] {
                {"r1bqkb1r/p2npp2/2pp1npp/1p6/3PPB2/2NB1N2/PPPQ1PPP/R3K2R b KQkq - 1 8", WHITE, sq("c5"), true},
                {"r1bqkb1r/p2npp2/2pp1npp/1p6/3PPB2/2NB1N2/PPPQ1PPP/R3K2R b KQkq - 1 8", WHITE, sq("a5"), false},
                {"r1bqkb1r/p2npp2/2pp1npp/1p6/3PPB2/2NB1N2/PPPQ1PPP/R3K2R b KQkq - 1 8", BLACK, sq("g4"), true},
                {"r1bqkb1r/p2npp2/2pp1npp/1p6/3PPB2/2NB1N2/PPPQ1PPP/R3K2R b KQkq - 1 8", BLACK, sq("h3"), false}
        };
    }

    @Test(dataProvider = "for_isThreatened_Square_Color")
    public void isThreatened_Square_Color(String fen, Color color, Square square, boolean expected) {
        String message = toString(fen, color, square, expected);
        PositionImpl position = new PositionImpl(fen);
        assertTrue(position.isCorrect(), message);
        assertTrue(square.isValid(), message);
        assertEquals(position.isThreatened(square, color), expected, message);
    }

    @DataProvider(name = "for_isCheck")
    public Object[][] getDataFor_isCheck() {
        return new Object[][] {
                {"r3k2r/p2p1p1R/b1p1pB2/7p/6K1/2N1q3/PPP2bPP/R2Q1B2 w kq - 0 14", true},
                {"1k4BK/7P/8/5pP1/8/8/1b6/8 w - f6 0 1", true},
                {"rnQ3k1/pp4pp/6q1/3b2b1/3P1r2/8/PP3PPP/4RRK1 b - - 7 24", true},
                {"rn3rk1/pp4pp/8/8/2bP4/8/PP3qPP/4R1K1 w - - 0 31", true},
                {"r3r1k1/1p3pp1/5b1P/4p3/p1P1Q2P/3P1N2/1q2BP2/1K1R3R w - - 0 25", true},
                {"6k1/pp3pp1/7p/2pP3b/2P4r/5K1Q/PP3P1P/4r3 w - - 9 36", true},
                {"4k3/p2P2p1/p6p/2p2p1P/2P1r3/1b3K2/8/8 b - - 0 49", true},
                {"8/p2k2p1/7p/2p2p1P/2b5/3q4/4rK2/8 w - - 8 59", true},
                {"rnb2rk1/1p4pp/p1pq4/5pB1/2Bp4/2N2N2/PP3PPP/R3R1K1 b - - 7 26", true},
                {"8/2b5/4p3/3k1ppp/8/2N2P1P/5PK1/8 b - - 4 56", true},
                {"8/2b5/4p3/5ppp/2k5/2N2P1P/5PK1/8 w - - 5 57", false},
                {"rnb1kb1r/pp1p1ppp/2p2n2/q3N3/4P3/3P4/PPP2PPP/RNBQKB1R w KQkq - 1 5", true},
                {"rnb1kb1r/pp1p1ppp/2p2n2/q3N3/4P3/2NP4/PPP2PPP/R1BQKB1R b KQkq - 2 5", false},
                {"rnb2rk1/ppq1bppB/2p4p/4P3/3P4/2N4P/PPP3P1/R1BQ1RK1 b - - 0 13", true},
                {"rnb2r2/ppq1bppk/2p4p/4P3/3P4/2N4P/PPP3P1/R1BQ1RK1 w - - 0 14", false},
                {"rnb2r2/ppq1bppk/2p4p/4P3/3P4/2NQ3P/PPP3P1/R1B2RK1 b - - 1 14", true},
                {"rnb2rk1/ppq1bpp1/2p4p/4P3/3P4/2NQ3P/PPP3P1/R1B2RK1 w - - 2 15", false},
                {"r4r2/pp1nb1pk/1qp1bN1p/4p3/3P1B2/2PQ3P/PP4P1/R4RK1 b - - 1 21", true},
                {"r4r1k/pp1nb1p1/1qp1bN1p/4p3/3P1B2/2PQ3P/PP4P1/R4RK1 w - - 2 22", false},
                {"r1bqk2r/pp1p1ppp/5b2/2p5/4Q3/8/PPP2PPP/RNB1KB1R b KQkq - 1 9", true},
                {"r1bqk2r/pp1pbppp/8/2p5/4Q3/8/PPP2PPP/RNB1KB1R w KQkq - 2 10", false},
                {"r1bqk2r/pp2bppp/8/2pp4/Q7/3B4/PPP2PPP/RNB1K2R b KQkq - 1 11", true},
                {"r2qk2r/pp1bbppp/8/2pp4/Q7/3B4/PPP2PPP/RNB1K2R w KQkq - 2 12", false},
                {"3q1rk1/1p3ppB/p1r2b2/2p5/2Pp1B2/8/PPQ2PPP/RN3RK1 b - - 0 19", true},
                {"3q1r1k/1p3ppB/p1r2b2/2p5/2Pp1B2/8/PPQ2PPP/RN3RK1 w - - 1 20", false},
                {"r1b2rk1/p2nqppp/5N2/2p3B1/2BQ4/8/PPP2PPP/3RR1K1 b - - 0 15", true},
                {"r1b2rk1/p2n1ppp/5q2/2p3B1/2BQ4/8/PPP2PPP/3RR1K1 w - - 0 16", false},
                {"3R2k1/R4r1p/1n4p1/2p3B1/8/5P2/P1r3PP/6K1 b - - 2 27", true},
                {"3R4/R4rkp/1n4p1/2p3B1/8/5P2/P1r3PP/6K1 w - - 3 28", false},
                {"2R5/3R2kp/6p1/8/r1p5/5P2/2r3PP/6K1 b - - 1 38", true},
                {"2R5/3R3p/6pk/8/r1p5/5P2/2r3PP/6K1 w - - 2 39", false},
                {"r3k2r/pppb1pp1/6qp/2bPp3/2P1P3/2NP1Q2/P1P3PP/R1B2RK1 w kq - 3 15", true},
                {"r3k2r/pppb1pp1/6qp/2bPp3/2P1P3/2NPBQ2/P1P3PP/R4RK1 b kq - 4 15", false},
                {"r3k2r/pppb1pp1/6qp/3Pp3/2P1P3/2NPbQ2/P1P3PP/R4RK1 w kq - 0 16", true},
                {"r3k2r/pppb1pp1/6qp/3Pp3/2P1P3/2NPQ3/P1P3PP/R4RK1 b kq - 0 16", false},
                {"r2qk2r/1ppbbppp/p2p1n2/6B1/4P3/2Nn1N1P/PPP2PP1/R2QK2R w KQkq - 0 11", true},
                {"r2qk2r/1ppbbppp/p2p1n2/6B1/4P3/2NQ1N1P/PPP2PP1/R3K2R b KQkq - 0 11", false},
                {"r2r2k1/4b1pp/p2pbp2/1q6/3QPB2/1pP2N1P/1P2KPP1/3RR3 w - - 5 24", true},
                {"r2r2k1/4b1pp/p2pbp2/1q6/3QPB2/1pPR1N1P/1P2KPP1/4R3 b - - 6 24", false},
                {"r2r2k1/6pp/p4P2/1q1p4/3N1B2/1pPb3P/1P2KPP1/3R4 w - - 0 29", true},
                {"r2r2k1/6pp/p4P2/1q1p4/3N1B2/1pPR3P/1P2KPP1/8 b - - 0 29", false},
                {"r3r1k1/6pp/p4P2/1q1p4/3N1B2/1pPR3P/1P2KPP1/8 w - - 1 30", true},
                {"r3r1k1/6pp/p4P2/1q1p4/3N1B2/1pPR1K1P/1P3PP1/8 b - - 2 30", false},
                {"r3r1k1/6pp/p4P2/3p4/3N1B2/1pPq1K1P/1P3PP1/8 w - - 0 31", true},
                {"r3r1k1/6pp/p4P2/3p4/3N4/1pPqBK1P/1P3PP1/8 b - - 1 31", false},
                {"r3r1k1/6pp/p4P2/3p4/3Nq3/1pP1BK1P/1P3PP1/8 w - - 2 32", true},
                {"r3r1k1/6pp/p4P2/3p4/3Nq3/1pP1B2P/1P2KPP1/8 b - - 3 32", false},
                {"r3r1k1/7p/p4p2/3p4/3N4/1pP1qP1P/1P2K1P1/8 w - - 0 34", true},
                {"6rk/6pp/6N1/8/8/8/8/2K5 b - - 0 1", true},
                {"3R3k/6pp/8/8/7b/7K/8/8 b - - 0 1", true},
                {"r2q1r2/1pp3kQ/p1n2p2/2bp1BB1/3p4/8/PPP2PPP/RN2R1K1 b - - 0 16", false},
                {"r4rk1/1bq1bp1Q/1p1p4/p2P4/P1P5/1P1B4/6PP/R3N1K1 b - - 5 26", false},
                {"1n1Q4/5p1k/p3b1pp/1p6/3P2P1/P6P/qPBBN3/K2R3R w - - 1 25", false},
                {"kQ6/8/3B4/p1P4P/P7/4p3/1P3P1P/2K5 b - - 3 38", false},
                {"bn3rk1/2p1bpp1/3p3p/4p3/1P6/2NPQ2N/1B3PqP/R5K1 w - - 0 23", false},
                {"4r3/8/5N2/8/8/5Pp1/5K1k/5B2 w - - 0 1", false},
                {"8/5kp1/3q1p2/5P2/3n4/8/2pP4/2BKQ3 w - - 0 1", false},
                {"6r1/8/8/6P1/1k6/n1p5/PK6/R1B5 w - - 0 1", false},
                {"8/8/8/8/b2k4/b3p3/N2KP3/4R3 w - - 0 1", false},
                {"rn6/kb6/pP6/8/8/4B1K1/5P2/R7 b - - 0 1", false},
                {"2N5/2pp4/3kp2r/1P3n2/2KP4/8/8/6R1 b - - 0 1", false},
                {"8/4B3/8/pp1n4/k7/1P6/2K5/8 b - - 0 1", false},
                {"4r2k/5Q2/6Np/8/8/6P1/5PKP/4q3 b - - 0 1", false},
                {"6k1/6Qp/6p1/6n1/8/5pPq/1B3P2/6K1 b - - 0 1", false},
                {"8/8/8/8/8/3K4/8/Q1k5 b - - 0 1", false},
                {"4k2R/8/4K3/8/8/8/8/8 b - - 0 1", false},
                {"7k/7R/5N2/8/8/8/1p5K/4r3 b - - 0 1", false},
                {"6rk/5Npp/8/8/8/8/8/3K4 b - - 0 1", false},
                {"6rk/6pp/6N1/8/8/8/8/2K4R b - - 0 1", false},
                {"4nkr1/6p1/6N1/8/8/8/8/5R1K b - - 0 1", false},
                {"4nkr1/6p1/6N1/8/8/3b4/6q1/2K2R2 b - - 0 1", false},
                {"3B4/6N1/8/4k3/R7/5N2/8/3R3K b - - 0 1", false},
                {"6k1/p7/1pp1N3/8/1P3N2/2n1q3/r3B3/1KR4R w - - 0 35", true},
                {"5r2/p1R3p1/3Rk3/8/1P2P3/P7/1BB1KPr1/8 b - - 3 38", true},
                {"r4k1r/pppq1Qpp/2nP4/6N1/8/2b5/P4PPP/R1B2RK1 b - - 2 17", true},
                {"7r/4Q1rk/p3P2R/1p1p2P1/8/P1pP4/2P5/R1B1K3 b Q - 0 30", true},
                {"4kr2/4bp2/p3p1b1/2r5/3P4/P2q1NQ1/1P1K1PPP/RB2R3 w - - 11 32", true},
                {"6k1/5p2/6p1/7p/3B4/4PnPK/R6r/8 w - - 0 35", true}
        };
    }

    @Test(dataProvider = "for_isCheck")
    public void isCheck(String fen, boolean expected) {
        String message = toString(fen, expected);
        PositionImpl position = new PositionImpl(fen);
        assertTrue(position.isCorrect(), message);
        assertEquals(position.isCheck(), expected, message);
    }

    @DataProvider(name = "for_isMate")
    public Object[][] getDataFor_isMate() {
        return new Object[][] {
                {"r2qkb1N/pp1npQ1p/2p5/3p1b2/3P4/8/PPP2PPP/RNB1KBNR b KQq - 0 8", true},
                {"6k1/r2b1pp1/3bp2p/1p1p4/2q2P2/1K6/1QP5/8 w - - 4 39", true},
                {"r1bk3r/p2pBpNp/n4n2/1p1NP2P/6P1/3P4/P1P1K3/q5b1 b - - 1 23", true},
                {"6k1/R4p2/6p1/7p/3B4/4PnPK/7r/8 w - - 0 35", true},
                {"4kr2/4bp2/p3p1b1/2r5/3P4/P2q1NQ1/1P1K1PPP/R1B1R3 w - - 11 32", true},
                {"4Q2r/6rk/p3P2R/1p1p2P1/8/P1pP4/2P5/R1B1K3 b Q - 0 30", true},
                {"r2q1k1r/ppp2Qpp/2nP4/6N1/8/2b5/P4PPP/R1B2RK1 b - - 2 17", true},
                {"5r2/p1R3p1/3Rk3/4P3/1P6/P7/1BB1KPr1/8 b - - 3 38", true},
                {"6k1/p7/1pp1N3/8/1P3N2/2n1q3/r3B3/1K2R2R w - - 0 35", true},
                {"r3k2r/p2p1p2/b1p1pB2/7p/6K1/2N1q3/PPP2bPP/R2Q1B1R w kq - 0 14", true},
                {"1k4BK/7P/8/5pP1/8/8/1b6/8 w - f6 0 1", false},
                {"4bnb1/4rkr1/4p1p1/8/8/8/8/5RK1 b - - 0 1", true},
                {"8/4k3/5p1p/6p1/4PPB1/r1pp2PP/1nK5/r7 w - - 0 47", true},
                {"rn3rk1/pp4pp/8/8/2bP4/8/PP4PP/4q2K w - - 0 32", true},
                {"r1r2k1Q/pp3pp1/3P1R2/3q4/1P1P4/P6R/6PP/7K b - - 0 30", true},
                {"r3r1k1/1p3pp1/5b1P/4p3/2P1Q2P/p2P1N2/1q2BP2/1K1R3R w - - 0 25", true},
                {"8/p2k2p1/7p/2p2p1P/2b5/8/4r3/3q1K2 w - - 10 60", true},
                {"8/2b5/4p3/3k1ppp/8/2N2P1P/5PK1/8 b - - 4 56", false},
                {"rnb1kb1r/pp1p1ppp/2p2n2/q3N3/4P3/3P4/PPP2PPP/RNBQKB1R w KQkq - 1 5", false},
                {"rnb2rk1/ppq1bppB/2p4p/4P3/3P4/2N4P/PPP3P1/R1BQ1RK1 b - - 0 13", false},
                {"rnb2r2/ppq1bppk/2p4p/4P3/3P4/2NQ3P/PPP3P1/R1B2RK1 b - - 1 14", false},
                {"r4r2/pp1nb1pk/1qp1bN1p/4p3/3P1B2/2PQ3P/PP4P1/R4RK1 b - - 1 21", false},
                {"r1bqk2r/pp1p1ppp/5b2/2p5/4Q3/8/PPP2PPP/RNB1KB1R b KQkq - 1 9", false},
                {"r1bqk2r/pp2bppp/8/2pp4/Q7/3B4/PPP2PPP/RNB1K2R b KQkq - 1 11", false},
                {"3q1rk1/1p3ppB/p1r2b2/2p5/2Pp1B2/8/PPQ2PPP/RN3RK1 b - - 0 19", false},
                {"r1b2rk1/p2nqppp/5N2/2p3B1/2BQ4/8/PPP2PPP/3RR1K1 b - - 0 15", false},
                {"3R2k1/R4r1p/1n4p1/2p3B1/8/5P2/P1r3PP/6K1 b - - 2 27", false},
                {"2R5/3R2kp/6p1/8/r1p5/5P2/2r3PP/6K1 b - - 1 38", false},
                {"r3k2r/pppb1pp1/6qp/2bPp3/2P1P3/2NP1Q2/P1P3PP/R1B2RK1 w kq - 3 15", false},
                {"r3k2r/pppb1pp1/6qp/3Pp3/2P1P3/2NPbQ2/P1P3PP/R4RK1 w kq - 0 16", false},
                {"r2qk2r/1ppbbppp/p2p1n2/6B1/4P3/2Nn1N1P/PPP2PP1/R2QK2R w KQkq - 0 11", false},
                {"r2r2k1/4b1pp/p2pbp2/1q6/3QPB2/1pP2N1P/1P2KPP1/3RR3 w - - 5 24", false},
                {"r2r2k1/6pp/p4P2/1q1p4/3N1B2/1pPb3P/1P2KPP1/3R4 w - - 0 29", false},
                {"r3r1k1/6pp/p4P2/1q1p4/3N1B2/1pPR3P/1P2KPP1/8 w - - 1 30", false},
                {"r3r1k1/6pp/p4P2/3p4/3N1B2/1pPq1K1P/1P3PP1/8 w - - 0 31", false},
                {"r3r1k1/6pp/p4P2/3p4/3Nq3/1pP1BK1P/1P3PP1/8 w - - 2 32", false},
                {"r3r1k1/7p/p4p2/3p4/3N4/1pP1qP1P/1P2K1P1/8 w - - 0 34", false},
                {"6rk/6pp/6N1/8/8/8/8/2K5 b - - 0 1", false},
                {"3R3k/6pp/8/8/7b/7K/8/8 b - - 0 1", false},
                {"r2q1r2/1pp3kQ/p1n2p2/2bp1BB1/3p4/8/PPP2PPP/RN2R1K1 b - - 0 16", true},
                {"r4rk1/1bq1bp1Q/1p1p4/p2P4/P1P5/1P1B4/6PP/R3N1K1 b - - 5 26", true},
                {"1n1Q4/5p1k/p3b1pp/1p6/3P2P1/P6P/qPBBN3/K2R3R w - - 1 25", true},
                {"kQ6/8/3B4/p1P4P/P7/4p3/1P3P1P/2K5 b - - 3 38", true},
                {"bn3rk1/2p1bpp1/3p3p/4p3/1P6/2NPQ2N/1B3PqP/R5K1 w - - 0 23", true},
                {"4r3/8/5N2/8/8/5Pp1/5K1k/5B2 w - - 0 1", true},
                {"8/5kp1/3q1p2/5P2/3n4/8/2pP4/2BKQ3 w - - 0 1", true},
                {"6r1/8/8/6P1/1k6/n1p5/PK6/R1B5 w - - 0 1", true},
                {"8/8/8/8/b2k4/b3p3/N2KP3/4R3 w - - 0 1", true},
                {"rn6/kb6/pP6/8/8/4B1K1/5P2/R7 b - - 0 1", true},
                {"2N5/2pp4/3kp2r/1P3n2/2KP4/8/8/6R1 b - - 0 1", true},
                {"8/4B3/8/pp1n4/k7/1P6/2K5/8 b - - 0 1", true},
                {"4r2k/5Q2/6Np/8/8/6P1/5PKP/4q3 b - - 0 1", true},
                {"6k1/6Qp/6p1/6n1/8/5pPq/1B3P2/6K1 b - - 0 1", true},
                {"8/8/8/8/8/3K4/8/Q1k5 b - - 0 1", true},
                {"4k2R/8/4K3/8/8/8/8/8 b - - 0 1", true},
                {"7k/7R/5N2/8/8/8/1p5K/4r3 b - - 0 1", true},
                {"6rk/5Npp/8/8/8/8/8/3K4 b - - 0 1", true},
                {"6rk/6pp/6N1/8/8/8/8/2K4R b - - 0 1", true},
                {"4nkr1/6p1/6N1/8/8/8/8/5R1K b - - 0 1", true},
                {"4nkr1/6p1/6N1/8/8/3b4/6q1/2K2R2 b - - 0 1", true},
                {"3B4/6N1/8/4k3/R7/5N2/8/3R3K b - - 0 1", true},
                {"rnb2rk1/1p4pp/p1pq4/5pB1/2Bp4/2N2N2/PP3PPP/R3R1K1 b - - 7 26", false}
        };
    }

    @Test(dataProvider = "for_isMate")
    public void isMate(String fen, boolean expected) {
        String message = toString(fen, expected);
        PositionImpl position = new PositionImpl(fen);
        assertTrue(position.isCorrect(), message);
        assertEquals(position.isCheckmate(), expected, message);
    }

    @DataProvider(name = "for_getAllThreats_Square_Color")
    public Object[][] getDataFor_getAllThreats_Square_Color() {
        return new Object[][] {
                {"3qr1k1/Qp3pbp/2p1b1p1/P3P3/2P2B2/7P/2P2PPK/R4B2 b - - 0 24", WHITE, sq("e3"),  sql("f2", "f4", "a7")},
                {"3qr1k1/Qp3pbp/2p1b1p1/P3P3/2P2B2/7P/2P2PPK/R4B2 b - - 0 24", BLACK, sq("f8"),  sql("e8", "g7", "g8")},
                {"3qr1k1/Qp3pbp/2p1b1p1/P3P3/2P2B2/7P/2P2PPK/R4B2 b - - 0 24", BLACK, sq("c3"),  sql()},
                {"2r2nR1/7p/4p2k/3p2N1/1P1P1PP1/8/P4K2/8 b - - 1 46", BLACK, sq("g7"), sql("h6")},
                {"2r2n2/6Rp/4p2k/3p2N1/1P1P1PP1/8/P4K2/8 w - - 0 46", WHITE, sq("g6"), sql("g7")},
                {"2r2n2/6Rp/4p2k/3p2N1/1P1P1PP1/8/P4K2/8 w - - 0 46", BLACK, sq("g6"), sql("f8", "h6", "h7")},
                {"2r2n2/6Rp/4p1kP/3p2N1/1P1P1PP1/8/P4K2/8 b - - 4 45", WHITE, sq("f7"), sql("g7", "g5")},
                {"2r2n2/6Rp/4p1kP/3p2N1/1P1P1PP1/8/P4K2/8 b - - 4 45", BLACK, sq("f7"), sql("g6")},
                {"2r2n2/5R1p/4p1kP/3p2N1/1P1P1PP1/8/P4K2/8 w - - 3 45", WHITE, sq("c4"), sql()},
                {"2r2n2/5R1p/4p1kP/3p2N1/1P1P1PP1/8/P4K2/8 w - - 3 45", BLACK, sq("c4"), sql("c8", "d5")},
                {"5n2/5R1p/4p1kP/3p2N1/1PrP1PP1/8/P4K2/8 b - - 2 44", WHITE, sq("a7"), sql("f7")},
                {"5n2/R6p/4p1kP/3p2N1/1PrP1PP1/8/P4K2/8 w - - 1 44", BLACK, sq("f6"), sql("g6")},
                {"5n2/R6p/4pk1P/3p2N1/1PrP1PP1/8/P4K2/8 b - - 0 43", WHITE, sq("a6"), sql("a7")},
                {"8/pr1n1k1p/R3pB1P/3p2p1/3P2P1/1P3NK1/P4P2/8 b - - 0 38", WHITE, sq("g7"), sql("f6", "h6")},
                {"8/pr1n1kBp/R3pb1P/3p2p1/3P2P1/1P3N2/P4PK1/8 b - - 0 36", BLACK, sq("b2"), sql()},
                {"8/p1rn1kBp/R3pb1P/3p2p1/3P2P1/5N2/PP3P2/6K1 w - - 3 35", BLACK, sq("g6"), sql("f7", "h7")},
                {"8/p2nbkBp/2r1p1pP/p2p4/3P4/1R3NP1/PP3P2/6K1 b - - 1 30", WHITE, sq("e3"), sql("b3", "f2")},
                {"2r3k1/p6p/pnr1pbpP/3p4/3P1B2/5NP1/PP3P2/2RR2K1 w - - 2 25", BLACK, sq("c4"), sql("d5", "c6", "b6")},
                {"2r3k1/p6p/pnr1pbpP/3p4/3P1B2/5NP1/PP3P2/2RR2K1 w - - 2 25", WHITE, sq("c7"), sql("f4")},
                {"2r2rkb/p2n3p/p1n1pppP/3pP3/B2P4/5NP1/PP3P2/R1BR2K1 w - - 0 21", BLACK, sq("f5"), sql("e6", "g6")},
                {"2r2rk1/pp1n1pbp/1qn1p1p1/3pP2P/3P4/1B3NP1/PP2QP2/R1BR2K1 b - - 2 17", WHITE, sq("d2"), sql("c1", "d1", "e2", "f3")},
                {"r1bqkb1r/p2npp2/2pp1npp/1p6/3PPB2/2NB1N2/PPPQ1PPP/R3K2R b KQkq - 1 8", WHITE, sq("g5"), sql("f3", "f4")}
        };
    }

    @Test(dataProvider = "for_getAllThreats_Square_Color")
    public void getAllThreats_Square_Color(String fen, Color color, Square square, List<Square> list) {
        String message = toString(fen, color, square, list);
        PositionImpl position = new PositionImpl(fen);
        assertTrue(position.isCorrect(), message);
        assertTrue(square.isValid(), message);
        List<Square> threats = position.getAllThreats(square, color);
        assertEquals(threats.size(), list.size(), message);
        for (Square expected : list) {
            assertTrue(threats.contains(expected), message);
        }
    }

    @Test
    public void testCreate() {
        Position position = Position.create();
        assertEquals(position.getFEN(), STARTING_POSITION);

        String fen = "r1b2rk1/ppppqppp/1bn3n1/4p3/3PP3/N1P2N2/PPQB1PPP/2KR1B1R w - - 9 9";
        position = Position.create(fen);
        assertEquals(position.getFEN(), fen);
    }

    @DataProvider(name = "testStalematePositions")
    public Object[][] getTestStalematePositions() {
        return new Object[][] {
                {"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", false},

                {"r7/8/4p2k/5b1r/3p4/Np6/1P6/K7 w - - 0 62", true},
                {"1r6/8/4p2k/5b1r/3p4/Np6/1P6/K7 w - - 0 62", false},
                {"r7/8/4p2k/5b1r/3p4/Npp5/1P6/K7 w - - 0 62", false},
                {"r7/8/4p2k/5b1r/2Pp4/Np6/1P6/K7 w - - 0 62", false},
                {"r7/8/4p2k/5b1r/3p4/Np6/1P6/K5R1 w - - 0 62", false},

                {"6k1/6b1/8/8/8/n7/PP6/K7 w - - 0 1", true},
                {"6k1/8/7b/8/8/n7/PP6/K7 w - - 0 1", false},
                {"6k1/6b1/8/8/8/n1P5/PP6/K7 w - - 0 1", false},
                {"6k1/6b1/8/8/8/n7/PP6/K3R3 w - - 0 1", false},
                {"6k1/6b1/8/8/8/n7/1P6/K7 w - - 0 1", false},

                {"4r3/5ppk/8/8/8/6q1/3r4/7K w - - 0 2", true},
                {"4r3/5ppk/8/8/8/5q2/3r4/7K w - - 0 2", false},
                {"4r3/4Rppk/8/8/8/6q1/3r4/7K w - - 0 2", false},
                {"4r3/5ppk/8/8/8/6q1/3r2P1/7K w - - 0 2", false},

                {"7k/7P/5KP1/8/8/8/8/8 b - - 0 2", true},
                {"7k/8/5KPP/8/8/8/8/8 b - - 0 2", false},

                {"7k/8/5N2/7N/8/P7/1K6/8 b - - 0 2", true},
                {"7k/5p2/5N1p/7N/8/P7/1K6/8 b - - 0 2", true},
                {"7k/4pp2/5N1p/7N/8/P7/1K6/8 b - - 0 2", false},

                {"5q2/K7/b7/k7/Pp6/1P6/8/8 w - - 0 1", true},
                {"4nq2/K7/b7/k7/Pp6/1P6/8/8 w - - 0 1", false},

                {"8/6pk/6p1/6P1/6PK/2q3PP/8/8 w - - 1 3", true},
                {"8/6pk/5pp1/5PP1/6PK/2q3PP/8/8 w - - 1 3", false},

                {"8/1p6/k1pp4/p7/8/1q6/1b6/1K6 w - - 0 2", true},
                {"8/1p6/k1pp4/p7/8/1qn5/1b6/1K6 w - - 0 2", false},

                {"2b5/2p3rk/2P5/4b3/5R2/5p2/5PpK/1r6 w - - 0 1", true},
                {"2b5/2p4k/2P5/8/5b2/5p2/5PpK/1r6 w - - 0 1", false},

                {"8/p2k2p1/7p/2p2p1P/2b5/3q4/4rK2/8 w - - 8 59", false},
                {"rnb2rk1/1p4pp/p1pq4/5pB1/2Bp4/2N2N2/PP3PPP/R3R1K1 b - - 7 26", false},
                {"8/2b5/4p3/3k1ppp/8/2N2P1P/5PK1/8 b - - 4 56", false}
        };
    }

    @Test(dataProvider = "testStalematePositions")
    public void testIsStalemate(String fen, boolean isStalemate) {
        Position pos = Position.create(fen);
        assertEquals(pos.isStalemate(), isStalemate);
    }

    private List<Square> sql(String... args) {
        List<Square> list = new ArrayList<>();
        for (String arg : args) {
            list.add(sq(arg));
        }
        return list;
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
