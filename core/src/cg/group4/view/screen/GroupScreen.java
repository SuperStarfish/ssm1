package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.data_structures.groups.GroupData;
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

    protected Label cTitle;
    protected TextButton cNewGroupButton, cJoinGroupButton, cBackButton;
    protected Table cTable;
    protected List<GroupData> cGroups;
    protected List<String> cMembers;

    @Override
    protected WidgetGroup createWidgetGroup() {
        ScreenStore screenStore = ScreenStore.getInstance();
        screenStore.addScreen("New-Group", new NewGroupScreen());
        screenStore.addScreen("Join-Group", new JoinGroupScreen());

        cTable = new Table();
        cTable.setFillParent(true);

        createTitle();
        createMemberOverview();
        createNewGroupButton();
        createJoinGroupButton();
        cBackButton = createBackButton();

        cTable.row().expandY();
        cTable.add(cBackButton).colspan(2);

        return cTable;

    }

    protected void createTitle() {
        cTitle = cGameSkin.generateDefaultLabel("Groups");
        cTable.row().expandY();
        cTable.add(cTitle).colspan(2);
    }

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

    protected void createGroupsOverview() {
        cGroups = cGameSkin.generateDefaultList();
        cGroups.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Client.getInstance().getMembers(cGroups.getSelected().getGroupId(), new ResponseHandler() {
                    @Override
                    public void handleResponse(Response response) {
                        if (response.isSuccess()) {
                            ArrayList<String> list = (ArrayList<String>) response.getData();
                            cMembers.setItems(list.toArray(new String[list.size()]));
                        }
                    }
                });
            }
        });
        cTable.add(new ScrollPane(cGroups)).fill();
        fillGroups();
    }

    protected void fillGroups() {
        Client.getInstance().getGroupData(new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                ArrayList<GroupData> groups = (ArrayList<GroupData>) response.getData();
                cGroups.setItems(groups.toArray(new GroupData[groups.size()]));
            }
        });
    }

    @Override
    protected void rebuildWidgetGroup() {
        cBackButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cNewGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cJoinGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }

    @Override
    public void display() {
        fillGroups();
    }
}
