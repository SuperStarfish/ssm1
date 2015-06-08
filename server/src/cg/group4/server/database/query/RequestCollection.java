package cg.group4.server.database.query;

import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.CollectibleFactory;
import cg.group4.server.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    public RequestCollection(final String groupId) {
        cGroupId = groupId;
    }

    @Override
    public Collection query(final DatabaseConnection databaseConnection) throws SQLException {
        Collection collection = new Collection(cGroupId);
        Statement statement = databaseConnection.query();

        CollectibleFactory factory = new CollectibleFactory();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM Collectible WHERE "
                + "GroupId = '" + cGroupId + "'");

        while (resultSet.next()) {
            try {
                collection.add(factory.generateCollectible(
                        resultSet.getString("Type"),
                        resultSet.getFloat("Hue"),
                        resultSet.getInt("Amount"),
                        new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("Date")),
                        resultSet.getString("OwnerId")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        resultSet.close();
        statement.close();
        return collection;
    }
}
