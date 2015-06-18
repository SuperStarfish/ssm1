package cg.group4.server.database.query;

import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.CollectibleFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public Collection query(final Connection databaseConnection) throws SQLException {
        Collection collection = new Collection(cGroupId);

        String preparedQuery = "SELECT * FROM Collectible WHERE GroupId = ?";

        try (PreparedStatement statement = databaseConnection.prepareStatement(preparedQuery)) {
            statement.setString(1, cGroupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                CollectibleFactory factory = new CollectibleFactory();
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
            }
        }
        return collection;
    }
}
