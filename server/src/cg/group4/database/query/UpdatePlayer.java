package cg.group4.database.query;

import cg.group4.Player;
import cg.group4.database.DatabaseConnection;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Updates the player data on the server.
 */
public class UpdatePlayer extends Query {

    protected final Player cPlayer;

    public UpdatePlayer(Player player) {
        cPlayer = player;
    }

    @Override
    public Serializable query(DatabaseConnection databaseConnection) throws SQLException {
        String update = "";

        if (cPlayer.getUsername() != null) {
            update += ", Username = '" + cPlayer.getUsername() + "'";
        }

        if (cPlayer.getIntervalTimeStamp() != 0) {
            update += ", Interval = " + cPlayer.getIntervalTimeStamp();
        }

        if (cPlayer.getStrollTimeStamp() != 0) {
            update += ", Stroll = " + cPlayer.getStrollTimeStamp();
        }

        if (cPlayer.getGroupId() != null) {
            update += ", GroupId = '" + cPlayer.getGroupId() + "'";
        }

        if (cPlayer.getCollection() != null) {
            new UpdateCollection(cPlayer.getCollection()).query(databaseConnection);
        }

        Statement statement = databaseConnection.query();

        statement.executeUpdate("UPDATE USER SET " + update.substring(2) + " WHERE ID = '"
                + cPlayer.getId() + "'");

        databaseConnection.commit();
        statement.close();

        return null;
    }
}
