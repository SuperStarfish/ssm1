package cg.group4.view.screen;

import cg.group4.client.Client;
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
 * Players can join groups here.
 */
public class JoinGroupScreen extends ScreenLogic {

    protected Table cTable;
    protected TextField cGroupNameField;
    protected TextButton cJoinGroupButton, cBack;
    protected Label cStatusLabel;


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

    private EventListener addGroupListener() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tryJoin();
            }
        };
    }

    private boolean naiveVerify() {
        if (!cGroupNameField.getText().matches("^-?\\d+$")) {
            cStatusLabel.setText("Unable to join group (non-decimal value)");
            return false;
        }
        return true;
    }

    private void tryJoin() {
        Client.getRemoteInstance().joinGroup(cGroupNameField.getText(), new ResponseHandler() {

            @Override
            public void handleResponse(Response response) {

                if (response.isSuccess()) {
                    cStatusLabel.setText("Successfully joined a group");
                } else {
                    cStatusLabel.setText("Unable to join group");
                }
                StandUp.getInstance().getPlayer().update();
            }
        });
    }

    @Override
    protected void rebuildWidgetGroup() {
        cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cJoinGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cGroupNameField.setStyle(cGameSkin.getDefaultTextFieldStyle());
        cStatusLabel.setStyle(cGameSkin.getDefaultLabelStyle());
    }

    @Override
    protected String setPreviousScreenName() {
        return "Groups";
    }
}
