package cg.group4.client;

import java.io.Serializable;

/**
 * @author Jurgen van Schagen
 */
public class Query implements Serializable{
    protected String cQuery;

    public Query(String query) {
        cQuery = query;
    }

    public String getcQuery() {
        return cQuery;
    }

    public void setcQuery(String query) {
        cQuery = query;
    }
}
