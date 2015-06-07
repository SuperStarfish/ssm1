package cg.group4.view.screen;

import cg.group4.collection.Collection;
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
     * Reward that has to be displayed of the screen.
     */
    protected Collection cRewardCollection;
    /**
     * Button to return to the HomeScreen.
     */
    protected TextButton cHomeButton;
    /**
     * Label containing the reward.
     */
    protected Label cRewardLabel;

    /**
     * Creates a new reward screen.
     *
     * @param reward The reward to be displayed on the reward screen.
     */
    public RewardScreen(final Collection reward) {
        cRewardCollection = reward;
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        Table table = new Table();
        table.setFillParent(true);

        cRewardLabel = new Label("Not yet implemented",
                cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        table.row().expandY();
        table.add(cRewardLabel);

        cHomeButton = cGameSkin.generateDefaultMenuButton("Main Menu");
        cHomeButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
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
