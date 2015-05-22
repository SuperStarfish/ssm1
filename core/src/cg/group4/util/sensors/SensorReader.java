package cg.group4.util.sensors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

/**
 * Objects that reads out the sensor values of the device.
 * Allows testing of objects that rely on these inputs.
 * @author Jean de Leeuw
 *
 */
public class SensorReader {
	
	/**
	 * Reads the accelerometer sensor values.
	 * @return Accelerometer sensor values.
	 */
	public Vector3 readAccelerometer() {
		return new Vector3(
				Gdx.input.getAccelerometerX(),
				Gdx.input.getAccelerometerY(),
				Gdx.input.getAccelerometerZ());
	}

}
