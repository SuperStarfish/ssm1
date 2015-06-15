package cg.group4.server.database.query;

import cg.group4.data_structures.PlayerData;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that will retrieve user data from the server with the given id.
 */
public class RequestPlayerData extends Query {

    /**
     * The PlayerData constructed from given id. Will be filled and returned.
     */
    protected PlayerData cPlayerData;

    /**
     * Constructor for the request.
     *
     * @param id The id of the player to be retrieved.
     */
    public RequestPlayerData(final String id) {
        cPlayerData = new PlayerData(id);
    }

    @Override
    public Serializable query(final Connection databaseConnection) throws SQLException {

        String preparedQuery = "SELECT * FROM 'User' WHERE ID = ? LIMIT 1";

        try (PreparedStatement statement = databaseConnection.prepareStatement(preparedQuery)) {
            statement.setString(1, cPlayerData.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cPlayerData.setUsername(resultSet.getString("Username"));
                    cPlayerData.setIntervalTimeStamp(resultSet.getLong("Interval"));
                    cPlayerData.setStrollTimeStamp(resultSet.getLong("Stroll"));
                    cPlayerData.setGroupId(resultSet.getString("GroupId"));
                    
                } else {
                    insertUser(databaseConnection);
                }
            }
        }

        cPlayerData.setCollection(new RequestCollection(cPlayerData.getId()).query(databaseConnection));

        return cPlayerData;
    }

    /**
     * Inserts the user in the database.
     * @param databaseConnection Connection with the database.
     * @throws SQLException If something went wrong with the insertion.
     */
    protected void insertUser(final Connection databaseConnection) throws SQLException {
        String preparedStatement = "INSERT INTO User (ID,Username) VALUES (?, ?)";
        try (PreparedStatement statement = databaseConnection.prepareStatement(preparedStatement)) {
            statement.setString(1, cPlayerData.getId());
            statement.setString(2, "New User");
            statement.executeUpdate();
        }
    }
}
