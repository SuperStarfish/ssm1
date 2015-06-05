package cg.group4.client.query;

import java.io.Serializable;

/**
 * Creates a new request.
 */
public final class Request implements Serializable {
    /**
     * The data that can be used for the request.
     */
    protected Data cData;

    /**
     * Creates a new request with the given Data.
     * @param data The data to be transmitted.
     */
    public Request(final Data data) {
        cData = data;
    }

    /**
     * Sets the data to the new Data.
     * @param data The new Data.
     */
    public void setcData(final Data data) {
        cData = data;
    }

    /**
     * Gets the data from this object.
     * @return The Data contained within.
     */
    public Data getcData() {
        return cData;
    }
}
