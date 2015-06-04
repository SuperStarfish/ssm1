package cg.group4.client.query;

import java.io.Serializable;

/**
 * An Update request that can be send with Data.
 */
public final class Update implements Serializable {
    /**
     * The Data to update.
     */
    protected Data cData;

    /**
     * Creates a new Update request using the Data supplied.
     * @param data The Data given to update.
     */
    public Update(final Data data) {
        cData = data;
    }

    /**
     * Gets the data from that needs to be updated.
     * @return The Data.
     */
    public Data getcData() {
        return cData;
    }
}
