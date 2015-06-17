package cg.group4.aquarium;

import cg.group4.client.Client;
import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.subscribe.Subject;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Observable connector with the database.
 */
public class Connector {

    /**
     * Client used to connect with the server.
     */
    protected Client cClient;

    /**
     * Configuration used to connect to the server.
     */
    protected Configuration cAquariumConfig;

    /**
     * Group id of which the collection has to be displayed.
     */
    protected String cGroupId;

    /**
     * Collection used to store the fetched collection from the anonymous ResponseHandler.
     */
    protected Collection cCollection;

    /**
     * Subject with goal being the Observable for the Connector.
     */
    protected Subject cCollectionFromServerSubject;

    /**
     * Executor thread for fixed schedule of updating the collection.
     */
    protected ScheduledExecutorService cCollectionUpdateExecutorService;

    /**
     * Refreshes the status of the connection.
     */
    protected ScheduledExecutorService cConnectionStatusExecutorService;

    /**
     * Runnable which calls the {#code fetchCollectionFromServer()} method to fetch the collection from the server.
     * This happens at a fixed interval.
     */
    protected final Runnable cFetcher = new Runnable() {
        @Override
        public void run() {
            fetchCollectionFromServer();
        }
    };

    /**
     * Initializes the connection.
     * @param groupId id of the group to display as aquarium.
     */
    public Connector(final String groupId) {
        cCollectionUpdateExecutorService = Executors.newSingleThreadScheduledExecutor();


        cCollectionFromServerSubject = new Subject();

        cAquariumConfig = new Configuration();
        connect();
        cCollection = new Collection(groupId);
        this.cGroupId = groupId;
    }

    /**
     * Connects the client to the server and adds an observer to it which schedules an collection fetch each interval.
     */
    public void connect() {
        cClient = Client.getRemoteInstance();
        cClient.connectToServer(cAquariumConfig.getHost(), cAquariumConfig.getPort());
        cClient.getRemoteChangeSubject().addObserver(new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                final long initialDelay = 3;
                final long scheduledDelay = 3;

                cCollectionUpdateExecutorService.scheduleAtFixedRate(cFetcher, initialDelay, scheduledDelay, TimeUnit.SECONDS);
            }
        });
    }

    /**
     * Fetches the collection from the server.
     */
    public void fetchCollectionFromServer() {
        cClient.getCollection(cGroupId, new ResponseHandler() {
            @Override
            public void handleResponse(final Response response) {
                if (response.isSuccess()) {
                    cCollection = (Collection) response.getData();
                    cCollectionFromServerSubject.update(cCollection);
                }
            }
        });
    }


    /**
     * Observable of the Connector.
     * @return cCollectionFromServerSubject
     */
    public Subject getCollectionSubject() {
        return cCollectionFromServerSubject;
    }
}
