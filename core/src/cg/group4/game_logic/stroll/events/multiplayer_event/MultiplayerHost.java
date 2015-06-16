package cg.group4.game_logic.stroll.events.multiplayer_event;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Host of a multi-player stroll.
 */
public class MultiplayerHost extends Host {

    @Override
    protected Socket createSocket() {
        try {
            return new ServerSocket(56151).accept();
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
