package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.util.audio.AudioPlayer;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Observable;
import java.util.Observer;

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
    protected Observer cAudioEnabledChanged = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            if (AudioPlayer.getInstance().getAudioEnabled()) {
                cVolumeLabelText = "Disable Audio";
            } else {
                cVolumeLabelText = "Enable Audio";
            }
            cButtonVolume.setText(cVolumeLabelText);
        }
    };

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

        cTable.row().expandY();
        cButtonResetInterval = cGameSkin.generateDefaultMenuButton("Reset Interval");
        cTable.add(cButtonResetInterval);
        cButtonResetInterval.addListener(resetIntervalBehaviour());

        cButtonStopInterval = cGameSkin.generateDefaultMenuButton("Stop Interval");
        cTable.add(cButtonStopInterval);
        cButtonStopInterval.addListener(stopIntervalBehaviour());

        cTable.row().expandY();
        cButtonResetStroll = cGameSkin.generateDefaultMenuButton("Reset Stroll");
        cTable.add(cButtonResetStroll);
        cButtonResetStroll.addListener(resetStrollBehaviour());

        cButtonStopStroll = cGameSkin.generateDefaultMenuButton("Stop Stroll");
        cTable.add(cButtonStopStroll);
        cButtonStopStroll.addListener(stopStrollBehaviour());

        cTable.row().expandY();
        cNetworkScreen = cGameSkin.generateDefaultMenuButton("Network");
        cTable.add(cNetworkScreen).colspan(2);
        cNetworkScreen.addListener(networkScreenBehaviour());

        if(AudioPlayer.getInstance().getAudioEnabled()) {
            cVolumeLabelText = "Disable Audio";
        } else {
            cVolumeLabelText = "Enable Audio";
        }

        cTable.row().expandY();
        cButtonVolume = cGameSkin.generateDefaultMenuButton(cVolumeLabelText);
        cTable.add(cButtonVolume).colspan(2);
        cButtonVolume.addListener(volumeBehavior());
        AudioPlayer.getInstance().getSubject().addObserver(cAudioEnabledChanged);


        cTable.row().expandY();
        cButtonBack = cGameSkin.generateDefaultMenuButton("Back");
        cTable.add(cButtonBack).colspan(2);
        cButtonBack.addListener(backBehaviour());

    }

    private ChangeListener volumeBehavior() {
        return new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                AudioPlayer.getInstance().changeAudioEnabled();
            }
        };
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
                Client.getRemoteInstance().connectToServer();
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


    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }
}
