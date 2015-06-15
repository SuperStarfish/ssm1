package cg.group4.game_logic.stroll.events.mp_fishingboat;

import cg.group4.game_logic.stroll.events.multiplayer_event.Host;
import cg.group4.game_logic.stroll.events.multiplayer_event.MultiplayerClient;

public class FishingBoatClient extends FishingBoatEvent {

    protected MultiplayerClient cOtherClient;

    public FishingBoatClient(Host host) {
        cOtherClient = (MultiplayerClient) host;
    }
}
