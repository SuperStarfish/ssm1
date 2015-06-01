package cg.group4.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Jurgen van Schagen
 */
public class Client extends Thread{
    protected Socket cConnection;

    protected final String defaultIP = "10.0.0.6";

    protected final int defaultPort = 56789;

    ObjectInputStream inputStream;

    ObjectOutputStream outputStream;

    public Client() { }

    @Override
    public void run() {
        connectToServer();
    }

    public void connectToServer() {
        cConnection = Gdx.net.newClientSocket(Net.Protocol.TCP, defaultIP, defaultPort, null);
        try {
            inputStream = new ObjectInputStream(cConnection.getInputStream());
            outputStream = new ObjectOutputStream(cConnection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            outputStream.close();
            inputStream.close();
            cConnection.dispose();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
