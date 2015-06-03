package cg.group4.client.connection;

import cg.group4.client.query.Data;
import cg.group4.client.query.UserData;

public interface Connection {
    Connection connect(String ip, int port);
    Connection disconnect();

    Data requestUserData(String id);
    void updateUserData(UserData data);
}
