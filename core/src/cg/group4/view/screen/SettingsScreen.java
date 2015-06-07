package cg.group4.view.screen;

import cg.group4.util.audio.AudioPlayer;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen from which the settings of the application can be configured by a user.
 *
 * @author Jurgen van Schagen
 * @author Martijn Gribnau
 */
public final class SettingsScreen extends ScreenLogic {

    /**
     * Container group used for the layout of the view.
     */
    protected Table cTable;

    /**
     * Buttons for the options in the settings menu.
     */
    protected TextButton cButtonResetInterval,
            cButtonVolume,
            cButtonStopInterval,
            cButtonResetStroll,
            cButtonStopStroll,
            cNetworkScreen,
            cButtonBack;

    /**
     * References to the STROLL Timer and INTERVAL Timer.
     */
    protected Timer cIntervalTimer, cStrollTimer;

    /**
     * Whether or not the audio should be enabled or disable
     */
    private String cVolumeLabelText;

    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);

        getTimers();
        createGUI();

        return cTable;
    }

    /**
     * Gets the STROLL Timer and INTERVAL Timer so they can easily be accessed within this screen.
     */
    protected void getTimers() {
        cIntervalTimer = TimerStore.getInstance().getTimer(Timer.Global.INTERVAL.name());
        cStrollTimer = TimerStore.getInstance().getTimer(Timer.Global.STROLL.name());
    }

    @Override
    protected void rebuildWidgetGroup() {
        getWidgetGroup();
        cButtonResetInterval.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cButtonResetStroll.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cButtonStopInterval.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cButtonBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cButtonStopStroll.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cNetworkScreen.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    /**
     * Creates the buttons of the settings menu and adds an event listener for each of them.
     */
    protected void createGUI() {
        cButtonResetInterval = createButton("Reset Interval");
        cButtonResetInterval.addListener(resetIntervalBehaviour());

        cButtonStopInterval = createButton("Stop Interval");
        cButtonStopInterval.addListener(stopIntervalBehaviour());

        cButtonResetStroll = createButton("Reset Stroll");
        cButtonResetStroll.addListener(resetStrollBehaviour());

        cButtonStopStroll = createButton("Stop Stroll");
        cButtonStopStroll.addListener(stopStrollBehaviour());

        cNetworkScreen = createButton("Network");
        cNetworkScreen.addListener(networkScreenBehaviour());

        cVolumeLabelText =
        cButtonVolume = createButton(cVolumeLabelText);
        cNetworkScreen.addListener(volumeBehavior());

        cButtonBack = createButton("Back");
        cButtonBack.addListener(backBehaviour());

    }

    private ChangeListener volumeBehavior() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AudioPlayer.changeAudioEnabled();
            }
        }
    }

    /**
     * Resets the interval timer to its default time.
     *
     * @return ChangeListener
     */
    protected ChangeListener resetIntervalBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
               cIntervalTimer.reset();
            }
        };
    }

    /**
     * Resets the scroll timer to its default time.
     *
     * @return ChangeListener
     */
    protected ChangeListener resetStrollBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                cStrollTimer.reset();
            }
        };
    }

    /**
     * Stops the stroll timer.
     *
     * @return ChangeListener
     */
    protected ChangeListener stopStrollBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                cStrollTimer.stop();
            }
        };
    }

    /**
     * Stops the interval timer.
     * Resets the internal preferences. By doing so it won't be able to start off the time on which it stopped.
     *
     * @return ChangeListener
     */
    protected ChangeListener stopIntervalBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                cIntervalTimer.stop();
            }
        };
    }

    /**
     * Goes to the network Screen.
     * 
     * @return ChangeListener
     */
    protected ChangeListener networkScreenBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                ScreenStore.getInstance().setScreen("Network");
            }
        };
    }

    /**
     * Sets the screen to the home menu.
     *
     * @return ChangeListener
     */
    protected ChangeListener backBehaviour() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                cScreenStore.setScreen("Home");
            }
        };
    }

    /**
     * Helper method for creating text buttons and adding them to the table container.
     *
     * @param label Label for the text button
     * @return The text button just created. It is returned so an event listener can be added to the button.
     */
    protected TextButton createButton(final String label) {
        TextButton button = cGameSkin.generateDefaultMenuButton(label);
        cTable.row().expandY();
        cTable.add(button);
        return button;
    }

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }
}
