package cg.group4.view;

import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.display_logic.DisplaySettings;
import cg.group4.view.util.rewards.CollectibleDrawer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Random;

// TODO always move forward (1 move method)
// probability to turn a random degree between min max
//

/**
 * .
 */
public class CollectibleRenderer {
    protected Image cCollectibleActor;
    protected Stage cStage;

    protected int cCurrentAngle;

    public CollectibleRenderer(final Collectible collectible) {
        cStage = new Stage();
        initCollectibleEntity(collectible);
        randomInitialization();
        cStage.addActor(cCollectibleActor);
    }

    /**
     *
     */
    public void render() {
        move();
        boundaryCheck();
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
        cCollectibleActor.layout();

        cCollectibleActor.setOrigin(
                cCollectibleActor.getImageWidth()/2,
                cCollectibleActor.getImageHeight()/2);
    }

    protected void randomInitialization() {
        Random rnd = new Random();
        do {
            cCurrentAngle = normalizeAngle(rnd.nextInt(360));
        } while(!validateAngle(cCurrentAngle));
        if(cCurrentAngle>0){
            flipImageY();
        }

        cCollectibleActor.setPosition(0,0);
    }

    public void flipImageY() {
        cCollectibleActor.setScaleY(-cCollectibleActor.getScaleY());
    }

    public void boundaryCheck() {
        if (getOriginX() < 0) {
            flipImageY();
            cCurrentAngle *= -1;
        } else if (getOriginX() > Gdx.graphics.getWidth()) {
            flipImageY();
            cCurrentAngle *= -1;
        }

        if (getOriginY() > Gdx.graphics.getHeight()) {
            cCurrentAngle = normalizeAngle(180 - cCurrentAngle);
        } else if (getOriginY() < 0) {
            cCurrentAngle = normalizeAngle(180 - cCurrentAngle);
        }
    }

    private float getOriginX() {
        return (cCollectibleActor.getX() + cCollectibleActor.getOriginX());
    }

    private float getOriginY() {
        return (cCollectibleActor.getY()+ cCollectibleActor.getOriginY());
    }

    /**
     * Moves the fish entity to the destination.
     */
    public void move() {
        generateAngle(4);
        moveToDestination(2);
    }

    /**
     * When the destination is reached, set a new destination;
     *
     */
    public void generateAngle(int movement) {
        int newAngle;
        do {
            newAngle = cCurrentAngle + (int)((Math.random() * 2 - 1f) * movement);
        } while (!validateAngle(newAngle));
        cCurrentAngle = newAngle;
    }

    protected boolean validateAngle(int angle){
        angle = Math.abs(angle);
        return 25 < angle && angle < 155;
    }

    protected int normalizeAngle(int angle){
        if(angle > 180) {
            angle = angle - 360;
        }
        if(angle < -180) {
            angle = angle + 360;
        }
        return angle;
    }

    public void moveToDestination(float speed) {

        cCollectibleActor.setRotation((float) cCurrentAngle + 90);

        cCollectibleActor.moveBy(
                (float) Math.sin(Math.toRadians(cCurrentAngle)) * speed,
                (float) Math.cos(Math.toRadians(cCurrentAngle)) * -speed);

   }

}
