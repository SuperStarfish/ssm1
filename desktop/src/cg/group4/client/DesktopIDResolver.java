package cg.group4.client;

public class DesktopIDResolver implements UserIDResolver {
    @Override
    public String getID() {
        return "Desktop/" + System.getProperty("user.name");
    }
}
