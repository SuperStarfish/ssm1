package cg.group4.client.connection;

public interface Connection {
    Connection connect(String ip, int port);
    Connection disconnect();

}
