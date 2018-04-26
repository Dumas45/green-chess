package xyz.alex.chess;

import org.testng.annotations.Test;

/**
 * @author dumas45
 */
public class EvalMoveTest {
    @Test
    public void create_successful() throws Exception {
        new EvalMove("e2e4", 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void create_exception() throws Exception {
        new EvalMove("e4", 1);
    }
}
