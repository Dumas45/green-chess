package xyz.alex.settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author dumas45
 */
@XmlRootElement(name = "engine")
public class EngineSettings {
    public static final String DEFAULT_EMPTY_PATH = "";
    static final String DEFAULT_NAME = "default";

    private static final int DEFAULT_DEPTH = 11;
    private static final int DEFAULT_HASH = 32;
    private static final int DEFAULT_MULTIPV = 4;
    private static final int DEFAULT_THREADS = 1;
    private static final String DEFAULT_BALANCE = "7 8";

    private String name = DEFAULT_NAME;
    private String path = DEFAULT_EMPTY_PATH;
    private int depth = DEFAULT_DEPTH;
    private int hash = DEFAULT_HASH;
    private int multiPV = DEFAULT_MULTIPV;
    private int threads = DEFAULT_THREADS;
    private String balance = DEFAULT_BALANCE;

    @XmlElement(name = "balance")
    public String getBalance() {
        return balance == null ? DEFAULT_BALANCE : balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name == null ? DEFAULT_NAME : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "path")
    public String getPath() {
        return path == null ? DEFAULT_EMPTY_PATH : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @XmlElement(name = "depth")
    public int getDepth() {
        return depth == 0 ? DEFAULT_DEPTH : depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @XmlElement(name = "hash")
    public int getHash() {
        return hash == 0 ? DEFAULT_HASH : hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    @XmlElement(name = "multiPV")
    public int getMultiPV() {
        return multiPV == 0 ? DEFAULT_MULTIPV : multiPV;
    }

    public void setMultiPV(int multiPV) {
        this.multiPV = multiPV;
    }

    @XmlElement(name = "threads")
    public int getThreads() {
        return threads == 0 ? DEFAULT_THREADS : threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }
}
