package cg.group4.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AndroidStorageResolver extends LocalStorageResolver {

    @Override
    protected String[] createDatabases() {
        return new String[]{cUserTable, cCollectibleTable, cEventHostsTable, cGroupTable};
    }

    @Override
    protected Connection createDatabaseConnection() throws SQLException {
        try {
            Class.forName("org.sqldroid.SQLDroidDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:sqldroid:/data/data/cg.group4.android/databases/local.db");
    }

    @Override
    protected boolean setLocal() {
        return true;
    }
}
