package cg.group4.database;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @author Jurgen van Schagen
 */
public class NoConnection extends ConnectionWrapper {
    /**
     * Default Java logging tool. Used for logging inside the class.
     */
    protected static final Logger LOGGER = Logger.getLogger(NoConnection.class.getName());

    @Override
    public final ConnectionWrapper openConnection() {
        try {
            return new Connected();
        } catch (ClassNotFoundException e) {
            LOGGER.severe("SQLite.JDBC dependency missing! Cannot establish connection to the database.");
        } catch (SQLException e) {
            LOGGER.severe("Could not establish connection to the database!");
        }
        return this;
    }

    @Override
    public final ConnectionWrapper closeConnection() {
        return this;
    }
}
