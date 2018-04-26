package xyz.alex.chess.uci;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import xyz.alex.settings.EngineSettings;
import xyz.alex.settings.SettingsHelper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * @author dumas45
 */
public class UciProcessImplTest {

    private UciProcessBuilder uciProcessBuilder;

    @BeforeClass
    public void init() throws Exception {
        EngineSettings settings = SettingsHelper.getDefaultEngineSettings();
        String enginePath = settings.getPath();
        if (enginePath.equals(EngineSettings.DEFAULT_EMPTY_PATH)) {
            fail("Configure your engine path in " + SettingsHelper.getPath());
        }

        assertTrue(Files.isExecutable(Paths.get(enginePath)), "Not enough permissions to execute file " + enginePath);
        uciProcessBuilder = new UciProcessBuilder(enginePath);
    }

    @Test
    public void justTest() throws Exception {
        UciProcessListenerImpl listener = new UciProcessListenerImpl();

        UciProcess process = uciProcessBuilder.create(listener);

        for (String line : listener.getAll())
            System.out.println(line);

        checkThreads(process);
        checkMultiPV(process);
        checkNewGame(process);
        checkPosition(process);
        checkSetHashSize(process);
        checkClearHash(process);

        process.destroy();
    }

    private void checkThreads(UciProcess process) throws Exception {
        process.setListener(new UciProcessListenerImpl());

        process.setThreads(2);

        UciProcessListenerImpl listener = (UciProcessListenerImpl) process.getListener();
        for (String line : listener.getAll())
            System.out.println(line);

        assertListInOrder(listener.getOutput(), asList("setoption name Threads value 2", "isready"));
        assertListInOrder(listener.getInput(), singletonList("readyok"));
        assertListInOrder(listener.getAll(), asList("setoption name Threads value 2", "isready", "readyok"));
    }

    private void checkMultiPV(UciProcess process) throws Exception {
        process.setListener(new UciProcessListenerImpl());

        process.setMultiPV(20);

        UciProcessListenerImpl listener = (UciProcessListenerImpl) process.getListener();
        for (String line : listener.getAll())
            System.out.println(line);

        assertListInOrder(listener.getOutput(), asList("setoption name MultiPV value 20", "isready"));
        assertListInOrder(listener.getInput(), singletonList("readyok"));
        assertListInOrder(listener.getAll(), asList("setoption name MultiPV value 20", "isready", "readyok"));
    }

    private void checkPosition(UciProcess process) throws Exception {
        process.setListener(new UciProcessListenerImpl());

        process.setPosition("startpos moves e2e4 e7e6 d2d4 d7d5 b1c3 g8f6");

        UciProcessListenerImpl listener = (UciProcessListenerImpl) process.getListener();
        for (String line : listener.getAll())
            System.out.println(line);

        assertListInOrder(listener.getOutput(), asList("position startpos moves e2e4 e7e6 d2d4 d7d5 b1c3 g8f6", "isready"));
        assertListInOrder(listener.getInput(), singletonList("readyok"));
        assertListInOrder(listener.getAll(), asList("position startpos moves e2e4 e7e6 d2d4 d7d5 b1c3 g8f6", "isready", "readyok"));
    }

    private void checkNewGame(UciProcess process) throws Exception {
        process.setListener(new UciProcessListenerImpl());

        process.newGame();

        UciProcessListenerImpl listener = (UciProcessListenerImpl) process.getListener();
        for (String line : listener.getAll())
            System.out.println(line);

        assertListInOrder(listener.getOutput(), asList("ucinewgame", "isready"));
        assertListInOrder(listener.getInput(), singletonList("readyok"));
        assertListInOrder(listener.getAll(), asList("ucinewgame", "isready", "readyok"));
    }

    private void checkClearHash(UciProcess process) throws Exception {
        process.setListener(new UciProcessListenerImpl());

        process.clearHash();

        UciProcessListenerImpl listener = (UciProcessListenerImpl) process.getListener();
        for (String line : listener.getAll())
            System.out.println(line);

        assertListInOrder(listener.getOutput(), asList("setoption name Clear Hash", "isready"));
        assertListInOrder(listener.getInput(), singletonList("readyok"));
        assertListInOrder(listener.getAll(), asList("setoption name Clear Hash", "isready", "readyok"));
    }

    private void checkSetHashSize(UciProcess process) throws Exception {
        process.setListener(new UciProcessListenerImpl());

        process.setHashSize(32);

        UciProcessListenerImpl listener = (UciProcessListenerImpl) process.getListener();
        for (String line : listener.getAll())
            System.out.println(line);

        assertListInOrder(listener.getOutput(), asList("setoption name Hash value 32", "isready"));
        assertListInOrder(listener.getInput(), singletonList("readyok"));
        assertListInOrder(listener.getAll(), asList("setoption name Hash value 32", "isready", "readyok"));
    }

    private static void assertListInOrder(List<?> list, List<?> elements) {
        int index = -1;
        for (Object element : elements) {
            assertTrue(index < (index = list.indexOf(element)));
        }
    }
}
