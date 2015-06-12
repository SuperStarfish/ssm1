package cg.group4.client;

import cg.group4.client.UserIDResolver;

/**
 * Gets the id of the desktop device.
 */
public class DesktopIDResolver implements UserIDResolver {
    @Override
    public String getID() {
        return "Desktop/" + System.getProperty("user.name");
    }
}
