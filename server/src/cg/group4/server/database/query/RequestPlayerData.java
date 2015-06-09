package cg.group4.server.database.query;

import cg.group4.data_structures.PlayerData;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Object that will retrieve user data from the server with the given id.
 */
public class RequestPlayerData extends Query {

    /**
     * The id of the user data to be retrieved.
     */
    protected String cId;

    /**
     * Constructor for the request.
     *
     * @param id The id of the player to be retrieved.
     */
    public RequestPlayerData(final String id) {
        cId = id;
    }

    @Override
    public Serializable query(final Connection databaseConnection) throws SQLException {
        Statement statement = databaseConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM User WHERE ID = '" + cId + "' LIMIT 1");

        PlayerData playerData = new PlayerData(cId);

        if (resultSet.isBeforeFirst()) {
            resultSet.next();

            playerData.setUsername(resultSet.getString("Username"));
            playerData.setIntervalTimeStamp(resultSet.getInt("Interval"));
            playerData.setStrollTimeStamp(resultSet.getInt("Stroll"));
            playerData.setGroupId(resultSet.getString("GroupId"));

        } else {
            statement.executeUpdate("INSERT INTO User (ID,Username) VALUES ('"
                    + playerData.getId() + "', '" + playerData.getUsername() + "')");
        }

        resultSet.close();
        statement.close();

        playerData.setCollection(new RequestCollection(cId).query(databaseConnection));

        return playerData;
    }
}
