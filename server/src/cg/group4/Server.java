package cg.group4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Server class creates an access point for clients. It contains a thread pool on which
 * server tasks are run.
 */
public class Server {
    /**
     * Default java logging functionality.
     */
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    /**
     * The port that the server will use.
     */
    public static final int DEFAULT_PORT = 63269;

    /**
     * The maximum number of Threads that can be active.
     */
    public static final int MAX_THREADS = 50;

    /**
     * The ExecutorService which only allows as much threads to be run as defined by MAX_THREADS.
     */
    protected ExecutorService cPool;

    /**
     * The local IP address and the external IP address.
     */
    protected InetAddress cLocalAddress, cExternalAddress;

    /**
     * The ServerSocket used by the server.
     */
    protected ServerSocket cServerSocket;

    /**
     * Creates a new Server.
     */
    public Server() {
        LOGGER.setLevel(Level.INFO);
    }

    /**
     * Gets the local and external IP address and creates the ServerSocket. Then waits for incoming connections.
     */
    public final void startRunning() {
        try {
            cLocalAddress = getLocalIP();
            cExternalAddress = getExternalIP();
            cServerSocket = createServerSocket();
            LOGGER.info(this.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        acceptConnections();
    }

    /**
     * Called when everything is set properly and waits for new incoming connections. Runs the connections in
     * separate ServerThreads.
     */
    protected final void acceptConnections() {
        cPool = Executors.newFixedThreadPool(MAX_THREADS);
        LOGGER.info("Server is running. Accepting incoming connections.");
        while (true) {
            try {
                Socket connection = cServerSocket.accept();
                Callable<Void> task = new ServerThread(connection);
                cPool.submit(task);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Looks up the local IP address. Uses InetAddress.getLocalHost() to do so.
     *
     * @return The localhost IP address.
     * @throws UnknownHostException The host could not be found.
     */
    protected final InetAddress getLocalIP() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    /**
     * Looks up the external IP address. Uses
     * <a href="http://checkip.amazonaws.com">http://checkip.amazonaws.com</a> to do so.
     *
     * @return The external IP address.
     * @throws IOException Exception when url could not be read.
     */
    protected final InetAddress getExternalIP() throws IOException {
        URL whatIsMyIP = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatIsMyIP.openStream()));
        String ip = in.readLine();
        in.close();
        return InetAddress.getByName(ip);
    }

    /**
     * Creates the ServerSocket on the DEFAULT_PORT.
     *
     * @return ServerSocket
     * @throws IOException Exits the application if port could not be bound.
     */
    protected final ServerSocket createServerSocket() throws IOException {
        try {
            return new ServerSocket(DEFAULT_PORT);
        } catch (BindException bindException) {
            System.out.println("Port " + DEFAULT_PORT + " is already in use. Please change or free the port.");
            System.exit(1);
        }
        return null;
    }

    /**
     * Returns the local IP address hostname.
     *
     * @return Local IP address.
     */
    public final String getLocalAddress() {
        return cLocalAddress.getHostAddress();
    }

    /**
     * Returns the external IP address hostname.
     *
     * @return External IP address.
     */
    public final String getExternalAddress() {
        return cExternalAddress.getHostAddress();
    }

    @Override
    public final String toString() {
        String separator = System.getProperty("line.separator");
        String result = "";
        result += "Local IP Address: " + getLocalAddress() + separator;
        result += "External IP Address: " + getExternalAddress() + separator;
        result += "Port: " + cServerSocket.getLocalPort();
        return result;
    }

    /**
     * Test method for the server.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        Server server = new Server();
        server.startRunning();
    }
}
