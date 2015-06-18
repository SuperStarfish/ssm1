package cg.group4.aquarium;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Data class containing the configuration required for the Aquarium.
 */
public class Configuration {

    /**
     * Tag for debugging & logging.
     */
    final protected String cTag = this.getClass().getSimpleName();

    /**
     * Location of the server configuration.
     */
    final protected String cConfigFile = "aquarium/server.cfg";

    /**
     * Reader of the configuration file.
     */
    protected ConfigurationReader cAquariumSettingsReader;

    /**
     * Server Host IP cAddress.
     */
    protected String cHost;

    /**
     * Server Host cPort.
     */
    protected Integer cPort;

    /**
     * Initializes the configuration data class.
     */
    public Configuration() {
        try {
            readConfig(cConfigFile);
        } catch (IOException e) {
            Gdx.app.log(cTag, e.getMessage());
        }
    }

    /**
     * Reads the host/ip:cPort configuration using a ConfigurationReader.
     * @param file Location of the config file.
     * @throws IOException thrown on failure of file operations
     */
    public void readConfig(final String file) throws IOException {
        cAquariumSettingsReader = new ConfigurationReader(new Scanner(new File(file)));
        ArrayList a = cAquariumSettingsReader.readSettings();
        setHost((String) a.get(0));
        setPort((Integer) a.get(1));
    }

    /**
     * Returns the host.
     * @return cHost
     */
    public String getHost() {
        return cHost;
    }

    /**
     * Sets the host.
     * @param host host to set
     */
    public void setHost(final String host) {
        this.cHost = host;
    }

    /**
     * Returns the cPort.
     * @return cPort
     */
    public int getPort() {
        return cPort;
    }

    /**
     * Sets the cPort.
     * @param port cPort to set
     */
    public void setPort(final int port) {
        this.cPort = port;
    }

}
