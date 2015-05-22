package cg.group4.util.accelerometer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

/**
 * Configurable object that reads and returns input from the accelerometer the device. 
 * @author Jean de Leeuw
 *
 */
public class Accelerometer {
	
	/**
	 * Keeps tracks if the gravity needs to be filtered.
	 */
	protected boolean cFilterGravity;
	
	/**
	 * Vector of the previous update. Needed for gravity filtering.
	 */
	protected Vector3 cBaseVector;
	
	/**
	 * Readings under this level will be discarded.
	 */
	protected float cNoiseThreshold;
	
	/**
	 * Constructs an accelerometer which is used to read the accelerometer data
	 * from the device.
	 */
	public Accelerometer() {
		cFilterGravity = false;
		cNoiseThreshold = 1.5f;
		cBaseVector = new Vector3(
				Gdx.input.getAccelerometerX(),
				Gdx.input.getAccelerometerY(),
				Gdx.input.getAccelerometerZ());
	}
	
	/**
	 * Method that reads and filters the current accelerometer readings.
	 * @return Current accelerometer readings.
	 */
	public final Vector3 update() {
		float accelX = Gdx.input.getAccelerometerX();
		float accelY = Gdx.input.getAccelerometerY();
		float accelZ = Gdx.input.getAccelerometerZ();
		
		Vector3 resultVector = new Vector3(accelX, accelY, accelZ);
		
		if (cFilterGravity) {
			resultVector.set(
					accelX - cBaseVector.x,
					accelY - cBaseVector.y,
					accelZ - cBaseVector.z);
		}
		
		resultVector.set(
				filterNoise(resultVector.x),
				filterNoise(resultVector.y),
				filterNoise(resultVector.z));
		
//		if (cFilterGravity) {
//			resultVector.set(
//					//SHOULD NOT BE ABS
//					Math.abs(cBaseVector.x - accelX),
//					Math.abs(cBaseVector.y - accelY),
//					Math.abs(cBaseVector.z - accelZ));
//		}
//		
//		resultVector.set(
//				filterNoise(accelX),
//				filterNoise(accelY),
//				filterNoise(accelZ));
		
		cBaseVector.set(accelX, accelY, accelZ);
		//System.out.println("Before filter: X: " + cBaseVector.x + " Y: " + cBaseVector.y + " Z: " + cBaseVector.z);
		System.out.println("After filter: X: " + resultVector.x + " Y: " + resultVector.y + " Z: " + resultVector.z);
		return resultVector;
	}
	
	/**
	 * Method that determines the highest (absolute) acceleration of the input.
	 * @param accelData Input data from which to determine the highest acceleration component.
	 * @return Highest acceleration of the input.
	 */
	public final float highestAccelerationComponent(final Vector3 accelData) {
		float highestComponent = Math.abs(accelData.x);
		
		float tempAccelStorage = Math.abs(accelData.y);
		if (tempAccelStorage > highestComponent) {
			highestComponent = tempAccelStorage;
		}
		tempAccelStorage = Math.abs(accelData.z);
		if (tempAccelStorage > highestComponent) {
			highestComponent = tempAccelStorage;
		}
		
		return highestComponent;
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Returns the input if the absolute input is higher than the noise threshold. 0 Otherwise.
	 * @param scalar Accelerometer component
	 * @return Input if scalar > noise threshold. 0 Otherwise.
	 */
	protected final float filterNoise(final float scalar) {
		float result = 0f;
		if (Math.abs(scalar) > cNoiseThreshold) {
			result = scalar;
		}
		return result;
	}
	
	/**
	 * Sets the filtering of the gravity from the accelerometer on/off.
	 * @param mode Default is off.
	 */
	public final void filterGravity(final boolean mode) {
		cFilterGravity = mode;
	}
	
	/**
	 * Returns the number of the axis on which the gravity currently acts.
	 * Only works when the device is being held still for a very brief period of time
	 * (during the reading).
	 * 1 = -X    4 = +Y
	 * 2 = +X    5 = -Z
	 * 3 = -Y    6 = +Z 
	 * @return Number which represents the axis on which the gravity currently acts.
	 */
	//Cleanup soon
	public final int getGravityAxis() {
		boolean storedGravitySetting = cFilterGravity;
		int result = -1;
		filterGravity(false);
		Vector3 accelVector = update();
		
		if (isGravity(accelVector.x)) {
			if (accelVector.x <= 0f) {
				result = 1;
			} else {
				result = 2;
			}
		} else if (isGravity(accelVector.y)) {
			if (accelVector.y <= 0f) {
				result = 3;
			} else {
				result = 4;
			}
		} else if (isGravity(accelVector.z)) {
			if (accelVector.z <= 0f) {
				result = 5;
			} else {
				result = 6;
			}
		}
		cFilterGravity = storedGravitySetting;
		return result;
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Checks whether a given acceleration is the gravity or not.
	 * @param scalar Acceleration
	 * @return Boolean whether the input acceleration is the gravity or not.
	 */
	protected final boolean isGravity(final float scalar) {
		float absoluteValue = Math.abs(scalar);
		return (absoluteValue > 9f) && (absoluteValue < 12f);
	}
	
	/**
	 * Sets the noise threshold.
	 * A higher threshold will result in only higher accelerations being registered by the accelerometer. 
	 * @param threshold Default is 1.5f
	 */
	public final void setNoiseThreshold(final float threshold) {
		this.cNoiseThreshold = threshold;
	}
}
