package cg.group4.view;

import cg.group4.game_logic.StandUp;
import cg.group4.util.camera.WorldRenderer;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


/**
 * Home screen for the StandUp application.
 * Contains the main menu.
 * Displays the time left until the user can start a new stroll.
 *
 * @author Jurgen van Schagen
 * @author Martijn Gribnau
 *
 */
public class HomeScreen extends ScreenLogic {

    /**
     * Container group used for the layout of the view.
     */
    protected Table cTable;

    /**
     *  Buttons for the stroll, settings.
     */
    protected TextButton cStrollButton, cSettingsButton;

    /**
     * Labels for title, timer.
     */
    protected Label title, timer;

    /**
     * Initializes the home screen.
     * Makes use of a table layout.
     * Does this by filling a table with the title, the stroll timer, button and setting buttons.
     * @param worldRenderer The camera viewport to use
     */
    public HomeScreen(final WorldRenderer worldRenderer) {
        super(worldRenderer);
        cTable = new Table();
        cTable.debugAll();
        cTable.setFillParent(true);

        initHomeScreenTitle();
        initStrollIntervalTimer();

        initStrollButton(worldRenderer);
        initSettingsButton(worldRenderer);

        worldRenderer.setActor(cTable);
    }

    /**
     * Initializes the title on the home screen.
     */
    public final void initHomeScreenTitle() {
        title = new Label("Super StartFish Mania",
                StandUp.getInstance().getGameSkin().get("default_labelStyle", Label.LabelStyle.class));
        cTable.row().expandY();
        cTable.add(title);
    }

    /**
     * Initializes the stroll interval timer to the view.
     * The default label is "3600".
     * If the timer is started, its label will change to the time left on the timer.
     */
    public final void initStrollIntervalTimer() {
        timer = new Label("3600", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        StandUp.getInstance().getTimeKeeper().getTimer(Timer.Global.INTERVAL.name()).subscribe(new TimerTask() {
            @Override
            public void onTick(final int seconds) {
                timer.setText(Integer.toString(seconds));
            }

            @Override
            public void onStart(final int seconds) {}

            @Override
            public void onStop() {}
        });
        cTable.row().expandY();
        cTable.add(timer);
    }

    /**
     * Initializes the stroll buttons on the home screen.
     * @param worldRenderer The camera viewport used
     */
    public final void initStrollButton(final WorldRenderer worldRenderer) {
        cStrollButton = cGameSkin.generateDefaultMenuButton("Stroll");
        cStrollButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                worldRenderer.setScreen(new TestScreen(worldRenderer));
            }
        });
        cTable.row().expandY();
        cTable.add(cStrollButton);
    }

    /**
     * Initializes the settings button on the home screen.
     * @param worldRenderer The camera viewport used
     */
    public final void initSettingsButton(final WorldRenderer worldRenderer) {
        cSettingsButton = cGameSkin.generateDefaultMenuButton("Settings");
        cSettingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                worldRenderer.setScreen(new SettingsScreen(worldRenderer));
            }
        });
        cTable.row().expandY();
        cTable.add(cSettingsButton);
    }

}
