package cg.group4.client;

import cg.group4.client.connection.Connected;
import cg.group4.client.connection.Connection;
import cg.group4.client.connection.Unconnected;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Jurgen van Schagen
 */
public class Client {
    protected Connection cConnection;

    public static final String defaultIP = "192.168.2.20";

    public static final int defaultPort = 56789;

    public Client() { cConnection = new Unconnected(); }

    public void connectToServer(String ip, int port) {
        cConnection = cConnection.connect(ip, port);
    }

    public void connectToServer() {
        cConnection = cConnection.connect(defaultIP, defaultPort);
    }

    public void closeConnection() {
        cConnection = cConnection.disconnect();
    }

}
