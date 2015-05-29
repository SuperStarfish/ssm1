package cg.group4.util.sensors;

import cg.group4.GdxTestRunner;
import com.badlogic.gdx.math.Vector3;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests to ensure the proper behaviour of the Accelerometer class.
 *
 * @author Jean de Leeuw
 */
@RunWith(GdxTestRunner.class)
public class AccelerometerTest {

    /**
     * Instance of accelerometer to test.
     */
    protected Accelerometer cAccelMeter;

    /**
     * Mock of the sensor reader so we can feed the accelerometer custom made readings.
     */
    protected SensorReader cSensorReader;

    /**
     * Constructs the mock and rules for the sensorReader.
     * Also constructs the accelerometer object which will be tested.
     */
    @Before
    public final void setUp() {
        cSensorReader = mock(SensorReader.class);
        when(cSensorReader.readAccelerometer()).thenReturn(new Vector3(1f, 2f, 3f));

        cAccelMeter = new Accelerometer(cSensorReader);
    }

    /**
     * Destroys the mock and the accelerometer so every test works with clean objects.
     */
    @After
    public final void tearDown() {
        cAccelMeter = null;
        cSensorReader = null;
    }

    /**
     * Tests if the noise threshold is adjustable.
     */
    @Test
    public final void setNoiseThresholdTest() {
        assertEquals(1.5f, cAccelMeter.cNoiseThreshold, 0);
        cAccelMeter.setNoiseThreshold(3.45f);
        assertEquals(3.45f, cAccelMeter.cNoiseThreshold, 0);
    }

    /**
     * Tests if a certain acceleration is approximately equal to gravity.
     */
    @Test
    public final void isGravity() {
        float scalar = 9.81f;
        assertTrue(cAccelMeter.isGravity(scalar));
        scalar = 8.9f;
        assertFalse(cAccelMeter.isGravity(scalar));
        scalar = 12.01f;
        assertFalse(cAccelMeter.isGravity(scalar));
    }

    /**
     * Tets if the setting to filter gravity from the readings is adjustable.
     */
    @Test
    public final void filterGravityTest() {
        assertFalse(cAccelMeter.cFilterGravity);
        cAccelMeter.filterGravity(true);
        assertTrue(cAccelMeter.cFilterGravity);
    }

    /**
     * Tests if readings below a certain level are discarded.
     */
    @Test
    public final void filterNoiseTest() {
        assertEquals(1.5f, cAccelMeter.cNoiseThreshold, 0);
        float scalar = 1.23f;
        assertEquals(0, cAccelMeter.filterNoise(scalar), 0);
        scalar = 1.6f;
        assertEquals(1.6f, cAccelMeter.filterNoise(scalar), 0);
    }

    /**
     * Tests if the highest component of a Vector3 object is returned.
     */
    @Test
    public final void highestAccelerationComponentTest() {
        Vector3 data = new Vector3(1f, 2f, 3f);
        assertEquals(3f, cAccelMeter.highestAccelerationComponent(data), 0);
        data.set(0.5f, 0.2f, 0.45f);
        assertEquals(0.5f, cAccelMeter.highestAccelerationComponent(data), 0);
    }

    /**
     * Tests if the accelerometer object is constructed correctly.
     */
    @Test
    public final void constructorTest() {
        assertFalse(cAccelMeter.cFilterGravity);
        assertEquals(1.5f, cAccelMeter.cNoiseThreshold, 0);
        assertEquals(cAccelMeter.cBaseVector.x, 1f, 0);
        assertEquals(cAccelMeter.cBaseVector.y, 2f, 0);
        assertEquals(cAccelMeter.cBaseVector.z, 3f, 0);
    }

    /**
     * Tests if the readings are correctly read when the gravity filter option is ON.
     */
    @Test
    public final void updateGravityFilterONTest1() {
        cAccelMeter.filterGravity(true);
        assertTrue(cAccelMeter.cFilterGravity);
        cAccelMeter.cBaseVector = new Vector3(1f, 1f, 1f);
        Vector3 result = cAccelMeter.update();

        assertEquals(0f, result.x, 0);
        assertEquals(0f, result.y, 0);
        assertEquals(2f, result.z, 0);
    }

    /**
     * Another test to see if the readings are correctly read when the gravity filter option is ON.
     */
    @Test
    public final void updateGravityFilterONTest2() {
        cAccelMeter.filterGravity(true);
        assertTrue(cAccelMeter.cFilterGravity);
        cAccelMeter.cBaseVector = new Vector3(1f, 0.2f, 0f);
        Vector3 result = cAccelMeter.update();

        assertEquals(0f, result.x, 0);
        assertEquals(1.8f, result.y, 0);
        assertEquals(3f, result.z, 0);
    }

    /**
     * Tests if the readings are correctly read when the gravity filter option is OFF.
     */
    @Test
    public final void updateGravityFilterOFFTest() {
        assertFalse(cAccelMeter.cFilterGravity);
        Vector3 result = cAccelMeter.update();

        assertEquals(0f, result.x, 0);
        assertEquals(2f, result.y, 0);
        assertEquals(3f, result.z, 0);
    }
}
