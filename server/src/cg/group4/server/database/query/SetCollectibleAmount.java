package cg.group4.server.database.query;

import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.server.database.DatabaseConnection;

import java.io.Serializable;
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
    protected SetCollectibleAmount(Collectible collectible, String groupId, Integer amount) {
        cCollectible = collectible;
        cGroupId = groupId;
        cAmount = amount;
    }

    @Override
    public Serializable query(DatabaseConnection databaseConnection) throws SQLException {
        if (cAmount == 0) {
            new RemoveCollectible(cCollectible, cGroupId).query(databaseConnection);
        } else {
            Statement statement = databaseConnection.query();
            statement.executeUpdate("UPDATE Collectible SET Amount = " + cAmount
                    + " WHERE OwnerId = '" + cCollectible.getOwnerId() + "' AND " + "Type = '"
                    + cCollectible.getClass().getSimpleName() + "'" + "AND Hue = '" + cCollectible.getHue()
                    + "' AND Date = '" + cCollectible.getDateAsString() + "' AND GroupId = '" + cGroupId + "'");

            databaseConnection.commit();
            statement.close();
        }
        return null;
    }
}
