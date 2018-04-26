package xyz.alex.chess;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author dumas45
 */
public class PieceTest {
    @Test
    public void forCode() {
        for (Piece piece : Piece.values()) {
            assertEquals(piece, Piece.get(piece.getCode()));
        }
    }

    @Test
    public void forLetter() {
        for (Piece piece : Piece.values()) {
            assertEquals(piece, Piece.get(piece.getLetter()));
        }
    }
}
