package cg.group4.view.aquarium;

import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.data_structures.subscribe.Subject;
import cg.group4.view.rewards.CollectibleDrawer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Random;

/**
 * Responsible for displaying a single collectible.
 */
public class CollectibleRenderer {

    /**
     * Speed of the image.
     */
    protected final int cSpeed = 2;
    /**
     * Actor used for the movements of the collectible.
     */
    protected Image cCollectibleActor;
    /**
     * Current angle for the direction in which the fish moves.
     */
    protected int cCurrentAngle;
    /**
     * Id for comparison.
     */
    protected String cId;
    /**
     * Subject to send information of the owner and achievement date of the collectible.
     */
    protected Subject cCollectibleInformationSubject;

    /**
     * Initializes a collectible object.
     *
     * @param collectible collectible to create a render entity from
     */
    public CollectibleRenderer(final Collectible collectible) {
        cCollectibleInformationSubject = new Subject();
        initCollectibleEntity(collectible);
        randomInitialization();

        addClickableArea(collectible);

    }

    /**
     * Creates an actor from a collectible object.
     *
     * @param collectible collectible entity to initialize for display
     */
    public void initCollectibleEntity(final Collectible collectible) {
        setId(collectible.getOwnerId(), collectible.getDateAsString(), collectible.getRarity());

        cCollectibleActor = new Image(CollectibleDrawer.drawCollectible(collectible));
        final float scale = 0.3f;
        cCollectibleActor.setScale(scale);
        cCollectibleActor.layout();

        cCollectibleActor.setOrigin(
                cCollectibleActor.getImageWidth() / 2,
                cCollectibleActor.getImageHeight() / 2);
    }

    /**
     * Initializes the collectible actor to position with a random angle.
     * Flips the image if required (if upside down).
     */
    protected void randomInitialization() {
        final int circleAngle = 360;
        Random rnd = new Random();
        do {
            cCurrentAngle = normalizeAngle(rnd.nextInt(circleAngle));
        } while (!validateAngle(cCurrentAngle));
        if (cCurrentAngle > 0) {
            flipImageY();
        }

        cCollectibleActor.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    /**
     * Adds a clickable area which when clicked on will update the owner/date information labels.
     *
     * @param collectible Collectible
     */
    public void addClickableArea(final Collectible collectible) {
        cCollectibleActor.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        cCollectibleInformationSubject.update(collectible);
                    }
                });
    }

    /**
     * Sets the cId of the collectible renderer.
     * Used for comparison of this class' objects.
     *
     * @param owner  Owner cId of the collectible.
     * @param date   Date of the collectible.
     * @param rarity Rarity based on  form and hue.
     */
    public void setId(String owner, String date, double rarity) {
        final String sep = ",";
        this.cId = owner + sep
                + date + sep
                + Double.toString(rarity);
    }

    /**
     * Normalizes the input angle.
     *
     * @param angle input angle
     * @return normalized angle
     */
    protected int normalizeAngle(int angle) {
        final int halfCircle = 180;
        final int fullCircle = 360;

        if (angle > halfCircle) {
            angle = angle - fullCircle;
        }
        if (angle < -halfCircle) {
            angle = angle + fullCircle;
        }
        return angle;
    }

    /**
     * Assures the angle of a rotation so the flipping of the images goes correct.
     * This is to assure no fish are swimming backwards.
     *
     * @param angle angle to validate
     * @return angle between coordinates
     */
    protected boolean validateAngle(int angle) {
        final int min = 25; // 25 default
        final int max = 155; // 155 default

        angle = Math.abs(angle);
        return min < angle && angle < max;
    }

    /**
     * Flips the image on the Y axis.
     */
    public void flipImageY() {
        cCollectibleActor.setScaleY(-cCollectibleActor.getScaleY());
    }

    /**
     * Actor to be added to a stage.
     *
     * @return actor
     */
    public Actor getActor() {
        return cCollectibleActor;
    }

    /**
     * Method which is called every frame to render the collectible to the screen.
     */
    public void render() {
        move();
        boundaryCheck();
    }

    /**
     * Moves the fish entity to the destination.
     */
    public void move() {
        final int angle = 4;
        generateAngle(angle);
        moveToDestination(cSpeed);
    }

    /**
     * Verifies whether the actor to be inside the boundary box, which is the window frame.
     */
    public void boundaryCheck() {
        final int halfCircle = 180;

        // horizontal check
        if (getOriginX() < 0 || Gdx.graphics.getWidth() < getOriginX()) {
            flipImageY();
            cCurrentAngle *= -1;
        }

        // vertical check
        if (getOriginY() < 0 || getOriginY() > Gdx.graphics.getHeight()) {
            cCurrentAngle = normalizeAngle(halfCircle - cCurrentAngle);
        }
    }

    /**
     * Generates a new angle.
     * The new degree of the angle is based on the old angle plus a value -1..1 times the  movement speed.
     *
     * @param angle angle of rotation per render cycle
     */
    public void generateAngle(int angle) {
        int newAngle;
        do {
            // create a new angle based on the old angle + a few additional degree of rotation
            // Math.random() * 2 - 1f
            //   :: Math.random() -> generates a double between 0 and 1
            //   :: 2 - 1f -> makes sure the angle will be between -1 and 1 instead of just 0 and 1
            newAngle = cCurrentAngle + (int) ((Math.random() * 2 - 1f) * angle);

            // keep angles which make the fish turn out of the new generated angles
        } while (!validateAngle(newAngle));
        cCurrentAngle = newAngle;
    }

    /**
     * Randomly moves a fish in the direction of the {#code cCurrentAngle}.
     *
     * @param speed Amount of pixels to move per step
     */
    public void moveToDestination(float speed) {

        final float defaultRotate = 90f;
        cCollectibleActor.setRotation((float) cCurrentAngle + defaultRotate);

        cCollectibleActor.moveBy(
                (float) Math.sin(Math.toRadians(cCurrentAngle)) * speed,
                (float) Math.cos(Math.toRadians(cCurrentAngle)) * -speed);

    }

    /**
     * Determines the x-axis origin of the collectible image.
     *
     * @return x-axis origin
     */
    private float getOriginX() {
        return (cCollectibleActor.getX() + cCollectibleActor.getOriginX());
    }

    /**
     * Determines the y-axis origin of the collectible image.
     *
     * @return y-axis origin
     */
    private float getOriginY() {
        return (cCollectibleActor.getY() + cCollectibleActor.getOriginY());
    }

    @Override
    public int hashCode() {
        return cId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollectibleRenderer)) {
            return false;
        }

        CollectibleRenderer that = (CollectibleRenderer) o;

        return cId.equals(that.cId);

    }

    /**
     * Returns the subject on collectible information.
     *
     * @return collectible information.
     */
    public Subject getSubject() {
        return cCollectibleInformationSubject;
    }
}
