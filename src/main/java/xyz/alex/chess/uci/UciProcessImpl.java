package xyz.alex.chess.uci;

import xyz.alex.chess.EvalMove;
import xyz.alex.chess.EvalNode;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static xyz.alex.utils.Utils.isNotEmpty;

/**
 * @author dumas45
 */
class UciProcessImpl implements UciProcess {
    private static final Pattern PATTERN_UCIOK = Pattern.compile("\\s*uciok\\s*");
    private static final Pattern PATTERN_READYOK = Pattern.compile("\\s*readyok\\s*");
    private static final Pattern DEPTH_PATTERN = Pattern.compile("\\s+depth\\s+(\\d+)");
    private static final Pattern MULTIPV_PATTERN = Pattern.compile("\\s+multipv\\s+(\\d+)");
    private static final Pattern PV_PATTERN = Pattern.compile("\\s+pv\\s+(\\S+)");
    private static final Pattern SCORE_PATTERN = Pattern.compile("\\s+score\\s+cp\\s+(-?\\d+)");

    private final Process process;
    private final BufferedReader in;
    private final PrintStream out;

    private UciProcessListener listener;

    UciProcessImpl(Process process) throws UciProcessException {
        this(process, null);
    }

    UciProcessImpl(Process process, UciProcessListener listener) throws UciProcessException {
        this.process = process;
        this.listener = listener;
        in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        out = new PrintStream(new BufferedOutputStream(process.getOutputStream()), true);

        waitForInputStreamReady();
        boltInputData();

        out.println("uci");
        logOutputLine("uci");

        if (!waitForInputStreamReady()) {
            throw new UciProcessException("Engine is not switched to UCI mode");
        }

        if (!boltInputDataAndSearchForLine(PATTERN_UCIOK)) {
            throw new UciProcessException("Engine is not switched to UCI mode");
        }
    }

    @Override
    public void setListener(UciProcessListener listener) {
        this.listener = listener;
    }

    @Override
    public UciProcessListener getListener() {
        return listener;
    }

    @Override
    public void destroy() throws UciProcessException {
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            throw new UciProcessException("Cannot close streams on process destroy");
        }
        process.destroy();
    }

    @Override
    public void setThreads(int threads) throws UciProcessException {
        if (threads < 1 || threads > 128)
            throw new UciProcessException("incorrect argument: setThreads(" + threads + ")");
        out.print("setoption name Threads value ");
        out.println(Integer.toString(threads));
        logOutputLine("setoption name Threads value " + threads);

        ready(UCI_TIMEOUT_MS);
    }

    @Override
    public void setMultiPV(int multiPV) throws UciProcessException {
        if (multiPV < 1 || multiPV > 500)
            throw new UciProcessException("incorrect argument: setMultiPV(" + multiPV + ")");
        out.print("setoption name MultiPV value ");
        out.println(Integer.toString(multiPV));
        logOutputLine("setoption name MultiPV value " + multiPV);

        ready(UCI_TIMEOUT_MS);
    }

    @Override
    public void setPosition(String position) throws UciProcessException {
        if (position == null)
            throw new UciProcessException("incorrect argument: setPosition(null)");
        out.print("position ");
        out.println(position);
        logOutputLine("position " + position);

        ready(UCI_TIMEOUT_MS);
    }

    @Override
    public boolean ready(long timeoutMilliseconds) throws UciProcessException {
        out.println("isready");
        logOutputLine("isready");

        return waitForInputString(PATTERN_READYOK, timeoutMilliseconds);
    }

    @Override
    public void clearHash() throws UciProcessException {
        out.println("setoption name Clear Hash");
        logOutputLine("setoption name Clear Hash");

        ready(UCI_TIMEOUT_MS);
    }

    @Override
    public void setHashSize(int MB) throws UciProcessException {
        out.print("setoption name Hash value ");
        out.println(Integer.toString(MB));
        logOutputLine("setoption name Hash value " + MB);

        ready(UCI_TIMEOUT_MS);
    }

    @Override
    public void newGame() throws UciProcessException {
        out.println("ucinewgame");
        logOutputLine("ucinewgame");

        ready(UCI_TIMEOUT_MS);
    }

    @Override
    public List<EvalNode> goDepth(final int maxDepth) throws UciProcessException {
        out.print("go depth ");
        out.println(Integer.toString(maxDepth));
        logOutputLine("go depth " + maxDepth);

        List<List<EvalMove>> data = new ArrayList<>();
        int curDepth = 0;

        long time = System.currentTimeMillis();
        long idleTime = 0;
        boolean finished = false;
        try {
            while (!finished && idleTime < UCI_TIMEOUT_MS) {
                if (waitForInputStreamReady(UCI_TIMEOUT_MS)) {
                    time = System.currentTimeMillis();
                    idleTime = 0;
                    String line;
                    while (in.ready() && (line = in.readLine()) != null) {
                        logInputLine(line);
                        if (line.startsWith("bestmove")) {
                            finished = true;
                        }
                        if (line.startsWith("info")) {
                            Matcher m;
                            String depthText;
                            String pv;
                            String multiPvText;
                            String scoreText;
                            if ((m = DEPTH_PATTERN.matcher(line)).find() && isNotEmpty(depthText = m.group(1))
                                    && (m = PV_PATTERN.matcher(line)).find() && isNotEmpty(pv = m.group(1))
                                    && (m = MULTIPV_PATTERN.matcher(line)).find() && isNotEmpty(multiPvText = m.group(1))
                                    && (m = SCORE_PATTERN.matcher(line)).find() && isNotEmpty(scoreText = m.group(1))) {
                                int depth = Integer.parseInt(depthText);
                                int score = Integer.parseInt(scoreText);
                                int multipv = Integer.parseInt(multiPvText);

                                if (curDepth < depth && depth <= maxDepth) {
                                    curDepth = depth;
                                }
                                while (data.size() < curDepth) {
                                    data.add(new ArrayList<>());
                                }
                                if (depth == curDepth) {
                                    while (data.get(depth - 1).size() < multipv) {
                                        data.get(depth - 1).add(null);
                                    }
                                    data.get(depth - 1).set(multipv - 1, new EvalMove(pv, score));
                                }
                            }
                        }
                    }
                } else {
                    idleTime = System.currentTimeMillis() - time;
                }
            }
        } catch (IOException e) {
            throw new UciProcessException(e.getMessage(), e);
        }

        List<EvalNode> result = new ArrayList<>();
        for (List<EvalMove> evalMoves : data) {
            result.add(EvalNode.create(evalMoves));
        }

        return result;
    }

    private boolean waitForInputString(Pattern pattern, long timeoutMilliseconds) throws UciProcessException {
        boolean result;
        long time = System.currentTimeMillis();
        long elapsed = 0;
        do {
            waitForInputStreamReady(timeoutMilliseconds - elapsed);
            result = boltInputDataAndSearchForLine(pattern);
        } while (!result && (elapsed = System.currentTimeMillis() - time) < timeoutMilliseconds);

        return result;
    }

    private boolean waitForInputStreamReady() throws UciProcessException {
        return waitForInputStreamReady(UCI_TIMEOUT_MS);
    }

    private boolean waitForInputStreamReady(long timeoutMilliseconds) throws UciProcessException {
        long time = System.currentTimeMillis();

        try {
            while (!in.ready() && System.currentTimeMillis() - time < timeoutMilliseconds) {
                Thread.sleep(10);
            }

            return in.ready();
        } catch (InterruptedException | IOException e) {
            throw new UciProcessException(e.getMessage(), e);
        }
    }

    private void boltInputData() throws UciProcessException {
        boltInputDataAndSearchForLine(null);
    }

    private boolean boltInputDataAndSearchForLine(Pattern pattern) throws UciProcessException {
        boolean result = false;
        String line;
        try {
            while (in.ready() && (line = in.readLine()) != null) {
                logInputLine(line);
                if (pattern != null && pattern.matcher(line).matches()) {
                    result = true;
                }
            }
        } catch (IOException e) {
            throw new UciProcessException(e.getMessage(), e);
        }

        return result;
    }

    private void logInputLine(String line) {
        if (listener != null) listener.input(line);
    }

    private void logOutputLine(String line) {
        if (listener != null) listener.output(line);
    }
}
