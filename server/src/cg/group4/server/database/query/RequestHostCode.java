package cg.group4.server.database.query;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Inserts a new event host into the game.
 */
public class RequestHostCode extends Query {
    protected final int cMaxCode = 10000;
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
    public Serializable query(Connection databaseConnection) throws SQLException {
        Statement statement = databaseConnection.createStatement();
        boolean exists;
        Random rnd = new Random();
        Integer code;

        do {
            code = rnd.nextInt(cMaxCode);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Event_Hosts WHERE Code = " + code);
            exists = resultSet.isBeforeFirst();
            resultSet.close();
        }
        while (exists);

        statement.executeUpdate("INSERT INTO Event_Hosts (Code, Ip) VALUES (" + code + ", '" + cIp + "')");

        statement.close();
        return code;
    }
}
