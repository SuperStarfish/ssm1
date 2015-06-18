package cg.group4.server.database.query;

import cg.group4.data_structures.PlayerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gets all the group data.
 */
public class GetMembers extends Query {

    /**
     * The groups id.
     */
    protected final String cGroupId;

    /**
     * Retrieves the members of the given group.
     *
     * @param groupId The given group id.
     */
    public GetMembers(final String groupId) {
        cGroupId = groupId;
    }

    @Override
    public ArrayList<PlayerData> query(final Connection databaseConnection) throws SQLException {
        ArrayList<PlayerData> list = new ArrayList<PlayerData>();
        String query = "SELECT Id,Username FROM User WHERE GroupId = ?";
        try (PreparedStatement statement = databaseConnection.prepareStatement(query)) {
            statement.setString(1, cGroupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PlayerData playerData = new PlayerData(resultSet.getString("Id"));
                    playerData.setUsername(resultSet.getString("Username"));
                    list.add(playerData);
                }
                resultSet.close();
            }
            statement.close();
        }
        return list;
    }
}
