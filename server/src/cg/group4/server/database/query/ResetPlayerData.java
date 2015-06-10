package cg.group4.server.database.query;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Resets the data of a player on the remote server.
 */
public class ResetPlayerData extends Query {

    /**
     * The player ID to reset.
     */
    protected String cId;

    /**
     * Creates a new ResetPlayerData Query using the ID of that player.
     * @param id The ID of the Player.
     */
    public ResetPlayerData(final String id) {
        cId = id;
    }

    @Override
    public Serializable query(final Connection databaseConnection) throws SQLException {
        String preparedStatement = "DELETE FROM Collectible WHERE GroupId = ?";
        try (PreparedStatement statement = databaseConnection.prepareStatement(preparedStatement)) {
            statement.setString(1, cId);
            statement.executeUpdate();
        }

        return null;
    }
}
