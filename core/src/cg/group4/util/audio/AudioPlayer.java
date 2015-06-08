package cg.group4.util.audio;

import cg.group4.game_logic.StandUp;
import cg.group4.util.subscribe.Subject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {

    /**
     * Class is a singleton, we only have one AudioPlayer which keeps track of the
     * settings and audio played.
     */
    protected static final AudioPlayer instance = new AudioPlayer();

    /**
     * Subject which notifies everyone when the audio is enabled or disabled.
     */
    protected Subject cAudioChangedSubject;

    /**
     * Preferences used to store settings.
     */
    protected Preferences cPreferences;

    /**
     * True if audio is enabled, false if not.
     */
    protected boolean cAudioEnabled;

    /**
     * The music that is last played.
     */
    protected Music cLastPlayed;

    /**
     * Constructor, initialises the subject and fetches all the data needed from the Preferences.
     */
    public AudioPlayer() {
        cAudioChangedSubject = new Subject();
        cPreferences = Gdx.app.getPreferences("AUDIO");
        cAudioEnabled = cPreferences.getBoolean("ENABLED", true);
    }

    /**
     * Returns the singleton instance.
     * @return The AudioPlayer singleton.
     */
    public static AudioPlayer getInstance() {
        return instance;
    }

    /**
     * Returns whether or not the audio is enabled.
     * @return Returns true if enabled, false if not.
     */
    public boolean getAudioEnabled() {
        return cAudioEnabled;
    }

    /**
     * Returns the subject which notifies all observers when audio is enabled/disabled.
     * @return Subject
     */
    public Subject getSubject() {
        return cAudioChangedSubject;
    }

    /**
     * Toggles whether or not the audio should be enabled.
     */
    public void changeAudioEnabled() {
        cAudioEnabled = !cAudioEnabled;
        afterChange();
    }

    public void setLastPlayed(Music music) {
        cLastPlayed = music;
    }

    /**
     * Gets ran after the audio enabled gets toggled. Updates every observer, stops or plays a new track
     * according to the cAudioEnabled variable. Finally updates the value stored in the preferences.
     */
    protected void afterChange() {
        cAudioChangedSubject.update();

        if(!cAudioEnabled){
            cLastPlayed.stop();
        } else {
            StandUp.getInstance().getBackGroundMusic().play();
        }

        cPreferences.putBoolean("ENABLED",cAudioEnabled);
        cPreferences.flush();
    }

    /**
     * Plays a music file using the AudioPlayer. Stops the previous
     * @param music The music file to be played.
     */
    public void playAudio(Music music) {
        if (cAudioEnabled){
            if (cLastPlayed.isPlaying()) {
                cLastPlayed.stop();
            }
            music.play();
            cLastPlayed = music;
        }
    }

    /**
     * Plays a sound file using the AudioPlayer.
     * @param sound The sound file to be played
     */
    public void playAudio(Sound sound) {
        if(cAudioEnabled){
            sound.play();
        }
    }
}
