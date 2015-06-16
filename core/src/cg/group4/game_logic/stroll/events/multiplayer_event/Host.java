package cg.group4.game_logic.stroll.events.multiplayer_event;

import java.io.Serializable;

public interface Host {
    boolean isHost();
    void send(Serializable object);
    void receive(MessageHandler messageHandler, boolean continuous);
}
