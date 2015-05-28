package cg.group4;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The server class handles storage of user data and interaction.
 */
public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * The local IP address and the external IP address.
     */
    protected InetAddress cLocalAddress, cExternalAddress;

    protected ServerSocket cServerSocket;

    protected ObjectInputStream cInputStream;

    protected ObjectOutputStream cOutputStream;

    protected Socket cConnection;

    /**
     * Creates a new Server.
     */
    public Server() {
        logger.setLevel(Level.INFO);
    }

    public void startRunning() {
        try {
            cLocalAddress = getLocalIP();
            cExternalAddress = getExternalIP();
            cServerSocket = createServerSocket();
            System.out.println(toString());
            while(true) {
                try {
                    waitForConnection();
                    createStreams();
                    whileInteracting();
                } finally {
                    cleanUp();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected final void waitForConnection() throws IOException {
        System.out.println("Waiting for connection...");
        cConnection = cServerSocket.accept();
        System.out.println("Connected to " + cConnection.getInetAddress().getHostName());
    }

    protected final void createStreams() throws IOException {
        cOutputStream = new ObjectOutputStream(cConnection.getOutputStream());
        cOutputStream.flush();
        cInputStream = new ObjectInputStream(cConnection.getInputStream());
    }

    protected final void cleanUp() throws IOException {
        cOutputStream.close();
        cInputStream.close();
        cConnection.close();
    }

    protected final void whileInteracting() throws IOException {
        String message = "You are now connected";
        do {
            try {
                message = (String) cInputStream.readObject();
                System.out.println(message);
            } catch (ClassNotFoundException cnfException) {
                System.out.println("Don't know what I received");
            }
        } while (!message.equals("STOP"));
    }

    /**
     * Looks up the local IP address. Uses InterAddress.getLocalHost() to do so.
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

    protected final ServerSocket createServerSocket() throws IOException {
        return new ServerSocket(63269);
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
     * @param args arguments
     */
    public static void main(final String[] args) {
        Server server = new Server();
        server.startRunning();
    }
}
