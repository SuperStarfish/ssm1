package cg.group4.client.connection;

import cg.group4.server.database.Response;
import cg.group4.server.database.query.Query;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A state in which the Client is connected to the server.
 */
public final class RemoteConnection extends Thread implements Connection {
    /**
     * How long to try and wait for the connection to the server to be made.
     * Time is in milliseconds.
     */
    protected final int cConnectionTimeOut = 7000;
    /**
     * The connection with the server.
     */
    protected Socket cConnection;
    /**
     * InputStream for objects from the server.
     */
    protected ObjectInputStream cInputStream;
    /**
     * OutputStream for objects to the server.
     */
    protected ObjectOutputStream cOutputStream;

    /**
     * Attempts to create a new connection with the server. Fails after cConnectionTimeOut milliseconds.
     *
     * @param ip   The IP to connect to.
     * @param port The port to connect to.
     * @throws IOException Exception if connection fails.
     */
    public RemoteConnection(final String ip, final int port) throws IOException {
        cConnection = new Socket(ip, port);
        cOutputStream = new ObjectOutputStream(cConnection.getOutputStream());
        cInputStream = new ObjectInputStream(cConnection.getInputStream());

        this.start();
    }

    @Override
    public Connection connect(final String ip, final int port) {
        return this;
    }

    @Override
    public Connection disconnect() {
        try {
            cOutputStream.close();
            cInputStream.close();
            cConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LocalConnection();
    }

    @Override
    public Response send(final Query data) {
        try {
            cOutputStream.writeObject(data);
            cOutputStream.flush();
            return (Response) cInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
