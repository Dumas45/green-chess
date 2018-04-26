package xyz.alex.chess;

import java.util.List;

/**
 * @author dumas45
 */
public interface EvalNode {

    static EvalNode create() {
        return new EvalNodeImpl();
    }

    static EvalNode create(List<EvalMove> evalMoves) {
        EvalNode node = new EvalNodeImpl();
        for (EvalMove evalMove : evalMoves) {
            if (evalMove != null) {
                node.add(evalMove);
            }
        }
        return node;
    }

    int getEval(String move);

    int getEval(EvalMove move);

    void add(EvalMove move);

    List<EvalMove> list();

    int size();

    EvalMove get(int index);
}
