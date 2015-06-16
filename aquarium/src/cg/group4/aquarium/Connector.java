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
    protected Client cClient;
    protected Configuration cAquariumConfig;
    protected String cGroupId;
    protected Collection cCollection;
//    protected Subject subject;

    public Connector(String groupId) {
        cAquariumConfig = new Configuration();
//        subject = new Subject();
//        subject.addObserver(getCollectionObserver());
        connect();
        cCollection = new Collection(groupId);
        this.cGroupId = groupId;
    }

//    public Observer getCollectionObserver() {
//        return new Observer() {
//            @Override
//            public void update(Observable o, Object arg) {
//
//            }
//        };
//    }

    /**
     *
     */
    public void connect() {
        cClient = Client.getRemoteInstance();
        cClient.connectToServer(cAquariumConfig.getHost(), cAquariumConfig.getPort());
//        cClient.connectFromAquarium(cAquariumConfig.getHost(), cAquariumConfig.getPort());
        System.out.println("Connected?: " + cClient.isConnected());
    }

    /**
     *
     * @return
     */
    public void fetchCollectionFromServer() {

        Client client = Client.getRemoteInstance();

        Client.getRemoteInstance().getCollection(cGroupId, new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                System.out.println("Is reached!");
                System.out.println(response);

                if (response.isSuccess()) {
                    System.out.println("Is reached!2");
                    cCollection = (Collection) response.getData();
                }
            }

        });

    }

    public Collection getCollection() {
        return this.cCollection;
    }

}
