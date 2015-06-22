package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;


/**
 * Screen to change the IP address.
 */
public class ChangeIpScreen extends ScreenLogic {

    /**
     * Table used for layout purposes.
     */
    protected Table cTable;

    /**
     * Buttons seen on the screen.
     */
    protected TextButton cSave, cBack, cReset;

    /**
     * TextField where the user can change his username.
     */
    protected TextField cIp, cPort;

    /**
     * Label containing messages if the update was successful or not.
     */
    protected Label cMessage;

    @Override
    protected String setPreviousScreenName() {
        return "Network";
    }

    @Override
    protected void rebuildWidgetGroup() {
        cMessage.setStyle(cGameSkin.getDefaultLabelStyle());
        cIp.setStyle(cGameSkin.getDefaultTextFieldStyle());
        cPort.setStyle(cGameSkin.getDefaultTextFieldStyle());
        cSave.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cReset.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);

        messageLabel();
        ipTextField();
        portTextField();
        saveButton();
        resetButton();
        backButton();

        return cTable;
    }

    /**
     * Creates the IP address text field so a user can try to change the ip address.
     */
    public void ipTextField() {
        cIp = cGameSkin.generateDefaultTextField(Client.getInstance().getIp());
        cIp.setAlignment(Align.center);
        cTable.row().expandY();
        cTable.add(cIp).fillX();
    }

    /**
     * Creates the port text field so a user can try to change the port address.
     */
    public void portTextField() {
        cPort = cGameSkin.generateDefaultTextField(Integer.toString(Client.getInstance().getPort()));
        cPort.setAlignment(Align.center);
        cTable.row().expandY();
        cTable.add(cPort).fillX();
    }

    /**
     * Creates the save button.
     */
    public void saveButton() {
        cSave = cGameSkin.generateDefaultMenuButton("Save");
        cSave.addListener(saveBehaviour());
        cTable.row().expandY();
        cTable.add(cSave);
    }

    /**
     * Creates the back button.
     */
    public void backButton() {
        cBack = createBackButton();
        cTable.row().expandY();
        cTable.add(cBack);
    }

    /**
     * Creates the reset button.
     */
    public void resetButton() {
        cReset = cGameSkin.generateDefaultMenuButton("Reset to defaults");
        cReset.addListener(resetBehaviour());
        cTable.row().expandY();
        cTable.add(cReset);
    }

    /**
     * Creates the message label.
     */
    public void messageLabel() {
        cMessage = cGameSkin.generateDefaultLabel("Press \"save\" to change the ip & port");
        cTable.row().expandY();
        cTable.add(cMessage).expandX();
    }

    /**
     * Returns the change listener behaviour of the save button.
     * @return save button behaviour
     */
    private ChangeListener saveBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Preferences preferences = Gdx.app.getPreferences("CUSTOM_IP");

                String ip = cIp.getText();
                if (isValidIp(ip)) {
                    Client.getInstance().setIp(ip);
                    preferences.putString("ssm-ip", ip);
                    cMessage.setText("Success! new IP was set");
                }

                String port = cPort.getText();
                if (isValidPort(port)) {
                    Client.getInstance().setPort(Integer.parseInt(port));
                    preferences.putInteger("ssm-port", Integer.parseInt(port));
                    cMessage.setText("Success! new port was set");
                }

                if (isValidIp(ip) && isValidPort(port)) {
                    cMessage.setText("Success!");
                }

                preferences.flush();
                reconnect();
            }
        };
    }

    /**
     * Returns the reset button behaviour.
     * @return reset button behaviour.
     */
    private ChangeListener resetBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Preferences preferences = Gdx.app.getPreferences("CUSTOM_IP");
                preferences.clear();
                preferences.flush();

                Client client = Client.getInstance();

                String ip = client.defaultIp();
                client.setIp(ip);
                cIp.setText(ip);

                int port = client.defaultPort();
                client.setPort(port);
                cPort.setText(Integer.toString(port));

                reconnect();
            }
        };
    }

    /**
     * Checks the validity of the port.
     *
     * @param port Port to validate
     * @return valid (true) or invalid (false) port
     */
    private boolean isValidPort(String port) {
        final int minRange = 1;
        final int maxRange = 65535;

        Integer res = -1;

        try {
            res = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            cMessage.setText("Invalid port");
        }

        return minRange <= res && res <= maxRange;
    }

    /**
     * Validates the Ip address input.
     * @param ip Input ip address.
     * @return valid (true) or invalid (false) ip address.
     */
    private boolean isValidIp(String ip) {
        return Client.validIP(ip);
    }


    /**
     * Reconnects the to the remote server.
     */
    private void reconnect() {
        Client.getInstance().closeRemoteConnection();
        Client.getInstance().connectToRemoteServer();
    }

}
