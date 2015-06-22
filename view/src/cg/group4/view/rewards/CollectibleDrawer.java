package cg.group4.view.rewards;

import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.view.screen_mechanics.Assets;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Class that creates images from collectibles and returns these created images.
 */
public final class CollectibleDrawer {

    /**
     * Utility class.
     */
    protected CollectibleDrawer() { }

    /**
     * Returns an image of the given collectible.
     *
     * @param c Collectible that needs to be drawn.
     * @return Texture image with the collectible.
     */
    public static SpriteDrawable drawCollectible(final Collectible c) {
        Sprite sprite = new Sprite(Assets.getInstance().getTexture(c.getImagePath()));
        sprite.setColor(RewardUtil.generateColor(c.getHue()));

        return new SpriteDrawable(sprite);
    }
}
