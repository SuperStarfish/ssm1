package cg.group4.client.connection;

import cg.group4.client.query.Data;
import cg.group4.client.query.UserData;
import cg.group4.rewards.Collection;

public interface Connection {
    Connection connect(String ip, int port);
    Connection disconnect();

    Data requestUserData(String id);
    void updateUserData(UserData data);
    boolean updateCollection(Collection collection, UserData userData);
}
