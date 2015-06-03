package cg.group4.client.query;

import java.io.Serializable;

public class Reply implements Serializable{
    protected Data cData;

    public Reply(Data data) {
        cData = data;
    }

    public Data getcData() {
        return cData;
    }

    public void setcData(Data data) {
        cData = data;
    }
}
