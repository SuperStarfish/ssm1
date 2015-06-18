package cg.group4.aquarium;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads the configuration from the given scanner object.
 */
public class ConfigurationReader {

    /**
     * Scanner used to read the configuration file.
     */
    protected Scanner cScanner;

    /**
     * Initializes the scanner so the configuration file can be read using {#code readSettings()}.
     * @param scanner Used to read the configuration file
     */
    public ConfigurationReader(final Scanner scanner) {
        cScanner = scanner;
    }

    /**
     * Returns a tuple (actually an ArrayList) with the host (ip) and the cPort.
     * @return requires to have exact two elements to qualify.
     */
    public ArrayList readSettings() {

        assert(cScanner != null);

        ArrayList arrayList = new ArrayList();

        if (cScanner.hasNextLine()) {
            String address = cScanner.nextLine();
            arrayList.add(0, address);
        }

        if (cScanner.hasNextInt()) {
            int port = cScanner.nextInt();
            arrayList.add(1, port);
        }

        assert (arrayList.size() == 2);

        return arrayList;
    }
}
