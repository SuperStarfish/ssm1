package cg.group4.data_structures.subscribe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SubjectTest {

    /**
     * Subject to test.
     */
    protected Subject cSubject;

    @Before
    public void setUp() {
        cSubject = new Subject();
    }

    @After
    public void tearDown() {
        cSubject = null;
    }

    @Test
    public void updateWithoutArgumentTest() {
        Subject subjectSpy = Mockito.spy(cSubject);

        subjectSpy.update();
        Mockito.verify(subjectSpy, Mockito.times(1)).update(null);
    }

    @Test
    public void updateWithArgumentTest() {
        final Integer i = new Integer(1);
        Subject subjectSpy = Mockito.spy(cSubject);

        subjectSpy.update(i);
        Mockito.verify(subjectSpy, Mockito.times(1)).notifyObservers(i);
    }

}
