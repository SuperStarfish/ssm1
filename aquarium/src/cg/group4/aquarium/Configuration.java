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

    final protected String tag = this.getClass().getSimpleName();
    final protected String configFile = "aquarium/server.cfg";
    protected ConfigurationReader cAquariumSettingsReader;
    protected String cHost;
    protected Integer cPort;

    /**
     *
     */
    public Configuration() {
        try {
            readConfig(configFile);
        } catch (IOException e) {
            Gdx.app.log(tag, e.getMessage());
        }
    }

    public void readConfig(String file) throws IOException {
        cAquariumSettingsReader = new ConfigurationReader(new Scanner(new File(file)));
        ArrayList a = cAquariumSettingsReader.readSettings();
        setHost((String) a.get(0));
        setPort((Integer) a.get(1));
    }

    /**
     *
     * @return
     */
    public String getHost() {
        return cHost;
    }

    /**
     *
     * @param host
     */
    public void setHost(String host) {
        this.cHost = host;
    }

    /**
     *
     * @return
     */
    public int getPort() {
        return cPort;
    }

    /**
     *
     * @param port
     */
    public void setPort(int port) {
        this.cPort = port;
    }


}
