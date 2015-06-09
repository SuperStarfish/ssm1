package cg.group4.server.database.query;

import cg.group4.data_structures.collection.collectibles.Collectible;

import java.io.Serializable;
import java.sql.Connection;
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
    protected RemoveCollectible(Collectible collectible, String groupId) {
        cCollectible = collectible;
        cGroupId = groupId;
    }

    @Override
    public Serializable query(Connection databaseConnection) throws SQLException {
        Statement statement = databaseConnection.createStatement();
        statement.executeUpdate("DELETE FROM Collectible"
                + " WHERE OwnerId = '" + cCollectible.getOwnerId() + "' AND " + "Type = '"
                + cCollectible.getClass().getSimpleName() + "'" + "AND Hue = '" + cCollectible.getHue()
                + "' AND Date = '" + cCollectible.getDateAsString() + "' AND GroupId = '" + cGroupId + "'");

        databaseConnection.commit();
        statement.close();
        return null;
    }
}
