package xyz.alex.chess.uci;

/**
 * @author dumas45
 */
public interface UciProcessListener {
    void output(String line);

    void input(String line);
}
