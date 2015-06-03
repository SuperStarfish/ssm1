package cg.group4.sensor;

import cg.group4.util.sensors.AccelerationState;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/** 
* AccelerationState Tester. 
*
* @since <pre>may, 2015</pre>
*/ 
public class AccelerationStateTest {

    /**
     * Test enum AccelerationState.RESTING
     */
    @Test
    public void testResting() {
        assertNotNull(AccelerationState.valueOf("RESTING"));
    }

    /**
     * Test enum AccelerationState.WALKING
     */
    @Test
    public void testWalking() {
        assertNotNull(AccelerationState.valueOf("WALKING"));
    }

    /**
     * Test enum AccelerationState.RUNNING
     */
    @Test
    public void testRunning() {
        assertNotNull(AccelerationState.valueOf("RUNNING"));
    }

    /**
     * Test enum AccelerationState.CHEATING
     */
    @Test
    public void testCheating() {
        assertNotNull(AccelerationState.valueOf("CHEATING"));
    }


} 
