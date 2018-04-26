package xyz.alex.chess.uci;

/**
 * @author dumas45
 */
public class UciProcessException extends Exception {

    UciProcessException(String message) {
        super(message);
    }

    UciProcessException(String message, Throwable cause) {


        super(message, cause);
    }
}
