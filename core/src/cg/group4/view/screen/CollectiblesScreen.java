package cg.group4.view.screen;

import cg.group4.collection.Collection;
import cg.group4.collection.collectibles.Collectible;
import cg.group4.collection.collectibles.collectible_sorters.CollectibleSorter;
import cg.group4.collection.collectibles.collectible_sorters.SortByRarity;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import cg.group4.view.util.rewards.CollectibleDrawer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Screen to be displayed when pressing the "Collection" button on the home screen.
 */
public final class CollectiblesScreen extends ScreenLogic {
	
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
	protected final int cItemsOnScreen = 10, cNumberOfTopBarItems = 3;
	
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
	 * Selectbox that contains the groups that the user is currently in.
	 */
	protected SelectBox<String> cGroupsBox;
	
	/**
	 * Selectbox that contains all the possible sorting options.
	 */
	protected SelectBox<String> cSortBox;
	
	/**
	 * Creates a new CollectibleScreen.
	 * @param collection of the player.
	 */
	public CollectiblesScreen(final Collection collection) {
		cCollection = collection;
		cDrawer = new CollectibleDrawer();
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
				
		cContentTable = new Table();
		cContentTable.setWidth(screenWidth);
		cScrollPane = new ScrollPane(cContentTable);
		cScrollPane.setForceScroll(false, true);
		
		cSortBox = cGameSkin.generateDefaultSelectbox();
		String[] def = new String[2];
		def[0] = "Sort_Rarity";
		def[1] = "<Insert Sort Object>";
		cSortBox.setItems(def);
		cSortBox.addListener(new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				System.out.println("Selected Sorting: " + cSortBox.getSelected());
			}
		});
		
		cGroupsBox = cGameSkin.generateDefaultSelectbox();
		String[] abc = new String[3];
		abc[0] = "My Collection";
		abc[1] = "Group_1";
		abc[2] = "Group_2";
		cGroupsBox.setItems(abc);
		cGroupsBox.addListener(new ChangeListener() {
			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				System.out.println("Selected Collection: " + cGroupsBox.getSelected());
			}	
		});
		
		cContainer.row().height(screenHeight / cItemsOnScreen).width(screenWidth / cNumberOfTopBarItems).fill();
		
		cContainer.add(cBackButton).fill();
		cContainer.add(cSortBox).fill();
		cContainer.add(cGroupsBox).fill();
		cContainer.row();
		cContainer.add(cScrollPane).colspan(cNumberOfTopBarItems).fill();
		
		constructContents(screenWidth, screenHeight);
		
		return cContainer;
	}

	@Override
	protected void rebuildWidgetGroup() {
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
		ArrayList<Collectible> sortedList = cSorter.sortCollectibles(cCollection);
		DecimalFormat format = new DecimalFormat("#.00");
		
		for (Collectible c : sortedList) {
			cContentTable.row().height(screenHeight / cItemsOnScreen).width(screenWidth / 5);
			Image img = new Image(cDrawer.drawCollectible(c));
			cContentTable.add(img);
			cContentTable.add(cGameSkin.generateDefaultLabel(format.format(c.getRarity())));
			cContentTable.add(cGameSkin.generateDefaultLabel("DATE"));
			cContentTable.add(cGameSkin.generateDefaultLabel("OWNER"));
			cContentTable.add(cGameSkin.generateDefaultLabel("GROUP"));
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
