package cg.group4.server.database.query;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        String preparedQuery = "SELECT Ip FROM Event_Hosts WHERE Code = ?";

        String ip = null;

        try (PreparedStatement statement = databaseConnection.prepareStatement(preparedQuery)) {
            statement.setInt(1, cCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ip = resultSet.getString("Ip");
                }
            }
        }

        return ip;
    }
}
