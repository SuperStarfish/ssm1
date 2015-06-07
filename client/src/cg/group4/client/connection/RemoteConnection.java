package cg.group4.client.connection;

import cg.group4.database.Response;
import cg.group4.database.query.Query;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A state in which the Client is connected to the server.
 */
public final class RemoteConnection extends Thread implements Connection {
    /**
     * The connection with the server.
     */
    protected Socket cConnection;

    /**
     * InputStream for objects from the server.
     */
    protected ObjectInputStream inputStream;

    /**
     * OutputStream for objects to the server.
     */
    protected ObjectOutputStream outputStream;

    /**
     * How long to try and wait for the connection to the server to be made.
     * Time is in milliseconds.
     */
    protected final int connectionTimeOut = 7000;

    /**
     * Attempts to create a new connection with the server. Fails after connectionTimeOut milliseconds.
     * @param ip The IP to connect to.
     * @param port The port to connect to.
     * @throws IOException Exception if connection fails.
     */
    public RemoteConnection(final String ip, final int port) throws IOException {
        cConnection = new Socket(ip, port);
        outputStream = new ObjectOutputStream(cConnection.getOutputStream());
        inputStream = new ObjectInputStream(cConnection.getInputStream());

        this.start();
    }

    @Override
    public Connection connect(final String ip, final int port) {
        return this;
    }

    @Override
    public Connection disconnect() {
        try {
            outputStream.close();
            inputStream.close();
            cConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LocalConnection();
    }

    @Override
    public Response send(Query data) {
        try {
            outputStream.writeObject(data);
            outputStream.flush();
            return (Response) inputStream.readObject();
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
