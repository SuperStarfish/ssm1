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
        setAudioEnabled(cPreferences.getBoolean("ENABLED", true));
    }

    public static AudioPlayer getInstance(){
        return instance;
    }

    public void setAudioEnabled(boolean bool){
        cAudioEnabled = bool;
        cPreferences.putBoolean("ENABLED",cAudioEnabled);
        cAudioChangedSubject.notifyObservers();
    }

    public void playMusic(Music music){
    }

    public void playSound(Sound sound){
    }
}
