package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class ChangeUsernameScreen extends ScreenLogic{
    Table cTable;
    TextButton cSave, cBack;
    TextField cUsername;
    Label cMessage;
    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);
        cTable.debugAll();

        cUsername = cGameSkin.generateDefaultTextField("Anonymous");
        cUsername.setAlignment(Align.center);
        cTable.row().expandY();
        cTable.add(cUsername).fillX();

        cMessage = cGameSkin.generateDefaultLabel("Click save to update.");
        cTable.row().expandY();
        cTable.add(cMessage).expandX();

        cSave = cGameSkin.generateDefaultMenuButton("Save");
        cSave.addListener(saveBehaviour());
        cTable.row().expandY();
        cTable.add(cSave);

        cBack = createBackButton();
        cTable.row().expandY();
        cTable.add(cBack);
        return cTable;
    }

    protected ChangeListener saveBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(Client.getInstance().updateUsername(cUsername.getText())) {
                    cMessage.setText("Succes!");
                } else {
                    cMessage.setText("Something went wrong!");
                }
            }
        };
    }

    @Override
    protected void rebuildWidgetGroup() {
        cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cSave.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cUsername.setStyle(cGameSkin.getDefaultTextFieldStyle());
        cMessage.setStyle(cGameSkin.getDefaultLabelStyle());
    }

    @Override
    protected String setPreviousScreenName() {
        return "Network";
    }
}
