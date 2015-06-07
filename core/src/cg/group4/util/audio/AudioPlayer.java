package cg.group4.util.audio;

import cg.group4.util.subscribe.Subject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class AudioPlayer {

    /**
     * Class is a singleton, we only have one AudioPlayer which keeps track of the
     * settings and audio played.
     */
    protected static final AudioPlayer instance = new AudioPlayer();

    protected Subject cAudioChangedSubject;

    protected Preferences cPreferences;

    protected boolean cAudioEnabled;

    protected Music cCurrentlyPlaying;

    public AudioPlayer() {
        cAudioChangedSubject = new Subject();
        cPreferences = Gdx.app.getPreferences("AUDIO");
        changeAudioEnabled(cPreferences.getBoolean("ENABLED", true));
    }

    public static AudioPlayer getInstance() {
        return instance;
    }

    public boolean getAudioEnabled() {
        return cAudioEnabled;
    }

    public Subject getSubject() {
        return cAudioChangedSubject;
    }

    public void changeAudioEnabled() {
        cAudioEnabled = !cAudioEnabled;
        afterChange();
    }

    public void changeAudioEnabled(boolean bool) {
        cAudioEnabled = bool;
        afterChange();
    }

    protected void afterChange() {
        cPreferences.putBoolean("ENABLED",cAudioEnabled);
        cAudioChangedSubject.notifyObservers();
        cPreferences.flush();

        if(!cAudioEnabled){
            cCurrentlyPlaying.stop();
        }
    }

    /**
     * Plays a music file using the AudioPlayer. Adds it to an arraylist so it can be stopped,
     * when music needs to be disabled again.
     * @param music The music to be played.
     */
    public void playAudio(Music music) {
        if(cAudioEnabled){
            music.play();
            cCurrentlyPlaying = music;
        }
    }

    public void playAudio(Sound sound) {
        if(cAudioEnabled){
            sound.play();
        }
    }
}
