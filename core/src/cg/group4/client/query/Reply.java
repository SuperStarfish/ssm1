package cg.group4.client.query;

import java.io.Serializable;

/**
 * Reply given between client and server. This should only be given when a request or update is done.
 */
public final class Reply implements Serializable {
    /**
     * The data to reply.
     */
    protected Data cData;

    /**
     * If the request or update was successful.
     */
    protected boolean cSuccess;

    /**
     * A new Reply that only contains Data.
     * @param data The Data to reply.
     */
    public Reply(final Data data) {
        cData = data;
    }

    /**
     * A new reply with both Data and successful or not.
     * @param data The Data to reply.
     * @param success If the request was successful or not.
     */
    public Reply(final Data data, final boolean success) {
        cData = data;
        cSuccess = success;
    }

    /**
     * A new reply with only if it was successful or not.
     * @param success If the request was successful or not.
     */
    public Reply(final boolean success) {
        cSuccess = success;
    }

    /**
     * Gets the data from the object.
     * @return The data stored in the object.
     */
    public Data getcData() {
        return cData;
    }

    /**
     * Sets the data to the new Data.
     * @param data The new Data that can be transmitted.
     */
    public void setcData(final Data data) {
        cData = data;
    }

    /**
     * Gets if the request was successful or not.
     * @return Successful or not.
     */
    public boolean iscSuccess() {
        return cSuccess;
    }

    /**
     * Sets the success to the new boolean.
     * @param success Successful or not.
     */
    public void setcSuccess(final boolean success) {
        cSuccess = success;
    }
}
