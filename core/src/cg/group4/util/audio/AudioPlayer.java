package cg.group4.util.audio;

import cg.group4.util.subscribe.Subject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {

    protected static final AudioPlayer instance = new AudioPlayer();

    protected Subject cAudioChangedSubject;

    protected Preferences cPreferences;

    protected boolean cAudioEnabled;

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

    public void changeAudioEnabled() {
        cAudioEnabled = !cAudioEnabled;
        cPreferences.putBoolean("ENABLED",cAudioEnabled);
        cAudioChangedSubject.notifyObservers();
        cPreferences.flush();
    }

    public void changeAudioEnabled(boolean bool) {
        cAudioEnabled = bool;
        cPreferences.putBoolean("ENABLED",cAudioEnabled);
        cAudioChangedSubject.notifyObservers();
        cPreferences.flush();
    }

    public void playAudio(Music music) {
        if(cAudioEnabled){
            music.play();
        }
    }

    public void playAudio(Sound sound) {
        if(cAudioEnabled){
            sound.play();
        }
    }
}
