package cg.group4.util.sensor;

import com.badlogic.gdx.math.Vector3;

/**
 * Configurable object that reads and returns input from the accelerometer the device.
 *
 * @author Jean de Leeuw
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
     * Reader that reads the sensor values from the device.
     */
    protected SensorReader cReader;

    /**
     * Constructs an accelerometer which is used to read the accelerometer data
     * from the device.
     *
     * @param reader SensorReader
     */
    public Accelerometer(final SensorReader reader) {
        cFilterGravity = false;
        cNoiseThreshold = 1.5f;
        cReader = reader;
        cBaseVector = cReader.readAccelerometer();
    }

    /**
     * Method that reads and filters the current accelerometer readings.
     *
     * @return Current accelerometer readings.
     */
    public Vector3 update() {
        Vector3 readings = cReader.readAccelerometer();
        Vector3 resultVector = readings.cpy();

        if (cFilterGravity) {
            resultVector.set(
                    resultVector.x - cBaseVector.x,
                    resultVector.y - cBaseVector.y,
                    resultVector.z - cBaseVector.z);
        }

        resultVector.set(
                filterNoise(resultVector.x),
                filterNoise(resultVector.y),
                filterNoise(resultVector.z));

        cBaseVector = readings;
        return resultVector;
    }

    /**
     * Method that determines the highest (absolute) acceleration of the input.
     *
     * @param accelData Input data from which to determine the highest acceleration component.
     * @return Highest acceleration of the input.
     */
    public float highestAccelerationComponent(final Vector3 accelData) {
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
     *
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
     *
     * @param mode Default is off.
     */
    public final void filterGravity(final boolean mode) {
        cFilterGravity = mode;
    }


    /**
     * Helper method that should not be called outside of this class.
     * Checks whether a given acceleration is the gravity or not.
     *
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
     *
     * @param threshold Default is 1.5f
     */
    public final void setNoiseThreshold(final float threshold) {
        this.cNoiseThreshold = threshold;
    }
}
