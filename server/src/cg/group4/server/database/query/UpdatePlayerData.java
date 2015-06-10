package cg.group4.server.database.query;

import cg.group4.data_structures.PlayerData;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    /**
     * Connection to the database to run the query on.
     */
    protected Connection cDatabaseConnection;

    @Override
    public Serializable query(final Connection databaseConnection) throws SQLException {
        cDatabaseConnection = databaseConnection;

        if (cPlayerData.getUsername() != null) {
            updateData("Username", cPlayerData.getUsername());
        }

        if (cPlayerData.getIntervalTimeStamp() != 0) {
            updateData("Interval", cPlayerData.getIntervalTimeStamp());
        }

        if (cPlayerData.getStrollTimeStamp() != 0) {
            updateData("Stroll", cPlayerData.getStrollTimeStamp());
        }

        if (cPlayerData.getGroupId() != null) {
            updateData("GroupId", cPlayerData.getGroupId());
        }

        if (cPlayerData.getCollection() != null) {
            new AddCollection(cPlayerData.getCollection()).query(databaseConnection);
        }

        return null;
    }

    /**
     * Updates the players data.
     * @param column The column to update.
     * @param newValue The new value.
     * @throws SQLException Throws if something went wrong while updating.
     */
    protected void updateData(final String column, final String newValue) throws SQLException {
        try (PreparedStatement statement = cDatabaseConnection.prepareStatement("UPDATE USER SET " + column + " = ?")) {
            statement.setString(1, newValue);
            statement.executeUpdate();
        }
    }

    /**
     * Updates the players data.
     * @param column The column to update.
     * @param newValue The new value.
     * @throws SQLException Throws if something went wrong while updating.
     */
    protected void updateData(final String column, final long newValue) throws SQLException {
        try (PreparedStatement statement = cDatabaseConnection.prepareStatement("UPDATE USER SET " + column + " = ?")) {
            statement.setLong(1, newValue);
            statement.executeUpdate();
        }
    }
}
