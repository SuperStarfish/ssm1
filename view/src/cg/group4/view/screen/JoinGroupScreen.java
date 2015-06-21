package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.game_logic.StandUp;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;
import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

/**
 * Players can join groups here.
 */
public class JoinGroupScreen extends ScreenLogic {

    /**
     * Table containing all the elements to be displayed.
     */
    protected Table cTable;

    /**
     * Textfield where the user can fill the group name the user wants to join.
     */
    protected TextField cGroupNameField;

    /**
     * Buttons to join the groups and to go back to the previous screen.
     */
    protected TextButton cJoinGroupButton, cBack;

    /**
     * Label that displays the current status of the group joining.
     */
    protected Label cStatusLabel;

    @Override
    protected String setPreviousScreenName() {
        return "Groups";
    }

    @Override
    protected void rebuildWidgetGroup() {
        cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cJoinGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cGroupNameField.setStyle(cGameSkin.getDefaultTextFieldStyle());
        cStatusLabel.setStyle(cGameSkin.getDefaultLabelStyle());
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);

        cGroupNameField = cGameSkin.generateDefaultTextField("0");
        cGroupNameField.setAlignment(Align.center);
        cTable.row().expandY();
        cTable.add(cGroupNameField).fillX();

        cStatusLabel = cGameSkin.generateDefaultLabel("Required: join code");
        cTable.row().expandY();
        cTable.add(cStatusLabel).expandX();

        cJoinGroupButton = cGameSkin.generateDefaultMenuButton("Join group");
        cJoinGroupButton.addListener(addGroupListener());
        cTable.row().expandY();
        cTable.add(cJoinGroupButton);

        cBack = createBackButton();
        cTable.row().expandY();
        cTable.add(cBack);

        return cTable;
    }

    /**
     * Returns a EventListener which tries to join a group when pressed.
     *
     * @return EventListener which tries to join a group when pressed.
     */
    private EventListener addGroupListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tryJoin();
            }
        };
    }

    /**
     * Tries to join a group and updates the status label whether this has been succesfull or not.
     */
    private void tryJoin() {
        Client.getInstance().joinGroup(cGroupNameField.getText(), new ResponseHandler() {

            @Override
            public void handleResponse(Response response) {

                if (response.isSuccess()) {
                    cStatusLabel.setText("Successfully joined a group");
                    StandUp.getInstance().getPlayer().updatePlayerData();
                } else {
                    cStatusLabel.setText("Unable to join group");
                }
            }
        });
    }

    /**
     * Verifies if the entered group name does not contain invalid characters.
     *
     * @return boolean whether the group name does not contain invalid characters.
     */
    private boolean naiveVerify() {
        if (!cGroupNameField.getText().matches("^-?\\d+$")) {
            cStatusLabel.setText("Unable to join group (non-decimal value)");
            return false;
        }
        return true;
    }
}
