package cg.group4.game_logic.stroll.events.multiplayer_event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Host of a multi-player stroll.
 */
public class MultiplayerHost implements Host{

    protected Socket cSocket;
    protected ObjectInputStream cInputStream;
    protected ObjectOutputStream cOutputStream;

    public MultiplayerHost() {
        try {
            cSocket = new ServerSocket(55555).accept();
            cInputStream = new ObjectInputStream(cSocket.getInputStream());
            cOutputStream = new ObjectOutputStream(cSocket.getOutputStream());
            cOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return true;
    }
}
