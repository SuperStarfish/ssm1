package cg.group4.client;

import cg.group4.client.connection.Connection;
import cg.group4.client.connection.Unconnected;
import cg.group4.client.query.Data;
import cg.group4.client.query.UserData;
import cg.group4.rewards.Collection;
import cg.group4.util.timer.Timer;

/**
 * @author Jurgen van Schagen
 */
public class Client {
    protected static Client cInstance;

    public static Client getInstance() {
        if(cInstance == null) {
            cInstance = new Client();
        }
        return cInstance;
    }

    protected Connection cConnection;

    protected UserIDResolver cUserIDResolver;

    public static final String defaultIP = "192.168.2.20";

    public static final int defaultPort = 56789;

    public Client() { cConnection = new Unconnected(); }

    public void connectToServer(String ip, int port) {
        cConnection = cConnection.connect(ip, port);
    }

    public void connectToServer() {
        cConnection = cConnection.connect(defaultIP, defaultPort);
    }

    public void closeConnection() {
        cConnection = cConnection.disconnect();
    }

    public void updateTimers(long timeStamp) {
        UserData data = new UserData();
        data.setcID(cUserIDResolver.getID());
        data.setcIntervalTimeStamp(timeStamp + Timer.Global.INTERVAL.getDuration());
        data.setcStrollTimeStamp(timeStamp + Timer.Global.STROLL.getDuration());

        cConnection.updateUserData(data);
    }

    public void updateUsername(String username) {
        UserData data = new UserData();
        data.setcID(cUserIDResolver.getID());
        data.setcUsername(username);

        cConnection.updateUserData(data);
    }

    public boolean updateCollection(Collection collection) {
        boolean successful = cConnection.updateCollection(collection, new UserData(cUserIDResolver.getID()));
        System.out.println("Was it a succes?: " + successful);
        return successful;
    }

    public Data getUserData() {
        return cConnection.requestUserData(cUserIDResolver.getID());
    }

    public void setUserIDResolver(UserIDResolver idResolver) {
        cUserIDResolver = idResolver;
    }

}
