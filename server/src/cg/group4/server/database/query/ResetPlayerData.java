package cg.group4.server.database.query;

import cg.group4.server.database.DatabaseConnection;

import java.io.Serializable;
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
    public Serializable query(DatabaseConnection databaseConnection) throws SQLException {
        Statement statement = databaseConnection.query();
        statement.executeUpdate("DELETE * FROM Collectible WHERE OwnerId = '" + cId + "'");

        databaseConnection.commit();
        statement.close();

        return null;
    }
}
