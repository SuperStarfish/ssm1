package cg.group4;

import java.io.Serializable;

public class Message implements Serializable{
    public Type type;
    public String body;

    public Message(){}

    public Message(Type typeIn){
        type = typeIn;
    }

    public Message(Type typeIn, String bodyIn) {
        type = typeIn;
        body = bodyIn;
    }

    protected enum Type{
        GET, PUT, POST, DELETE, REPLY, CLOSE
    }
}
