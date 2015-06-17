package cg.group4.server.database;

/**
 * Combines multiple response handlers to a single response handler.
 * Only successfulness of the operations is handled.
 */
public class MultiResponseHandler implements ResponseHandler {

    protected final ResponseHandler cResponseHandler;
    protected int cNumberOfResponses;
    protected int cMaxNumberOfResponses;

    public MultiResponseHandler(ResponseHandler responseHandler, final int maxNumberOfResponses) {
        cResponseHandler = responseHandler;
        cNumberOfResponses = 0;
        cMaxNumberOfResponses = maxNumberOfResponses;
    }

    @Override
    public void handleResponse(Response response) {
        cNumberOfResponses++;
        if (!response.isSuccess() || cNumberOfResponses == cMaxNumberOfResponses) {
            cResponseHandler.handleResponse(response);
        }
    }
}
