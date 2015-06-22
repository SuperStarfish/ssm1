package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.game_logic.StandUp;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Observable;
import java.util.Observer;

/**
 * First screen displayed when opening the application.
 */
public final class HomeScreen extends ScreenLogic {
    /**
     * Container group used for the layout of the view.
     */
    protected Table cTable;

    /**
     * Buttons for the stroll, settings, collection.
     */
    protected TextButton cStrollButton, cSettingsButton, cCollectionButton, cGroupButton;

    /**
     * Labels for cTitle, timer.
     */
    protected Label cTitle, cTimer;
    
    /**
     * Boolean whether a stroll is currently running.
     */
    protected boolean cOnGoingStroll;

    /**
     * Observer that gets called on the start of a new stroll.
     */
    protected Observer cNewStrollObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
        	cOnGoingStroll = (boolean) arg;
            ScreenStore.getInstance().addScreen("Stroll", new StrollScreen());
        }
    };

    /**
     * Observer to subscribe to the tick subject of the interval timer.
     */
    protected Observer cIntervalTickObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            if (!cOnGoingStroll) {
                cTimer.setText(arg.toString());
            }
        }
    };
    /**
     * Observer to subscribe to the stop subject of the interval timer.
     */
    protected Observer cIntervalStopObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            if (!cOnGoingStroll) {
                cStrollButton.setDisabled(false);
                cTimer.setText("Ready!");
            }
        }
    };
    /**
     * The interval timer of the game.
     */
    protected Timer cIntervalTimer;
    /**
     * Observer to subscribe to the start subject of the interval timer.
     */
    protected Observer cIntervalStartObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            if (!cOnGoingStroll) {
                cStrollButton.setDisabled(true);
                cTimer.setText("Stroll running!");
            }
        }
    };

    /**
     * Creates the home screen.
     */
    public HomeScreen() {
        StandUp.getInstance().getOngoingStrollSubject().addObserver(cNewStrollObserver);
    }

    @Override
    protected String setPreviousScreenName() {
        return null;
    }

    @Override
    protected void rebuildWidgetGroup() {
        cTitle.setStyle(cGameSkin.getDefaultLabelStyle());
        cTimer.setStyle(cGameSkin.getDefaultLabelStyle());
        cStrollButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cSettingsButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cCollectionButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cGroupButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    public void display() {
        cStrollButton.setDisabled(!cOnGoingStroll && cIntervalTimer.isRunning());
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        initTimers();
        initHomeScreenTitle();

        initStrollButton();
        initCollectionButton();
        initGroupButton();
        initSettingsButton();

        initStrollIntervalTimer();

        fillTable();
        return cTable;
    }

    /**
     * Obtains both the STROLL and INTERVAL Timers so we can properly create the GUI.
     */
    protected void initTimers() {
        cIntervalTimer = TimerStore.getInstance().getTimer(Timer.Global.INTERVAL.name());
    }

    /**
     * Initializes the title on the home screen.
     */
    protected void initHomeScreenTitle() {
        cTitle = cGameSkin.generateDefaultLabel("Super StarFish Mania");
    }

    /**
     * Initializes the stroll buttons on the home screen.
     */
    protected void initStrollButton() {
        cStrollButton = cGameSkin.generateDefaultMenuButton("Stroll");
        cStrollButton.setDisabled(!cOnGoingStroll && cIntervalTimer.isRunning());
        cStrollButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                StandUp.getInstance().startStroll();
                ScreenStore.getInstance().setScreen("Stroll");
                Client client = Client.getInstance();
                client.updateStrollTimestamp(System.currentTimeMillis(), null);
                client.updateIntervalTimestamp(System.currentTimeMillis(), null);
            }
        });
    }

    /**
     * Initializes the collection button on the home screen.
     */
    protected void initCollectionButton() {
        cCollectionButton = cGameSkin.generateDefaultMenuButton("Collection");
        cCollectionButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                ScreenStore.getInstance().setScreen("Collection");
            }
        });
    }

    /**
     * Init groups.
     */
    protected void initGroupButton() {
        cGroupButton = cGameSkin.generateDefaultMenuButton("Groups");
        cGroupButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenStore.getInstance().setScreen("Groups");
            }
        });
        checkGroupButton();
    }

    /**
     * Initializes the settings button on the home screen.
     */
    protected void initSettingsButton() {
        cSettingsButton = cGameSkin.generateDefaultMenuButton("Settings");
        cSettingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                ScreenStore.getInstance().setScreen("Settings");
            }
        });
    }

    /**
     * Gets the IntervalTimer and initializes buttons and behaviour. Then adds the label to the WidgetGroup.
     */
    protected void initStrollIntervalTimer() {
        String textToDisplay = "";
        if (!cStrollButton.isDisabled()) {
            if (cOnGoingStroll) {
                textToDisplay = "Stroll running!";
            } else {
                textToDisplay = "Ready!";
            }
        }
        cTimer = cGameSkin.generateDefaultLabel(textToDisplay);

        cIntervalTimer.getStartSubject().addObserver(cIntervalStartObserver);
        cIntervalTimer.getStopSubject().addObserver(cIntervalStopObserver);
        cIntervalTimer.getTickSubject().addObserver(cIntervalTickObserver);
    }

    /**
     * Fills the layout table.
     */
    protected void fillTable() {
        cTable = new Table();
        cTable.setFillParent(true);

        cTable.row().expandY();
        cTable.add(cTitle).colspan(2);

        cTable.row().expandY();
        cTable.add(cTimer).colspan(2);

        cTable.row().expandY();
        cTable.add(cStrollButton).colspan(2);

        cTable.row().expandY();
        cTable.add(cCollectionButton);
        cTable.add(cGroupButton);

        cTable.row().expandY();
        cTable.add(cSettingsButton).colspan(2);
    }

    /**
     * Checks whether the groupButton should be enabled or disabled.
     */
    protected void checkGroupButton() {
        cGroupButton.setDisabled(!Client.getInstance().isRemoteConnected());
        Client.getInstance().getRemoteChangeSubject().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                cGroupButton.setDisabled(!(boolean) arg);
            }
        });
    }
}
