package cg.group4.server.database.query;

import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.server.database.DatabaseConnection;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Updates the collection data on the server.
 */
public class UpdateCollection extends Query {

    /**
     * The collection with the changes to be made.
     */
    protected Collection cCollection;

    /**
     * Constructs a new query to update the given collection in the server.
     *
     * @param collection The collection containing the changes to be made in the database.
     */
    public UpdateCollection(final Collection collection) {
        cCollection = collection;
    }

    @Override
    public Serializable query(final DatabaseConnection databaseConnection) throws SQLException {
        for (Collectible collectible : cCollection) {
            int amount = getAmount(databaseConnection, collectible);
            if (amount == 0) {
                insertCollectible(databaseConnection, collectible);
            } else {
                updateCollectible(databaseConnection, collectible, amount);
            }
        }
        return null;
    }

    /**
     * Inserts a new Collectible in the database.
     *
     * @param databaseConnection The database connection.
     * @param collectible        The collectible to update.
     * @throws SQLException Something went wrong while inserting.
     */
    protected void insertCollectible(final DatabaseConnection databaseConnection, final Collectible collectible)
            throws SQLException {
        Statement statement = databaseConnection.query();
        statement.executeUpdate("INSERT INTO Collectible (OwnerId, Type, Hue, Amount, Date, GroupId) VALUES ('"
                + collectible.getOwnerId() + "', '" + collectible.getClass().getSimpleName() + "', "
                + collectible.getHue() + ", " + collectible.getAmount() + ", '" + collectible.getDateAsString()
                + "', '" + cCollection.getId() + "')");

        databaseConnection.commit();
        statement.close();
    }

    /**
     * Updates the amount of the collectible to the database.
     *
     * @param databaseConnection The database connection.
     * @param collectible        The collectible to update.
     * @param amount             The amount already set in teh server.
     * @throws SQLException Something went wrong while updating.
     */
    protected void updateCollectible(final DatabaseConnection databaseConnection,
                                     final Collectible collectible, final int amount) throws SQLException {
        Statement statement = databaseConnection.query();

        statement.executeUpdate("UPDATE Collectible SET Amount = " + (collectible.getAmount() + amount)
                + " WHERE OwnerId = '" + collectible.getOwnerId() + "' AND " + "Type = '"
                + collectible.getClass().getSimpleName() + "'" + "AND Hue = '" + collectible.getHue()
                + "' AND Date = '" + collectible.getDateAsString() + "' AND GroupId = '" + cCollection.getId() + "'");

        databaseConnection.commit();
        statement.close();
    }

    /**
     * Returns the amount of this collectible in the server.
     *
     * @param databaseConnection The database connection.
     * @param collectible        The collectible to update.
     * @return The amount of this collectible in the server.
     * @throws SQLException Something went wrong while retrieving the amount.
     */
    protected int getAmount(final DatabaseConnection databaseConnection, final Collectible collectible)
            throws SQLException {
        Statement statement = databaseConnection.query();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Collectible"
                + " WHERE OwnerId = '" + collectible.getOwnerId() + "' AND " + "Type = '"
                + collectible.getClass().getSimpleName() + "'" + "AND Hue = '" + collectible.getHue()
                + "' AND Date = '" + collectible.getDateAsString() + "' AND GroupId = '" + cCollection.getId() + "'");

        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt("Amount");
        }
        resultSet.close();
        statement.close();
        return result;
    }
}
