package cg.group4.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RemoteStorageResolver extends LocalStorageResolver {

    @Override
    public String[] createDatabases() {
        return new String[] {cUserTable, cCollectibleTable, cEventHostsTable, cGroupTable};
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
    protected boolean setLocal() {
        return false;
    }
}
