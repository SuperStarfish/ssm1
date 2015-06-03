package cg.group4.client.query;

import java.io.Serializable;

public class Reply implements Serializable{
    protected Data cData;

    protected boolean cSuccess;

    public Reply(Data data) {
        cData = data;
    }

    public Reply(Data data, boolean success) {
        cData = data;
        cSuccess = success;
    }

    public Reply(boolean success) {
        cSuccess = success;
    }

    public Data getcData() {
        return cData;
    }

    public void setcData(Data data) {
        cData = data;
    }

    public boolean iscSuccess() {
        return cSuccess;
    }

    public void setcSuccess(boolean success) {
        cSuccess = success;
    }
}
