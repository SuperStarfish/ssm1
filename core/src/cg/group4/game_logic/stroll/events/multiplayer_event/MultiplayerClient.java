package cg.group4.game_logic.stroll.events.multiplayer_event;

import cg.group4.client.connection.Connection;
import cg.group4.client.connection.UnConnected;
import cg.group4.game_logic.stroll.events.StrollEvent;

/**
 * Client of a multi-player stroll.
 */
public abstract class MultiplayerClient extends StrollEvent {

    protected Connection cConnection;

    public MultiplayerClient(String ip) {
        cConnection = new UnConnected();
        cConnection.connect(ip, 55555);
    }
}
