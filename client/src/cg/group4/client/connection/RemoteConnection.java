package cg.group4.client.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A state in which the Client is connected to the server.
 */
public final class RemoteConnection extends Connection {
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
    protected boolean cWaiting;
    /**
     * A queue that is used as buffer/storage for connection packets that must be sent but is no room for yet.
     */
    protected ConcurrentLinkedQueue<ConnectionPacket> cBuffer;

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
        cWaiting = true;
        cBuffer = new ConcurrentLinkedQueue<ConnectionPacket>();
    }

    /**
     * Has an empty body because the server connection has already been made.
     *
     * @param ip   The IP to connect to.
     * @param port The port to connect to.
     */
    @Override
    public void connect(final String ip, final int port) {
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public void send(final ConnectionPacket connectionPacket) {
        cBuffer.add(connectionPacket);
        if (cWaiting) {
            cWaiting = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!cBuffer.isEmpty()) {
                        sendPacket(cBuffer.poll(), cOutputStream, cInputStream);
                    }
                    cWaiting = true;
                }
            }).start();
        }
    }
}
