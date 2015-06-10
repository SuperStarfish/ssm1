package cg.group4.server.database.query;

import cg.group4.data_structures.collection.collectibles.Collectible;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Adds a new collectible to the server.
 */
public class AddCollectible extends Query {

    /**
     * Id of the group the collectible belongs to.
     */
    protected final String cGroupId;
    /**
     * The collectible to add.
     */
    protected Collectible cCollectible;

    /**
     * Inserts a new Collectible in the database.
     *
     * @param collectible The collectible to update.
     * @param groupId     Id of the group the collectible belongs to.
     */
    protected AddCollectible(final Collectible collectible, final String groupId) {
        cCollectible = collectible;
        cGroupId = groupId;
    }

    @Override
    public Serializable query(final Connection databaseConnection) throws SQLException {
        String preparedQuery = "INSERT INTO Collectible (OwnerId, Type, Hue, Amount, Date, GroupId) VALUES " +
                "(?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = databaseConnection.prepareStatement(preparedQuery)) {
            statement.setString(1, cCollectible.getOwnerId());
            statement.setString(2, cCollectible.getClass().getSimpleName());
            statement.setFloat(3, cCollectible.getHue());
            statement.setInt(4, cCollectible.getAmount());
            statement.setString(5, cCollectible.getDateAsString());
            statement.setString(6, cGroupId);
            statement.executeUpdate();
        }

        return null;
    }
}
