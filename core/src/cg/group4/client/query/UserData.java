package cg.group4.client.query;

import java.io.Serializable;

public class UserData extends Data implements Serializable {
    public String ID;
    public String username;
    public long strollTimeStamp;
    public long intervalTimeStamp;

    public UserData() { }

    public UserData(String id) {
        ID = id;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "ID='" + ID + '\'' +
                ", username='" + username + '\'' +
                ", strollTimeStamp=" + strollTimeStamp +
                ", intervalTimeStamp=" + intervalTimeStamp +
                '}';
    }
}
