package cg.group4.aquarium;

import cg.group4.client.Client;
import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.data_structures.subscribe.Subject;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;

import java.util.Observable;
import java.util.Observer;

/**
 * Observable connector with the database.
 */
public class Connector {
    Client cClient;
    Configuration cAquariumConfig;
    String cGroupId;
    Collection cCollection;
    Subject subject;

    public Connector(String groupId) {
        cAquariumConfig = new Configuration();
        subject = new Subject();
        subject.addObserver(getCollectionObserver());
        connect();
        this.cGroupId = groupId;
    }

    public Observer getCollectionObserver() {
        return new Observer() {
            @Override
            public void update(Observable o, Object arg) {

            }
        };
    }

    public void connect() {
        cClient = Client.getRemoteInstance();
        cClient.connectFromAquarium(cAquariumConfig.getHost(), cAquariumConfig.getPort());
        System.out.println("Connected?: " + cClient.isConnected());
    }

    public Collection getCollectionFromServer() {
        System.out.println("Opening collection request...");
        cClient.getCollection(cGroupId, new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                if (response.isSuccess()) {
                    cCollection = (Collection) response.getData();
                    cCollection.add(new Collectible(0.3f, "fin") {
                        @Override
                        public String getImagePath() {
                            return null;
                        }

                        @Override
                        public float getFormRarity() {
                            return 0;
                        }
                    });
                    System.out.println(cCollection.toString());
                } else {
                    System.out.println("NO CONNECTION RESPONSE");
                }
            }
        });


        return cCollection;
    }


}
