package xyz.alex.cmd;

import xyz.alex.chess.EvalMove;
import xyz.alex.chess.EvalNode;
import xyz.alex.chess.Position;
import xyz.alex.chess.uci.UciProcess;
import xyz.alex.chess.uci.UciProcessBuilder;
import xyz.alex.chess.uci.UciProcessException;
import xyz.alex.settings.EngineSettings;
import xyz.alex.settings.SettingsHelper;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static xyz.alex.chess.MoveFormat.ALGEBRAIC;
import static xyz.alex.chess.MoveFormat.PGN;
import static xyz.alex.chess.MoveFormat.getMovesFromPgnLine;
import static xyz.alex.chess.uci.UciProcess.UCI_TIMEOUT_MS;
import static xyz.alex.utils.Utils.checkArgument;
import static xyz.alex.utils.Utils.checkNotNull;
import static xyz.alex.utils.Utils.isNotBlank;
import static xyz.alex.utils.Utils.isNotEmpty;
import static xyz.alex.utils.Utils.timeToString;
import static xyz.alex.utils.Utils.validState;

/**
 * @author dumas45
 */
public class CmdMain {

    public static void main(String[] args) throws UciProcessException, IOException {

        long time = System.currentTimeMillis();
        String fen = null;
        String pgnMoves = null;

        String prev = null;
        for (String arg : args) {
            if ("-p".equals(prev)) {
                fen = arg;
            }
            if ("-m".equals(prev)) {
                pgnMoves = arg;
            }

            prev = arg;
        }

        Position position;
        if (fen == null) {
            position = Position.create();
        } else {
            position = Position.create(fen);
        }
        String movesParam = "";
        Position pos = position;
        if (isNotBlank(pgnMoves)) {
            List<String> pgnLine = getMovesFromPgnLine(pgnMoves);
            StringBuilder sb = new StringBuilder(" moves");
            for (String pgnMove : pgnLine) {
                sb.append(' ');
                String move = pos.formatMove(pgnMove, PGN, ALGEBRAIC);
                sb.append(move);
                pos = pos.move(move, ALGEBRAIC);
            }
            movesParam = sb.toString();
        }

        EngineSettings settings = SettingsHelper.getDefaultEngineSettings();
        String enginePath = settings.getPath();
        if (enginePath.equals(EngineSettings.DEFAULT_EMPTY_PATH)) {
            System.out.println("Configure your engine path in " + SettingsHelper.getPath());
            System.exit(1);
        }

        if (!Files.isExecutable(Paths.get(enginePath))) {
            throw new RuntimeException("Not enough permissions to execute file " + enginePath);
        }
        UciProcessBuilder uciProcessBuilder = new UciProcessBuilder(enginePath);

        UciProcess process = uciProcessBuilder.create();

        process.setHashSize(settings.getHash());
        process.setThreads(settings.getThreads());
        process.setMultiPV(settings.getMultiPV());

        validState(process.ready(UCI_TIMEOUT_MS));

        final int depth = settings.getDepth();
        List<Integer> balance = new ArrayList<>();
        if (isNotEmpty(settings.getBalance())) {
            for (String one : settings.getBalance().split("\\D+")) {
                if (isNotEmpty(one)) {
                    int b = Integer.parseInt(one);
                    if (b > 0 && b < depth) {
                        balance.add(b);
                    }
                }
            }
        }

        List<List<EvalNode>> data = new ArrayList<>();
        process.newGame();

        if (fen == null) {
            process.setPosition("startpos" + movesParam);
        } else {
            process.setPosition("fen " + position.getFEN() + movesParam);
        }

        validState(process.ready(UCI_TIMEOUT_MS));

        List<EvalNode> list = process.goDepth(depth);
        data.add(list);

        process.destroy();

        List<EvalNode> result = average(data);

        print(result, pos, depth, balance);

        System.out.println(timeToString(System.currentTimeMillis() - time));
    }

    private static List<EvalNode> average(List<List<EvalNode>> data) {
        final int ATTEMPTS = data.size();
        checkArgument(ATTEMPTS > 0);
        final int DEPTH = data.get(0).size();
        for (List<EvalNode> list : data) {
            checkArgument(list.size() == DEPTH);
        }

        if (ATTEMPTS == 1) {
            return data.get(0);
        }

        List<EvalNode> result = new ArrayList<>();
        for (int d = 1; d <= DEPTH; ++d) {
            EvalNode resultNode = EvalNode.create();
            result.add(resultNode);

            Set<String> moveSet = new HashSet<>();
            for (int a = 1; a <= ATTEMPTS; ++a) {
                for (EvalMove evalMove : data.get(a - 1).get(d - 1).list()) {
                    moveSet.add(evalMove.move);
                }
            }

            for (String move : moveSet) {
                int eval = 0;
                for (int a = 1; a <= ATTEMPTS; ++a) {
                    eval += data.get(a - 1).get(d - 1).getEval(move);
                }
                resultNode.add(new EvalMove(move, eval / ATTEMPTS));
            }
        }

        return result;
    }

    private static void print(List<EvalNode> result, Position position,
                              final int depth, List<Integer> balance) throws IOException {
        checkNotNull(result);
        checkNotNull(position);
        checkArgument(depth == result.size());

        Path path = Paths.get("result.txt");
        try (PrintStream out = new PrintStream(new BufferedOutputStream(
                Files.newOutputStream(path, CREATE, WRITE, TRUNCATE_EXISTING)), true)) {

            int maxLength = 0;
            for (int i = 0; i < result.size(); ++i) {
                maxLength = Math.max(maxLength, result.get(i).size());
                if (i > 0) {
                    out.print('\t');
                }
                out.print(i + 1);
                out.print('\t');
                out.print("EV");
            }

            for (int k = 0; k < maxLength; ++k) {
                out.println();
                for (int i = 0; i < result.size(); ++i) {
                    if (i > 0) {
                        out.print('\t');
                    }
                    if (k < result.get(i).size()) {
                        EvalMove evalMove = result.get(i).get(k);
                        out.print(position.formatMove(evalMove.move, ALGEBRAIC, PGN));
                        out.print('\t');
                        out.print(evalMove.eval);
                    } else {
                        out.print('\t');
                    }
                }
            }

            if (isNotEmpty(balance)) {
                final int TOLERANCE = 20;
                EvalNode balancedNode = EvalNode.create();

                Set<String> moveSet = new HashSet<>();
                for (int b : balance) {
                    for (EvalMove evalMove : result.get(b - 1).list()) {
                        moveSet.add(evalMove.move);
                    }
                }

                for (String move : moveSet) {
                    int eval = 0;
                    for (int b : balance) {
                        eval += result.get(b - 1).getEval(move);
                    }
                    balancedNode.add(new EvalMove(move, eval / balance.size()));
                }

                if (balancedNode.size() > 0) {
                    String bestMove = null;
                    EvalNode lastNode = result.get(depth - 1);
                    int bestEval = lastNode.get(0).eval;
                    out.println();
                    out.println();
                    out.print("BAL\tEV\t\t");
                    out.print(depth);
                    out.println("\tEV");
                    for (int i = 0; i < Math.max(balancedNode.size(), result.get(depth - 1).size()); ++i) {
                        if (i < balancedNode.size()) {
                            EvalMove evalMove = balancedNode.get(i);
                            out.print(position.formatMove(evalMove.move, ALGEBRAIC, PGN));
                            out.print('\t');
                            out.print(evalMove.eval);

                            if (bestMove == null && Math.abs(bestEval - lastNode.getEval(evalMove.move)) <= TOLERANCE) {
                                bestMove = evalMove.move;
                            }
                        } else {
                            out.print('\t');
                        }

                        out.print("\t\t");

                        if (i < result.get(depth - 1).size()) {
                            EvalMove evalMove = result.get(depth - 1).get(i);
                            out.print(position.formatMove(evalMove.move, ALGEBRAIC, PGN));
                            out.print('\t');
                            out.print(evalMove.eval);
                        } else {
                            out.print('\t');
                        }

                        out.println();
                    }

                    System.out.println("Best move: " + position.formatMove(bestMove, ALGEBRAIC, PGN));
                }
            }
        }
    }
}
