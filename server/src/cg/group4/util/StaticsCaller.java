package cg.group4.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class StaticsCaller {
    public InetAddress getLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    public InetAddress getByName(String ip) throws UnknownHostException {
        return InetAddress.getByName(ip);
    }
}
