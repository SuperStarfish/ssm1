package cg.group4.database.query;

import cg.group4.Player;
import cg.group4.collection.Collection;
import cg.group4.database.DatabaseConnection;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Object that will retrieve user data from the server with the given id.
 */
public class RequestPlayer extends Query {

    /**
     * The id of the user data to be retrieved
     */
    protected String cId;

    /**
     * Constructor for the request.
     *
     * @param id The id of the player to be retrieved.
     */
    public RequestPlayer(String id) {
        cId = id;
    }

    @Override
    public Serializable query(DatabaseConnection databaseConnection) throws SQLException {
        Statement statement = databaseConnection.query();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM User WHERE ID = '" + cId + "' LIMIT 1");

        Player player = new Player(cId);
        if (resultSet.isBeforeFirst()) {
            resultSet.next();

            player.setId(resultSet.getString("ID"));
            player.setUsername(resultSet.getString("Username"));
            player.setIntervalTimeStamp(resultSet.getInt("Interval"));
            player.setStrollTimeStamp(resultSet.getInt("Stroll"));

        } else {
            statement.executeUpdate("INSERT INTO User (ID) VALUES ('"
                    + player.getId() + "')");
            databaseConnection.commit();
        }

        resultSet.close();
        statement.close();

        player.setCollection((Collection) new RequestCollection(cId).query(databaseConnection));

        return player;
    }
}
