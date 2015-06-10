package cg.group4.server.database.query;

import cg.group4.data_structures.collection.collectibles.Collectible;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Updates an given collectibles amount in the server.
 */
public class SetCollectibleAmount extends Query {

    /**
     * The collectible.
     */
    protected Collectible cCollectible;
    /**
     * The group id.
     */
    protected String cGroupId;
    /**
     * The new amount of the collectible.
     */
    protected Integer cAmount;

    /**
     * Updates the amount of the collectible to the database.
     *
     * @param collectible The collectible to update.
     * @param groupId     The group it belongs to.
     * @param amount      The amount already set in teh server.
     **/
    protected SetCollectibleAmount(final Collectible collectible, final String groupId, final Integer amount) {
        cCollectible = collectible;
        cGroupId = groupId;
        cAmount = amount;
    }

    @Override
    public Serializable query(final Connection databaseConnection) throws SQLException {
        if (cAmount == 0) {
            new RemoveCollectible(cCollectible, cGroupId).query(databaseConnection);
        } else {
            String query = "UPDATE Collectible SET Amount = " + cAmount
                    + " WHERE OwnerId = '" + cCollectible.getOwnerId() + "' AND " + "Type = '"
                    + cCollectible.getClass().getSimpleName() + "'" + "AND Hue = '" + cCollectible.getHue()
                    + "' AND Date = '" + cCollectible.getDateAsString() + "' AND GroupId = '" + cGroupId + "'";

            try (Statement statement = databaseConnection.createStatement()) {
                statement.executeUpdate(query);
            }
        }
        return null;
    }
}
