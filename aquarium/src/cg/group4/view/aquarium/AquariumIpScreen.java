package cg.group4.view.aquarium;

import cg.group4.aquarium.Aquarium;
import cg.group4.client.Client;
import cg.group4.view.screen_mechanics.GameSkin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Ben on 22-6-2015.
 */
public class AquariumIpScreen implements Screen {

    /**
     * Stage for the content.
     */
    protected final Stage cStage = new Stage();
    /**
     * Style for layout items.
     */
    protected final GameSkin cStyle = new GameSkin();
    /**
     * Table used for layout purposes.
     */
    protected Table cTable;
    /**
     * The background colour.
     */
    protected Vector3f cBackgroundColour;
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

    public AquariumIpScreen() {
        final int fontSize = 720;
        cStyle.createUIElements(fontSize);
        initBackgroundColour();
        cStage.addActor(fillTable());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(cStage);
    }

    protected Table fillTable() {
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
        cIp = cStyle.generateDefaultTextField(Client.getInstance().getIp());
        cIp.setAlignment(Align.center);
        cTable.row().expandY();
        cTable.add(cIp).fillX();
    }

    /**
     * Creates the port text field so a user can try to change the port address.
     */
    public void portTextField() {
        cPort = cStyle.generateDefaultTextField(Integer.toString(Client.getInstance().getPort()));
        cPort.setAlignment(Align.center);
        cTable.row().expandY();
        cTable.add(cPort).fillX();
    }

    /**
     * Creates the save button.
     */
    public void saveButton() {
        cSave = cStyle.generateDefaultMenuButton("Save");
        cSave.addListener(saveBehaviour());
        cTable.row().expandY();
        cTable.add(cSave);
    }

    /**
     * Creates the back button.
     */
    public void backButton() {
        cBack = cStyle.generateDefaultMenuButton("Back");
        cBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Aquarium.getInstance().setScreen(Aquarium.getInstance().getAquariumScreen());
            }
        });
        cTable.row().expandY();
        cTable.add(cBack);
    }

    /**
     * Creates the reset button.
     */
    public void resetButton() {
        cReset = cStyle.generateDefaultMenuButton("Reset to defaults");
        cReset.addListener(resetBehaviour());
        cTable.row().expandY();
        cTable.add(cReset);
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

    /**
     * Creates the message label.
     */
    public void messageLabel() {
        cMessage = cStyle.generateDefaultLabel("Press \"save\" to change the ip & port");
        cTable.row().expandY();
        cTable.add(cMessage).expandX();
    }

    /**
     * Returns the background colour.
     *
     * @return Blue background colour.
     */
    private void initBackgroundColour() {
        final float maxColour = 255f;
        final float x = 149f / maxColour;
        final float y = 221f / maxColour;
        final float z = 226f / maxColour;
        cBackgroundColour =  new Vector3f(x, y, z);
    }

    @Override
    public void render(float delta) {
        cStage.act();
        final Vector3f background = cBackgroundColour;

        Gdx.gl.glClearColor(background.getX(), background.getY(), background.getZ(), 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
