package cg.group4.client.connection;

import cg.group4.client.Client;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * A state in which the Client is connected to the server.
 */
public final class RemoteConnection implements Connection {
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
     * Boolean whether the connection is currently accepting new requests (not waiting for response).
     */
    protected boolean cAcceptingRequest;
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
        cAcceptingRequest = true;
    }

    /**
     * Has an empty body because the server connection has already been made.
     * @param ip   The IP to connect to.
     * @param port The port to connect to.
     */
    @Override
    public void connect(final String ip, final int port) { }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public void send(final Serializable data, final ResponseHandler responseHandler) {
        if (cAcceptingRequest) {
            cAcceptingRequest = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cOutputStream.writeObject(data);
                        cOutputStream.flush();
                        final Response response = (Response) cInputStream.readObject();
                        cAcceptingRequest = true;
                        Client.getRemoteInstance().addPostRunnables(new Runnable() {
                            @Override
                            public void run() {
                                if (responseHandler != null) {
                                    responseHandler.handleResponse(response);
                                }
                            }
                        });
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
