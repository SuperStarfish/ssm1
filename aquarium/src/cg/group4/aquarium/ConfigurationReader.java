package cg.group4.aquarium;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class ConfigurationReader {

    /**
     *
     */
    protected String address;

    /**
     *
     */
    protected int port;

    /**
     *
     */
    protected Scanner cScanner;

    /**
     *
     * @param scanner
     */
    public ConfigurationReader(Scanner scanner) {
        cScanner = scanner;
    }

    /**
     * Returns a tuple (actually an ArrayList with the host (ip) and the port.
     * @return requires to have exact two elements to qualify.
     */
    public ArrayList readSettings() {

        ArrayList arrayList = new ArrayList();

        if (cScanner.hasNextLine()) {
            address = cScanner.nextLine();
            arrayList.add(0, address);
        }

        if (cScanner.hasNextInt()) {
            port = cScanner.nextInt();
            arrayList.add(1, port);
        }

        assert (arrayList.size() == 2);

        return arrayList;
    }



}
