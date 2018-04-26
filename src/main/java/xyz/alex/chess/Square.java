package xyz.alex.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static xyz.alex.utils.Utils.checkArgument;
import static xyz.alex.utils.Utils.checkNotNull;

/**
 * @author dumas45
 */
class Square {
    private static final Pattern SQUARE_STRING_PATTERN = Pattern.compile("[a-h][1-8]");

    static final Square NONE = new Square(0, 0);

    private static final List<List<Square>> SQUARES;
    static {
        // fill SQUARES
        List<List<Square>> squares = new ArrayList<>(8);
        for (int v = 1; v <= 8; ++v) {
            List<Square> vList = new ArrayList<>();
            for (int h = 1; h <= 8; ++h) {
                vList.add(new Square(v, h));
            }
            squares.add(Collections.unmodifiableList(vList));
        }
        SQUARES = Collections.unmodifiableList(squares);
    }

    static Square square(int v, int h) {
        if (!isValid(v, h)) {
            return NONE;
        }
        return SQUARES.get(v-1).get(h-1);
    }

    static Square square(String squareName) {
        checkNotNull(squareName);
        checkArgument(SQUARE_STRING_PATTERN.matcher(squareName).matches(), squareName);
        int v = squareName.charAt(0) - 'a' + 1;
        int h = Character.digit(squareName.charAt(1), 10);
        checkArgument(Square.isValid(v, h), squareName);
        return square(v, h);
    }

    final int v;
    final int h;

    private Square(int v, int h) {
        this.v = v;
        this.h = h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Square square = (Square) o;

        return v == square.v && h == square.h;
    }

    @Override
    public int hashCode() {
        int result = v;
        result = 31 * result + h;
        return result;
    }

    @Override
    public String toString() {
        return "[" + ((char) ('a' + v - 1)) + ", " + h + "]";
    }

    boolean isValid() {
         return isValid(v, h);
    }

    static boolean isValid(int v, int h) {
        return v >= 1 && v <= 8 && h >= 1 && h <= 8;
    }
}
