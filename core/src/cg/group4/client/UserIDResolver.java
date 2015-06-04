package cg.group4.client;

/**
 * Resolves the unique id for different devices.
 */
public interface UserIDResolver {
    /**
     * Returns the ID for the device.
     * @return Device ID.
     */
    String getID();
}
