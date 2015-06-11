package cg.group4.game_logic.stroll.events.multiplayer_event;

import cg.group4.game_logic.stroll.events.StrollEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Host of a multi-player stroll.
 */
public abstract class MultiplayerHost extends StrollEvent {

    protected Socket cSocket;

    public MultiplayerHost() {
        try {
            cSocket = new ServerSocket(55555).accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
