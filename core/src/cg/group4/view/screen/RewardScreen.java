package cg.group4.view.screen;

import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen to display the rewards that have been gathered during an event.
 */
public final class RewardScreen extends ScreenLogic {
    
    /**
     * Button to return to the HomeScreen.
     */
    TextButton cHomeButton;
    
    /**
     * Reward that has to be displayed of the screen.
     */
    protected int cReward;

    /**
     * Label containing the reward.
     */
    Label cRewardLabel;

    @Override
    protected WidgetGroup createWidgetGroup() {
        Table table = new Table();
        table.setFillParent(true);

        cRewardLabel = new Label(Integer.toString(cReward), cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        table.row().expandY();
        table.add(cRewardLabel);

        cHomeButton = cGameSkin.generateDefaultMenuButton("Main Menu");
        cHomeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cScreenStore.setScreen("Home");
            }
        });
        table.row().expandY();
        table.add(cHomeButton);

        return table;
    }

    @Override
    protected void rebuildWidgetGroup() {
        cHomeButton.setStyle(cGameSkin.get("default_textButtonStyle", TextButton.TextButtonStyle.class));
        cRewardLabel.setStyle(cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
    }

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }
}
