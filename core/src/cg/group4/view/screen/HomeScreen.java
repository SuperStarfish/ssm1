package cg.group4.view.screen;

import cg.group4.game_logic.StandUp;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
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

public class HomeScreen extends ScreenLogic {
    /**
     * Container group used for the layout of the view.
     */
    protected Table cTable;

    /**
     * Buttons for the stroll, settings.
     */
    protected TextButton cStrollButton, cSettingsButton;

    /**
     * Reference to the IntervalTimer to properly display remaining time.
     */
    protected Timer cIntervalTimer;

    /**
     * Labels for title, timer.
     */
    protected Label cTitle, cTimer;

    /**
     * Observer that gets called on the start of a new stroll.
     */
    protected Observer cNewStrollObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            ScreenStore.getInstance().addScreen("Stroll", new StrollScreen());
        }
    };

    public HomeScreen() {
        StandUp.getInstance().getNewStrollSubject().addObserver(cNewStrollObserver);
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);

        initHomeScreenTitle();
        initStrollIntervalTimer();

        initStrollButton();
        initSettingsButton();

        return cTable;
    }

    @Override
    protected void rebuildWidgetGroup() {
        cTitle.setStyle(cGameSkin.getDefaultLabelStyle());
        cTimer.setStyle(cGameSkin.getDefaultLabelStyle());
        cStrollButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cSettingsButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    /**
     * Initializes the title on the home screen.
     */
    public final void initHomeScreenTitle() {
        cTitle = new Label("Super StarFish Mania", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        cTable.row().expandY();
        cTable.add(cTitle);
    }

    /**
     * Gets the IntervalTimer and initializes buttons and behaviour. Then adds the label to the WidgetGroup.
     */
    public final void initStrollIntervalTimer() {
        cIntervalTimer = TimeKeeper.getInstance().getTimer(Timer.Global.INTERVAL.name());

        createTimeRemainingLabel();
        addTimerBevahiour();

        cTable.row().expandY();
        cTable.add(cTimer);
    }

    /**
     * Creates the label and initializes the text depending on the remaining time for the IntervalTimer
     */
    protected void createTimeRemainingLabel(){
        if(cIntervalTimer.getRemainingTime() > 0 ){
            cTimer = new Label(Integer.toString(cIntervalTimer.getRemainingTime()), cGameSkin.getDefaultLabelStyle());
        } else {
            cTimer = new Label("Ready", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        }
    }

    /**
     * Adds behaviour for the IntervalTimer.
     */
    protected void addTimerBevahiour(){
        TimeKeeper.getInstance().getTimer(Timer.Global.INTERVAL.name()).subscribe(new TimerTask() {
            @Override
            public void onTick(final int seconds) {
                HomeScreen.this.cTimer.setText(Integer.toString(seconds));
            }

            @Override
            public void onStart(final int seconds) {
            }

            @Override
            public void onStop() {
            }
        });
    }

    /**
     * Initializes the stroll buttons on the home screen.
     */
    public final void initStrollButton() {
        cStrollButton = cGameSkin.generateDefaultMenuButton("Stroll");
        cStrollButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                StandUp.getInstance().startStroll();
                ScreenStore.getInstance().setScreen("Stroll");
            }
        });
        cTable.row().expandY();
        cTable.add(cStrollButton);
    }

    /**
     * Initializes the settings button on the home screen.
     */
    public final void initSettingsButton() {
        cSettingsButton = cGameSkin.generateDefaultMenuButton("Settings");
        cSettingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                ScreenStore.getInstance().setScreen("Settings");
            }
        });
        cTable.row().expandY();
        cTable.add(cSettingsButton);
    }

    @Override
    protected String setPreviousScreenName() {
        return null;
    }
}
