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
    public Serializable query(final Connection databaseConnection) throws SQLException {
        for (Collectible collectible : cCollection) {
            int amount = new GetCollectibleAmount(collectible, cCollection.getId()).query(databaseConnection);
            if (amount == 0) {
                new AddCollectible(collectible, cCollection.getId()).query(databaseConnection);
            } else {
                amount += collectible.getAmount();
                new SetCollectibleAmount(collectible, cCollection.getId(), amount).query(databaseConnection);
            }
        }
        return null;
    }
}
