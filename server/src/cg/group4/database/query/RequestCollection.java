package cg.group4.database.query;

import cg.group4.collection.Collection;
import cg.group4.collection.collectibles.CollectibleFactory;
import cg.group4.database.DatabaseConnection;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Request a collectible form the server.
 */
public class RequestCollection extends Query {
    /**
     * The group id of the collection to retrieve.
     */
    protected String cGroupId;

    /**
     * Requests a collection from the server with a specific group id.
     *
     * @param groupId The group id;
     */
    public RequestCollection(String groupId) {
        cGroupId = groupId;
    }

    @Override
    public Serializable query(DatabaseConnection databaseConnection) throws SQLException {
        Collection collection = new Collection(cGroupId);
        Statement statement = databaseConnection.query();

        CollectibleFactory factory = new CollectibleFactory();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM Collectible WHERE "
                + "GroupId = '" + cGroupId + "'");

        while (resultSet.next()) {
            collection.add(factory.generateCollectible(
                    resultSet.getString("Type"),
                    resultSet.getFloat("Hue"),
                    resultSet.getInt("Amount"),
                    resultSet.getDate("Date"),
                    resultSet.getString("OwnerId")));
        }

        resultSet.close();
        statement.close();
        return collection;
    }
}
