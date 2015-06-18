package cg.group4.game_logic.stroll.events.multiplayer_event;

/**
 * Used to define behaviour when receiving messages over TCP or UDP between clients.
 */
public interface MessageHandler {
    /**
     * The action to take when messages are received.
     * @param message The Object in the message.
     */
    void handleMessage(Object message);
}
