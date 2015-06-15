package cg.group4.server;


import cg.group4.util.StaticsCaller;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * @author Jurgen van Schagen
 */
public class ServerTest {
    /**
     * The server to be tested upon.
     */
    protected Server cServer;


    /**
     * Sets up the environment for the tests.
     */
    @Before
    public void setUp() {
        cServer = new Server(new RemoteStorageResolver());
    }

    /**
     * Tears down the environment after the tests.
     */
    @Test
    public void testCreateLocalIP() {
        cServer.createLocalIP();
        try {
            assertEquals(cServer.cIp, InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes sure an exception will be thrown createLocalIp fails.
     */
    @Test
    public void testCreateLocalIPWithException() {
        cServer.cStaticsCaller = mock(StaticsCaller.class);
        try {
            doThrow(new UnknownHostException()).when(cServer.cStaticsCaller).getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        cServer.createLocalIP();
        assertNull(cServer.cIp);
    }

    /**
     * Checks port validity.
     */
    @Test
    public void testIsValidPortNegative() {
        assertFalse(cServer.isValidPort(-1));
    }

    /**
     * Checks port validity.
     */
    @Test
    public void testIsValidPort() {
        assertTrue(cServer.isValidPort(20));
    }
}
