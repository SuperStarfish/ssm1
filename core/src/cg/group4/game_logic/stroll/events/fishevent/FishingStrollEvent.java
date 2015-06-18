package cg.group4.game_logic.stroll.events.fishevent;

import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.util.sensor.Accelerometer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

import java.util.Observable;

/**
 * Stroll event where you have to fish to complete it.
 */
public class FishingStrollEvent extends StrollEvent {

    /**
     * Amount of points that you should gain when completing this event.
     */
    protected static final int REWARDS = 10;

    /**
     * Sound played when the task is completed.
     */
    protected Sound cCompletedTaskSound;

    /**
     * The state where the event is in.
     */
    protected FishEventState cState;

    /**
     * Accelerometer used to fetch the input to complete the event.
     */
    protected Accelerometer cAccelMeter;

    /**
     * Creates a new fishing event, with delay timer, text, screen and input.
     */
    public FishingStrollEvent() {
        super();

        cCompletedTaskSound = Gdx.audio.newSound(Gdx.files.internal("sounds/completedTask.wav"));

        cAccelMeter = new Accelerometer(StandUp.getInstance().getSensorReader());
    }

    @Override
    public final void update(final Observable o, final Object arg) {
        Vector3 accel = cAccelMeter.update();
        cState.processInput(accel);
    }

    /**
     * Gets called when the event is completed.
     */
    public final void eventCompleted() {
        clearEvent();
    }

    @Override
    public final int getReward() {
        return REWARDS;
    }

    @Override
    protected void clearEvent() {
        
    }

    @Override
    public void start() {
        cAccelMeter.filterGravity(true);
        cState = new CastForwardState(this);
    }

    /**
     * Sets the text of the label.
     *
     * @param text The text of the label.
     */
    public void setText(final String text) {
        cLabelSubject.update(text);
    }
}
