package cg.group4.view.screen;

import cg.group4.data_structures.mp_fishingboat.Coordinate;
import cg.group4.data_structures.mp_fishingboat.FishingBoatData;
import cg.group4.data_structures.mp_fishingboat.SmallFishData;
import cg.group4.game_logic.stroll.events.StrollEvent;

import cg.group4.game_logic.stroll.events.mp_fishingboat.SmallFish;
import cg.group4.game_logic.stroll.events.multiplayer.Boat;
import cg.group4.game_logic.stroll.events.multiplayer.Crane;
import cg.group4.game_logic.stroll.events.multiplayer.CraneHitbox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import java.util.HashMap;

public class CraneFishingScreen extends EventScreen {

    protected Stack cBoatStack;
    protected Boat cBoat;
    protected Crane cCrane;
    protected CraneHitbox cCraneHitBox;
    protected HashMap<Integer, SmallFish> fishList;

    protected float devSize = 1440f;

    protected int boatSize = 256;

    protected int hitBoxSize = 16;

    protected float scalar;

    protected int maxWidth, maxHeight;
	
	protected WidgetGroup cContainer;
	
	public CraneFishingScreen(final StrollEvent eventLogic) {
		super(eventLogic);
        cContainer = new WidgetGroup();
        maxWidth = Gdx.graphics.getWidth();
        maxHeight = Gdx.graphics.getHeight();
        scalar = maxWidth > maxHeight ? maxHeight / devSize : maxWidth / devSize;
    }

    @Override
    void onEventChange(Object updatedData) {
        FishingBoatData boatData = (FishingBoatData) updatedData;
        updateCraneRotation(boatData.getcCraneRotation());
        updateBoatPosition(boatData.getcBoatCoordinate());
        setHitBoxPosition(boatData.getcCraneRotation());
        moveFishes(boatData.getcSmallFishCoordinates());
    }

    protected void moveFishes(HashMap<Integer, SmallFishData> fishCoordinates) {
        for(int key : fishCoordinates.keySet()) {
            Coordinate coordinate = fishCoordinates.get(key).getPosition();
            fishList.get(key).setPosition(coordinate.getX() * maxWidth, coordinate.getY() * maxHeight);
        }
    }

    protected void updateBoatPosition(Coordinate coordinate) {
        cBoatStack.setPosition(coordinate.getX() * maxWidth, coordinate.getY() * maxHeight);
    }

    protected void updateCraneRotation(double angle) {
        cCrane.setRotation((float)Math.toDegrees(angle));
    }

    protected void setHitBoxPosition(double angle) {
        float radius = cBoatStack.getHeight() / 2 - cCraneHitBox.getHeight() / 2;

        float xCoordCenter = cBoatStack.getX() + radius;
        float yCoordCenter = cBoatStack.getY() + radius;

        float xPosition = (float)Math.cos(angle) * radius;
        float yPosition = (float)Math.sin(angle) * radius;

        cCraneHitBox.setPosition(xCoordCenter + xPosition, yCoordCenter + yPosition);
    }

    @Override
	protected WidgetGroup createWidgetGroup() {
        cBoatStack = new Stack();

        System.out.println(maxHeight);
        System.out.println(maxWidth);
        System.out.println(scalar);

        cBoatStack.setPosition(0.3f * maxWidth, 0.3f * maxHeight);
        cBoatStack.setSize(boatSize * scalar, boatSize * scalar);

        cCraneHitBox = new CraneHitbox(new Texture(Gdx.files.internal("images/HitBox.png")));

        cCraneHitBox.setSize(hitBoxSize * scalar, hitBoxSize * scalar);

        setHitBoxPosition(0);

        cBoat = new Boat();
        cCrane = new Crane();

        cBoatStack.add(cBoat);
        cBoatStack.add(cCrane);
        cCrane.setOrigin(cBoatStack.getWidth() /2, cBoatStack.getHeight() / 2);

        cContainer.addActor(cBoatStack);
        cContainer.addActor(cCraneHitBox);

        fishList = generateFishes();

		return cContainer;
	}

    protected HashMap<Integer, SmallFish> generateFishes() {
        HashMap<Integer, SmallFish> fishes = new HashMap<Integer, SmallFish>();
        for(int i = 0; i < 10; i++) {
            SmallFish fish = new SmallFish(cAssets.getTexture("images/SmallFish.png"));
            fishes.put(i, fish);
            cContainer.addActor(fish);
        }
        return fishes;
    }

	@Override
	protected void rebuildWidgetGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String setPreviousScreenName() {
		// TODO Auto-generated method stub
		return null;
	}
}
