package cg.group4.server.database.query;

import cg.group4.data_structures.collection.collectibles.Collectible;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Removes the given collectible from the server.
 */
public class RemoveCollectible extends Query {

    /**
     * The group of the collectible.
     */
    protected final String cGroupId;
    /**
     * The collectible to remove.
     */
    protected final Collectible cCollectible;

    /**
     * Removes the given collectible from the server.
     *
     * @param collectible The collectible to remove.
     * @param groupId     The group of the collectible.
     */
    protected RemoveCollectible(final Collectible collectible, final String groupId) {
        cCollectible = collectible;
        cGroupId = groupId;
    }

    @Override
    public Serializable query(final Connection databaseConnection) throws SQLException {

        String preparedQuery = "DELETE FROM Collectible WHERE OwnerId = ? AND Type = ? AND Hue = ? AND "
                + "Date = ? AND GroupId = ?";

        try (PreparedStatement statement = databaseConnection.prepareStatement(preparedQuery)) {
            statement.setString(1, cCollectible.getOwnerId());
            statement.setString(2, cCollectible.getClass().getSimpleName());
            statement.setFloat(3, cCollectible.getHue());
            statement.setString(4, cCollectible.getDateAsString());
            statement.setString(5, cGroupId);
            statement.executeUpdate();
        }

        return null;
    }
}
