package cg.group4.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Jurgen van Schagen
 */
public class Assets extends AssetManager {
    protected static Assets instance;

    public static Assets getInstance() {
        if(instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    public Assets() {
        loadTexture("images/default_landscape_background.jpg");
        loadTexture("images/default_portrait_background.jpg");
        loadTexture("images/wooden_sign.png");
        loadTexture("images/debugpixel.png");
        loadTexture("images/blackpixel.jpg");
        loadTexture("images/FishA.png");
        loadTexture("images/FishB.png");
        loadTexture("images/FishC.png");
        loadTexture("images/FishD.png");
        loadTexture("images/CheckBoxOff.png");
        loadTexture("images/CheckBoxOn.png");
    }

    public void loadTexture(String file) {
        load(file, Texture.class);
    }

    public Texture getTexture(String file) {
        if(isLoaded(file)) {
            return get(file, Texture.class);
        }
        return null;
    }
}
