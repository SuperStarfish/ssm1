package cg.group4.server.database.query;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Resets the data of a player on the remote server.
 */
public class ResetPlayerData extends Query {

    /**
     *
     */
    protected String cId;

    public ResetPlayerData(final String id) {
        cId = id;
    }

    @Override
    public Serializable query(Connection databaseConnection) throws SQLException {
        Statement statement = databaseConnection.createStatement();
        statement.executeUpdate("DELETE FROM Collectible WHERE GroupId = '" + cId + "'");

        statement.executeUpdate("DELETE FROM User WHERE Id = '" + cId + "'");

        statement.close();

        return null;
    }
}
