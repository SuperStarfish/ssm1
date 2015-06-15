package cg.group4.view.screen;

import cg.group4.game_logic.stroll.events.multiplayer.Boat;
import cg.group4.game_logic.stroll.events.multiplayer.Crane;
import cg.group4.game_logic.stroll.events.multiplayer.CraneHitbox;
import cg.group4.game_logic.stroll.events.multiplayer.SmallFish;
import cg.group4.view.screen_mechanics.ScreenLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import java.util.ArrayList;

public class CraneFishingScreen extends ScreenLogic {

    protected Stack cBoatStack;
    protected Boat cBoat;
    protected Crane cCrane;
    protected CraneHitbox cCraneHitBox;
    protected ArrayList<SmallFish> fishList;

	protected WidgetGroup cContainer;

	public CraneFishingScreen() {
		cContainer = new WidgetGroup();
	}

	@Override
	protected WidgetGroup createWidgetGroup() {
        cBoatStack = new Stack();

        cBoatStack.setPosition(240, 440);
        cBoatStack.setSize(128, 128);

        cCraneHitBox = new CraneHitbox(new Texture(Gdx.files.internal("images/HitBox.png")));

        cCraneHitBox.setSize(16,16);

        setHitBoxPosition(cBoatStack, 0);

        cBoat = new Boat();
        cCrane = new Crane();

        cBoatStack.add(cBoat);
        cBoatStack.add(cCrane);
        cCrane.setOrigin(cBoatStack.getWidth() /2, cBoatStack.getHeight() / 2);
        cContainer.addActor(cBoatStack);
		return cContainer;
	}

    protected void setHitBoxPosition(Stack boat, double angle) {
        angle = angle % (Math.PI * 2);

        float radius = boat.getHeight() / 2;
        float hitBoxRadius = cCraneHitBox.getWidth() / 2;
        float xCoordCenter = boat.getX() + radius;
        float yCoordCenter = boat.getY() + radius;

        float xPosition = (float)Math.cos(angle) * radius;
        float yPosition = (float)Math.sin(angle) * radius;

        cCraneHitBox.setPosition(xCoordCenter + xPosition - hitBoxRadius, yCoordCenter + yPosition - hitBoxRadius);
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
