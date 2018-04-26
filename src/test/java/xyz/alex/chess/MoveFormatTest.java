package xyz.alex.chess;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * @author dumas45
 */
public class MoveFormatTest {
    @Test
    public void getMovesFromPgnLine() throws Exception {
        assertEquals(
                MoveFormat.getMovesFromPgnLine("1. e4 g5"),
                Arrays.asList("e4", "g5"));

        assertEquals(
                MoveFormat.getMovesFromPgnLine("1. e4 g5 2. Bc4 f5 3. exf5 Nh6 4. Qh5+ Nf7 5. Bxf7#"),
                Arrays.asList("e4", "g5", "Bc4", "f5", "exf5", "Nh6", "Qh5+", "Nf7", "Bxf7#"));

        assertEquals(
                MoveFormat.getMovesFromPgnLine("3... d6 4. Nf3 Nxe4 5. d4 d5 6. Bd3"),
                Arrays.asList("d6", "Nf3", "Nxe4", "d4", "d5", "Bd3"));
    }

    @DataProvider(name = "correctAlgebraicMoves")
    public Object[][] getCorrectAlgebraicMoves() {
        return new Object[][] {{"e2e4"}, {"c1g5"}, {"c7c8q"}};
    }

    @DataProvider(name = "incorrectAlgebraicMoves")
    public Object[][] getIncorrectAlgebraicMoves() {
        return new Object[][] {{"c6c8q"}, {"c1j7"}, {"a2a9"}, {"e4"}, {""}, {"e24"}};
    }

    @Test(dataProvider = "correctAlgebraicMoves")
    public void algebraicPattern(String move) throws Exception {
        assertTrue(MoveFormat.ALGEBRAIC.validate(move));
    }

    @Test(dataProvider = "incorrectAlgebraicMoves")
    public void algebraicPattern_incorrect(String move) throws Exception {
        assertFalse(MoveFormat.ALGEBRAIC.validate(move));
    }

    @DataProvider(name = "correctPgnMoves")
    public Object[][] getCorrectPgnMoves() {
        return new Object[][] {
                {"e4"}, {"e2e4"}, {"fxg3"}, {"f2xg3"}, {"h8=B"}, {"dxc8=Q"}, {"dxc6+"}, {"c8=Q"},

                {"Qh4+"}, {"Qh4#"}, {"dxc8=Q+"}, {"Bg5"}, {"R5d4"},
                {"Rcd4"}, {"R5xd4"}, {"Rcxd4"}, {"Na6xc5"}, {"Na6c5"},
                {"Kxh2"}, {"Kf4"}, {"Qxg2#"}, {"Nc3"}, {"Bc4"}, {"Rae1"}, {"Qb5"},

                {"O-O"}, {"O-O-O"}, {"0-0"}, {"0-0-0"}, {"O-O+"}, {"O-O-O+"}, {"0-0+"}, {"0-0-0+"}
        };
    }

    @DataProvider(name = "incorrectPgnMoves")
    public Object[][] getIncorrectPgnMoves() {
        return new Object[][] {
                {""}, {" Bg5"}, {"ed"}, {"e9"}, {"a0"}, {"e2-e4"}, {"e2 e4"}, {"Bg22"}, {"e12"}, {"exf7 +"}, {"Qf3 +"},
                {"Qxd0"}, {"c9=Q"}, {"Qxg2 #"}, {"c8=q"}, {"kf4"}, {"qxg2#"}, {"nc3"}, {"bc4"}, {"rae1"}, {"qb5"},
                {"Af4"}, {"Cxg2#"}, {"Dc3"}, {"Ec4"}, {"Fae1"}, {"Gb5"}, {"H5"}
        };
    }

    @Test(dataProvider = "correctPgnMoves")
    public void pgnPattern(String move) throws Exception {
        assertTrue(MoveFormat.PGN.validate(move));
    }

    @Test(dataProvider = "incorrectPgnMoves")
    public void pgnPattern_incorrect(String move) throws Exception {
        assertFalse(MoveFormat.PGN.validate(move));
    }

    @DataProvider(name = "correctPgnToAlgebraicMoves")
    public Object[][] getDataFor_correctPgnToAlgebraicMoves() {
        return new Object[][] {
                {"2kr2r1/ppp4p/4qbp1/3pRp2/5P2/1PP5/P5PP/RNBQ3K w - - 1 23", "Rxe6", "e5e6"},
                {"rnb1kbnr/pppp1ppp/5q2/4p3/3PP3/5N2/PPP2PPP/RNBQKB1R b KQkq d3 0 3", "exd4", "e5d4"},
                {"rnb1k1nr/pppp1ppp/8/2b5/3NP3/2N1B3/PqP2PPP/R2QKB1R w KQkq - 0 7", "Ndb5", "d4b5"},
                {"rn2k2r/ppp1bp1p/1q2b1pB/3pP3/3N4/1PP2P2/P5PP/RN1QK2R w KQkq - 2 14", "0-0", "e1g1"},
                {"rn2k2r/ppp1bp1p/1q2b1pB/3pP3/3N4/1PP2P2/P5PP/RN1QK2R w KQkq - 2 14", "O-O", "e1g1"},
                {"r3k1r1/ppp4p/4q1p1/3pnp2/5P1b/1PP5/P3R1PP/RNBQ3K b q - 0 21", "0-0-0", "e8c8"},
                {"r3k1r1/ppp4p/4q1p1/3pnp2/5P1b/1PP5/P3R1PP/RNBQ3K b q - 0 21", "O-O-O", "e8c8"},
                {"rnbqk2r/pppp1Npp/5n2/2b5/4P3/8/PPPP1PPP/RNBQKB1R b KQkq - 0 4", "O-O", "e8g8"},
                {"rnbqk2r/pppp1Npp/5n2/2b5/4P3/8/PPPP1PPP/RNBQKB1R b KQkq - 0 4", "0-0", "e8g8"},
                {"r3kb1r/ppp2ppp/2n1b3/4q3/4p3/P1N1B3/1PP1QPPP/R3KB1R w KQkq - 4 10", "O-O-O", "e1c1"},
                {"r3kb1r/ppp2ppp/2n1b3/4q3/4p3/P1N1B3/1PP1QPPP/R3KB1R w KQkq - 4 10", "0-0-0", "e1c1"},
                {"7k/8/1N6/3n4/1N3N2/8/8/7K w - - 0 1", "Nb4xd5", "b4d5"},
                {"r1bqk2r/p1ppbp1p/1p2pn1R/4n2p/P7/8/1PPPPPP1/1NBQKBNR w Kkq - 4 9", "R6xh5", "h6h5"},
                {"r1b1k2r/pp1qbppp/2np1n2/4p3/3pP3/2PB1N2/PP1B1PPP/RN1QK2R w KQkq - 1 9", "cxd4", "c3d4"},
                {"r1bq1rk1/pp3ppp/2n1pn2/2pp4/1bP5/2N2NP1/PPQPPPBP/R1B2RK1 w - d6 0 8", "c4xd5", "c4d5"},
                {"r1b1k2r/pp1qbppp/2np1n2/4p3/3pP3/2PB1N2/PP1B1PPP/RN1QK2R w KQkq - 1 9", "a4", "a2a4"},
                {"r1b1k2r/pp1qbppp/2np1n2/4p3/3pP3/2PB1N2/PP1B1PPP/RN1QK2R w KQkq - 1 9", "b3", "b2b3"},
                {"r1b1k2r/pp1qbppp/2np1n2/4p3/3pP3/2PB1N2/PP1B1PPP/RN1QK2R w KQkq - 1 9", "b4", "b2b4"},
                {"r1bqkbnr/pppppppp/8/4n3/3P1P2/8/PPP1P1PP/RNBQKBNR w KQkq - 1 3", "fxe5", "f4e5"},
                {"rnbqkbnr/pp1p1ppp/8/2p1p3/3P4/4P3/PPPB1PPP/RN1QKBNR b KQkq - 0 3", "cxd4", "c5d4"},
                {"2r2k2/3r3p/3p1p2/p2BpP2/2PR3P/6Q1/pP3P1K/8 b - - 0 32", "a1=R", "a2a1r"},
                {"8/1p5p/2B5/4k1p1/8/P5P1/1P1RpP1P/1K6 b - - 0 41", "e1=Q+", "e2e1q"},
                {"8/3P4/2k5/8/8/8/8/7K w - - 0 1", "d8=Q", "d7d8q"},
                {"r1bqkbnr/pp1npppp/3p4/1Bp5/4P3/5N2/PPPP1PPP/RNBQ1RK1 b kq - 3 4", "Nf6", "g8f6"},
                {"r4brk/p4ppp/4b3/1p5N/2q5/3pP2P/PB1P1PP1/1KR4R b - - 3 23", "Qxa2#", "c4a2"}
        };
    }

    @Test(dataProvider = "correctPgnToAlgebraicMoves")
    public void pgnFormatToAlgebraic(String fen, String pgnMove, String algebraicMove) {
        Board board = new Board(fen);
        assertEquals(MoveFormat.PGN.formatToAlgebraic(pgnMove, board), algebraicMove);
    }

    @DataProvider(name = "correctAlgebraicToPgnMoves")
    public Object[][] getDataFor_correctAlgebraicToPgnMoves() {
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
    public void formatFromAlgebraic(String fen, String algebraicMove, String pgnMove) {
        Board board = new Board(fen);
        assertEquals(MoveFormat.PGN.formatFromAlgebraic(algebraicMove, board), pgnMove);
    }

    @DataProvider(name = "incorrectPgnMovesInPosition")
    public Object[][] getDataFor_incorrectPgnMovesInPosition() {
        return new Object[][] {
                {"5rk1/ppqbb1p1/2n4p/4p3/3p2Q1/3P2NP/PPP2PP1/R4RK1 w - - 8 22", "a2-a4"},
                {"5rk1/ppqbb1p1/2n4p/4p3/3p2Q1/3P2NP/PPP2PP1/R4RK1 w - - 8 22", "Ra4"},
                {"5rk1/ppqbb1p1/2n4p/4p3/3p2Q1/3P2NP/PPP2PP1/R4RK1 w - - 8 22", "Rb1"},
                {"5rk1/ppqbb1p1/2n4p/4p3/3p2Q1/3P2NP/PPP2PP1/R4RK1 w - - 8 22", "cxd4"},
                {"5rk1/ppqbb1p1/2n4p/4p3/3p2Q1/3P2NP/PPP2PP1/R4RK1 w - - 8 22", "Rf7"},
                {"5rk1/ppqbb1p1/2n4p/4p3/3p2Q1/3P2NP/PPP2PP1/R4RK1 w - - 8 22", "Nb4"},
                {"r6r/pp1kbpp1/3p1nq1/4n3/3pP2p/5N1P/PP3PPK/RNBQR3 w - - 0 17", "Rxb1"},
                {"1r1qkb1r/pp2pppp/3p4/2Pp4/2P5/5N2/P1P1PPPP/RN1QKBR1 w Qk - 1 12", "3xd5"},
                {"rnbqkbnr/pppp1ppp/8/4p3/3P4/5N2/PPP1PPPP/RNBQKB1R b KQkq - 1 2", "6xd4"},
                {"1r1qkb1r/pp2pppp/3p4/2Pp4/2P5/5N2/P1P1PPPP/RN1QKBR1 w Qk - 1 12", "4xd5"},
                {"rnbqkbnr/pppp1ppp/8/4p3/3P4/5N2/PPP1PPPP/RNBQKB1R b KQkq - 1 2", "5xd4"},
                {"rnbqkbnr/pppp1ppp/8/4p3/3P4/5N2/PPP1PPPP/RNBQKB1R b KQkq - 1 2", "xd4"},
                {"r1bq1rk1/4nppp/3p4/p2Np1b1/1pP1P3/6P1/PPN2PBP/R2Q1RK1 w - - 5 16", "cxd5"},
                {"r1bq1rk1/4nppp/3p4/p2Np1b1/1pP1P2P/6P1/PPN2PB1/R2Q1RK1 b - - 0 16", "axb4"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP2PPP/R3K2R w - - 10 18", "O-O"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP2PPP/R3K2R w - - 10 18", "O-O-O"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP1KPPP/R6R b - - 11 18", "O-O"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP1KPPP/R6R b - - 11 18", "O-O-O"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP2PPP/R3K2R w - - 10 18", "0-0"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP2PPP/R3K2R w - - 10 18", "0-0-0"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP1KPPP/R6R b - - 11 18", "0-0"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP1KPPP/R6R b - - 11 18", "0-0-0"},
                {"4k3/3np3/8/8/B7/8/8/4K3 b - - 0 1", "Nc5"},
                {"7k/8/8/8/8/r1P3K1/8/2R5 w - - 0 1", "c4"}
        };
    }

    @Test(dataProvider = "incorrectPgnMovesInPosition", expectedExceptions = IllegalArgumentException.class)
    public void pgnFormatToAlgebraic_fail(String fen, String pgnMove) {
        Board board = new Board(fen);
        MoveFormat.PGN.formatToAlgebraic(pgnMove, board);
        fail();
    }

    @DataProvider(name = "incorrectAlgebraicMovesInPosition")
    public Object[][] getDataFor_incorrectAlgebraicMovesInPosition() {
        return new Object[][] {
                {"rnb1kbnr/pppp1ppp/5q2/4p3/3PP3/5N2/PPP2PPP/RNBQKB1R b KQkq d3 0 3", "exd4"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "e5e4"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "d5d4"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "c4c5"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "b4b5"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "a1a3"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "f4d2"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "f4c2"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "f4g4"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "f4d7"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "d2b4"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "d2c2"},
                {"3r1rk1/2pqbppp/p1n5/1p1pP3/5B2/2P2P2/PPBQ1P1P/R4RK1 w - - 1 16", "d2g2"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP2PPP/R3K2R w - - 10 18", "e1g1"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP2PPP/R3K2R w - - 10 18", "e1c1"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP1KPPP/R6R b - - 11 18", "e8g8"},
                {"r3k2r/ppp2ppp/6b1/8/8/2B5/PPP1KPPP/R6R b - - 11 18", "e8c8"},
                {"r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3", "e4e5"},
                {"r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3", "e5e4"},
                {"r2qkb1r/2p2ppp/p1n1b3/1p1pP3/4n3/1B3N2/PPPN1PPP/R1BQ1RK1 b kq - 2 9", "e6b3"},
                {"r2qkb1r/2p2ppp/p1n1b3/1p1pP3/4n3/1B3N2/PPPN1PPP/R1BQ1RK1 b kq - 2 9", "c6d8"},
                {"r2qkb1r/2p2ppp/p1n1b3/1p1pP3/4n3/1B3N2/PPPN1PPP/R1BQ1RK1 b kq - 2 9", "b5c5"},
                {"r2qkb1r/2p2ppp/p1n1b3/1p1pP3/4n3/1B3N2/PPPN1PPP/R1BQ1RK1 b kq - 2 9", "a8g8"},
                {"r2qkb1r/2p2ppp/p1n1b3/1p1pP3/4n3/1B3N2/PPPN1PPP/R1BQ1RK1 b kq - 2 9", "f8g8"},
                {"r2qkb1r/2p2ppp/p1n1b3/1p1pP3/4n3/1B3N2/PPPN1PPP/R1BQ1RK1 b kq - 2 9", "e6e6"},
                {"4k3/3np3/8/8/B7/8/8/4K3 b - - 0 1", "d7c5"},
                {"7k/8/8/8/8/r1P3K1/8/2R5 w - - 0 1", "c3c4"}
        };
    }

    @Test(dataProvider = "incorrectAlgebraicMovesInPosition", expectedExceptions = IllegalArgumentException.class)
    public void formatFromAlgebraic_fail(String fen, String algebraicMove) {
        Board board = new Board(fen);
        MoveFormat.PGN.formatFromAlgebraic(algebraicMove, board);
        fail();
    }
}
