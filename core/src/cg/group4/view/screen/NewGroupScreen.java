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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

/**
 * Players can add new groups here.
 */
public class NewGroupScreen extends ScreenLogic {

    protected Table cTable;
    protected TextField cGroupNameField;
    protected TextButton cAddGroupButton, cBack;
    protected Label cStatusLabel;


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

    private EventListener addGroupListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               addNewGroup();
            }
        };
    }

    private void addNewGroup() {
        Client.getRemoteInstance().createGroup(cGroupNameField.getText(), new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                final Group group = (Group) response.getData();
                final int groupId = group.getGroupData().getGroupId();

                final Player player = StandUp.getInstance().getPlayer();
                player.setPlayerDataGroupId(groupId);

                if (response.isSuccess()) {
                    cStatusLabel.setText("Successfully created new group");
                } else {
                    cStatusLabel.setText("Failed to create group");
                }
                StandUp.getInstance().getPlayer().update();
            }
        });
    }

    @Override
    protected void rebuildWidgetGroup() {
        cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cAddGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cGroupNameField.setStyle(cGameSkin.getDefaultTextFieldStyle());
        cStatusLabel.setStyle(cGameSkin.getDefaultLabelStyle());
    }

    @Override
    protected String setPreviousScreenName() {
        return "Groups";
    }
}
