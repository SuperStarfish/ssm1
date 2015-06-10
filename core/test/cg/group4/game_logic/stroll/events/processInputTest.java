package cg.group4.game_logic.stroll.events;
import cg.group4.util.sensors.Accelerometer;
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
                {2.4f,2.4f,2.4f,2.4f,0,0},
                {2.0f,2.5f,2.0f,2.5f,0,1},
                {2.0f,-2.5f,2.0f,2.5f,1,1},
                {-2.5f,2.0f,2.0f,2.5f,2,1},
                {2.5f,2.0f,2.0f,2.5f,3,1},
                {2.0f,2.0f,-2.5f,2.5f,4,1},
                {2.0f,2.0f,2.5f,2.5f,5,1},
                {2.0f,2.4f,2.0f,2.5f,0,0},
                {2.0f,-2.4f,2.0f,2.5f,1,0},
                {-2.4f,2.0f,2.0f,2.5f,2,0},
                {2.4f,2.0f,2.0f,2.5f,3,0},
                {2.0f,2.0f,-2.4f,2.5f,4,0},
                {2.0f,2.0f,2.4f,2.5f,5,0},
                {2.5f,2.5f,2.5f,2.5f,6,0},
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
