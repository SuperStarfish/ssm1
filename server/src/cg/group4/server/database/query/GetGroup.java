package cg.group4.server.database.query;

import cg.group4.data_structures.groups.Group;
import cg.group4.data_structures.groups.GroupData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves a group from the server based on the id.
 */
public class GetGroup extends Query {
    /**
     * The groups id.
     */
    protected final String cGroupId;

    /**
     * Constructs a group with the given group id.
     *
     * @param groupId The given group id.
     */
    public GetGroup(final String groupId) {
        cGroupId = groupId;
    }

    @Override
    public Group query(final Connection databaseConnection) throws SQLException {
        String preparedStatement = "SELECT * FROM 'Group' INNER JOIN User ON 'Group'.OwnerId = User.Id "
                + "WHERE 'Group'.Key = ? LIMIT 1";

        Group group = new Group(cGroupId);

        try (PreparedStatement statement = databaseConnection.prepareStatement(preparedStatement)) {
            statement.setString(1, cGroupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    group.setGroupData(new GroupData(
                            resultSet.getInt("Key"),
                            resultSet.getString("Name"),
                            resultSet.getString("OwnerId"),
                            resultSet.getString("Username")
                    ));
                }
            }
        }

        group.setCollection(new RequestCollection(cGroupId).query(databaseConnection));

        return group;
    }
}
