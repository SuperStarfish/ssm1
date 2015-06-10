package cg.group4.server.database.query;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Inserts a new event host into the game.
 */
public class RequestHostCode extends Query {
    /**
     * The highest number for host keys.
     */
    protected final static int MAXCODE = 10000;
    /**
     * The ip of the host.
     */
    protected String cIp;

    /**
     * Creates a new host.
     *
     * @param ip The ip of the host.
     */
    public RequestHostCode(final String ip) {
        cIp = ip;
    }

    @Override
    public Serializable query(final Connection databaseConnection) throws SQLException {
        boolean exists;
        Random rnd = new Random();
        Integer code;

        String preparedQuery = "SELECT * FROM Event_Hosts WHERE Code = ?";

        try (PreparedStatement statement = databaseConnection.prepareStatement(preparedQuery)) {
            do {
                code = rnd.nextInt(MAXCODE);
                statement.setInt(1, code);

                try (ResultSet resultSet = statement.executeQuery()) {
                    exists = resultSet.isBeforeFirst();
                }
            }
            while (exists);
        }

        preparedQuery = "INSERT INTO Event_Hosts (Code, Ip) VALUES (?, ?)";

        try (PreparedStatement statement = databaseConnection.prepareStatement(preparedQuery)) {
            statement.setInt(1, code);
            statement.setString(2, cIp);
            statement.executeUpdate();
        }

        return code;
    }
}
