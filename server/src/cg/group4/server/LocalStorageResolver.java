package cg.group4.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public abstract class LocalStorageResolver {
    protected static final Logger LOGGER = Logger.getLogger(LocalStorageResolver.class.getSimpleName());

    protected String cUserTable = "CREATE TABLE IF NOT EXISTS 'User' (Key INTEGER PRIMARY KEY NOT NULL, " +
            "Id TEXT NOT NULL, Username TEXT, Interval INTEGER, Stroll INTEGER, GroupId TEXT NULL);";

    protected String cCollectibleTable = "CREATE TABLE IF NOT EXISTS 'Collectible' (Key INTEGER PRIMARY KEY NOT NULL, " +
            "OwnerId TEXT, Type TEXT NOT NULL, Hue REAL NOT NULL, Amount INTEGER NOT NULL, " +
            "Date DATE NOT NULL, GroupId INTEGER);";

    protected String cGroupTable = "CREATE TABLE IF NOT EXISTS 'Group' (Key INTEGER PRIMARY KEY NOT NULL, " +
            "OwnerId TEXT NOT NULL, Name TEXT NOT NULL);";

    protected String cEventHostsTable = "CREATE TABLE IF NOT EXISTS 'Event_Hosts' (Code SMALLINT PRIMARY KEY NOT NULL, " +
            "Ip TEXT NOT NULL);";

    protected Connection cConnection;

    protected boolean cIsLocal;

    public LocalStorageResolver() {
        cIsLocal = setLocal();
        try {
            cConnection.setAutoCommit(false);
            cConnection = createDatabaseConnection();
            LOGGER.info("Database connection established.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(String table : createDatabases()) {
            createDatabase(table);
        }
    }

    protected void createDatabase(String query) {
        try {
            Statement statement = cConnection.createStatement();
            statement.execute(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract String[] createDatabases();

    protected abstract Connection createDatabaseConnection() throws SQLException;

    public Connection getConnection() {
        return cConnection;
    }

    protected abstract boolean setLocal();

    boolean isLocal() {
        return cIsLocal;
    }
}
