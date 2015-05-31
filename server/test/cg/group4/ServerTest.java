package cg.group4;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author Jurgen van Schagen
 */
public class ServerTest {
    private Server cServer;


    @Before
    public void setUp() {
        cServer = new Server();
    }

    @Test
    public void testCreateLocalIP() {
        cServer.createLocalIP();
        try {
            assertEquals(InetAddress.getLocalHost().getHostAddress(), cServer.cLocalHost.toString());
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testIsValidPortNegative() {
        assertFalse(cServer.isValidPort(-1));
    }

    @Test
    public void testIsValidPort() {
        assertTrue(cServer.isValidPort(20));
    }
}
