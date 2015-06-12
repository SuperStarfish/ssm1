package cg.group4.view.util.rewards;

import cg.group4.data_structures.collection.collectibles.Collectible;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Class that creates images from collectibles and returns these created images.
 *
 * @author Jean de Leeuw
 */
public class CollectibleDrawer {

    /**
     * R, G and B value of the replacement colour.
     */
    protected final float cReplaceColourComponent = 120f / 255f;
    /**
     * Colour in the image that needs to be replaced.
     */
    protected Color cReplacementColour = new Color(
            cReplaceColourComponent,
            cReplaceColourComponent,
            cReplaceColourComponent,
            1f);

    /**
     * Returns an image of the given collectible.
     *
     * @param c Collectible that needs to be drawn.
     * @return Texture image with the collectible.
     */
    public final Texture drawCollectible(final Collectible c) {
        Pixmap pixImage = new Pixmap(Gdx.files.internal(c.getImagePath()));
        pixImage.setColor(RewardUtil.generateColor(c.getHue()));
        replaceColours(pixImage);

        return new Texture(pixImage);
    }

    /**
     * Helper method that should not be called outside of this class.
     * Method that replaces pixels with a certain colour with another colour.
     *
     * @param pix Pixmap containing the image that needs colour replacement.
     */
    protected final void replaceColours(final Pixmap pix) {

        for (int y = 0; y < pix.getHeight(); y++) {
            for (int x = 0; x < pix.getWidth(); x++) {

                Color currentColour = new Color(pix.getPixel(x, y));

                if (currentColour.equals(cReplacementColour)) {
                    pix.drawPixel(x, y);
                }
            }
        }
    }
}
