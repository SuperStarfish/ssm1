package cg.group4.database.query;

import cg.group4.database.DatabaseConnection;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * Query's the database of the server.
 */
public abstract class Query {

    public abstract Serializable query(DatabaseConnection databaseConnection) throws SQLException;
}
