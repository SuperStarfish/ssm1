package cg.group4.view.screen;

import cg.group4.data_structures.groups.Group;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

/**
 * Screen to be displayed when a player wants to look at groups.
 */
public class GroupScreen extends ScreenLogic {

    protected Label cTitle;
    protected TextButton cNewGroupButton, cJoinGroupButton, cBackButton;
    protected Table cTable;
    protected Table cInnerTable;
    protected ScrollPane cScrollPane;
    protected ArrayList<Group> cGroupsDisplayList;

    @Override
    protected WidgetGroup createWidgetGroup() {
        ScreenStore screenStore = ScreenStore.getInstance();
        screenStore.addScreen("New-Group", new NewGroupScreen());
        screenStore.addScreen("Join-Group", new JoinGroupScreen());

        cTable = new Table();
        cTable.setFillParent(true);

        cGroupsDisplayList = new ArrayList<Group>();

        createTitle();
        createNewGroupButton();
        createGroupsOverview();
        createJoinGroupButton();

        cBackButton = createBackButton();
        cTable.row();
        cTable.add(cBackButton);

        return cTable;

    }

    protected void createTitle() {
        cTitle = new Label("Groups", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        cTable.add(cTitle);
        cTable.row().expandY();
    }

    protected void createNewGroupButton() {
        cNewGroupButton = cGameSkin.generateDefaultMenuButton("Create group");
        cNewGroupButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenStore.getInstance().setScreen("New-Group");
            }
        });
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
        cTable.row().expandY();
    }

    protected void createGroupsOverview() {
        cInnerTable = new Table();
        cScrollPane = new ScrollPane(cInnerTable);
        cScrollPane.setForceScroll(false, true);
        cTable.add(cScrollPane);
    }


    @Override
    protected void rebuildWidgetGroup() {
        cTitle.setStyle(cGameSkin.getDefaultLabelStyle());
        cNewGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cJoinGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }

    protected void addDisplayedGroup(final Group group) {
        cGroupsDisplayList.add(group);
    }

    protected void addDisplayedGroups(final ArrayList<Group> groups) {
        cGroupsDisplayList.addAll(groups);
    }

    // fills the scroll pane with all listed groups
    protected void fillGroupPanel() {

    }
}
