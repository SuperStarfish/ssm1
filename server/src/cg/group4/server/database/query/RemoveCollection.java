package cg.group4.server.database.query;

import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.server.database.DatabaseConnection;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * Removes the collection data from the server.
 */
public class RemoveCollection extends Query {

    /**
     * The collection with the changes to be made.
     */
    protected Collection cCollection;

    /**
     * Constructs a new query to update the given collection in the server.
     *
     * @param collection The collection containing the changes to be made in the database.
     */
    public RemoveCollection(final Collection collection) {
        cCollection = collection;
    }

    @Override
    public Serializable query(final DatabaseConnection databaseConnection) throws SQLException {
        for (Collectible collectible : cCollection) {
            int amount = new GetCollectibleAmount(collectible, cCollection.getId()).query(databaseConnection);
            new SetCollectibleAmount(collectible, cCollection.getId(), amount - collectible.getAmount())
                    .query(databaseConnection);
        }
        return null;
    }
}
