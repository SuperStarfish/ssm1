package cg.group4.host;

import java.net.InetAddress;

/**
 *
 * @author Jurgen van Schagen
 */
public class ExternalHost extends Host {

    /**
     * Adds the INetAddress to the LocalHost container.
     *
     * @param inetAddress Address to store.
     */
    public ExternalHost(final InetAddress inetAddress) {
        super(inetAddress);
    }

    @Override
    public final String toString() {
        return cInetAddress.getHostAddress();
    }
}
