package cg.group4.database;

import java.io.Serializable;

/**
 * Object that server sends to client.
 */
public class Response implements Serializable {
    protected Boolean cSuccess;
    protected Serializable cData;

    public Response(boolean success, Serializable data) {
        cSuccess = success;
        cData = data;
    }

    public Boolean isSuccess() {
        return cSuccess;
    }

    public Serializable getData() {
        return cData;
    }
}
