package cg.group4.client.connection;

import cg.group4.client.query.NoReply;
import cg.group4.client.query.UserData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Unconnected implements Connection {
    private static final String TAG = Unconnected.class.getSimpleName();
    @Override
    public Connection connect(String ip, int port) {
        try {
            Gdx.app.debug(TAG, "Trying to connect to the server");
            return new Connected(ip, port);
        } catch (GdxRuntimeException e) {
            Gdx.app.debug(TAG, "Connection failed!");
        }
        return this;
    }

    @Override
    public Connection disconnect() {
        return this;
    }

    @Override
    public NoReply requestUserData(String id) {
        return new NoReply("Not connected to the server!");
    }

    @Override
    public void updateUserData(UserData data) { }
}
