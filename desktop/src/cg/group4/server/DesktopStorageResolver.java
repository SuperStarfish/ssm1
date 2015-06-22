package cg.group4.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gets the user id's from the desktop application.
 */
public class DesktopStorageResolver extends LocalStorageResolver {

    @Override
    public boolean setLocal() {
        return true;
    }

    @Override
    protected Connection createDatabaseConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:sqlite:local.sqlite");
    }

    @Override
    protected String[] createDatabases() {
        return new String[]{cUserTable, cCollectibleTable};
    }
}
