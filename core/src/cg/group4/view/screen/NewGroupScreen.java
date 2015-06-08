package cg.group4.view.screen;

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
                if (false) {
                    cStatusLabel.setText("Successfully created new group");
                } else {
                    cStatusLabel.setText("Unable to create new group");
                }
            }
        };
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
