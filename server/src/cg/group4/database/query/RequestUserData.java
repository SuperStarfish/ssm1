package cg.group4.database.query;

import cg.group4.database.DatabaseConnection;
import cg.group4.database.datastructures.UserData;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Object that will retrieve user data from the server with the given id.
 */
public class RequestUserData extends Query {

    /**
     * The id of the user data to be retrieved
     */
    protected String cId;

    /**
     * Constructor for the request.
     *
     * @param id The id of the user data to be retreved.
     */
    public RequestUserData(String id) {
        cId = id;
    }

    @Override
    public Serializable query(DatabaseConnection databaseConnection) throws SQLException {
        Statement statement = databaseConnection.query();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USER WHERE ID = '" + cId + "' LIMIT 1");

        UserData userData = new UserData();
        while (resultSet.next()) {
            userData.setcID(resultSet.getString("ID"));
            userData.setcUsername(resultSet.getString("Username"));
            userData.setcIntervalTimeStamp(resultSet.getInt("Interval"));
            userData.setcStrollTimeStamp(resultSet.getInt("Stroll"));
        }

        resultSet.close();
        statement.close();

        return userData;
    }
}
