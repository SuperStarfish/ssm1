package cg.group4.database;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles database connection. Uses a ConnectionWrapper state for proper behaviour.
 *
 * @author Jurgen van Schagen
 */
public class DatabaseConnection {
    /**
     * Default Java logging tool. Used for logging inside the class.
     */
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    /**
     * The ConnectionWrapper on which methods are called.
     */
    protected ConnectionWrapper cConnection;

    /**
     * Sets the log level and creates a NoConnection wrapper.
     */
    public DatabaseConnection() {
        LOGGER.setLevel(Level.ALL);
        cConnection = new NoConnection();
    }

    /**
     * Asks the ConnectionWrapper to return the proper Wrapper for opening the connection.
     */
    public final void connect() {
        cConnection = cConnection.openConnection();
    }

    /**
     * Asks the ConnectionWrapper to return the proper Wrapper for closing the connection.
     */
    public final void disconnect() {
        cConnection = cConnection.closeConnection();
    }

//        Connection c = null;
//        Statement stmt = null;
//        try {
//
//            c = DriverManager.getConnection("jdbc:sqlite:data.sqlite");
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery( "SELECT * FROM USER;" );
//            while ( rs.next() ) {
//                int id = rs.getInt("ID");
//                String  name = rs.getString("Name");
//                System.out.println( "ID = " + id );
//                System.out.println( "NAME = " + name );
//                System.out.println();
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Operation done successfully");

}
