package cg.group4;

import cg.group4.client.query.Update;
import cg.group4.client.query.UserData;
import cg.group4.client.query.Data;
import cg.group4.client.query.Reply;
import cg.group4.client.query.Request;
import cg.group4.database.DatabaseConnection;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * The ServerThread interacts with the Client.
 */
public class ServerThread implements Callable<Void> {
    /**
     * Default java logging functionality.
     */
    protected static final Logger LOGGER = Logger.getLogger(ServerThread.class.getName());

    /**
     * The connection with the Client.
     */
    protected Socket cConnection;

    /**
     * The ObjectOutputStream for outgoing messages.
     */
    protected ObjectOutputStream cOutputStream;

    /**
     * The ObjectInputStream for incoming messages.
     */
    protected ObjectInputStream cInputStream;

    /**
     * Determines if the connection needs to be kept alive.
     */
    protected boolean cKeepAlive = true;

    protected DatabaseConnection cDatabaseConnection;

    /**
     * Creates a new ServerThread for communication with the server and the client.
     *
     * @param connection The connection with the Client.
     */
    public ServerThread(final Socket connection) {
        cConnection = connection;
        cDatabaseConnection = new DatabaseConnection();
        LOGGER.info("Established a connection with: " + cConnection.getInetAddress().getHostName());
    }

    @Override
    public final Void call() throws Exception {
        try {
            createStreams();
            interactWithClient();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                cleanUp();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Creates the ObjectInput- and ObjectOutputStreams.
     *
     * @throws IOException IOException
     */
    protected final void createStreams() throws IOException {
        cOutputStream = new ObjectOutputStream(cConnection.getOutputStream());
        cOutputStream.flush();
        cInputStream = new ObjectInputStream(cConnection.getInputStream());
    }

    /**
     * Closes the ObjectInput- and ObjectOutputStream as well as the connection with the Client.
     *
     * @throws IOException IOException
     */
    protected final void cleanUp() throws IOException {
        String hostName = cConnection.getInetAddress().getHostName();
        cOutputStream.close();
        cInputStream.close();
        cConnection.close();
        LOGGER.info("Closed connection with: " + hostName);
    }

    /**
     * This method is used for incoming messages from the client.
     */
    protected final void interactWithClient() {
        do {
            try {
                Object input = cInputStream.readObject();
                if(input instanceof Request) {
                    handleRequest((Request) input);
                } else if(input instanceof Update) {
                    handleUpdate((Update) input);
                } else {
                    Class trueClass = input.getClass();
                    handleRequest(trueClass.cast(input));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EOFException e){
                LOGGER.severe("Lost connection with: " + cConnection.getInetAddress().getHostName());
                cKeepAlive = false;
            } catch (SocketException e){
                LOGGER.info("Lost connection with: " + cConnection.getInetAddress().getHostName());
                cKeepAlive = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(cKeepAlive);
    }

    protected  void handleRequest(Object request) {
        LOGGER.severe("DID NOT GO AS EXPECTED!");
        System.out.println(request.getClass().toString());
    }

    protected void handleRequest(Request request) {
        if(request.getcData() instanceof UserData) {
            reply(getUserData((UserData)request.getcData()));
        }
    }

    protected void handleUpdate(Update update) {
        if(update.getcData() instanceof UserData) {
            updateUserData((UserData) update.getcData());
        }
    }

    protected void updateUserData(UserData data) {
        cDatabaseConnection.connect();
        Statement stateMent = cDatabaseConnection.query();
        String update = "";

        if (data.username != null) {
            update += ", Username = '" + data.username + "'";
        }

        if (data.intervalTimeStamp != 0) {
            update += ", Interval = " + data.intervalTimeStamp;
        }

        if (data.strollTimeStamp != 0) {
            update += ", Stroll = " + data.strollTimeStamp;
        }

        if(!update.equals("")) {
            try {
                stateMent.executeUpdate("UPDATE USER SET" + update.substring(1) + " WHERE ID = '" + data.ID + "'");
                cDatabaseConnection.commit();
                stateMent.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        cDatabaseConnection.disconnect();
    }

    protected void reply(Data data) {
        try {
            cOutputStream.writeObject(new Reply(data));
            cOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected UserData getUserData(UserData data) {
        cDatabaseConnection.connect();
        try {
            Statement stateMent = cDatabaseConnection.query();
            ResultSet resultSet = stateMent.executeQuery("SELECT * FROM USER WHERE ID = '"+ data.ID +"' LIMIT 1");
            while (resultSet.next()) {
                data.ID = resultSet.getString("ID");
                data.username = resultSet.getString("Username");
                data.intervalTimeStamp = resultSet.getInt("Interval");
                data.strollTimeStamp = resultSet.getInt("Stroll");
            }
            resultSet.close();
            stateMent.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cDatabaseConnection.disconnect();
        return data;
    }

}
