package cg.group4.aquarium;

import cg.group4.client.Client;
import cg.group4.data_structures.subscribe.Subject;

import java.util.Observer;

/**
 *
 */
public class AquariumConnector extends Subject {

    Client client;

    /**
     *
     */
    public AquariumConnector() {
        client = Client.getRemoteInstance();
        client.connectToServer();
    }


}
