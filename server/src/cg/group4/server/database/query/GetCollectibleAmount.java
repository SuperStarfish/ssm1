package cg.group4.server.database.query;

import cg.group4.data_structures.collection.collectibles.Collectible;

import java.sql.Connection;
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
    public GetCollectibleAmount(Collectible collectible, String groupId) {
        cCollectible = collectible;
        cGroupId = groupId;
    }

    @Override
    public Integer query(Connection databaseConnection) throws SQLException {
        Statement statement = databaseConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Collectible"
                + " WHERE OwnerId = '" + cCollectible.getOwnerId() + "' AND " + "Type = '"
                + cCollectible.getClass().getSimpleName() + "'" + "AND Hue = '" + cCollectible.getHue()
                + "' AND Date = '" + cCollectible.getDateAsString() + "' AND GroupId = '" + cGroupId + "'");

        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt("Amount");
        }
        resultSet.close();
        statement.close();
        return result;
    }
}
