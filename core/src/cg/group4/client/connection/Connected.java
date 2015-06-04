package cg.group4.client.connection;

import cg.group4.client.query.CollectionWrapper;
import cg.group4.client.query.Update;
import cg.group4.client.query.UserData;
import cg.group4.client.query.Reply;
import cg.group4.client.query.Request;
import cg.group4.rewards.Collection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A state in which the Client is connected to the server.
 */
public final class Connected extends Thread implements Connection {
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
     *
     * @param ip The IP to connect to.
     * @param port The port to connect to.
     * @throws GdxRuntimeException Connection could not be established.
     */
    public Connected(final String ip,  final int port) throws GdxRuntimeException {
        SocketHints hints = new SocketHints();
        hints.connectTimeout = connectionTimeOut;
        cConnection = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, port, hints);
        try {
            outputStream = new ObjectOutputStream(cConnection.getOutputStream());
            inputStream = new ObjectInputStream(cConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            cConnection.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Unconnected();
    }

    @Override
    public UserData requestUserData(final String id) {
        try {
            outputStream.writeObject(new Request(new UserData(id)));
            outputStream.flush();
            Reply reply = (Reply) inputStream.readObject();
            return (UserData) reply.getcData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCollection(final Collection collection, final UserData userData) {
        try {
            outputStream.writeObject(new Update(new CollectionWrapper(collection, userData)));
            outputStream.flush();
            Reply reply = (Reply) inputStream.readObject();
            return reply.iscSuccess();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateUserData(final UserData data) {
        boolean success = false;
        try {
            outputStream.writeObject(new Update(data));
            outputStream.flush();
            Reply reply = (Reply) inputStream.readObject();
            success = reply.iscSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
