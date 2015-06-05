package cg.group4;

import cg.group4.host.LocalHost;
import cg.group4.host.UnknownHost;
import cg.group4.util.StaticsCaller;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.hamcrest.CoreMatchers.instanceOf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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
        cServer.cStaticsCaller = mock(StaticsCaller.class);
        cServer.createLocalIP();
        assertThat(cServer.cLocalHost, instanceOf(LocalHost.class));
    }

    @Test
    public void testCreateLocalIPWithException() {
        cServer.cStaticsCaller = mock(StaticsCaller.class);
        try {
            doThrow(new UnknownHostException()).when(cServer.cStaticsCaller).getLocalHost();
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
        cServer.createLocalIP();
        assertThat(cServer.cLocalHost, instanceOf(UnknownHost.class));
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
