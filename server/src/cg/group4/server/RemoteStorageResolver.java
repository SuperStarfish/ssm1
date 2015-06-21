package cg.group4.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Definition on how to properly connect to the database for a remote server. Remote servers are run as standalone
 * Java applications and do not require LibGDX to function.
 */
public class RemoteStorageResolver extends LocalStorageResolver {

    @Override
    protected boolean setLocal() {
        return false;
    }

    @Override
    public Connection createDatabaseConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:remote.sqlite");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String[] createDatabases() {
        return new String[]{cUserTable, cCollectibleTable, cEventHostsTable, cGroupTable};
    }
}
