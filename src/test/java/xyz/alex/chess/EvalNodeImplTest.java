package xyz.alex.chess;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static xyz.alex.chess.EvalNodeImpl.MISSING_MOVE_PENALTY;

/**
 * @author dumas45
 */
public class EvalNodeImplTest {
    @Test
    public void justTest() throws Exception {
        EvalNode node = new EvalNodeImpl();

        assertEquals(node.getEval("a1a8"), Integer.MIN_VALUE);
        assertEquals(node.getEval(new EvalMove("a1a8", 0)), Integer.MIN_VALUE);
        assertTrue(node.list().isEmpty());

        node.add(new EvalMove("e2e4", 10));

        assertEquals(node.getEval("a1a8"), 10 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval(new EvalMove("a1a8", 0)), 10 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval("e2e4"), 10);
        assertEquals(node.getEval(new EvalMove("e2e4", 0)), 10);
        assertEquals(node.list(), Collections.singletonList(new EvalMove("e2e4", 10)));

        node.add(new EvalMove("e2e3", -20));

        assertEquals(node.getEval("a1a8"), -20 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval(new EvalMove("a1a8", 0)), -20 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval("e2e4"), 10);
        assertEquals(node.getEval("e2e3"), -20);
        assertEquals(node.getEval(new EvalMove("e2e4", 0)), 10);
        assertEquals(node.getEval(new EvalMove("e2e3", 0)), -20);
        assertEquals(node.list(), Arrays.asList(new EvalMove("e2e4", 10), new EvalMove("e2e3", -20)));

        node.add(new EvalMove("d2d4", 30));

        assertEquals(node.getEval("a1a8"), -20 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval(new EvalMove("a1a8", 0)), -20 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval("e2e4"), 10);
        assertEquals(node.getEval("e2e3"), -20);
        assertEquals(node.getEval("d2d4"), 30);
        assertEquals(node.getEval(new EvalMove("e2e4", 0)), 10);
        assertEquals(node.getEval(new EvalMove("e2e3", 0)), -20);
        assertEquals(node.getEval(new EvalMove("d2d4", 0)), 30);
        assertEquals(node.list(), Arrays.asList(
                new EvalMove("d2d4", 30),
                new EvalMove("e2e4", 10),
                new EvalMove("e2e3", -20)));

        node.add(new EvalMove("d2d3", 0));

        assertEquals(node.getEval("a1a8"), -20 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval(new EvalMove("a1a8", 0)), -20 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval("e2e4"), 10);
        assertEquals(node.getEval("e2e3"), -20);
        assertEquals(node.getEval("d2d4"), 30);
        assertEquals(node.getEval("d2d3"), 0);
        assertEquals(node.getEval(new EvalMove("e2e4", 0)), 10);
        assertEquals(node.getEval(new EvalMove("e2e3", 0)), -20);
        assertEquals(node.getEval(new EvalMove("d2d4", 0)), 30);
        assertEquals(node.getEval(new EvalMove("d2d3", 0)), 0);
        assertEquals(node.list(), Arrays.asList(
                new EvalMove("d2d4", 30),
                new EvalMove("e2e4", 10),
                new EvalMove("d2d3", 0),
                new EvalMove("e2e3", -20)));

        node.add(new EvalMove("c2c5", 20));

        assertEquals(node.getEval("a1a8"), -20 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval(new EvalMove("a1a8", 0)), -20 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval("e2e4"), 10);
        assertEquals(node.getEval("e2e3"), -20);
        assertEquals(node.getEval("d2d4"), 30);
        assertEquals(node.getEval("d2d3"), 0);
        assertEquals(node.getEval("c2c5"), 20);
        assertEquals(node.getEval(new EvalMove("e2e4", 0)), 10);
        assertEquals(node.getEval(new EvalMove("e2e3", 0)), -20);
        assertEquals(node.getEval(new EvalMove("d2d4", 0)), 30);
        assertEquals(node.getEval(new EvalMove("d2d3", 0)), 0);
        assertEquals(node.getEval(new EvalMove("c2c5", 0)), 20);
        assertEquals(node.list(), Arrays.asList(
                new EvalMove("d2d4", 30),
                new EvalMove("c2c5", 20),
                new EvalMove("e2e4", 10),
                new EvalMove("d2d3", 0),
                new EvalMove("e2e3", -20)));

        node.add(new EvalMove("c2c3", -30));

        assertEquals(node.getEval("a1a8"), -30 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval(new EvalMove("a1a8", 0)), -30 - MISSING_MOVE_PENALTY);
        assertEquals(node.getEval("e2e4"), 10);
        assertEquals(node.getEval("e2e3"), -20);
        assertEquals(node.getEval("d2d4"), 30);
        assertEquals(node.getEval("d2d3"), 0);
        assertEquals(node.getEval("c2c5"), 20);
        assertEquals(node.getEval("c2c3"), -30);
        assertEquals(node.getEval(new EvalMove("e2e4", 0)), 10);
        assertEquals(node.getEval(new EvalMove("e2e3", 0)), -20);
        assertEquals(node.getEval(new EvalMove("d2d4", 0)), 30);
        assertEquals(node.getEval(new EvalMove("d2d3", 0)), 0);
        assertEquals(node.getEval(new EvalMove("c2c5", 0)), 20);
        assertEquals(node.getEval(new EvalMove("c2c3", 0)), -30);
        assertEquals(node.list(), Arrays.asList(
                new EvalMove("d2d4", 30),
                new EvalMove("c2c5", 20),
                new EvalMove("e2e4", 10),
                new EvalMove("d2d3", 0),
                new EvalMove("e2e3", -20),
                new EvalMove("c2c3", -30)));
    }

    @Test
    public void testAddTwoDifferentMovesWithEqualEval() {
        EvalNode node = new EvalNodeImpl();
        node.add(new EvalMove("e2e4", 119));
        node.add(new EvalMove("d2d4", 119));

        assertEquals(node.list().size(), 2);
        assertEquals(node.getEval("e2e4"), 119);
        assertEquals(node.getEval(new EvalMove("d2d4", 0)), 119);
    }
}
