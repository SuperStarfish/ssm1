package cg.group4.client.query;

import java.io.Serializable;

public class UserData extends Data implements Serializable {
    protected String cID;
    protected String cUsername;
    protected long cStrollTimeStamp;
    protected long cIntervalTimeStamp;

    public UserData() { }

    public UserData(String id) {
        cID = id;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String ID) {
        cID = ID;
    }

    public String getcUsername() {
        return cUsername;
    }

    public void setcUsername(String username) {
        cUsername = username;
    }

    public long getcStrollTimeStamp() {
        return cStrollTimeStamp;
    }

    public void setcStrollTimeStamp(long strollTimeStamp) {
        cStrollTimeStamp = strollTimeStamp;
    }

    public long getcIntervalTimeStamp() {
        return cIntervalTimeStamp;
    }

    public void setcIntervalTimeStamp(long intervalTimeStamp) {
        cIntervalTimeStamp = intervalTimeStamp;
    }
}
