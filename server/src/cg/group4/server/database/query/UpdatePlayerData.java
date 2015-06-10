package cg.group4.server.database.query;

import cg.group4.data_structures.PlayerData;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Updates the player data on the server.
 */
public class UpdatePlayerData extends Query {

    /**
     * Player data containing he changes to be made to the server. The players id should be the same.
     */
    protected final PlayerData cPlayerData;

    /**
     * Constructs a new query based on the player data to be changed.
     *
     * @param playerData The changes.
     */
    public UpdatePlayerData(final PlayerData playerData) {
        cPlayerData = playerData;
    }

    @Override
    public Serializable query(final Connection databaseConnection) throws SQLException {
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
            new AddCollection(cPlayerData.getCollection()).query(databaseConnection);
        }

        String query = "UPDATE USER SET " + update.substring(2) + " WHERE ID = '"
                + cPlayerData.getId() + "'";

        try (Statement statement = databaseConnection.createStatement()){
            statement.executeUpdate(query);
        }

        return null;
    }
}
