package cg.group4.view.screen.mp_fishingboat;

import cg.group4.data_structures.mp_fishingboat.BoatData;
import cg.group4.data_structures.mp_fishingboat.Coordinate;
import cg.group4.data_structures.mp_fishingboat.FishingBoatEventData;
import cg.group4.data_structures.mp_fishingboat.SmallFishData;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.game_logic.stroll.events.mp_fishingboat.SmallFish;
import cg.group4.view.screen.EventScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import java.util.HashMap;

/**
 * Screen that shows the CraneFishing Event state.
 */
public class CraneFishingScreen extends EventScreen {
    /**
     * The Boat and Crane wrapper that also determines the location.
     */
    protected Stack cBoatStack;
    /**
     * The Boat sprite.
     */
    protected Boat cBoat;
    /**
     * The Crane sprite.
     */
    protected Crane cCrane;
    /**
     * The HitBox Sprite.
     */
    protected CraneHitbox cCraneHitBox;
    /**
     * All the fishes that are seen on the screen.
     */
    protected HashMap<Integer, SmallFish> cFishList;
    /**
     * The development size to scale against.
     */
    protected final float cDevSize = 1440f;
    /**
     * The size of the BoatSprite in development.
     */
    protected final int cBoatSize = 256;
    /**
     * The size of the HitBox in development.
     */
    protected final int cHitBoxSize = 16;
    /**
     * Where the boat starts.
     */
    protected final float cStartLocation = 0.3f;
    /**
     * Scalar used to scale the size of the UI against the defaults.
     */
    protected float cScalar = 1f;
    /**
     * The size of a fish on the screen.
     */
    protected final int cSmallFishSize = 64;
    /**
     * The number of fish being spawned.
     */
    protected final int cNumberOfFish = 10;
    /**
     * The width and height used to map the 0-1 coordinate of the event logic to actual pixels.
     */
    protected int cMaxWidth, cMaxHeight;
    /**
     * Container for all the actors.
     */
	protected WidgetGroup cContainer;

    /**
     * Creates a new CraneFishingScreen.
     * @param eventLogic The FishingBoatEvent Event.
     */
	public CraneFishingScreen(final StrollEvent eventLogic) {
		super(eventLogic);
    }

    @Override
    protected void init() {
        cContainer = new WidgetGroup();
    }

    @Override
    protected void onEventChange(Object updatedData) {
        if (updatedData instanceof Coordinate) {
            setHitBoxPosition((Coordinate) updatedData);
        } else {
            FishingBoatEventData boatData = (FishingBoatEventData) updatedData;
            updateCraneRotation(boatData.getcCraneRotation());
            updateBoatPosition(boatData.getcBoatData());
            setHitBoxPosition(boatData.getcCraneRotation());
            moveFishes(boatData.getcSmallFishCoordinates());
        }
    }

    /**
     * Moves the fishes a step towards their destination.
     * @param fishCoordinates The data containing current position.
     */
    protected void moveFishes(HashMap<Integer, SmallFishData> fishCoordinates) {
        for (int key : fishCoordinates.keySet()) {
            SmallFishData fishdata = fishCoordinates.get(key);
            Coordinate coordinate = fishdata.getPosition();

            SmallFish fish = cFishList.get(key);
            if (coordinate == null) {
                cContainer.removeActor(fish);
                cFishList.remove(key);
            } else {
                Coordinate destination = fishdata.getDestination();
                double angle = Math.atan2(
                        destination.getY() - coordinate.getY(), destination.getX() - coordinate.getX());
                fish.setRotation((float) Math.toDegrees(angle));
                fish.setPosition(coordinate.getX() * cMaxWidth, coordinate.getY() * cMaxHeight);
            }
        }
    }

    /**
     * Moves the boat to the new location and sets its rotation.
     * @param boatData The new location and rotation to draw at.
     */
    protected void updateBoatPosition(BoatData boatData) {
        Coordinate coordinate = boatData.getcLocation();
        cBoatStack.setPosition(coordinate.getX() * cMaxWidth, coordinate.getY() * cMaxHeight);
        cBoat.setRotation((float) Math.toDegrees(boatData.getcRotation()));
    }

    /**
     * Sets the rotation for the crane to the provided rotation.
     * @param angle The rotation to use.
     */
    protected void updateCraneRotation(double angle) {
        cCrane.setRotation((float) Math.toDegrees(angle));
    }

    /**
     * Sets the hitbox in proper position using the knowledge of the position and rotation.
     * @param angle The angle of the crane.
     */
    protected void setHitBoxPosition(double angle) {
        float radius = cBoatStack.getHeight() / 2;
        float hitboxRadius = cCraneHitBox.getHeight() / 2;

        float xCoordCenter = cBoatStack.getX() + radius;
        float yCoordCenter = cBoatStack.getY() + radius;

        float xPosition = (float) Math.cos(angle) * (radius - hitboxRadius);
        float yPosition = (float) Math.sin(angle) * (radius - hitboxRadius);

        cCraneHitBox.setPosition(xCoordCenter + xPosition - hitboxRadius, yCoordCenter + yPosition - hitboxRadius);
    }

    /**
     * Sets the hitbox according to the 0-1 mapping (if this is given by the host).
     * @param coordinate The location of the HitBox.
     */
    protected void setHitBoxPosition(Coordinate coordinate) {
        System.out.println(coordinate);
        cCraneHitBox.setPosition(coordinate.getX() * cMaxWidth, coordinate.getY() * cMaxHeight);
    }

    @Override
	protected WidgetGroup createWidgetGroup() {
        setUISize();

        cBoatStack = new Stack();

        cBoatStack.setPosition(cStartLocation * cMaxWidth, cStartLocation * cMaxHeight);
        cBoatStack.setSize(cBoatSize * cScalar, cBoatSize * cScalar);

        cCraneHitBox = new CraneHitbox(new Texture(Gdx.files.internal("images/HitBox.png")));

        cCraneHitBox.setSize(cHitBoxSize * cScalar, cHitBoxSize * cScalar);

        setHitBoxPosition(0);

        cBoat = new Boat(cBoatStack);
        cCrane = new Crane(cBoatStack);

        cBoatStack.add(cBoat);
        cBoatStack.add(cCrane);

        cFishList = generateFishes();

        cContainer.addActor(cBoatStack);

        cContainer.addActor(cCraneHitBox);

		return cContainer;
	}

    /**
     * Generates a set of fishes that are used to draw the location of the fishes in the event.
     * @return The set of fishes that are drawn.
     */
    protected HashMap<Integer, SmallFish> generateFishes() {
        HashMap<Integer, SmallFish> fishes = new HashMap<>();
        for (int i = 0; i < cNumberOfFish; i++) {
            SmallFish fish = new SmallFish(cAssets.getTexture("images/SmallFish.png"));
            fish.setSize(cSmallFishSize * cScalar, cSmallFishSize * cScalar);
            fishes.put(i, fish);
            cContainer.addActor(fish);
        }
        return fishes;
    }

	@Override
	protected void rebuildWidgetGroup() {
        setUISize();
	}

    /**
     * Used to properly map the 0-1 scale to the screen size.
     */
    protected void setUISize() {
        cMaxWidth = Gdx.graphics.getWidth();
        cMaxHeight = Gdx.graphics.getHeight();
        if (cMaxWidth > cMaxHeight) {
            cScalar = cMaxHeight / cDevSize;
        } else {
            cScalar = cMaxWidth / cDevSize;
        }
    }
}
