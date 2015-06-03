package cg.group4.client.query;

import java.io.Serializable;

public class Update implements Serializable {
    Data cData;

    public Update(Data data) {
        cData = data;
    }

    public Data getcData() {
        return cData;
    }
}
