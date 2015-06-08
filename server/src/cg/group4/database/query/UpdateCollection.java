package cg.group4.database.query;

import cg.group4.collection.Collection;
import cg.group4.collection.collectibles.Collectible;
import cg.group4.database.DatabaseConnection;

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

    public UpdateCollection(Collection collection) {
        cCollection = collection;
    }

    @Override
    public Serializable query(DatabaseConnection databaseConnection) throws SQLException {
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
    protected void insertCollectible(DatabaseConnection databaseConnection, Collectible collectible)
            throws SQLException {
        Statement statement = databaseConnection.query();
        statement.executeUpdate("INSERT INTO Collectible (OwnerId, Type, Hue, Amount, Date, GroupId) VALUES ('" +
                collectible.getOwnerId() + "', '" + collectible.getClass().getSimpleName() + "', " +
                collectible.getHue() + ", " + collectible.getAmount() + ", '" + collectible.getDateAsString() +
                "', '" + cCollection.getId() + "')");

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
    protected void updateCollectible(DatabaseConnection databaseConnection, Collectible collectible, int amount)
            throws SQLException {
        Statement statement = databaseConnection.query();

        statement.executeUpdate("UPDATE Collectible SET Amount = " + (collectible.getAmount() + amount) +
                " WHERE OwnerId = '" + collectible.getOwnerId() + "' AND " + "Type = '" +
                collectible.getClass().getSimpleName() + "'" + "AND Hue = '" + collectible.getHue() +
                "' AND Date = '" + collectible.getDateAsString() + "' AND GroupId = '" + cCollection.getId() + "'");

        databaseConnection.commit();
        statement.close();
    }

    /**
     * Returns the amount of this collectible in the server.
     *
     * @param databaseConnection The database connection.
     * @param collectible        The collectible to update.
     * @return The amount of this collectible in the server.
     */
    protected int getAmount(DatabaseConnection databaseConnection, Collectible collectible) throws SQLException {
        Statement statement = databaseConnection.query();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Collectible" +
                " WHERE OwnerId = '" + collectible.getOwnerId() + "' AND " + "Type = '" +
                collectible.getClass().getSimpleName() + "'" + "AND Hue = '" + collectible.getHue() +
                "' AND Date = '" + collectible.getDateAsString() + "' AND GroupId = '" + cCollection.getId() + "'");

        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt("Amount");
        }
        resultSet.close();
        statement.close();
        return result;
    }
}
