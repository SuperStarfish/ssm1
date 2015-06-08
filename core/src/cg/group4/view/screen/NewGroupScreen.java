package cg.group4.view.screen;

import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
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
    protected TextButton cAddGroupButton;



    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);

        cGroupNameField = cGameSkin.generateDefaultTextField("Name");
        cGroupNameField.setAlignment(Align.center);
        cTable.row().expandY();
        cTable.add(cGroupNameField);

        cAddGroupButton = cGameSkin.generateDefaultMenuButton("Create group");
        cAddGroupButton.addListener(addGroupListener());

        return cTable;
    }

    private EventListener addGroupListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //
            }
        };
    }

    @Override
    protected void rebuildWidgetGroup() {

    }

    @Override
    protected String setPreviousScreenName() {
        return null;
    }
}
