package xyz.alex.settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * @author dumas45
 */
@XmlRootElement(name = "settings")
public class Settings {
    private ArrayList<EngineSettings> engines;

    public Settings() {
        engines = new ArrayList<>();
        engines.add(new EngineSettings());
    }

    @XmlElementWrapper(name = "engines")
    @XmlElement(name = "engine")
    public ArrayList<EngineSettings> getEngines() {
        return engines;
    }

    public void setEngines(ArrayList<EngineSettings> engines) {
        this.engines = engines;
    }
}
