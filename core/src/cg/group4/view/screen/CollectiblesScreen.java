package cg.group4.view.screen;

import java.util.ArrayList;

import cg.group4.rewards.CollectibleDrawer;
import cg.group4.rewards.Collection;
import cg.group4.rewards.collectibles.Collectible;
import cg.group4.rewards.collectibles.collectible_sorters.CollectibleSorter;
import cg.group4.rewards.collectibles.collectible_sorters.SortByRarity;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen to be displayed when pressing the "Collection" button on the home screen.
 */
public class CollectiblesScreen extends ScreenLogic {
	
	/**
	 * cContentTable contains the collectibles of the collection.
	 * cContainer contains the scrollpane displaying the collectibles.
	 */
	protected Table cContentTable, cContainer;
	
	/**
	 * ScrollPane displaying the collectibles of the collection.
	 */
	protected ScrollPane cScrollPane;
	
	/**
	 * Collection of the player.
	 */
	protected Collection cCollection;
	
	/**
	 * Button that takes the player back to the home screen.
	 */
	protected TextButton cBackButton;
	
	/**
	 * Numbers of collectibles displayed without scrolling, and the amount of
	 * checkboxes in the menu.
	 */
	protected final int cItemsOnScreen = 10, cNumberOfCheckboxes = 1;
	
	/**
	 * Object that creates images for the collectibles.
	 */
	protected CollectibleDrawer cDrawer;
	
	/**
	 * Object that sorts the collectibles in the collection.
	 */
	protected CollectibleSorter cSorter;
	
	/**
	 * Checkbox that sets the CollectibleSorter to sorting by rarity.
	 */
	protected CheckBox cSortRarity;
	
	/**
	 * Creates a new CollectibleScreen.
	 * @param collection of the player.
	 */
	public CollectiblesScreen(final Collection collection) {
		cCollection = collection;
		cSorter = new SortByRarity();
	}

	@Override
	protected WidgetGroup createWidgetGroup() {
		final int screenWidth = Gdx.graphics.getWidth();
		final int screenHeight = Gdx.graphics.getHeight();
		cDrawer = new CollectibleDrawer();
		
		cContainer = new Table();
		cContainer.setFillParent(true);
		
		cBackButton = cGameSkin.generateDefaultMenuButton("Back");
		cBackButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                ScreenStore.getInstance().setScreen("Home");
            }
        });
		
		cContainer.add(cBackButton).expandX().height(screenHeight / cItemsOnScreen);
		
		cContentTable = new Table();
		cContentTable.setWidth(screenWidth);
		cScrollPane = new ScrollPane(cContentTable);
		cScrollPane.setForceScroll(false, true);
		
		cContainer.add(cSortRarity);
		cContainer.row();
		cContainer.add(cScrollPane).colspan(cNumberOfCheckboxes + 1);
		
		constructContents(screenWidth, screenHeight);
		
		return cContainer.debugAll();
	}

	@Override
	protected void rebuildWidgetGroup() {
		this.getWidgetGroup();
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		
		cBackButton.setStyle(cGameSkin.getDefaultTextButtonStyle());
		cContentTable.clear();
		constructContents(screenWidth, screenHeight);
		
	}

	@Override
	protected String setPreviousScreenName() {
		return "Home";
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Sorts and rebuilds all the collectibles in the collection. Called upon initialisation
	 * of the screen and on resizes.
	 * 
	 * @param screenWidth current width of the screen in pixels.
	 * @param screenHeight current height of the screen in pixels.
	 */
	protected void constructContents(final int screenWidth, final int screenHeight) {
		ArrayList<Collectible> sortedList = cSorter.sortCollectibles(cCollection.getCollection());
		
		for (Collectible c : sortedList) {
			cContentTable.row().height(screenHeight / cItemsOnScreen).width(screenWidth);
			cContentTable.add(cGameSkin.generateDefaultLabel(Double.toString(c.getRarity())));
		}
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Clears all the checkboxes.
	 */
	protected void clearCheckboxes() {
		cSortRarity.setChecked(false);
	}

}
