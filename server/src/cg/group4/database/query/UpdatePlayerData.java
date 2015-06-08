package cg.group4.database.query;

import cg.group4.PlayerData;
import cg.group4.database.DatabaseConnection;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Updates the player data on the server.
 */
public class UpdatePlayerData extends Query {

    protected final PlayerData cPlayerData;

    public UpdatePlayerData(PlayerData playerData) {
        cPlayerData = playerData;
    }

    @Override
    public Serializable query(DatabaseConnection databaseConnection) throws SQLException {
        String update = "";

        if (cPlayerData.getUsername() != null) {
            update += ", Username = '" + cPlayerData.getUsername() + "'";
        }

        if (cPlayerData.getIntervalTimeStamp() != 0) {
            update += ", Interval = " + cPlayerData.getIntervalTimeStamp();
        }

        if (cPlayerData.getStrollTimeStamp() != 0) {
            update += ", Stroll = " + cPlayerData.getStrollTimeStamp();
        }

        if (cPlayerData.getGroupId() != null) {
            update += ", GroupId = '" + cPlayerData.getGroupId() + "'";
        }

        if (cPlayerData.getCollection() != null) {
            new UpdateCollection(cPlayerData.getCollection()).query(databaseConnection);
        }

        Statement statement = databaseConnection.query();

        statement.executeUpdate("UPDATE USER SET " + update.substring(2) + " WHERE ID = '"
                + cPlayerData.getId() + "'");

        databaseConnection.commit();
        statement.close();

        return null;
    }
}
