package cg.group4.server.database.query;

import cg.group4.data_structures.groups.GroupData;
import cg.group4.server.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Gets all the group data.
 */
public class GetGroupData extends Query {

    @Override
    public ArrayList<GroupData> query(DatabaseConnection databaseConnection) throws SQLException {
        ArrayList<GroupData> list = new ArrayList<GroupData>();
        Statement statement = databaseConnection.query();
        ResultSet resultSet = statement.executeQuery(
                "SELECT G.Key AS GroupId, G.Name AS Name, G.OwnerId AS OwnerId, U.Username AS Username, U.Id "
                        + "FROM 'Group' G INNER JOIN User u ON G.OwnerId = U.Id");
        while (resultSet.next()) {
            list.add(new GroupData(
                    resultSet.getInt("GroupId"),
                    resultSet.getString("Name"),
                    resultSet.getString("OwnerId"),
                    resultSet.getString("Username")
            ));
        }
        resultSet.close();
        statement.close();
        return list;
    }
}
