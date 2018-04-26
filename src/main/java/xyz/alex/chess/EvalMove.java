package xyz.alex.chess;

import static xyz.alex.utils.Utils.checkArgument;
import static xyz.alex.utils.Utils.checkNotNull;

/**
 * Evaluated move
 *
 * @author dumas45
 */
public class EvalMove {
    public final String move;
    public final int eval;

    public EvalMove(String move, int eval) {
        checkNotNull(move);
        checkArgument(MoveFormat.ALGEBRAIC.validate(move), () -> "move = " + move);

        this.move = move;
        this.eval = eval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvalMove evalMove = (EvalMove) o;

        return eval == evalMove.eval && move.equals(evalMove.move);
    }

    @Override
    public int hashCode() {
        int result = move.hashCode();
        result = 31 * result + eval;
        return result;
    }

    @Override
    public String toString() {
        return "" + move + " " + eval;
    }
}
