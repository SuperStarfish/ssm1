package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.data_structures.groups.Group;
import cg.group4.game_logic.Player;
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
 * Players can add new groups here.
 */
public class NewGroupScreen extends ScreenLogic {

    /**
     * Table containing all the elements to be displayed.
     */
    protected Table cTable;

    /**
     * Textfield to fill in the desired group name.
     */
    protected TextField cGroupNameField;

    /**
     * Buttons to create the group and go back to the previous screen.
     */
    protected TextButton cAddGroupButton, cBack;

    /**
     * Label that displays the current status of the group creation.
     */
    protected Label cStatusLabel;

    @Override
    protected String setPreviousScreenName() {
        return "Groups";
    }

    @Override
    protected void rebuildWidgetGroup() {
        cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cAddGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cGroupNameField.setStyle(cGameSkin.getDefaultTextFieldStyle());
        cStatusLabel.setStyle(cGameSkin.getDefaultLabelStyle());
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);

        cGroupNameField = cGameSkin.generateDefaultTextField("Name");
        cGroupNameField.setAlignment(Align.center);
        cTable.row().expandY();
        cTable.add(cGroupNameField).fillX();

        cStatusLabel = cGameSkin.generateDefaultLabel("Click below to start a new group");
        cTable.row().expandY();
        cTable.add(cStatusLabel).expandX();

        cAddGroupButton = cGameSkin.generateDefaultMenuButton("Create group");
        cAddGroupButton.addListener(addGroupListener());
        cTable.row().expandY();
        cTable.add(cAddGroupButton);

        cBack = createBackButton();
        cTable.row().expandY();
        cTable.add(cBack);

        return cTable;
    }

    /**
     * Returns a EventListener which tries to create a group when pressed.
     *
     * @return EventListener which tries to create a group when pressed.
     */
    private EventListener addGroupListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addNewGroup();
            }
        };
    }

    /**
     * Creates a new group.
     */
    private void addNewGroup() {
        Client.getInstance().createGroup(cGroupNameField.getText(), new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                final Group group = (Group) response.getData();
                final String groupId = group.getGroupData().getGroupId();

                final Player player = StandUp.getInstance().getPlayer();
                player.setGroupId(groupId);

                if (response.isSuccess()) {
                    cStatusLabel.setText("Successfully created new group");
                    StandUp.getInstance().getPlayer().updatePlayerData();
                } else {
                    cStatusLabel.setText("Failed to create group");
                }
            }
        });
    }
}
