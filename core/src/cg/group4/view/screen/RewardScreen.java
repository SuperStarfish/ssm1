package cg.group4.view.screen;

import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class RewardScreen extends ScreenLogic {
    TextButton homeButton;
    Label rewardLabel;

    @Override
    protected WidgetGroup createWidgetGroup() {
        Table table = new Table();
        table.setFillParent(true);

        rewardLabel = new Label("REWARDS COME LATER", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        table.row().expandY();
        table.add(rewardLabel);

        homeButton = cGameSkin.generateDefaultMenuButton("Main Menu");
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cScreenStore.setScreen("Home");
            }
        });
        table.row().expandY();
        table.add(homeButton);

        return table;
    }

    @Override
    protected void rebuildWidgetGroup() {
        homeButton.setStyle(cGameSkin.get("default_textButtonStyle", TextButton.TextButtonStyle.class));
        rewardLabel.setStyle(cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
    }

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }
}
