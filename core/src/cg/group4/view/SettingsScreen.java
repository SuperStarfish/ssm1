package cg.group4.view;

import cg.group4.game_logic.StandUp;
import cg.group4.util.camera.WorldRenderer;
import cg.group4.util.timer.Timer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen from which the settings of the application can be configured by a user.
 *
 * @author Jurgen van Schagen
 * @author Martijn Gribnau
 */
public class SettingsScreen extends ScreenLogic {

    /**
     * Container group used for the layout of the view.
     */
    protected Table cTable;

    /**
     * Buttons for the options in the settings menu.
     */
    protected TextButton cButtonResetInterval,
            cButtonResetStroll,
            cButtonStopInterval,
            cButtonBack;

    /**
     * Initializes the settings screen.
     * @param worldRenderer The camera viewport
     */
    public SettingsScreen(final WorldRenderer worldRenderer) {
        super(worldRenderer);
        createTable();
        createGUI();

        setAsActiveScreen();
    }

    /**
     * Initializes the table container.
     */
    protected final void createTable() {
        cTable = new Table();
        cTable.setFillParent(true);
        cTable.debugAll();
    }

    /**
     * Creates the buttons of the settings menu and adds an event listener for each of them.
     */
    protected final void createGUI() {
        cButtonResetInterval = createButton("Reset Interval");
        cButtonResetInterval.addListener(resetIntervalBehaviour());

        cButtonResetStroll = createButton("Reset Stroll");
        cButtonResetStroll.addListener(resetStrollBehaviour());

        cButtonStopInterval = createButton("Stop Interval");
        cButtonStopInterval.addListener(stopIntervalBehaviour());

        cButtonBack = createButton("Back");
        cButtonBack.addListener(backBehaviour());

    }

    /**
     * Resets the interval timer to its default time.
     * @return ChangeListener
     */
    protected final ChangeListener resetIntervalBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                StandUp.getInstance().getTimeKeeper().getTimer(Timer.Global.INTERVAL.name()).reset();
            }
        };
    }

    /**
     * Resets the scroll timer to its default time.
     * @return ChangeListener
     */
    protected final ChangeListener resetStrollBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                StandUp.getInstance().getTimeKeeper().getTimer(Timer.Global.STROLL.name()).reset();
                //cWorldRenderer.setScreen(new RewardScreen(cWorldRenderer));
            }
        };
    }

    /**
     * Stops the interval timer.
     * Resets the internal preferences. By doing so it won't be able to start off the time on which it stopped.
     * @return ChangeListener
     */
    protected final ChangeListener stopIntervalBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                StandUp.getInstance().getTimeKeeper().getTimer(Timer.Global.INTERVAL.name()).stop();
            }
        };
    }

    /**
     * Sets the screen to the home menu
     * @return ChangeListener
     */
    protected ChangeListener backBehaviour(){
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cWorldRenderer.setScreen(new HomeScreen(cWorldRenderer));
            }
        };
    }

    /**
     * Helper method for creating text buttons and adding them to the table container.
     * @param label Label for the text button
     * @return The text button just created. It is returned so an event listener can be added to the button.
     */
    protected TextButton createButton(String label){
        TextButton button = cGameSkin.generateDefaultMenuButton(label);
        cTable.row().expandY();
        cTable.add(button);
        return button;
    }

	@Override
	public void setAsActiveScreen() {
		// TODO Auto-generated method stub
		cWorldRenderer.setActor(cTable);
	}
}
