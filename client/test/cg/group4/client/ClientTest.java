package cg.group4.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 *
 */
public class ClientTest {

    Client client = Client.getInstance();

    @After
    public void tearDown() throws Exception {
        client = null;
    }


    @Test
    public void testDefaultIp() throws Exception {
        assertEquals("127.0.0.1", client.defaultIp());
    }

    @Test
    public void testDefaultPort() throws Exception {
        assertTrue(56789 == client.defaultPort());
    }
}