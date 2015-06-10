package cg.group4.util.audio;

import cg.group4.data_structures.subscribe.Subject;
import cg.group4.view.screen_mechanics.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {

    /**
     * Class is a singleton, we only have one AudioPlayer which keeps track of the
     * settings and audio played.
     */
    protected static final AudioPlayer INSTANCE = new AudioPlayer();

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
     * The default music being played.
     */
    protected Music cDefaultMusic;

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
        cDefaultMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Summer Day.mp3"));
//        cDefaultMusic = Assets.getInstance().getMusic("music/Summer Day.mp3");
        cDefaultMusic.setLooping(true);
        setLastPlayed(cDefaultMusic);
        playAudio(cDefaultMusic);
    }

    /**
     * Returns the singleton instance.
     * @return The AudioPlayer singleton.
     */
    public static AudioPlayer getInstance() {
        return INSTANCE;
    }

    /**
     * Returns whether or not the audio is enabled.
     * @return Returns true if enabled, false if not.
     */
    public final boolean getAudioEnabled() {
        return cAudioEnabled;
    }

    /**
     * Returns the subject which notifies all observers when audio is enabled/disabled.
     * @return Subject
     */
    public final Subject getSubject() {
        return cAudioChangedSubject;
    }

    /**
     * Toggles whether or not the audio should be enabled.
     */
    public final void changeAudioEnabled() {
        cAudioEnabled = !cAudioEnabled;
        afterChange();
    }

    /**
     * Sets the last played Music.
     * @param music Music to be set as last played.
     */
    public final void setLastPlayed(Music music) {
        cLastPlayed = music;
    }

    /**
     * Gets run after the audio enabled gets toggled. Updates every observer, stops or plays a new track
     * according to the cAudioEnabled variable. Finally updates the value stored in the preferences.
     */
    protected final void afterChange() {
        cAudioChangedSubject.update();

        if(!cAudioEnabled){
            cLastPlayed.stop();
        } else {
            AudioPlayer.getInstance().playAudio(cDefaultMusic);
        }

        cPreferences.putBoolean("ENABLED",cAudioEnabled);
        cPreferences.flush();
    }

    /**
     * Plays a music file using the AudioPlayer. Stops the previous
     * @param music The music file to be played.
     */
    public final void playAudio(Music music) {
        if (cAudioEnabled){
            if (cLastPlayed.isPlaying()) {
                cLastPlayed.stop();
            }
            music.play();
            if(!music.equals(cLastPlayed)){
                cLastPlayed = music;
            }
        }
    }

    /**
     * Plays a sound file using the AudioPlayer.
     * @param sound The sound file to be played
     */
    public final void playAudio(Sound sound) {
        if(cAudioEnabled){
            sound.play();
        }
    }
}
