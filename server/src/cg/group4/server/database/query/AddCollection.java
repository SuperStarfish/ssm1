package cg.group4.server.database.query;

import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.Collectible;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Adds the collection data to the server.
 */
public class AddCollection extends Query {

    /**
     * The collection with the changes to be made.
     */
    protected Collection cCollection;

    /**
     * Constructs a new query to update the given collection in the server.
     *
     * @param collection The collection containing the changes to be made in the database.
     */
    public AddCollection(final Collection collection) {
        cCollection = collection;
    }

    @Override
    public Serializable query(final Connection connection) throws SQLException {
        for (Collectible collectible : cCollection) {
            new AddCollectible(collectible, cCollection.getId()).query(connection);
        }
        return null;
    }
}
