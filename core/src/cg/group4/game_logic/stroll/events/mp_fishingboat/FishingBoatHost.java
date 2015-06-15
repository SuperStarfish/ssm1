package cg.group4.game_logic.stroll.events.mp_fishingboat;

import cg.group4.game_logic.stroll.events.multiplayer_event.Host;
import cg.group4.game_logic.stroll.events.multiplayer_event.MultiplayerHost;

public class FishingBoatHost extends FishingBoatEvent {

    protected MultiplayerHost cOtherClient;

    public FishingBoatHost(Host host) {
        cOtherClient = (MultiplayerHost) host;
    }
}
