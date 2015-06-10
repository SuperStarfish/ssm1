package cg.group4.view;

import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.view.util.rewards.CollectibleDrawer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * .
 */
public class CollectibleRenderer {
    protected CollectibleDrawer cCollectibleDrawer;
    protected Image cCollectibleActor;

    public void render() {

    }

    public void setCollection(final Collectible collectible) {
        cCollectibleDrawer = new CollectibleDrawer();
        final Texture collectibleTexture = cCollectibleDrawer.drawCollectible(collectible);
        cCollectibleActor = new Image(collectibleTexture);
    }

    public Image getCollectibleActor() {
        return cCollectibleActor;
    }

    public void move(String direction) {
        switch (direction) {
            case "left":
                moveLeft();
                break;
            case "right":
                moveRight();
                break;
            case "down":
                moveDown();
                break;
            default:
                moveUp();
                break;
        }
    }

    private void moveUp() {
        cCollectibleActor.setPosition(cCollectibleActor.getImageX(), cCollectibleActor.getImageY() + 3);
    }

    private void moveDown() {

    }

    private void moveRight() {

    }

    private void moveLeft() {

    }

}
