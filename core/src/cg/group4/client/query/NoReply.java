package cg.group4.client.query;

/**
 * If no reply is given by the server.
 */
public final class NoReply extends Data {

    /**
     * The error message.
     */
    protected String cMessage;

    /**
     * Construct a new object that indicates that there was no reply from the server.
     * @param message The error message supplied.
     */
    public NoReply(final String message) {
        cMessage = message;
    }

    /**
     * Get the error message.
     * @return Error message inside the NoReply.
     */
    public String getMessage() {
        return cMessage;
    }
}
