package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.data_structures.PlayerData;
import cg.group4.data_structures.groups.GroupData;
import cg.group4.game_logic.StandUp;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

/**
 * Screen to be displayed when a player wants to look at groups.
 */
public class GroupScreen extends ScreenLogic {

    /**
     * cTitle: Label that displays the title of the screen.
     * cGroupLabel: Label that displays the name of the group.
     */
    protected Label cTitle, cGroupLabel;

    /**
     * Buttons to create new groupsm join groups, and go back to the previous screen.
     */
    protected TextButton cNewGroupButton, cJoinGroupButton, cBackButton;

    /**
     * Table containing all the elements to be displayed.
     */
    protected Table cTable;

    /**
     * List containing all the different groups.
     */
    protected List<GroupData> cGroups;

    /**
     * List containing all the members of the groups.
     */
    protected List<PlayerData> cMembers;

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }

    @Override
    protected void rebuildWidgetGroup() {
        cBackButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cNewGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cJoinGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    public void display() {
        fillGroups();
        setGroupLabel();
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        ScreenStore screenStore = ScreenStore.getInstance();
        screenStore.addScreen("New-Group", new NewGroupScreen());
        screenStore.addScreen("Join-Group", new JoinGroupScreen());

        cTable = new Table();
        cTable.setFillParent(true);

        createTitle();
        createGroupLabel();
        createMemberOverview();
        createNewGroupButton();
        createJoinGroupButton();
        cBackButton = createBackButton();

        cTable.row().expandY();
        cTable.add(cBackButton).colspan(2);

        return cTable;

    }

    /**
     * Creates the title of the screen.
     */
    protected void createTitle() {
        cTitle = cGameSkin.generateDefaultLabel("Groups");
        cTable.row().expandY();
        cTable.add(cTitle).colspan(2);
    }

    /**
     * Label that displays the players group.
     */
    protected void createGroupLabel() {
        cGroupLabel = cGameSkin.generateDefaultLabel("You are not yet a member of a group.");
        cTable.row().expandY();
        cTable.add(cGroupLabel).colspan(2);
        setGroupLabel();
    }

    /**
     * Displays the members of the selected group.
     */
    protected void createMemberOverview() {
        cMembers = cGameSkin.generateDefaultList();
        Drawable selection = new TextureRegionDrawable(
                new TextureRegion(cAssets.getTexture("images/wooden_sign.png"))).tint(new Color(0, 0, 0, 0));
        selection.setLeftWidth(10);
        selection.setRightWidth(10);
        cMembers.getStyle().selection = selection;
        cMembers.getStyle().fontColorSelected = cMembers.getStyle().fontColorUnselected;
        cTable.row();
        createGroupsOverview();
        cTable.add(new ScrollPane(cMembers)).fill();
    }

    /**
     * Displays al the groups.
     */
    protected void createNewGroupButton() {
        cNewGroupButton = cGameSkin.generateDefaultMenuButton("Create group");
        cNewGroupButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenStore.getInstance().setScreen("New-Group");
            }
        });
        cTable.row().expandY();
        cTable.add(cNewGroupButton);
    }

    /**
     * Button to join a group.
     */
    protected void createJoinGroupButton() {
        cJoinGroupButton = cGameSkin.generateDefaultMenuButton("Join group");
        cJoinGroupButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenStore.getInstance().setScreen("Join-Group");
            }
        });
        cTable.add(cJoinGroupButton);

    }

    /**
     * Button to create a new group.
     */
    protected void createGroupsOverview() {
        cGroups = cGameSkin.generateDefaultList();
        cGroups.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Client.getInstance().getMembers(cGroups.getSelected().getGroupId(), new ResponseHandler() {
                    @Override
                    public void handleResponse(Response response) {
                        if (response.isSuccess()) {
                            ArrayList<PlayerData> list = (ArrayList<PlayerData>) response.getData();
                            cMembers.setItems(list.toArray(new PlayerData[list.size()]));
                        }
                    }
                });
            }
        });
        cTable.add(new ScrollPane(cGroups)).fill();
        fillGroups();
    }

    /**
     * Fill group display.
     */
    protected void fillGroups() {
        Client.getInstance().getGroupData(new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                if (response.isSuccess()) {
                    ArrayList<GroupData> list = (ArrayList<GroupData>) response.getData();
                    cGroups.setItems(list.toArray(new GroupData[list.size()]));
                }
            }
        });
    }

    /**
     * Sets the text of the group label.
     */
    protected void setGroupLabel() {
        String groupId = StandUp.getInstance().getPlayer().getGroupId();
        if (groupId != null) {
            cGroupLabel.setText("Retrieving group data..");
            Client.getInstance().getGroup(groupId, new ResponseHandler() {
                @Override
                public void handleResponse(Response response) {
                    if (response.isSuccess()) {
                        GroupData groupData = (GroupData) response.getData();
                        cGroupLabel.setText("Group: " + groupData.toString() + " (" + groupData.getGroupId() + ")");
                    } else {
                        cGroupLabel.setText("Could not retrieve group data.");
                    }
                }
            });
        }
    }
}
