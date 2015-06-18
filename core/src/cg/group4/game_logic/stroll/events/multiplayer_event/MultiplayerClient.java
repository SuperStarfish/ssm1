package cg.group4.game_logic.stroll.events.multiplayer_event;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Client of a multi-player stroll.
 */
public class MultiplayerClient extends Host {
    /**
     * The IP to connect to.
     */
    protected String cIP;
    /**
     * The port to connect to.
     */
    protected final int cPortToUse = 56151;

    /**
     * Creates a new Client that connects to the given port.
     * @param ip The port to connect to.
     * @throws IOException When connection couldn't be established.
     */
    public MultiplayerClient(String ip) throws IOException {
        super();
        cIP = ip;
    }

    @Override
    protected Socket createSocket() {
        try {
            return new Socket(cIP, cPortToUse);
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
