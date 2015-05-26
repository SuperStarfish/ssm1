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

    protected int cReward;

    public RewardScreen(int reward) {
        cReward = reward;
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        Table table = new Table();
        table.setFillParent(true);

        Label label = new Label(Integer.toString(cReward), cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        table.row().expandY();
        table.add(label);

        TextButton button = cGameSkin.generateDefaultMenuButton("Main Menu");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cScreenStore.setScreen("Home");
            }
        });
        table.row().expandY();
        table.add(button);

        return table;
    }
}
