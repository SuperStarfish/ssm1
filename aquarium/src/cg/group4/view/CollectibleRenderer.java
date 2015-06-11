package cg.group4.view;

import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.display_logic.DisplaySettings;
import cg.group4.view.util.rewards.CollectibleDrawer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

// TODO always move forward (1 move method)
// probability to turn a random degree between min max
//

/**
 * .
 */
public class CollectibleRenderer {
    protected Image cCollectibleActor;
    protected Stage cStage;

    protected double cCurrentAngle;

    public CollectibleRenderer(final Collectible collectible) {
        cStage = new Stage();
        initCollectibleEntity(collectible);

        cStage.addActor(cCollectibleActor);
        cCurrentAngle = -90;
    }

    /**
     *
     */
    public void render() {
        move();
        cStage.act();
        cStage.draw();
    }

    /**
     *
     * @param collectible
     */
    public void initCollectibleEntity(final Collectible collectible) {
        final CollectibleDrawer cCollectibleDrawer = new CollectibleDrawer();
        final Texture collectibleTexture = cCollectibleDrawer.drawCollectible(collectible);
        cCollectibleActor = new Image(collectibleTexture);
        cCollectibleActor.setScale(0.3f);

        cCollectibleActor.setPosition(DisplaySettings.screenMaxX/2 - cCollectibleActor.getOriginX(), DisplaySettings.screenMaxY/2 - cCollectibleActor.getOriginY());
    }

    public void flipImageX() {
        cCollectibleActor.setScaleX(-cCollectibleActor.getScaleX());
    }

    public void flipImageY() {
        cCollectibleActor.setScaleY(-cCollectibleActor.getScaleY());
    }

    /**
     * Moves the fish entity to the destination.
     */
    public void move() {
        generateAngle();
        moveToDestination(2);
    }

    /**
     * When the destination is reached, set a new destination;
     *
     */
    public void generateAngle() {
        cCurrentAngle += (Math.random()*2-1f)*5;
    }




    public void moveToDestination(float speed) {
        cCollectibleActor.setOrigin(
                cCollectibleActor.getImageWidth()/2,
                cCollectibleActor.getImageHeight()/2);

        cCollectibleActor.setRotation((float) cCurrentAngle + 90);

        cCollectibleActor.moveBy(
                (float) Math.sin(Math.toRadians(cCurrentAngle)) * speed,
                (float) Math.cos(Math.toRadians(cCurrentAngle)) * -speed);

   }

}
