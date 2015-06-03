package cg.group4.client.query;

public class NoReply extends Data {

    protected String cMessage;

    public NoReply(String message) {
        cMessage = message;
    }

    public String getMessage() {
        return cMessage;
    }
}
