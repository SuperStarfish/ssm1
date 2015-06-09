package cg.group4.server.database.query;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Query's the database of the server.
 */
public abstract class Query implements Serializable {

    /**
     * Query's the given database connection and returns the result that the server should return.
     * This Query object is made by the client, sent to the server and executed on the database.
     * The result of this query is sent back to the client.
     *
     * @param databaseConnection The database connection with which can be queried.
     * @return The result of this query.
     * @throws SQLException Something went wrong with the query.
     */
    public abstract Serializable query(Connection databaseConnection) throws SQLException;
}
