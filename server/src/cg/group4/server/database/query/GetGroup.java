package cg.group4.server.database.query;

import cg.group4.data_structures.groups.Group;
import cg.group4.server.database.DatabaseConnection;

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
    public Group query(DatabaseConnection databaseConnection) throws SQLException {
        Group group = new Group(cGroupId);
        Statement statement = databaseConnection.query();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Group WHERE Key = '" + cGroupId + "' LIMIT 1");

        if (resultSet.next()) {
            group.setOwnerId(resultSet.getString("OwnerId"));
            group.setName(resultSet.getString("Name"));

            resultSet.close();
            resultSet = statement.executeQuery("SELECT Username FROM User WHERE Id = '" + group.getOwnerId() + "'");

            if (resultSet.next()) {
                group.setOwnerName(resultSet.getString("Username"));
            }
        }
        resultSet.close();
        statement.close();

        group.setCollection(new RequestCollection(cGroupId).query(databaseConnection));

        return group;
    }
}
