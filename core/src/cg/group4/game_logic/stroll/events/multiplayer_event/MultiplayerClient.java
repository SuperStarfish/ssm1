package cg.group4.game_logic.stroll.events.multiplayer_event;

import cg.group4.client.connection.Connection;
import cg.group4.client.connection.UnConnected;
import cg.group4.game_logic.stroll.events.StrollEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Client of a multi-player stroll.
 */
public class MultiplayerClient implements Host{
    protected Socket cSocket;
    protected ObjectInputStream cInputStream;
    protected ObjectOutputStream cOutputStream;

    public MultiplayerClient(String ip) throws IOException{
        cSocket = new Socket(ip, 55555);
        cInputStream = new ObjectInputStream(cSocket.getInputStream());
        cOutputStream = new ObjectOutputStream(cSocket.getOutputStream());
        cOutputStream.flush();
    }

    public void send(Serializable object) {
        try {
            cOutputStream.writeObject(object);
            cOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object receive() {
        try {
            return cInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isHost() {
        return false;
    }
}
