package cg.group4.client;

/**
 * Gets the id of the desktop device.
 */
public class DesktopIDResolver implements UserIDResolver {
    @Override
    public String getID() {
        return "Desktop/" + System.getProperty("user.name");
    }
}
