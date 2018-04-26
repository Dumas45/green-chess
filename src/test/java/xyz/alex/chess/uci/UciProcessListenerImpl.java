package xyz.alex.chess.uci;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>This implementation is not synchronized.</b> If multiple threads access an UciProcessListenerImpl instance
 * concurrently it must be synchronized externally.
 *
 * @author dumas45
 */
public class UciProcessListenerImpl implements UciProcessListener {
    private List<String> input = new ArrayList<>();
    private List<String> output = new ArrayList<>();
    private List<String> all = new ArrayList<>();

    @Override
    public void output(String line) {
        all.add(line);
        output.add(line);
    }

    @Override
    public void input(String line) {
        all.add(line);
        input.add(line);
    }

    public List<String> getInput() {
        return input;
    }

    public List<String> getOutput() {
        return output;
    }

    public List<String> getAll() {
        return all;
    }
}
