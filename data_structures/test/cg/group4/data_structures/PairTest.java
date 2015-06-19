package cg.group4.data_structures;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for pair class.
 */
public class PairTest {

    /**
     * Pair used for testing this class.
     */
    Pair<String> pair;

    /**
     * Sets up the pair with two words.
     *
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        final String word1 = "1";
        final String word2 = "2";
        pair = new Pair<String>(word1, word2);
    }

    /**
     * Tears down the test.
     *
     * @throws Exception e
     */
    @After
    public void tearDown() throws Exception {
        pair = null;
    }

    /**
     * Tests getting the first element of the pair.
     *
     * @throws Exception e
     */
    @Test
    public void testGetElement1() throws Exception {
        final String expected = "1";
        assertEquals(expected, pair.getElement1());
    }

    /**
     * Tests setting the first element of the pair.
     *
     * @throws Exception e
     */
    @Test
    public void testSetElement1() throws Exception {
        final String expectedI = "1";
        final String expectedII = "3";
        assertEquals(expectedI, pair.getElement1());
        pair.setElement1("3");
        assertEquals(expectedII, pair.getElement1());
    }

    /**
     * Tests getting the second element of the pair.
     *
     * @throws Exception e
     */
    @Test
    public void testGetElement2() throws Exception {
        final String expected = "2";
        assertEquals(expected, pair.getElement2());
    }

    /**
     * Tests setting the second element of the pair.
     *
     * @throws Exception e
     */
    @Test
    public void testSetElement2() throws Exception {
        final String expectedI = "2";
        final String expectedII = "4";
        assertEquals(expectedI, pair.getElement2());
        pair.setElement2("4");
        assertEquals(expectedII, pair.getElement2());
    }
}