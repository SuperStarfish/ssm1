package cg.group4.server.database.query;

import cg.group4.data_structures.groups.Group;
import cg.group4.data_structures.groups.GroupData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public GetGroup(String groupId) {
        cGroupId = groupId;
    }

    @Override
    public Group query(Connection databaseConnection) throws SQLException {
        Group group = new Group(cGroupId);
        Statement statement = databaseConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM 'Group' INNER JOIN User ON 'Group'.OwnerId = User.Id "
                        + "WHERE 'Group'.Key = '" + cGroupId + "' LIMIT 1");

        if (resultSet.next()) {
            group.setGroupData(new GroupData(
                    resultSet.getInt("Key"),
                    resultSet.getString("Name"),
                    resultSet.getString("OwnerId"),
                    resultSet.getString("Username")
            ));
        }
        resultSet.close();
        statement.close();

        group.setCollection(new RequestCollection(cGroupId).query(databaseConnection));

        return group;
    }
}
