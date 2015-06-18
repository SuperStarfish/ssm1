package cg.group4.aquarium;

import cg.group4.client.Client;
import cg.group4.data_structures.PlayerData;
import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.subscribe.Subject;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
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
     * Group cId of which the collection has to be displayed.
     */
    protected String cGroupId;

    /**
     * Collection used to store the fetched collection from the anonymous ResponseHandler.
     */
    protected Collection cCollection;

    /**
     * Subject with goal being the Observable for the Connector for the collection.
     */
    protected Subject cCollectionFromServerSubject;
    /**
     * Subject with goal being the Observable for the Connector for the members.
     */
    protected Subject cMembersFromServerSubject;
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
     * Executor thread for fixed schedule of updating the collection.
     */
    protected ScheduledExecutorService cCollectionUpdateExecutorService;

    /**
     * Initializes the connection.
     *
     * @param groupId cId of the group to display as aquarium.
     */
    public Connector(final String groupId, Configuration config) {
        cCollectionUpdateExecutorService = Executors.newSingleThreadScheduledExecutor();
        cAquariumConfig = config;

        cCollectionFromServerSubject = new Subject();
        cMembersFromServerSubject = new Subject();

        cCollection = new Collection(groupId);
        cGroupId = groupId;
    }

    /**
     * Connects the client to the server and adds an observer to it which schedules an collection fetch each interval.
     */
    public void connect() {
        cClient = Client.getInstance();
        cClient.connectToRemoteServer();
        cClient.getRemoteChangeSubject().addObserver(new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                final long initialDelay = 3;
                final long scheduledDelay = 3;

                cCollectionUpdateExecutorService.scheduleAtFixedRate(cFetcher, initialDelay, scheduledDelay,
                        TimeUnit.SECONDS);
            }
        });
    }

    /**
     * Fetches the collection from the server.
     */
    public void fetchCollectionFromServer() {
        cClient.getMembers(cGroupId, new ResponseHandler() {
            @Override
            public void handleResponse(final Response response) {
                if (response.isSuccess()) {
                    HashMap<String, String> idToUsername = new HashMap<String, String>();
                    for (PlayerData playerData : (ArrayList<PlayerData>) response.getData()) {
                        idToUsername.put(playerData.getId(), playerData.toString());
                    }
                    cMembersFromServerSubject.update(idToUsername);
                }
            }
        });
        cClient.getGroupCollection(cGroupId, new ResponseHandler() {
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
     * Observable of the collection.
     *
     * @return cCollectionFromServerSubject
     */
    public Subject getCollectionSubject() {
        return cCollectionFromServerSubject;
    }

    /**
     * Observable of the memebers.
     *
     * @return cMembersFromServerSubject.
     */
    public Subject getMembersSubject() {
        return cMembersFromServerSubject;
    }
}
