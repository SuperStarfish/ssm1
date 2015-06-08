package cg.group4.server.host;

import java.net.InetAddress;

/**
 * @author Jurgen van Schagen
 */
public abstract class Host {
    /**
     * The INetAddress that is provided.
     */
    protected InetAddress cInetAddress;

    /**
     * Empty constructor.
     */
    public Host() {
    }

    /**
     * Adds the INetAddress to the LocalHost container.
     *
     * @param inetAddress Address to store.
     */
    public Host(final InetAddress inetAddress) {
        cInetAddress = inetAddress;
    }

    @Override
    public abstract String toString();
}
