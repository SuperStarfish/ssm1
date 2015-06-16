package cg.group4.game_logic.stroll.events.multiplayer_event;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client of a multi-player stroll.
 */
public class MultiplayerClient extends Host {

    protected String cIP;

    public MultiplayerClient(String ip) throws IOException {
        super();
        cIP = ip;
    }

    @Override
    protected Socket createSocket() {
        try {
            return new Socket(cIP, 56151);
        } catch (UnknownHostException e) {
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
