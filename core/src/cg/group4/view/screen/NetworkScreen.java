package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.game_logic.Player;
import cg.group4.game_logic.StandUp;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Observable;
import java.util.Observer;

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
    protected TextButton cChangeUsername, cResetUser, cBack;

    /**
     * The label containing if we are connected with the server or not.
     */
    protected Label cMessage;


    @Override
    protected WidgetGroup createWidgetGroup() {
        Client client = Client.getInstance();
        client.connectToRemoteServer();

        ScreenStore screenStore = ScreenStore.getInstance();
        screenStore.addScreen("Change-Username", new ChangeUsernameScreen());

        cTable = new Table();
        cTable.setFillParent(true);

        if (client.isRemoteConnected()) {
            addLabel("Connected to remote!");

        } else {
            addLabel("Not connected to remote!");
        }

        addChangeUserName();

        addResetCollection();

        cBack = createBackButton();
        cTable.row().expandY();
        cTable.add(cBack);

        addConnectionChangeListener();

        return cTable;
    }

    /**
     * Adds the label with the message the user should at first.
     *
     * @param text The text for the message.
     */
    protected void addLabel(final String text) {
        cMessage = cGameSkin.generateDefaultLabel(text);
        cTable.row().expandY();
        cTable.add(cMessage);
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

    protected void addResetCollection() {
        cResetUser = cGameSkin.generateDefaultMenuButton("Reset Stats");
        cResetUser.addListener(resetPlayerDataBehaviour());
        cTable.row().expandY();
        cTable.add(cResetUser);
    }

    /**
     * If the connection state changes, this will handle the proper behaviour.
     */
    protected void addConnectionChangeListener() {
        Client.getInstance().getRemoteChangeSubject().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                boolean isConnected = (Boolean) arg;
                if (isConnected) {
                    cMessage.setText("Connected to remote!");
                } else {
                    cMessage.setText("Not connected to remote!");
                }
                cChangeUsername.setDisabled(!isConnected);
            }
        });
    }

    /**
     * Adds the screen toggling behaviour to the 'change username screen'.
     *
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

    protected ChangeListener resetPlayerDataBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player player = StandUp.getInstance().getPlayer();

                Client.getInstance().deletePlayerData(new ResponseHandler() {
                    @Override
                    public void handleResponse(Response response) {
                        if (response.isSuccess()) {
                            cMessage.setText("Your account has been reset.");
                            StandUp.getInstance().getPlayer().updatePlayerData();
                        } else {
                            cMessage.setText("Something went wrong.");
                        }

                    }
                });

                player.getCollection().resetCollection();
            }
        };
    }

    @Override
    protected void rebuildWidgetGroup() {
        cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    protected String setPreviousScreenName() {
        return "Settings";
    }
}
