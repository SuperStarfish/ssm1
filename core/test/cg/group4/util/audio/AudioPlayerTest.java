package cg.group4.util.audio;

import cg.group4.GdxTestRunner;
import cg.group4.data_structures.subscribe.Subject;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the AudioPlayer.test class;
 */
@RunWith(GdxTestRunner.class)
public class AudioPlayerTest {

    /**
     * AudioPlayer which is going to be tested.
     */
    AudioPlayer cAudioPlayer = AudioPlayer.getInstance();

    /**
     * Mock of class Music.
     */
    Music cMusicMock;

    /**
     * Mock of class Music to test the previous played field.
     */
    Music cPrefPlayedMock;

    /***
     * Mock of class Sound.
     */
    Sound cSoundMock;

    /**
     * Setup method, gets ran before every test.
     **/
    @Before
    public void setUp() {
       cMusicMock = Mockito.mock(Music.class);
       cSoundMock = Mockito.mock(Sound.class);
       cPrefPlayedMock = Mockito.mock(Music.class);
    }

    /**
     * The audioplayer should not be null.
     */
    @Test
    public void getInstanceNotNull() {
        assertNotNull(cAudioPlayer);
    }

    /**
     * The sound should be played when audio is enabled.
     */
    @Test
    public void playAudioSoundAudioEnabledTest()  {
        cAudioPlayer.cAudioEnabled = true;
        cAudioPlayer.playAudio(cSoundMock);
        Mockito.verify(cSoundMock).play();
    }

    /**
     * The sound shouldn't be played when audio is disabled.
     */
    @Test
    public void playSoundAudioDisabledTest()  {
        cAudioPlayer.cAudioEnabled = false;
        cAudioPlayer.playAudio(cSoundMock);
        Mockito.verify(cSoundMock,Mockito.times(0)).play();
    }

    /**
     * The sound should be played when audio is enabled and the previous song should be stopped if
     * it was playing.
     */
    @Test
    public void playMusicAudioEnabledSongPlayingTest()  {
        cAudioPlayer.cAudioEnabled = true;
        Mockito.when(cPrefPlayedMock.isPlaying()).thenReturn(true);
        cAudioPlayer.cLastPlayed = cPrefPlayedMock;
        cAudioPlayer.playAudio(cMusicMock);
        Mockito.verify(cPrefPlayedMock).stop();
        Mockito.verify(cMusicMock).play();
    }

    /**
     * The sound should be played when audio is enabled and the previous song should be stopped if
     * it was playing.
     */
    @Test
    public void playMusicAudioEnabledNoSongPlayingTest()  {
        cAudioPlayer.cAudioEnabled = true;
        Mockito.when(cPrefPlayedMock.isPlaying()).thenReturn(false);
        cAudioPlayer.cLastPlayed = cPrefPlayedMock;
        cAudioPlayer.playAudio(cMusicMock);
        Mockito.verify(cPrefPlayedMock,Mockito.times(0)).stop();
        Mockito.verify(cMusicMock).play();
    }

    /**
     * The sound shouldn't be played when audio is disabled.
     */
    @Test
    public void playMusicAudioDisabledTest()  {
        cAudioPlayer.cAudioEnabled = false;
        cAudioPlayer.playAudio(cMusicMock);
        Mockito.verify(cMusicMock,Mockito.times(0)).play();
    }

    /**
     * Whenever you toggle the audio, it should return to the same value after 2 toggles.
     */
    @Test
    public void changeAudioEnabledTest() {
        boolean enabled = cAudioPlayer.getAudioEnabled();
        cAudioPlayer.changeAudioEnabled();
        assertEquals(!enabled, cAudioPlayer.getAudioEnabled());
        cAudioPlayer.changeAudioEnabled();
        assertEquals(enabled, cAudioPlayer.getAudioEnabled());
    }

    /**
     * Test for the boolean getter.
     */
    @Test
    public void getAudioEnabledTest() {
        cAudioPlayer.cAudioEnabled = true;
        assertTrue(cAudioPlayer.getAudioEnabled());
    }

    /**
     * Test for the subject getter.
     */
    @Test
    public void getSubjectTest() {
        Subject subject = new Subject();
        cAudioPlayer.cAudioChangedSubject = subject;
        assertEquals(subject, cAudioPlayer.getSubject());
    }

    /**
     * Test for the last played setter.
     */
    @Test
    public void setLastPlayedTest() {
        cAudioPlayer.setLastPlayed(cMusicMock);
        assertEquals(cMusicMock, cAudioPlayer.cLastPlayed);
    }

    /**
     * Whenever the audio is turned back on again, it should play the default background music.
     */
    @Test
    public void afterChangeAudioSwitchedToEnabledTest() {
        cAudioPlayer.cAudioEnabled = true;
        cAudioPlayer.afterChange();
    }

    /**
     * Whenever the audio is turned back off again, it should play the default background music.
     */
    @Test
    public void afterChangeAudioSwitchedToDisabledTest() {
        cAudioPlayer.cAudioEnabled = false;
        cAudioPlayer.cLastPlayed = cMusicMock;

        cAudioPlayer.afterChange();

        Mockito.verify(cMusicMock).stop();
    }
}
