package cg.group4.server.database.query;

import cg.group4.data_structures.collection.collectibles.Collectible;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gets the amount of a collectible on the server.
 */
public class GetCollectibleAmount extends Query {

    /**
     * The collectible.
     */
    protected Collectible cCollectible;
    /**
     * The group id.
     */
    protected String cGroupId;

    /**
     * Returns the amount of this collectible in the server.
     *
     * @param collectible The collectible to update.
     * @param groupId     The id of the group the collectible belongs to.
     */
    public GetCollectibleAmount(final Collectible collectible, final String groupId) {
        cCollectible = collectible;
        cGroupId = groupId;
    }

    @Override
    public Integer query(final Connection databaseConnection) throws SQLException {
        String preparedQuery = "SELECT * FROM Collectible WHERE OwnerId = ? AND Type = ? AND Hue = ? AND Date = ? "
                + "AND GroupId = ?";

        int result = 0;

        try(PreparedStatement statement = databaseConnection.prepareStatement(preparedQuery)) {
            statement.setString(1, cCollectible.getOwnerId());
            statement.setString(2, cCollectible.getClass().getSimpleName());
            statement.setFloat(3, cCollectible.getHue());
            statement.setString(4, cCollectible.getDateAsString());
            statement.setString(5, cGroupId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("Amount");
                }
            }
        }

        return result;
    }
}
