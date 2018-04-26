package xyz.alex.chess.uci;

import java.io.IOException;

/**
 * @author dumas45
 */
public class UciProcessBuilder {
    private final ProcessBuilder builder;

    public UciProcessBuilder(String... command) {
        builder = new ProcessBuilder(command);
    }

    public UciProcess create() throws UciProcessException {
        try {
            return new UciProcessImpl(builder.start());
        } catch (IOException e) {
            throw new UciProcessException(e.getMessage(), e);
        }
    }

    public UciProcess create(UciProcessListener listener) throws UciProcessException {
        try {
            return new UciProcessImpl(builder.start(), listener);
        } catch (IOException e) {
            throw new UciProcessException(e.getMessage(), e);
        }
    }
}
