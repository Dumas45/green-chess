package xyz.alex.chess.uci;

import xyz.alex.chess.EvalNode;

import java.util.List;

/**
 * UCI Process
 *
 * @author dumas45
 */
public interface UciProcess {
    long UCI_TIMEOUT_MS = 300000;

    void destroy() throws UciProcessException;

    void setThreads(int threads) throws UciProcessException;

    void setMultiPV(int multiPV) throws UciProcessException;

    void setPosition(String position) throws UciProcessException;

    boolean ready(long timeoutMilliseconds) throws UciProcessException;

    void clearHash() throws UciProcessException;

    void setHashSize(int MB) throws UciProcessException;

    void newGame() throws UciProcessException;

    void setListener(UciProcessListener listener);
    UciProcessListener getListener();

    List<EvalNode> goDepth(int depth) throws UciProcessException;
}
