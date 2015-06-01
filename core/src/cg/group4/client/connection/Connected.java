package cg.group4.client.connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Connected extends Thread implements Connection {
    protected Socket cConnection;

    protected ObjectInputStream inputStream;

    protected ObjectOutputStream outputStream;

    public Connected(String ip, int port) throws GdxRuntimeException {
        cConnection = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, port, null);
        try {
            outputStream = new ObjectOutputStream(cConnection.getOutputStream());
            inputStream = new ObjectInputStream(cConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }

    @Override
    public Connection connect(String ip, int port) {
        return this;
    }

    @Override
    public Connection disconnect() {
        try {
            outputStream.close();
            inputStream.close();
            cConnection.dispose();
        } catch (IOException e){
            e.printStackTrace();
        }
        return new Unconnected();
    }
}
