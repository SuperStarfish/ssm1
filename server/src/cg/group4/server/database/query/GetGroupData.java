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
                "SELECT * FROM 'Group' INNER JOIN User ON 'Group'.OwnerId = User.Id");
        while (resultSet.next()) {
            list.add(new GroupData(
                    resultSet.getInt("'Group'.Key"),
                    resultSet.getString("'Group'.Name"),
                    resultSet.getString("'Group'.OwnerId"),
                    resultSet.getString("User.Username")
            ));
        }
        resultSet.close();
        statement.close();
        return list;
    }
}
