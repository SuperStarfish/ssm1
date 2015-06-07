package cg.group4.database.query;

import cg.group4.database.DatabaseConnection;
import cg.group4.database.datastructures.UserData;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class UpdateUserData extends Query {

    protected final UserData cUserData;

    public UpdateUserData(UserData userData) {
        cUserData = userData;
    }

    @Override
    public Serializable query(DatabaseConnection databaseConnection) throws SQLException {
        String update = "";

        if (cUserData.getcUsername() != null) {
            update += ", Username = '" + cUserData.getcUsername() + "'";
        }

        if (cUserData.getcIntervalTimeStamp() != 0) {
            update += ", Interval = " + cUserData.getcIntervalTimeStamp();
        }

        if (cUserData.getcStrollTimeStamp() != 0) {
            update += ", Stroll = " + cUserData.getcStrollTimeStamp();
        }

        Statement statement = databaseConnection.query();

        statement.executeUpdate("UPDATE USER SET " + update.substring(2) + " WHERE ID = '"
                + cUserData.getcID() + "'");

        databaseConnection.commit();
        statement.close();

        return null;
    }
}
