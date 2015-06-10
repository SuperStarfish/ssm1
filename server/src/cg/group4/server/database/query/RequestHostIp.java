package cg.group4.server.database.query;

import java.io.Serializable;
import java.sql.Connection;
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
    public RequestHostIp(final Integer code) {
        cCode = code;
    }

    @Override
    public Serializable query(final Connection databaseConnection) throws SQLException {
        String query = "SELECT Ip FROM Event_Hosts WHERE Code = " + cCode;

        String ip = null;

        try (Statement statement = databaseConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                ip = resultSet.getString("Ip");
            }
        }

        return ip;
    }
}
