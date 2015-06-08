package cg.group4.server.database;

import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles database connection. Uses a ConnectionWrapper state for proper behaviour.
 *
 * @author Jurgen van Schagen
 */
public final class DatabaseConnection {
    /**
     * Default Java logging tool. Used for logging inside the class.
     */
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    /**
     * The ConnectionWrapper on which methods are called.
     */
    protected ConnectionWrapper cConnection;

    /**
     * Sets the log level and creates a NoConnection wrapper.
     */
    public DatabaseConnection() {
        LOGGER.setLevel(Level.ALL);
        cConnection = new NoConnection();
    }

    /**
     * Asks the ConnectionWrapper to return the proper Wrapper for opening the connection.
     */
    public void connect() {
        cConnection = cConnection.openConnection();
    }

    /**
     * Commits changes to the database.
     */
    public void commit() {
        cConnection.commit();
    }

    /**
     * Asks the ConnectionWrapper to return the proper Wrapper for closing the connection.
     */
    public void disconnect() {
        cConnection = cConnection.closeConnection();
    }

    /**
     * Returns a Statement on which queries can be executed.
     *
     * @return Statement for queries.
     */
    public Statement query() {
        return cConnection.query();
    }

}
