package cg.group4.view;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Assets to be loaded for the aquarium display.
 */
public class AquariumAssets extends AssetManager {

    public AquariumAssets() {
        super();
        loadTexture("images/debugpixel.png");
        loadAssets();
    }

    public void loadAssets() {
        loadTexture("images/FishA.png");
        loadTexture("images/FishB.png");
        loadTexture("images/FishC.png");
    }

    public void loadTexture(String url) {
        this.load(url, Texture.class);
    }

    public boolean verifyPlaceholderIsLoaded() {
        return (this.isLoaded("images/debugpixel.png"));
    }

    public Texture getTexture(String url) {
        if (!this.isLoaded(url) && verifyPlaceholderIsLoaded()) {
            return this.get("images/debugpixel.png", Texture.class);
        } else if (!verifyPlaceholderIsLoaded()) {
            return null;
        } else {
            return this.get(url);
        }
    }
}
