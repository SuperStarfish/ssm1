package cg.group4.util.audio;

import cg.group4.GdxTestRunner;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertNotNull;

@RunWith(GdxTestRunner.class)
public class AudioPlayerTest {

    AudioPlayer cAudioPlayer = AudioPlayer.getInstance();

    Music cMusicMock;

    Sound cSoundMock;

    @Before
    public void setUp() {
       cMusicMock = Mockito.mock(Music.class);
       cSoundMock = Mockito.mock(Sound.class);
    }

    @Test
    public void getInstanceNotNull() {
        assertNotNull(cAudioPlayer);
    }

    @Test
    public void playAudioSoundTest()  {
        cAudioPlayer.cAudioEnabled = true;
        cAudioPlayer.playAudio(cSoundMock);
        Mockito.verify(cSoundMock).play();
    }
}
