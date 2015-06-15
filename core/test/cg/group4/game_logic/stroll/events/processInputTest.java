package cg.group4.game_logic.stroll.events;
import cg.group4.util.sensor.Accelerometer;
import com.badlogic.gdx.math.Vector3;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

/**
 * Tests the processInput method of TestStrollEvent.
 */
@RunWith(Parameterized.class)
public class processInputTest {


    float x;
    float y;
    float z;
    float max;
    int calls;
    int oper;

    /**
     * Constants used for each task case.
     */
    protected static final int MOVE_LEFT = 0, MOVE_RIGHT = 1, MOVE_DOWN = 2, MOVE_UP = 3,
            MOVE_AWAY = 4, MOVE_TOWARDS = 5, NOT_EXISTING_TASK = 6;

    /**
     * Value used for the vectors which makes the equality statements true.
     */
    protected static final float DELTA = 2.5f;

    /**
     * Value used for the vectors which make the equality statements false.
     */
    protected static final float NOT_OVER_DELTA = 2.4f;

    /**
     * Value used for the vectors which doesn't change the result at all.
     */
    protected static final float BASE = 2.0f;

    /**
     * Constructor, creates a new test.
     * @param vecx Acceleration in the x axis
     * @param vecy Acceleration in the y axis
     * @param vecz Acceleration in the z axis
     * @param highaccel The highest acceleration value.
     * @param operationnr Operation number, defines where we have to move our phone to.
     * @param methodcalls The amount of times taskCompleted() should be called.
     */
    public processInputTest(float vecx, float vecy, float vecz, float highaccel, int operationnr, int methodcalls){
        x = vecx;
        y = vecy;
        z = vecz;
        max = highaccel;
        calls = methodcalls;
        oper = operationnr;
    }

    /**
     * Returns our paramters in an array.
     * @return Parameterized array containing our parameters used for testing.
     */
    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][]{
                {NOT_OVER_DELTA,NOT_OVER_DELTA,NOT_OVER_DELTA,NOT_OVER_DELTA,MOVE_LEFT,0},
                {BASE,DELTA,BASE,DELTA,MOVE_LEFT,1},
                {BASE,-DELTA,BASE,DELTA,MOVE_RIGHT,1},
                {-DELTA,BASE,BASE,DELTA,MOVE_DOWN,1},
                {DELTA,BASE,BASE,DELTA,MOVE_UP,1},
                {BASE,BASE,-DELTA,DELTA,MOVE_AWAY,1},
                {BASE,BASE,DELTA,DELTA,MOVE_TOWARDS,1},
                {BASE,NOT_OVER_DELTA,BASE,DELTA,MOVE_LEFT,0},
                {BASE,-NOT_OVER_DELTA,BASE,DELTA,MOVE_RIGHT,0},
                {-NOT_OVER_DELTA,BASE,BASE,DELTA,MOVE_DOWN,0},
                {NOT_OVER_DELTA,BASE,BASE,DELTA,MOVE_UP,0},
                {BASE,BASE,-NOT_OVER_DELTA,DELTA,MOVE_AWAY,0},
                {BASE,BASE,NOT_OVER_DELTA,DELTA,MOVE_TOWARDS,0},
                {DELTA,DELTA,DELTA,DELTA,NOT_EXISTING_TASK,0},
        });
    }

    /**
     * Tests if the task is completed depending on the input.
     */
    @Test
    public void processInputParamTest() {
        Vector3 vec = new Vector3().set(x,y,z);
        Accelerometer mock = Mockito.mock(Accelerometer.class);
        Mockito.when(mock.highestAccelerationComponent(vec)).thenReturn(max);

        TestStrollEvent test = Mockito.spy(new TestStrollEvent());
        test.cAccelMeter = mock;
        test.cOperationNr = oper;

        test.processInput(vec);

        Mockito.verify(test,Mockito.times(calls)).taskCompleted();
    }
}
