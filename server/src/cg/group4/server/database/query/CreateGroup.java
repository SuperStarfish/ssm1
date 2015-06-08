package cg.group4.server.database.query;

import cg.group4.data_structures.groups.Group;
import cg.group4.data_structures.groups.GroupData;
import cg.group4.server.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class CreateGroup extends Query {

    protected final String cGroupName;
    private final String cOwnerId;

    public CreateGroup(String groupName, String ownerId) {
        cGroupName = groupName;
        cOwnerId = ownerId;
    }


    @Override
    public Group query(DatabaseConnection databaseConnection) throws SQLException {
        Statement statement = databaseConnection.query();
        statement.executeUpdate("INSERT INTO 'Group' (OwnerId,Name) VALUES ('"
                + cOwnerId + "', '" + cGroupName + "')");
        databaseConnection.commit();

        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM 'Group' INNER JOIN User ON 'Group'.OwnerId = User.Id "
                        + "WHERE 'Group'.Name = '" + cGroupName + "' AND 'Group'.OwnerId = '" + cOwnerId + "' LIMIT 1");


        GroupData groupData = new GroupData(
                resultSet.getInt("'Group'.Key"),
                resultSet.getString("'Group'.Name"),
                resultSet.getString("'Group'.OwnerId"),
                resultSet.getString("User.Username")
        );
        Group group = new Group(Integer.toString(groupData.getGroupId()), groupData);

        resultSet.close();
        statement.close();
        return group;
    }
}
