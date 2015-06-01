package cg.group4.client.connection;

import com.badlogic.gdx.utils.GdxRuntimeException;

public class Unconnected implements Connection {
    @Override
    public Connection connect(String ip, int port) {
        try {
            return new Connected(ip, port);
        } catch (GdxRuntimeException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public Connection disconnect() {
        return this;
    }
}
