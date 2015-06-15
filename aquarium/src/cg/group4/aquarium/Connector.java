package cg.group4.aquarium;

import cg.group4.client.Client;
import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.subscribe.Subject;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;

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
        connect();
        updateSubject();
        this.cGroupId = groupId;
    }

    public void connect() {
        cClient = Client.getRemoteInstance();
        cClient.connectToServer(cAquariumConfig.getHost(), cAquariumConfig.getPort());
        cClient.createGroup("test", new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                System.out.println(response.toString());
            }
        });
    }

    public Collection updateSubject() {
        cClient.getCollection(cGroupId, new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                if (response.isSuccess()) {
                    cCollection = (Collection) response.getData();
                    System.out.println(cCollection.toString());
                }
            }
        });


        return cCollection;
    }

}
