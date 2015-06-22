package cg.group4.aquarium;

import cg.group4.client.Client;
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
     * Group cId of which the collection has to be displayed.
     */
    protected String cGroupId;
    /**
     * Subject with goal being the Observable for the Connector for the collection.
     */
    protected Subject cCollectionFromServerSubject;
    /**
     * Subject with goal being the Observable for the Connector for the group data.
     */
    protected Subject cGroupDataFromServerSubject;
    /**
     * Subject with goal being the Observable for the Connector for the members.
     */
    protected Subject cMembersFromServerSubject;
    /**
     * Runnable which calls the {#code fetchCollection()} method to fetch the collection from the server.
     * This happens at a fixed interval.
     */
    protected final Runnable cFetcher = new Runnable() {
        @Override
        public void run() {
            if(cGroupId != null) {
                fetchMembers();
                fetchCollection();
            }
        }
    };
    /**
     * Executor thread for fixed schedule of updating the collection.
     */
    protected ScheduledExecutorService cCollectionUpdateExecutorService;

    /**
     * Initializes the connection.
     */
    public Connector() {
        cCollectionUpdateExecutorService = Executors.newSingleThreadScheduledExecutor();

        cCollectionFromServerSubject = new Subject();
        cGroupDataFromServerSubject = new Subject();
        cMembersFromServerSubject = new Subject();

        final long delay = 3;
        cCollectionUpdateExecutorService.scheduleAtFixedRate(cFetcher, delay, delay, TimeUnit.SECONDS);


        Client.getInstance().getRemoteChangeSubject().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                fetchGroupData();
                cFetcher.run();
            }
        });

        connect();
    }

    /**
     * Connects the client to the server and adds an observer to it which schedules an collection fetch each interval.
     */
    public void connect() {
        Client.getInstance().connectToRemoteServer();
    }

    /**
     * Sets the group to retrieve.
     * @param groupId cId of the group to display as aquarium.
     */
    public void setGroupId(final String groupId){
        cGroupId = groupId;
        cFetcher.run();
    }

    /**
     * Fetches the members from the server.
     */
    public void fetchMembers() {
        Client.getInstance().getMembers(cGroupId, new ResponseHandler() {
            @Override
            public void handleResponse(final Response response) {
                if (response.isSuccess()) {
                    cMembersFromServerSubject.update(response.getData());
                }
            }
        });
    }

    /**
     * Fetches the group data from the server.
     */
    public void fetchGroupData() {
        Client.getInstance().getGroupData(new ResponseHandler() {
            @Override
            public void handleResponse(final Response response) {
                if (response.isSuccess()) {
                    cGroupDataFromServerSubject.update(response.getData());
                }
            }
        });
    }

    /**
     * Fetches the collection from the server.
     */
    public void fetchCollection() {
        Client.getInstance().getGroupCollection(cGroupId, new ResponseHandler() {
            @Override
            public void handleResponse(final Response response) {
                if (response.isSuccess()) {
                    cCollectionFromServerSubject.update(response.getData());
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
     * Observable of the collection.
     *
     * @return cCollectionFromServerSubject
     */
    public Subject getGroupDataSubject() {
        return cGroupDataFromServerSubject;
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
