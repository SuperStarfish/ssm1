package cg.group4.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * In this state a database connection is successfully created.
 * Throws an exception if Connection could not be created in the constructor.
 * @author Jurgen van Schagen
 */
public class Connected extends ConnectionWrapper {
    /**
     * Default Java logging tool. Used for logging inside the class.
     */
    protected static final Logger LOGGER = Logger.getLogger(Connected.class.getName());

    /**
     * The Connection with the database.
     */
    protected Connection cConnection;

    /**
     * Attempts to establish a connection with the SQLite database.
     *
     * @throws ClassNotFoundException Thrown if sqlite.JDBC dependencies are missing.
     * @throws SQLException Thrown if connection could not be established.
     */
    public Connected() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        cConnection = DriverManager.getConnection("jdbc:sqlite:data.sqlite");
        cConnection.setAutoCommit(false);
        LOGGER.info("Succesfully connected to the database.");
    }

    @Override
    public void commit() {
        try {
            cConnection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement query() {
        try {
            return cConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public final ConnectionWrapper openConnection() {
        return this;
    }

    @Override
    public final ConnectionWrapper closeConnection() {
        try {
            cConnection.close();
            LOGGER.info("Successfully disconnected from the database.");
        } catch (SQLException e) {
            LOGGER.severe("Something went wrong while trying to disconnect! " + e.getMessage());
        }
        return new NoConnection();
    }
}
