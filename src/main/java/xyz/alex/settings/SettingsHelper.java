package xyz.alex.settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;
import static xyz.alex.utils.Utils.checkNotNull;

/**
 * @author dumas45
 */
public class SettingsHelper {
    public static final String SETTINGS_FILE_NAME = ".green-chess";

    public static Settings getSettings() {
        Path path = getPath();
        if (Files.notExists(path)) {
            Settings settings = new Settings();
            saveSettings(settings);
            return settings;
        }

        try (InputStream in = Files.newInputStream(path, READ)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Settings) unmarshaller.unmarshal(in);
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void saveSettings(Settings settings) {
        checkNotNull(settings);

        try (OutputStream out = Files.newOutputStream(getPath(), CREATE, WRITE, TRUNCATE_EXISTING)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(settings, out);
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Path getPath() {
        String userHome = System.getProperty("user.home");
        Path path = Paths.get(userHome, SETTINGS_FILE_NAME);
        if (Files.isDirectory(path)) {
            throw new RuntimeException("Found unexpected directory instead of a file: " + path);
        }
        return path;
    }

    public static EngineSettings getDefaultEngineSettings() {
        Settings settings = getSettings();
        for (EngineSettings engine : settings.getEngines()) {
            if (engine.getName().equals(EngineSettings.DEFAULT_NAME)) {
                return engine;
            }
        }

        throw new RuntimeException("Could not find default engine settings");
    }
}
