package cg.group4.server.database.query;

import cg.group4.server.database.DatabaseConnection;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Request the host ip from the server belonging to the given code.
 */
public class RequestHostIp extends Query {

    /**
     * Code belonging to the host ip.
     */
    protected Integer cCode;

    /**
     * Makes a new Query to retrieve the host ip belonging to the given code.
     *
     * @param code The given code.
     */
    public RequestHostIp(Integer code) {
        cCode = code;
    }

    @Override
    public Serializable query(DatabaseConnection databaseConnection) throws SQLException {
        Statement statement = databaseConnection.query();
        ResultSet resultSet = statement.executeQuery("SELECT Ip FROM Event_Hosts WHERE Code = " + cCode);
        String ip = null;
        if (resultSet.next()) {
            ip = resultSet.getString("Ip");
        }
        resultSet.close();
        statement.close();
        return ip;
    }
}
