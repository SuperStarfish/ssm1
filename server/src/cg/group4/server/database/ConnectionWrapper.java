package cg.group4.server.database;

import java.sql.Statement;

/**
 * Wrapper used for the 'Connected' states.
 *
 * @author Jurgen van Schagen
 */
public abstract class ConnectionWrapper {

    /**
     * Attempts to return a Connected Wrapper.
     *
     * @return A Connected State.
     */
    public abstract ConnectionWrapper openConnection();

    /**
     * Attempts to return a NoConnection Wrapper.
     *
     * @return A Connected State.
     */
    public abstract ConnectionWrapper closeConnection();

    /**
     * Commits changes to the database, since Autocommit = false.
     */
    public abstract void commit();

    /**
     * Returns a Statement on which queries can be executed.
     *
     * @return Statement for queries.
     */
    public abstract Statement query();
}
