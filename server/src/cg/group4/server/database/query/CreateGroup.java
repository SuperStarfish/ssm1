package cg.group4.server.database.query;

import cg.group4.data_structures.PlayerData;
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
    protected final String cOwnerId;

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
                "SELECT G.Key AS GroupId, G.Name AS Name, G.OwnerId As OwnerId, U.Username AS Username, U.Id "
                        + "FROM 'Group' G INNER JOIN User U ON G.OwnerId = U.Id "
                        + "WHERE G.Name = '" + cGroupName + "' AND G.OwnerId = '" + cOwnerId + "' LIMIT 1");

        int groupId = resultSet.getInt("GroupId");

        GroupData groupData = new GroupData(
                groupId,
                resultSet.getString("Name"),
                resultSet.getString("OwnerId"),
                resultSet.getString("Username")
        );

        resultSet.close();
        statement.close();

        Group group = new Group(Integer.toString(groupData.getGroupId()), groupData);
        PlayerData playerData = new PlayerData(cOwnerId);
        playerData.setUsername(null);

        playerData.setGroupId(Integer.toString(groupId));

        new UpdatePlayerData(playerData).query(databaseConnection);

        return group;
    }
}
