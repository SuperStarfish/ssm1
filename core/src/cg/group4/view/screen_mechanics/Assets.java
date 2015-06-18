package cg.group4.view.screen_mechanics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

/**
 * Asynchronously loads all the assets that are needed and contains helper method to quickly load and get these assets.
 * @author Jurgen van Schagen
 */
public class Assets extends AssetManager {
    /**
     * Singleton instance of the Assets.
     */
    protected static Assets instance;

    /**
     * Getter for the Singleton instance.
     * @return The Assets instance.
     */
    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    /**
     * Forces the load of all the textures.
     */
    public Assets() {
        loadTexture("images/default_landscape_background.jpg");
        loadTexture("images/default_portrait_background.jpg");
        loadTexture("images/wooden_sign_gray.png");
        loadTexture("images/wooden_sign.png");
        loadTexture("images/debugpixel.png");
        loadTexture("images/blackpixel.jpg");
        loadTexture("images/SmallFish.png");
        loadTexture("images/FishA.png");
        loadTexture("images/FishB.png");
        loadTexture("images/FishC.png");
        loadTexture("images/FishD.png");
        loadTexture("images/Boat.png");
        loadTexture("images/Crane.png");
        loadTexture("images/CheckBoxOff.png");
        loadTexture("images/CheckBoxOn.png");
        loadMusic("music/Summer Day.mp3");

    }

    /**
     * Method that loads the Texture found at the given location.
     * @param file The location where the Texture can be found.
     */
    public void loadTexture(final String file) {
        load(file, Texture.class);
    }

    /**
     * Method that returns the Texture that has been loaded.
     * @param file The location of the Texture.
     * @return A Texture instance belonging to that texture.
     */
    public Texture getTexture(final String file) {
        if (isLoaded(file)) {
            return get(file, Texture.class);
        } else {
            return null;
        }
    }

    /**
     * Method that loads the Music found at the given location.
     * @param file The location where the Music can be found.
     */
    public void loadMusic(final String file) { load(file, Music.class); }

    /**
     * Method that returns the Music that has been loaded.
     * @param file The location of the Music.
     * @return A Music instance belonging to that texture.
     */
    public Music getMusic(final String file) {
        if (isLoaded(file)) {
            return get(file, Music.class);
        } else {
            return null;
        }
    }
}
