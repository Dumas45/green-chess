package xyz.alex.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static xyz.alex.utils.Utils.checkArgument;
import static xyz.alex.utils.Utils.checkNotNull;
import static xyz.alex.utils.Utils.validState;

/**
 * <b>This implementation is not synchronized.</b> If multiple threads access an UciProcessListenerImpl instance
 * concurrently it must be synchronized externally.
 *
 * @author dumas45
 */
class EvalNodeImpl implements EvalNode {
    private final List<EvalMove> sortedByMove;
    private final List<EvalMove> sortedByEval;
    private static final Comparator<EvalMove> MOVE_COMPARATOR = Comparator.comparing(o -> o.move);
    private static final Comparator<EvalMove> EVAL_COMPARATOR = (left, right) -> {
        if (left.eval < right.eval) {
            return 1;
        } else if (left.eval > right.eval) {
            return -1;
        } else {
            return left.move.compareTo(right.move);
        }
    };
    static final int MISSING_MOVE_PENALTY = 20;

    EvalNodeImpl() {
        sortedByMove = new ArrayList<>();
        sortedByEval = new ArrayList<>();
    }

    @Override
    public int getEval(String move) {
        checkNotNull(move);
        checkArgument(MoveFormat.ALGEBRAIC.validate(move), () -> "move = " + move);

        int index = indexedBinarySearch(sortedByMove, move);

        return getEval(index);
    }

    @Override
    public int getEval(EvalMove move) {
        checkNotNull(move);

        int index = Collections.binarySearch(sortedByMove, move, MOVE_COMPARATOR);

        return getEval(index);
    }

    @Override
    public void add(EvalMove move) {
        checkNotNull(move);
        int size = sortedByMove.size();
        validState(size == sortedByEval.size());

        int indexForMoveSorting = Collections.binarySearch(sortedByMove, move, MOVE_COMPARATOR);
        int indexForEvalSorting = Collections.binarySearch(sortedByEval, move, EVAL_COMPARATOR);

        if (indexForMoveSorting < 0) {
            validState(indexForEvalSorting < 0);
            indexForMoveSorting = -(indexForMoveSorting + 1);
            indexForEvalSorting = -(indexForEvalSorting + 1);
            validState(indexForMoveSorting >= 0 && indexForMoveSorting <= size);
            validState(indexForEvalSorting >= 0 && indexForEvalSorting <= size);

            sortedByMove.add(indexForMoveSorting, move);
            sortedByEval.add(indexForEvalSorting, move);
        } else {
            validState(indexForMoveSorting < size);
            validState(indexForEvalSorting >= 0 && indexForEvalSorting < size);

            sortedByMove.set(indexForMoveSorting, move);
            sortedByEval.set(indexForEvalSorting, move);
        }
    }

    @Override
    public List<EvalMove> list() {
        return Collections.unmodifiableList(sortedByEval);
    }

    @Override
    public int size() {
        return sortedByEval.size();
    }

    @Override
    public EvalMove get(int index) {
        return sortedByEval.get(index);
    }

    @Override
    public String toString() {
        return sortedByEval.toString();
    }

    private static int indexedBinarySearch(List<EvalMove> list, String move) {
        int low = 0;
        int high = list.size()-1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            EvalMove midVal = list.get(mid);
            int cmp = midVal.move.compareTo(move);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // move found
        }
        return -(low + 1);  // move not found
    }

    private int getEval(int moveIndex) {
        if (moveIndex < 0) {
            if (sortedByEval.isEmpty()) {
                return Integer.MIN_VALUE;
            } else {
                return sortedByEval.get(sortedByEval.size() - 1).eval - MISSING_MOVE_PENALTY;
            }
        } else {
            return sortedByMove.get(moveIndex).eval;
        }
    }
}
