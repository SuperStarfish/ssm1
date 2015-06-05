package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * The screen where we can interact with the server.
 */
public final class NetworkScreen extends ScreenLogic {
    /**
     * Table used for layout purposes.
     */
    protected Table cTable;

    /**
     * The buttons that are seen on this screen.
     */
    protected TextButton cChangeUsername, cBack;

    /**
     * The label containing if we are connected with the server or not.
     */
    protected Label cMessage;

    @Override
    protected WidgetGroup createWidgetGroup() {
        Client client = Client.getInstance();
        client.connectToServer();

        ScreenStore screenStore = ScreenStore.getInstance();
        screenStore.addScreen("Change-Username", new ChangeUsernameScreen());

        cTable = new Table();
        cTable.setFillParent(true);

        addLabel("Connected");
//        if (client.isConnected()) {
//            addLabel("Connected");
//
//        } else {
//            addLabel("Not Connected!");
//        }

        addChangeUserName();

        cBack = createBackButton();
        cTable.row().expandY();
        cTable.add(cBack);

        return cTable;
    }

    /**
     * Creates the username text field.
     */
    protected void addChangeUserName() {
        cChangeUsername = cGameSkin.generateDefaultMenuButton("Username");
        cChangeUsername.addListener(usernameBehaviour());
        cTable.row().expandY();
        cTable.add(cChangeUsername);
    }

    /**
     * Adds the label with the message the user should at first.
     * @param text The text for the message.
     */
    protected void addLabel(final String text) {
        cMessage = cGameSkin.generateDefaultLabel(text);
        cTable.row().expandY();
        cTable.add(cMessage);
    }

    /**
     * Adds the screen toggling behaviour to the 'change username screen'.
     * @return Listener for when clicked on.
     */
    protected ChangeListener usernameBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                ScreenStore.getInstance().setScreen("Change-Username");
            }
        };
    }

    @Override
    protected void rebuildWidgetGroup() {
        getWidgetGroup();

        cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    protected String setPreviousScreenName() {
        return "Settings";
    }
}
