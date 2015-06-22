package cg.group4.server.database.query;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Event_Host_Clean extends Query {

    protected String query = "DELETE FROM 'Event_Hosts' WHERE Timestamp <= datetime('now','-30 minutes')";

    @Override
    public Serializable query(Connection databaseConnection) throws SQLException {
        try (Statement statement = databaseConnection.createStatement()) {
            statement.executeUpdate(query);
        }
        return null;
    }
}
