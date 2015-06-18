package cg.group4.server.database.query;

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
    public ArrayList<String> query(final Connection databaseConnection) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        String query = "SELECT Username FROM User WHERE GroupId = ?";
        try (PreparedStatement statement = databaseConnection.prepareStatement(query)) {
            statement.setString(1, cGroupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("Username");
                    if (username == null) {
                        username = "null";
                    }
                    list.add(username);
                }
                resultSet.close();
            }
            statement.close();
        }
        return list;
    }
}
