package cg.group4.server;

import cg.group4.server.host.ExternalHost;
import cg.group4.server.host.Host;
import cg.group4.server.host.LocalHost;
import cg.group4.server.host.UnknownHost;
import cg.group4.util.IpResolver;
import cg.group4.util.StaticsCaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Server has a main method that starts a Server instance. The server looks up the local IP and the external IP.
 * It creates a ServerSocket on the specified (default) port and then accepts connections. Incoming connections are
 * handled in new Threads. This is done with an ExecutorService that defines the maximum number of Threads.
 */
public class Server {
    /**
     * Default Logger in Java used for the purpose of logging changes in the Server.
     */
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    /**
     * The largest port number that can exist.
     */
    protected final int cMaxPortNumber = 65535;
    /**
     * The default port to be used for the ServerSocket.
     */
    protected final int cDefaultPort = 56789;
    /**
     * The maximum number of threads that the ExecutorService can have.
     */
    protected final int cMaxThreads = 50;
    /**
     * Containers for the local and external INetAddress. Can be UnknownHost if lookup failed.
     */
    protected Host cLocalHost, cExternalHost;
    /**
     * The ServerSocket used by this Server to accept incoming connections.
     */
    protected ServerSocket cServerSocket;
    /**
     * The ExecutorPool that limits the number of Threads spawned.
     */
    protected ExecutorService cPool;
    /**
     * Used to make calls to static methods. Primarily used for mocking in tests.
     */
    protected StaticsCaller cStaticsCaller;


    /**
     * Creates an instance of Server.
     */
    public Server() {
        cStaticsCaller = new StaticsCaller();
    }

    /**
     * Starts a server.
     *
     * @param args Not used, but default for main method.
     */
    public static void main(final String[] args) {
        Server server = new Server();
        server.LOGGER.setLevel(Level.INFO);
        server.start();
    }

    /**
     * Starts the Server. First looks up the Local IP and then the External IP. After that it tries to create a
     * ServerSocket on the default port. If it fails it asks to give a new port on which to make the ServerSocket.
     * Then validates to see if the Server can be contacted through the external IP address.
     * Finally keeps waiting for new connections forever.
     */
    public final void start() {
        createLocalIP();

        createExternalIP();

        createServerSocket(cDefaultPort);

        validateExternalConnection();

        while (true) {
            acceptConnections();
        }
    }

    /**
     * Attempts to get the local IP address. If it fails it assigns as UnknownHost.
     */
    protected final void createLocalIP() {
        try {
            cLocalHost = new LocalHost(cStaticsCaller.getLocalHost());
        } catch (UnknownHostException e) {
            cLocalHost = new UnknownHost();
            LOGGER.severe("Could not set up local IP!");
        }
    }

    /**
     * Attempts to get the External IP address. If it fails it assigns as UnknownHost.
     */
    protected final void createExternalIP() {
        IpResolver ipResolver = new IpResolver();
        try {
            cExternalHost = new ExternalHost(cStaticsCaller.getByName(ipResolver.getExternalIP()));
            LOGGER.info("External IP: " + cExternalHost.toString());
        } catch (UnknownHostException e) {
            cExternalHost = new UnknownHost();
            LOGGER.severe("Could not set up local IP!");
        }
    }

    /**
     * Attempts to create a ServerSocket using the port given. If it fails, it will ask for a new port.
     *
     * @param port The port to use for the ServerSocket.
     */
    protected final void createServerSocket(final int port) {
        try {
            cServerSocket = new ServerSocket(port);
            LOGGER.info("Successfully bound to port " + port + ".");
        } catch (IOException e) {
            LOGGER.severe("Port " + port + " is already in use!");
            createServerSocket(askForPort());
        }
    }

    /**
     * Asks the user for a port. It validates if the port is within proper range.
     *
     * @return The port that the user provided.
     */
    protected final int askForPort() {
        int port = -1;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        do {
            LOGGER.info("Please enter a port number:");
            try {
                String line = bufferedReader.readLine();
                port = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                LOGGER.severe("Bad input. Try again.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!isValidPort(port));
        return port;
    }

    /**
     * Returns if the port is within proper range.
     *
     * @param port The port to check.
     * @return Returns if the port is within valid range.
     */
    protected final boolean isValidPort(final int port) {
        return port >= 0 && port <= cMaxPortNumber;
    }

    /**
     * Checks to see if the Server is accessible from the outside world.
     */
    protected final void validateExternalConnection() {
        try {
            LOGGER.info("Checking if the port is open.");
            Socket socket = new Socket(cExternalHost.toString(), cDefaultPort);
            cServerSocket.accept();
            LOGGER.info("Port " + cDefaultPort + " is open. Managed to connect over external IP");
            socket.close();
        } catch (ConnectException e) {
            LOGGER.severe("Port " + cDefaultPort + " does not appear to be open! "
                    + "Can't connect over external IP. Use local IP to connect to the server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for a new incoming connection and then adds it in a new ServerThread in the pool.
     */
    protected final void acceptConnections() {
        try {
            cPool = Executors.newFixedThreadPool(cMaxThreads);
            Socket connection = cServerSocket.accept();
            Callable<Void> task = new ServerThread(connection);
            cPool.submit(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final String toString() {
        String result = "";
        String lineSeparator = System.getProperty("line.separator");
        result += "Local IP: " + cLocalHost.toString() + lineSeparator;
        result += "External IP: " + cExternalHost.toString() + lineSeparator;
        return result;
    }

}
