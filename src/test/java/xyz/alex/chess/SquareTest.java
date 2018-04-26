package xyz.alex.chess;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author dumas45
 */
public class SquareTest {
    @Test
    public void square() throws Exception {
        for (int v = 1; v <= 8; ++v) {
            for (int h = 1; h <= 8; ++h) {
                Square square = Square.square(v, h);
                assertEquals(square.v, v);
                assertEquals(square.h, h);
            }
        }
    }

    @DataProvider(name = "incorrectCoordinates")
    public Object[][] getIncorrectCoordinates() {
        return new Object[][] {{1, 0}, {0, 2}, {8, 9}, {9, 7}};
    }

    @Test(dataProvider = "incorrectCoordinates")
    public void square_incorrectCoordinates(int v, int h) {
        assertEquals(Square.square(v, h), Square.NONE);
    }

    @DataProvider(name = "for_square_String")
    public Object[][] getDataFor_square_String() {
        return new Object[][] {
                {"e5", 5, 5},
                {"a1", 1, 1},
                {"h7", 8, 7}
        };
    }

    @Test(dataProvider = "for_square_String")
    public void square_String(String squareName, int v, int h) {
        Square square = Square.square(squareName);
        assertEquals(square.v, v);
        assertEquals(square.h, h);
    }

    @DataProvider(name = "for_square_String_incorrectNames")
    public Object[][] getDataFor_square_String_incorrectNames() {
        return new Object[][] {{"e0"}, {"a9"}, {"k2"}};
    }

    @Test(dataProvider = "for_square_String_incorrectNames", expectedExceptions = IllegalArgumentException.class)
    public void square_String_incorrectNames(String squareName) {
        Square.square(squareName);
        fail();
    }
}
