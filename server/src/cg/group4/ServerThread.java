package cg.group4;

import cg.group4.client.query.CollectionWrapper;
import cg.group4.client.query.Update;
import cg.group4.client.query.UserData;
import cg.group4.client.query.Data;
import cg.group4.client.query.Reply;
import cg.group4.client.query.Request;
import cg.group4.database.DatabaseConnection;
import cg.group4.rewards.Collection;
import cg.group4.rewards.collectibles.Collectible;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * The ServerThread interacts with the Client.
 */
public final class ServerThread implements Callable<Void> {
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

    /**
     * The connection to the database. This can be a either in a connected state or no connection.
     */
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
    public Void call() throws Exception {
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
    protected void createStreams() throws IOException {
        cOutputStream = new ObjectOutputStream(cConnection.getOutputStream());
        cOutputStream.flush();
        cInputStream = new ObjectInputStream(cConnection.getInputStream());
    }

    /**
     * Closes the ObjectInput- and ObjectOutputStream as well as the connection with the Client.
     *
     * @throws IOException IOException
     */
    protected void cleanUp() throws IOException {
        String hostName = cConnection.getInetAddress().getHostName();
        cOutputStream.close();
        cInputStream.close();
        cConnection.close();
        LOGGER.info("Closed connection with: " + hostName);
    }

    /**
     * This method is used for incoming messages from the client.
     */
    protected void interactWithClient() {
        do {
            try {
                Object input = cInputStream.readObject();
                if (input instanceof Request) {
                    handleRequest((Request) input);
                } else if (input instanceof Update) {
                    handleUpdate((Update) input);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EOFException e) {
                LOGGER.severe("Lost connection with: " + cConnection.getInetAddress().getHostName());
                cKeepAlive = false;
            } catch (SocketException e) {
                LOGGER.info("Lost connection with: " + cConnection.getInetAddress().getHostName());
                cKeepAlive = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(cKeepAlive);
    }

    /**
     * Handles an incoming request. Delegates to the proper Data request.
     *
     * @param request The request that is made.
     */
    protected void handleRequest(final Request request) {
        if (request.getcData() instanceof UserData) {
            reply(getUserData((UserData) request.getcData()));
        }
    }

    /**
     * Handles an incoming update. Delegates to the proper Data update.
     *
     * @param update The update that is made.
     */
    protected void handleUpdate(final Update update) {
        Data data = update.getcData();
        if (data instanceof UserData) {
            updateUserData((UserData) data);
        } else if (data instanceof CollectionWrapper) {
            CollectionWrapper wrapper = (CollectionWrapper) data;
            updateCollection(wrapper.getcCollection(), wrapper.getcUserData().getcID());
        }
    }

    /**
     * Updates the collection for a user. Tries to see if Collectible already exists. In that case call update.
     * Otherwise call insert. Sends a reply if success or not.
     *
     * @param collection The new entries in the Collection.
     * @param userID The userID on which to add the Collectibles.
     */
    protected void updateCollection(final Collection collection, final String userID) {
        boolean success = true;
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Collectible collectible = (Collectible) iterator.next();

            int amount = getCollectible(collectible, userID);
            try {
                if (amount == -1) {
                    insertCollectible(collectible, userID);
                } else {
                    updateCollectible(collectible, userID, amount);
                }
            } catch (SQLException e) {
                success = false;
            }
        }

        try {
            cOutputStream.writeObject(new Reply(success));
            cOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new Collectible in the database.
     *
     * @param collectible The collectible to add.
     * @param userID The user to give the collectible to.
     * @throws SQLException If something went wrong with the database.
     */
    protected void insertCollectible(final Collectible collectible, final String userID) throws SQLException {
        cDatabaseConnection.connect();
        try {
            Statement statement = cDatabaseConnection.query();
            statement.executeUpdate("INSERT INTO Collectible (OwnerID, Type, WaveLength, Amount, Date, GroupID) "
                    + "VALUES ('" + userID + "', '" + collectible.getClass().getSimpleName() + "', "
                    + collectible.getcWavelength() + ", " + collectible.getAmount() + ", '"
                    + collectible.getDateAsString() + "', '" + userID + "')");
            cDatabaseConnection.commit();
        } catch (SQLException e) {
            throw e;
        }
        cDatabaseConnection.disconnect();
    }

    /**
     * Updates the amount of the collectible to the database.
     *
     * @param collectible The collectible to update.
     * @param userID The user to update the collectible for.
     * @param extra The amount already in the database.
     * @throws SQLException Something went wrong while updating.
     */
    protected void updateCollectible(final Collectible collectible, final String userID,
                                     final int extra) throws SQLException {

        cDatabaseConnection.connect();

        try {
            Statement statement = cDatabaseConnection.query();

            String rowToUpdate = "GroupId = '" + userID + "' AND Type = '" + collectible.getClass().getSimpleName()
                    + "'  AND WaveLength = " + collectible.getcWavelength();

            statement.executeUpdate("UPDATE Collectible SET Amount = " + (collectible.getAmount() + extra) + " WHERE "
                    + rowToUpdate);


            cDatabaseConnection.commit();
            statement.close();
        } catch (SQLException e) {
            throw e;
        }

        cDatabaseConnection.disconnect();
    }

    /**
     * Returns the amount of this collectible for that user. Returns -1 when not found.
     *
     * @param collectible The collectible to find.
     * @param ownerID The owner of the collectible.
     * @return The amount found.
     */
    protected int getCollectible(final Collectible collectible, final String ownerID) {
        cDatabaseConnection.connect();
        int result = -1;
        try {
            Statement statement = cDatabaseConnection.query();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Collectible WHERE "
                    + "GroupID = '" + ownerID + "' AND " + "Type = '" + collectible.getClass().getSimpleName() + "'"
                    + "AND WaveLength = '" + collectible.getcWavelength() + "'");

            if (resultSet.next()) {
                result = resultSet.getInt("Amount");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cDatabaseConnection.disconnect();
        return result;

    }

    /**
     * Updates the users data. Updates all values that are not null/0 (except ID).
     * @param data The data to update.
     */
    protected void updateUserData(final UserData data) {
        cDatabaseConnection.connect();
        boolean success = false;
        Statement stateMent = cDatabaseConnection.query();
        String update = "";

        if (data.getcUsername() != null) {
            update += ", Username = '" + data.getcUsername() + "'";
        }

        if (data.getcIntervalTimeStamp() != 0) {
            update += ", Interval = " + data.getcIntervalTimeStamp();
        }

        if (data.getcStrollTimeStamp() != 0) {
            update += ", Stroll = " + data.getcStrollTimeStamp();
        }

        if (!update.equals("")) {
            try {
                stateMent.executeUpdate("UPDATE USER SET" + update.substring(1) + " WHERE ID = '"
                        + data.getcID() + "'");
                cDatabaseConnection.commit();
                stateMent.close();
                success = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        reply(success);

        cDatabaseConnection.disconnect();
    }

    /**
     * Sends a reply with successful or not.
     *
     * @param isSuccess If it was successful or not.
     */
    protected void reply(final boolean isSuccess) {
        try {
            cOutputStream.writeObject(new Reply(isSuccess));
            cOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a reply that contains the data that is requested.
     *
     * @param data The data to reply to the client.
     */
    protected void reply(final Data data) {
        try {
            cOutputStream.writeObject(new Reply(data));
            cOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the userdata from the database and returns the same object back to the client.
     *
     * @param data The users data. Only ID is used.
     * @return Complete UserData with all info.
     */
    protected UserData getUserData(final UserData data) {
        cDatabaseConnection.connect();
        try {
            Statement stateMent = cDatabaseConnection.query();
            ResultSet resultSet = stateMent.executeQuery("SELECT * FROM USER WHERE ID = '" + data.getcID()
                    + "' LIMIT 1");

            while (resultSet.next()) {
                data.setcID(resultSet.getString("ID"));
                data.setcUsername(resultSet.getString("Username"));
                data.setcIntervalTimeStamp(resultSet.getInt("Interval"));
                data.setcStrollTimeStamp(resultSet.getInt("Stroll"));
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
